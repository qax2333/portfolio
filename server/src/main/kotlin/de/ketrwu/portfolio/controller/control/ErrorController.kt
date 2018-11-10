package de.ketrwu.portfolio.controller.control

import de.ketrwu.portfolio.Slf4j
import org.slf4j.Logger
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author Kenneth Wußmann
 */
@Controller
class ErrorController : org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping(PATH)
    fun error(request: HttpServletRequest, response: HttpServletResponse, model: MutableMap<String, Any>): String {
        model["method"] = request.method.toUpperCase()
        model["statusCode"] = response.status
        model["path"] = request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI)

        if (response.status == HttpServletResponse.SC_NOT_FOUND) {
            log.warn("Resource not found at: \"{} {}\"", model["method"], model["path"])
        } else if (response.status >= 500) {
            log.error("Server-side error occurred ({}) at path \"{} {}\"", response.status, model["method"], model["path"])
        }

        return "content/text/error"
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception) {
        log.error("An otherwise not catched exception occurred", e)
    }

    override fun getErrorPath(): String {
        return PATH
    }

    companion object {
        const val PATH = "/error"

        @Slf4j
        lateinit var log: Logger
    }
}