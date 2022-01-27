package com.xxx.rent.api.filter;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.xxx.rent.api.util.Result;
import com.xxx.rent.api.util.ThrowableUtil;
import com.xxx.rent.api.exception.BizException;
import com.xxx.rent.api.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

@Component
public class ApiFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(ApiFilter.class);
    private static final Cache<String, String> CACHE = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).recordStats().build();
    @Resource
    private ApiProperties properties;
    /**
     * 签名过期时间
     */
    private static final int SIGN_EXPIRE_MINUTE = 10;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();
        if (!properties.getEnable() || ArrayUtil.contains(properties.getWhite(), uri)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        RequestWrapper wrapper = new RequestWrapper(request);
        TreeMap<String, Object> params = SignUtil.getParams(wrapper);
        String sign = wrapper.getParameter(SignUtil.SIGN_FIELD);
        if (ObjectUtils.isEmpty(sign)) {
            errorResp(servletResponse, 1001, "缺少签名");
            return;
        }
        // 检查随机字符
        if (ObjectUtils.isEmpty(params.get(SignUtil.NONCE_FIELD))) {
            errorResp(servletResponse, 1001, "缺少随机字符");
            return;
        }

//        String cacheKey = "API:SIGN:" + params.get(SignUtil.NONCE_FIELD);
//        if (null != CACHE.getIfPresent(cacheKey)) {
//            errorResp(servletResponse, 1001, "重复请求");
//            return;
//        } else {
//            CACHE.put(cacheKey, "1");
//        }

        // 检查时间戳
        Date signTime = new Date(Long.valueOf(params.get(SignUtil.TIMESTAMP_FIELD).toString()));
        long interval = DateUtil.between(signTime, DateUtil.date(), DateUnit.MINUTE, true);

        if (interval >= SIGN_EXPIRE_MINUTE) {
            logger.error("签名过期,{}", interval);
            errorResp(servletResponse, 1001, "签名过期");
            return;
        }

        String body = SignUtil.postParams(wrapper);

        if (SignUtil.checkSignature(properties.getSecret(), params, body, sign)) {
            filterChain.doFilter(wrapper, servletResponse);
        } else {
            errorResp(servletResponse, 1001, "签名校验失败");
            return;
        }
    }

    /**
     * 响应错误信息
     *
     * @param response
     * @param errCode
     * @param errMsg
     */
    private void errorResp(ServletResponse response, Integer errCode, String errMsg) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter pw = null;
        try {
            Result result = new Result(false, errCode, errMsg);
            pw = response.getWriter();
            pw.write(JSON.toJSONString(result));
            pw.flush();
        } catch (IOException e) {
            logger.error("服务异常,", e);
            throw new BizException("服务异常");
        } finally {
            if (null != pw) {
                pw.close();
            }
        }
    }

    class RequestWrapper extends HttpServletRequestWrapper {
        private byte[] body;

        public byte[] getBody() {
            return body;
        }

        public RequestWrapper(HttpServletRequest request) throws UnsupportedEncodingException {
            super(request);
            if ("POST".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod()) || "DELETE".equalsIgnoreCase(request.getMethod())) {
                String bodyString = getBodyString(request);
                body = ObjectUtils.isEmpty(bodyString) ? "".getBytes(StandardCharsets.UTF_8) : bodyString.getBytes("UTF-8");
            }
        }

        @Override
        public BufferedReader getReader() {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }

        @Override
        public ServletInputStream getInputStream() {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
            };
        }

        /**
         * 获取请求Body
         *
         * @param request
         * @return
         */
        private String getBodyString(ServletRequest request) {
            StringBuilder sb = new StringBuilder();
            InputStream inputStream = null;
            BufferedReader reader = null;
            try {
                inputStream = request.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("数据读取失败,", ThrowableUtil.getStackTrace(e));
                throw new BizException("数据读取失败");
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        logger.error("数据读取失败,", ThrowableUtil.getStackTrace(e));
                        throw new BizException("数据读取失败");
                    }
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        logger.error("数据读取失败,", ThrowableUtil.getStackTrace(e));
                        throw new BizException("数据读取失败");
                    }
                }
            }
            return sb.toString();
        }
    }
}
