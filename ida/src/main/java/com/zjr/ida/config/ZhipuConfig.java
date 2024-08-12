package com.zjr.ida.config;

import com.zhipu.oapi.ClientV4;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "zhipu.client")
@Data
public class ZhipuConfig {
    private String apiSecret;
    @Bean
    public ClientV4 clientV4(){
        ClientV4 client = new ClientV4.Builder(apiSecret).build();
        return client;
    }
}
