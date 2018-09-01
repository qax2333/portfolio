package de.ketrwu.portfolio.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * @author Kenneth Wußmann
 */
@Configuration
@ConfigurationProperties(prefix = "app")
data class ApplicationConfiguration (
        val address: Address,
        val domains: Array<String>,
        val googleSiteVerification: String
) {
    data class Address (
            val name: String,
            val street: String,
            val zip: String,
            val country: String
    )
}
