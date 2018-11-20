package de.ketrwu.portfolio.controller.control

import de.ketrwu.portfolio.Slf4j
import org.slf4j.Logger
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Controller to handle issues occurred and not elsewhere catched
 * @author Kenneth Wußmann
 */
@ControllerAdvice
class ErrorHandler {

    companion object {
        @Slf4j
        lateinit var log: Logger
    }

    /**
     * Handle no handler found (dead links)
     */
    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(ex: NoHandlerFoundException): String {
        log.warn("Resource not found at: {} {}", ex.httpMethod, ex.requestURL, ex)
        return "content/text/error"
    }

}