package de.ketrwu.portfolio.controller.api

import de.ketrwu.portfolio.entity.api.CaptchaReloadRequest
import de.ketrwu.portfolio.entity.api.CaptchaReloadResponse
import de.ketrwu.portfolio.service.CaptchaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CaptchaController {

    @Autowired
    private lateinit var captchaService: CaptchaService

    @PostMapping("/api/captcha")
    fun reloadCaptcha(@RequestBody @Validated request: CaptchaReloadRequest): ResponseEntity<CaptchaReloadResponse> {
        return captchaService.reloadCaptcha(request)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.badRequest().build()
    }
}