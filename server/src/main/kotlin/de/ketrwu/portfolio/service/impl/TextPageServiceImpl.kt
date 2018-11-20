package de.ketrwu.portfolio.service.impl

import de.ketrwu.portfolio.Slf4j
import de.ketrwu.portfolio.entity.MarkdownMeta
import de.ketrwu.portfolio.entity.MarkdownTextPage
import de.ketrwu.portfolio.entity.ProjectMarkdownMeta
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
        const val MARKDOWN_PATH = "/templates/content/text/markdown"
        const val HTML_PATH = "/templates/content/text"
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

    private fun getResources(extension: String, path: String, recursive: Boolean): Array<Resource> {
        val cl = this.javaClass.classLoader
        val resolver = PathMatchingResourcePatternResolver(cl)
        var matcher = "classpath*:$path/"
        if (recursive) {
            matcher += "**/"
        }
        matcher += "*.$extension"
        try {
            return resolver.getResources(matcher)
        } catch (e: IOException) {
            log.error("Failed to resource from classpath", e)
        }
        return arrayOf()
    }

    private fun getResource(
        fileWithoutExtension: String,
        extension: String,
        path: String,
        recursive: Boolean
    ): Resource? {
        for (resource in getResources(extension, path, recursive)) {
            val found = resource.filename?.let {
                return@let it.replace(".$extension", "")
                    .equals(fileWithoutExtension, ignoreCase = true)
            } ?: false
            if (found) {
                return resource
            }
        }
        return null
    }

    private fun resourceExists(
        fileWithoutExtension: String,
        extension: String,
        path: String,
        recursive: Boolean
    ): Boolean {
        return getResource(fileWithoutExtension, extension, path, recursive)?.exists() ?: false
    }

    private fun renderMarkdown(resource: Resource, page: String): MarkdownTextPage {
        val markdown = FileCopyUtils.copyToString(InputStreamReader(
            resource.inputStream,
            charset("UTF-8")
        ))
        val node = markdownParser.parse(markdown)
        val yamlFrontMatterVisitor = YamlFrontMatterVisitor()
        node.accept(yamlFrontMatterVisitor)
        val rendered = htmlRenderer.render(node)
        return MarkdownTextPage(
            rendered,
            if (yamlFrontMatterVisitor.data.containsKey("project")) {
                ProjectMarkdownMeta(page).of(yamlFrontMatterVisitor.data)
            } else {
                MarkdownMeta(page).of(yamlFrontMatterVisitor.data)
            }
        )
    }

    /**
     * {@inheritDoc}
     */
    override fun isMarkdownTextPage(page: String, tag: String?): Boolean {
        val exists = resourceExists(page, "md", MARKDOWN_PATH, true)
        return when {
            tag == null -> exists
            exists -> renderMarkdown(page).meta.tags.contains(tag.toLowerCase())
            else -> false
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun isHtmlTextPage(page: String): Boolean {
        return resourceExists(page, "html", HTML_PATH, false)
    }

    /**
     * {@inheritDoc}
     */
    override fun renderMarkdown(page: String): MarkdownTextPage {
        return getResource(page, "md", MARKDOWN_PATH, true)?.let { resource ->
            return renderMarkdown(resource, page)
        } ?: MarkdownTextPage()
    }

    /**
     * {@inheritDoc}
     */
    override fun getPages(tag: String): List<MarkdownTextPage> {
        return getResources("md", MARKDOWN_PATH, true)
            .map { renderMarkdown(it, it.filename!!.toLowerCase().replace(".md", "")) }
            .filter { it.meta.tags.contains(tag) }
    }
}