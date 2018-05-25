package de.ketrwu.portfolio.controller.control;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(PATH)
    public String error(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        model.put("method", request.getMethod().toUpperCase());
        model.put("statusCode", response.getStatus());
        model.put("path", request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI));

        if (response.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
            log.warn("Resource not found at: \"{} {}\"", model.get("method"), model.get("path"));
        } else if (response.getStatus() >= 500) {
            log.error("Server-side error occurred ({}) at path \"{} {}\"", response.getStatus(), model.get("method"), model.get("path"));
        }

        return "content/text/error";
    }

    @ExceptionHandler(Exception.class)
    public void handleException(Exception e) {
        log.error("An otherwise not catched exception occurred", e);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

}