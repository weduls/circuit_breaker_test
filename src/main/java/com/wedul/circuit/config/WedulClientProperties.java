package com.wedul.circuit.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("wedul.client")
public class WedulClientProperties {

    private int timeout;
    private int connectTimeout;
    private int socketTimeout;
    private String url;
    private String port;
}
