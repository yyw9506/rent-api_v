package com.xxx.rent.api.exception;

public class BizException extends RuntimeException {
    /**
     * 错误信息
     */
    private String errMsg;
    /**
     * 错误码
     */
    private Integer errCode;

    public BizException() {
        super();
    }

    public BizException(String errMsg) {
        super(errMsg);
        this.errCode = 500;
        this.errMsg = errMsg;
    }

    public BizException(String errMsg, Integer errCode) {
        super(errMsg);
        this.errMsg = errMsg;
        this.errCode = errCode;
    }

    @Override
    public String getMessage() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }
}
