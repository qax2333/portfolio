package de.ketrwu.portfolio.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.AntPathMatcher
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Configuration of the servlet
 * @author Kenneth Wußmann
 */
@Configuration
open class WebConfiguration {

    /**
     * WebMvcConfigurer for handling webfonts and case-insensitive path matching
     */
    @Bean
    open fun configurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
                registry
                    .addResourceHandler("/webfonts/**")
                    .addResourceLocations("classpath:/static/webfonts/")
            }

            override fun configurePathMatch(configurer: PathMatchConfigurer) {
                val matcher = AntPathMatcher()
                matcher.setCaseSensitive(false)
                configurer.pathMatcher = matcher
            }
        }
    }
}