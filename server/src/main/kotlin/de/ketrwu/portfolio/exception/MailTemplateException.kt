package de.ketrwu.portfolio.exception

/**
 * Exception thrown when something went wrong with rendering a mail template
 * @author Kenneth Wußmann
 */
class MailTemplateException(
    override val message: String?,
    override val cause: Throwable? = null
) : PortfolioException(message, cause)