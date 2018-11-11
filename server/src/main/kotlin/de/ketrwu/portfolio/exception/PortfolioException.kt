package de.ketrwu.portfolio.exception

/**
 * Exception thrown by portfolio application
 * @author Kenneth Wußmann
 */
open class PortfolioException(
    override val message: String?,
    override val cause: Throwable?
) : Exception(message, cause)