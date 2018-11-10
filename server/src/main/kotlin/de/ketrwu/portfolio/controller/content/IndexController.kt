package de.ketrwu.portfolio.controller.content

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

/**
 * @author Kenneth Wußmann
 */
@Controller
class IndexController {

    @RequestMapping("/")
    fun index(): String {
        return "content/home"
    }
}
