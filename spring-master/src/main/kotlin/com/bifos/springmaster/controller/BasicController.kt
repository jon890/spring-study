package com.bifos.springmaster.controller

import com.bifos.springmaster.domain.Welcome
import org.springframework.context.MessageSource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
class BasicController(
    val messageSource: MessageSource
) {

    private val helloWorldTemplate = "Hello World, %s!"

    @GetMapping("/welcome")
    fun welcome(): String? {
        return "Hello World"
    }

    @GetMapping("/welcome-with-object")
    fun welcomeWithObject(): Welcome? {
        return Welcome("Hello World")
    }

    @GetMapping("/welcome-with-parameter/name/{name}")
    fun welcomeWithParameter(@PathVariable name: String?): Welcome? {
        return Welcome(String.format(helloWorldTemplate, name))
    }

    @GetMapping("/welcome-internationalized")
    fun msg(@RequestHeader(value = "Accept-Language", required = false) locale: Locale) : String{
        return messageSource.getMessage("welcome.message", null, locale)
    }
}
