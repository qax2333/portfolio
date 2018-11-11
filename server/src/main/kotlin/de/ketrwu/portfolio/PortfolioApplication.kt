package de.ketrwu.portfolio

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * @author Kenneth Wußmann
 */
@SpringBootApplication(scanBasePackages = ["de.ketrwu.portfolio"])
@EnableScheduling
open class PortfolioApplication : SpringBootServletInitializer() {

    override fun configure(builder: SpringApplicationBuilder?): SpringApplicationBuilder? {
        return builder?.sources(PortfolioApplication::class.java)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(PortfolioApplication::class.java, *args)
}