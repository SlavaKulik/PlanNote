package edu.cources.plannote.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
@EnableConfigurationProperties
@ConfigurationProperties
public class YamlConfig {
    private String name;
    private String environment;
    private boolean enabled;
}
