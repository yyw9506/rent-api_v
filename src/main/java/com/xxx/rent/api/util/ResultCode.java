package com.xxx.rent.api.util;

public enum ResultCode {

    SUCCESS(0, "SUCCESS"), // 成功
    ERROR(1, "ERROR"), // 失败

    OK(200, "OK"), // 请求被成功处理，服务器会根据不同的请求方法返回结果

    NO_CONTENT(204, "NO_CONETNT"), // 该状态码表示服务器接收到的请求已经处理完毕，但是服务器不需要返回响应体

    PARTIAL_CONTENT(206, "PARTIAL_CONTENT"), // 该状态码表示客户端进行了范围请求，而服务器成功执行了这部分的GET请求。

    MOVIED_PERMANENTLY(301, "MOVIED_PERMANENTLY"), // 永久性重定向。

    FOUND(302, "FOUND"), // 临时性重定向。

    SEE_OTHER(303, "SEE_OTHER"), // 该状态码表示由于请求对应的资源存在另一个URI，应使用GET方法定向获取请求的资源

    NOT_MODIFIED(304, "NOT_MODIFIED"), // 该状态码表示客户端发送附带条件请求时，服务器端允许请求访问资源，但未满足条件的情况。

    TEMPORARY_REDIRECT(307, "TEMPORARY_REDIRECT"), // 临时重定向。

    // 表示该请求报文中存在语法错误
    BAD_REQUEST(400, "前端请求出错"),

    // 该状态码表示发送的请求需要有通过HTTP认证(Basic认证，Digest认证)的认证信息。
    UNAUTHORIZED(401, "未认证"),
    // 该状态码表示发送的请求需要有通过HTTP认证(Basic认证，Digest认证)的认证信息。
    UNLOGIN(4011, "未登录"),
    KITOUT(4012, "账户在其他地方登录"),
    TOKEN_EXPRE(4013, "登录过期，请重新登录！"),

    // 该状态码表明对请求资源的访问被服务器拒绝了。
    FORBIDDEN(403, "没有权限"),

    // 该状态码表明服务器上无法找到指定的资源。
    NOT_FOUND(404, "NOT_FOUND"),

    // 该状态码表明服务器端在执行请求时发生了错误。也有可能是Web应用存在的BUG或某些临时的故障。
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),

    // 该状态码表明服务器暂时处于超负载或正在进行停机维护，
    SERVICE_UNAVAILABLE(503, "SERVICE_UNAVAILABLE"),

    LOGIN_FAIL(5001, "登录失败"),
    LOGIN_FAIL_NO_CAPTCHA(5011, "缺少滑块参数"),
    LOGIN_FAIL_CAPTCHA_ERROR(5011, "滑块验证失败"),
    UNKONW_ACCOUNT(5002, "用户名或密码错误"),
    LOCKED_ACCOUNT(5003, "账户被冻结"),
    DISABLED_ACCOUNT(5004, "账户被禁用"),
    AUDITING_ACCOUNT(5005, "账户审核中"),
    UNREGISTER(5006, "未注册"),
    REGISTERED_ACCOUNT(6001, "该用户已注册"),
    ;

    private final int code;
    private final String desc;

    ResultCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
