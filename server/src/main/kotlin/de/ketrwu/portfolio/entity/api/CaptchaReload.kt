package de.ketrwu.portfolio.entity.api

import javax.validation.constraints.NotEmpty

class CaptchaReloadRequest {

    @NotEmpty
    var token: String? = null
}

data class CaptchaReloadResponse(
    val token: String,
    val image: String
)