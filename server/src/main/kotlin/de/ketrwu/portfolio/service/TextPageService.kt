package de.ketrwu.portfolio.service

import de.ketrwu.portfolio.entity.MarkdownTextPage

/**
 * Service for resolving HTML and MD files from classpath and for rendering Markdown to HTML
 * @author Kenneth Wußmann
 */
interface TextPageService {

    /**
     * Check if classpath has a Markdown file with given name
     */
    fun isMarkdownTextPage(page: String): Boolean

    /**
     * Check if classpath has a html file with given name
     */
    fun isHtmlTextPage(page: String): Boolean

    /**
     * Render a Markdown file to HTML
     */
    fun renderMarkdown(page: String): MarkdownTextPage

}