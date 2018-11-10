package de.ketrwu.portfolio

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * @author Kenneth Wußmann
 */
@SpringBootApplication(scanBasePackages = ["de.ketrwu.portfolio"])
@EnableScheduling
open class PortfolioApplication

fun main(args: Array<String>) {
    SpringApplication.run(PortfolioApplication::class.java, *args)
}