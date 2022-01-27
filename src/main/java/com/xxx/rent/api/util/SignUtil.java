package com.xxx.rent.api.util;

import cn.hutool.http.ContentType;
import com.xxx.rent.api.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;
import org.springframework.util.ObjectUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

public class SignUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignUtil.class);
    public static final String TIMESTAMP_FIELD = "timestamp";
    public static final String NONCE_FIELD = "nonce";
    public static final String SIGN_FIELD = "sign";
    public static final String SECRET_FIELD = "secret";
    public static final String DEFAULT_SECRET = "9EDBqhpnWbYMPaL6";

    /**
     * 检验签名
     *
     * @param secret     密钥
     * @param params     请求参数
     * @param clientSign 客户端签名
     * @return
     */
    public static boolean checkSignature(String secret, TreeMap<String, Object> params, String body, String clientSign) {
        if (null == params.get(TIMESTAMP_FIELD) || null == params.get(NONCE_FIELD) || ObjectUtils.isEmpty(clientSign)) {
            return false;
        }

        // 校验传入的签名是否与服务端的签名一致
        String serverSign = signature(secret, params, body);
        if (clientSign.equals(serverSign)) {
            return true;
        } else {
            LOGGER.error("签名校验失败,客户端签名:{},服务端签名:{}", clientSign, serverSign);
            return false;
        }
    }

    /**
     * 对内容进行签名
     *
     * @param secret 密钥
     * @param params 参数
     * @return 签名结果
     */
    public static String signature(String secret, TreeMap<String, Object> params, String body) {
        // get参数
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (null != entry.getValue()) {
                if (first) {
                    sb.append(entry.getKey()).append("=").append(entry.getValue());
                    first = false;
                } else {
                    sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
                }
            }
        }

        // json参数
        if (!ObjectUtils.isEmpty(body)) {
            String base64 = Base64Utils.encodeToString(body.getBytes(StandardCharsets.UTF_8));
            sb.append(base64);
        }

        Mac hmac;
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        try {
            hmac = Mac.getInstance("HmacSHA256");
            hmac.init(secretKey);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("签名失败,", ThrowableUtil.getStackTrace(e));
            throw new BizException("签名失败");
        } catch (InvalidKeyException e) {
            LOGGER.error("签名失败,", ThrowableUtil.getStackTrace(e));
            throw new BizException("签名失败");
        }

        byte[] bytes = hmac.doFinal(sb.toString().getBytes(StandardCharsets.UTF_8));
        StringBuffer sign = new StringBuffer();
        for (byte item : bytes) {
            sign.append(Integer.toHexString((item & 0xFF) | 0x100), 1, 3);
        }

        LOGGER.debug("密钥:[{}],签名结果:[{}],签名参数:{}", secret, sign, sb);
        return sign.toString();
    }

    /**
     * 获取请求参数
     *
     * @param request
     * @return
     */
    public static TreeMap<String, Object> getParams(HttpServletRequest request) {
        TreeMap<String, Object> params = new TreeMap<>();

        // 获取url参数
        Map<String, String[]> map = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String key = entry.getKey();
            String[] value = entry.getValue();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < value.length; i++) {
                if (i == value.length - 1) {
                    sb.append(value[i]);
                } else {
                    sb.append(value[i]).append(",");
                }
            }
            params.put(key, sb.toString());
        }

        // 移除未参与签名的字段
        params.remove(SIGN_FIELD);
        return params;
    }

    /**
     * 获取请求中的json数据
     *
     * @param request
     * @return
     */
    public static String postParams(HttpServletRequest request) {
        // post请求且内容类型为json
        boolean allowMethod = request.getMethod().equals("POST") || request.getMethod().equals("PUT") || request.getMethod().equals("DELETE");
        boolean notEmpty = !ObjectUtils.isEmpty(request.getContentType()) && request.getContentType().indexOf(ContentType.JSON.getValue()) != -1;
        if (allowMethod && notEmpty) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            } catch (IOException e) {
                LOGGER.error("数据流读取失败,", ThrowableUtil.getStackTrace(e));
            }
            StringBuffer bs = new StringBuffer();
            String text;
            while (true) {
                try {
                    if ((text = reader.readLine()) == null) {
                        break;
                    }
                } catch (IOException e) {
                    LOGGER.error("数据流读取失败,", ThrowableUtil.getStackTrace(e));
                    throw new BizException("数据流读取失败");
                }
                bs.append(text);
            }

            return bs.toString();
        }
        return null;
    }
}
