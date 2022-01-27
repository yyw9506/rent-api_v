package com.xxx.rent.api.filter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api")
public class ApiProperties {
    /**
     * 是否启用网关校验
     */
    private Boolean enable;
    /**
     * 网关签名密钥
     */
    private String secret;

    private String[] white;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String[] getWhite() {
        return white;
    }

    public void setWhite(String[] white) {
        this.white = white;
    }
}
