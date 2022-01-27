package com.xxx.rent.api.util;

/**
 * 返回码
 */
public class StatusCode {
    /**
     * 成功
     */
    public static final int OK = 200;
    /**
     * 失败
     */
    public static final int ERROR = 500;
    /**
     * 用户名或密码错误
     */
    public static final int LOGINERROR = 20002;
    /**
     * 权限不足
     */
    public static final int ACCESSERROR = 20003;
    /**
     * 远程调用失败
     */
    public static final int REMOTEERROR = 20004;
    /**
     * 重复操作
     */
    public static final int REPERROR = 20005;
    /**
     *
     */
    public static final int NOTFOUNDERROR = 20006;
}
