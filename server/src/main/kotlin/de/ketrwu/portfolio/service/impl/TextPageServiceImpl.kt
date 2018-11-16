package de.ketrwu.portfolio.service.impl

import de.ketrwu.portfolio.Slf4j
import de.ketrwu.portfolio.entity.MarkdownTextPage
import de.ketrwu.portfolio.service.TextPageService
import org.commonmark.ext.autolink.AutolinkExtension
import org.commonmark.ext.front.matter.YamlFrontMatterExtension
import org.commonmark.ext.front.matter.YamlFrontMatterVisitor
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.slf4j.Logger
import org.springframework.core.io.Resource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.stereotype.Service
import org.springframework.util.FileCopyUtils
import java.io.IOException
import java.io.InputStreamReader

/**
 * Implementation of a TextPageService to resolve and render html and md files
 * @author Kenneth Wußmann
 */
@Service
class TextPageServiceImpl : TextPageService {

    companion object {
        @Slf4j
        lateinit var log: Logger
        lateinit var markdownParser: Parser
        lateinit var htmlRenderer: HtmlRenderer
    }

    init {
        val extensions = listOf(
                TablesExtension.create(),
                AutolinkExtension.create(),
                HeadingAnchorExtension.create(),
                YamlFrontMatterExtension.create()
        )
        markdownParser = Parser.builder()
                .extensions(extensions)
                .build()
        htmlRenderer = HtmlRenderer.builder()
                .extensions(extensions)
                .build()
    }

    private fun getResource(fileWithoutExtension: String, extension: String, path: String): Resource? {
        val cl = this.javaClass.classLoader
        val resolver = PathMatchingResourcePatternResolver(cl)
        try {
            for (resource in resolver.getResources("classpath*:$path/*.$extension")) {
                val found = resource.filename?.let {
                    return@let it.replace(".$extension", "").equals(fileWithoutExtension, ignoreCase = true)
                } ?: false
                if (found) {
                    return resource
                }
            }
        } catch (e: IOException) {
            log.error("Failed to resource from classpath", e)
        }
        return null
    }

    private fun resourceExists(fileWithoutExtension: String, extension: String, path: String): Boolean {
        return getResource(fileWithoutExtension, extension, path)?.exists() ?: false
    }

    /**
     * {@inheritDoc}
     */
    override fun isMarkdownTextPage(page: String): Boolean {
        return resourceExists(page, "md", "/templates/content/text/markdown")
    }

    /**
     * {@inheritDoc}
     */
    override fun isHtmlTextPage(page: String): Boolean {
        return resourceExists(page, "html", "/templates/content/text")
    }

    /**
     * {@inheritDoc}
     */
    override fun renderMarkdown(page: String): MarkdownTextPage {
        return getResource(page, "md", "/templates/content/text/markdown")?.let { resource ->
            val markdown = FileCopyUtils.copyToString(InputStreamReader(
                resource.inputStream,
                charset("UTF-8")
            ))
            val node = markdownParser.parse(markdown)
            val yamlFrontMatterVisitor = YamlFrontMatterVisitor()
            node.accept(yamlFrontMatterVisitor)

            val title = yamlFrontMatterVisitor.data["title"]!![0]
            val headline = yamlFrontMatterVisitor.data["headline"]!![0]
            val description = yamlFrontMatterVisitor.data["description"]!![0]
            val rendered = htmlRenderer.render(node)
            return MarkdownTextPage(title, headline, description, rendered)
        } ?: MarkdownTextPage()
    }
}