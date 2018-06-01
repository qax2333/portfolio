package de.ketrwu.portfolio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration {

    @Bean
    public WebMvcConfigurer configurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addResourceHandlers (ResourceHandlerRegistry registry) {
                registry
                        .addResourceHandler("/webfonts/**")
                        .addResourceLocations("classpath:/static/webfonts/");
            }

            @Override
            public void configurePathMatch(PathMatchConfigurer configurer) {
                AntPathMatcher matcher = new AntPathMatcher();
                matcher.setCaseSensitive(false);
                configurer.setPathMatcher(matcher);
            }

        };
    }

}
