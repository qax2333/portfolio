package de.ketrwu.portfolio.controller.content

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Controller to serve the start page
 * @author Kenneth Wußmann
 */
@Controller
class IndexController {

    /**
     * Make all http methods respond with the home template
     */
    @RequestMapping("/")
    fun index(): String {
        return "content/home"
    }
}
