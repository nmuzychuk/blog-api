package com.nmuzychuk.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Customizes the Java-based configuration for Spring.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE")
                .allowedHeaders("Authorization", "Location", "Cache-Control", "Content-Type")
                .exposedHeaders("Authorization", "Location", "Cache-Control", "Content-Type");
    }
}
