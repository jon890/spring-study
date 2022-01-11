package com.bifos.springsecurity.web.controllers

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ErrorController {

    companion object {
        private val logger = LoggerFactory.getLogger(ErrorController::class.java)
    }

    @ExceptionHandler(Throwable::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(throwable: Throwable?, model: Model): String {
        logger.error("Exception during execution of SpringSecurity application", throwable)
        val errorMessage = if (throwable != null) throwable.message else "Unknown error"
        model.addAttribute("error", errorMessage)
        return "error"
    }
}