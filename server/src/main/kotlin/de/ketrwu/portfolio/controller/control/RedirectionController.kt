package de.ketrwu.portfolio.controller.control

import de.ketrwu.portfolio.controller.content.TextPageController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * @author Kenneth Wußmann
 */
@Controller
class RedirectionController {

    @Autowired
    private val textPageController: TextPageController? = null

    @GetMapping("/impressum", "/imprint")
    private fun impressum(): String {
        return textPageController!!.getTextPage("imprint")
    }

    @GetMapping("/datenschutz", "/datenschutzerklaerung", "/datenschutzerklärung", "/privacy")
    private fun datenschutz(): String {
        return textPageController!!.getTextPage("privacy")
    }
}
