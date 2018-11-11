package de.ketrwu.portfolio.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * Configuration of application internal settings
 * @author Kenneth Wußmann
 */
@Configuration
@ConfigurationProperties(prefix = "app")
open class ApplicationConfiguration {
    var address: Address? = null
    var domains: Array<String>? = emptyArray()
    var googleSiteVerification: String? = null

    /**
     * Configuration for the address shown in imprint and privacy
     * @author Kenneth Wußmann
     */
    class Address {
        var name: String? = null
        var street: String? = null
        var zip: String? = null
        var country: String? = null
    }
}