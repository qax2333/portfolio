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
 * Controller to handle issues occurred and not elsewhere catched
 * @author Kenneth Wußmann
 */
@Controller
class ErrorController : org.springframework.boot.web.servlet.error.ErrorController {

    private val MODEL_KEY_METHOD = "method"
    private val MODEL_KEY_STATUS_CODE = "statusCode"
    private val MODEL_KEY_PATH = "path"

    /**
     * Mapping for the error path. Logs additional info about the error
     */
    @RequestMapping(PATH)
    fun error(request: HttpServletRequest, response: HttpServletResponse, model: MutableMap<String, Any>): String {
        model[MODEL_KEY_METHOD] = request.method.toUpperCase()
        model[MODEL_KEY_STATUS_CODE] = response.status
        model[MODEL_KEY_PATH] = request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI)

        if (response.status == HttpServletResponse.SC_NOT_FOUND) {
            log.warn("Resource not found at: \"{} {}\"", model[MODEL_KEY_METHOD], model[MODEL_KEY_PATH])
        } else if (response.status >= 500) {
            log.error("Server-side error occurred ({}) at path \"{} {}\"", response.status, model[MODEL_KEY_METHOD], model[MODEL_KEY_PATH])
        }

        return "content/text/error"
    }

    /**
     * Register ExceptionHandler
     */
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