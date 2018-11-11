package de.ketrwu.portfolio.controller.content

import de.ketrwu.portfolio.Slf4j
import org.slf4j.Logger
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.io.IOException

/**
 * Controller to serve pages in templates/contet/text/ dynamically
 * @author Kenneth Wußmann
 */
@Controller
@RequestMapping
class TextPageController {

    /**
     * Mapping that looks for path-variable page in classpath and serves it
     */
    @GetMapping("/page/{page}")
    fun getTextPage(@PathVariable page: String): String {
        val cl = this.javaClass.classLoader
        val resolver = PathMatchingResourcePatternResolver(cl)
        try {
            for (resource in resolver.getResources("classpath*:/templates/content/text/*.html")) {
                val existPage = resource.filename?.let {
                    it.replace(".html", "").equals(page, ignoreCase = true)
                } ?: false
                if (existPage) {
                    return "content/text/" + page.toLowerCase()
                }
            }
        } catch (e: IOException) {
            log.error("Failed to load text-pages from classpath", e)
        }

        return "content/text/error"
    }

    companion object {
        @Slf4j
        lateinit var log: Logger
    }
}
