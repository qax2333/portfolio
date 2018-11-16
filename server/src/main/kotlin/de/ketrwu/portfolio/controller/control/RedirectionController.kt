package de.ketrwu.portfolio.controller.control

import de.ketrwu.portfolio.controller.content.TextPageController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * Controller that serves TextPages under another alias-path
 * @author Kenneth Wußmann
 */
@Controller
class RedirectionController {

    @Autowired
    private val textPageController: TextPageController? = null

    @GetMapping("/impressum", "/imprint")
    private fun impressum(model: MutableMap<String, Any>): String {
        return textPageController?.getTextPage("imprint", model) ?: "/"
    }

    @GetMapping("/datenschutz", "/datenschutzerklaerung", "/datenschutzerklärung", "/privacy")
    private fun datenschutz(model: MutableMap<String, Any>): String {
        return textPageController?.getTextPage("privacy", model) ?: "/"
    }
}
