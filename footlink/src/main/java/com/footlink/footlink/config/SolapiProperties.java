package com.footlink.footlink.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "solapi")
@Getter
@Setter
public class SolapiProperties {
    private String apiKey;
    private String apiSecret;
    private String from; // 발신번호
}
