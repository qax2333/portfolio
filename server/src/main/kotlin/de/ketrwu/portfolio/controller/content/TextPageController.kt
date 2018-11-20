package de.ketrwu.portfolio.controller.content

import de.ketrwu.portfolio.service.TextPageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Controller to serve pages in templates/contet/text/ dynamically
 * @author Kenneth Wußmann
 */
@Controller
@RequestMapping
class TextPageController {

    @Autowired
    private val textPageService: TextPageService? = null

    /**
     * Mapping that looks for path-variable page in classpath and serves it
     */
    @GetMapping("/page/{page}")
    fun getTextPage(@PathVariable page: String, model: MutableMap<String, Any>): String {
        return when {
            textPageService!!.isHtmlTextPage(page) -> "content/text/" + page.toLowerCase()
            textPageService.isMarkdownTextPage(page) -> {
                model["page"] = textPageService.renderMarkdown(page)
                "content/text/markdown"
            }
            else -> "content/text/error"
        }
    }

    /**
     * Mapping that looks for path-variable page in classpath and serves it at its tags-based path
     */
    @GetMapping("/page/{tag}/{page}")
    fun getTaggedTextPage(
        @PathVariable tag: String,
        @PathVariable page: String,
        model: MutableMap<String, Any>
    ): String {
        return when {
            textPageService!!.isMarkdownTextPage(page, tag) -> {
                model["page"] = textPageService.renderMarkdown(page)
                "content/text/markdown"
            }
            else -> "content/text/error"
        }
    }
}
