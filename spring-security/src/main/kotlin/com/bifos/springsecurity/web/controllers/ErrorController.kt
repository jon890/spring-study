package com.bifos.springsecurity.web.controllers

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.ModelAndView


@ControllerAdvice
class ErrorController {

    companion object {
        private val logger = LoggerFactory.getLogger(ErrorController::class.java)
    }

    @ExceptionHandler(Throwable::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun exception(throwable: Throwable?): ModelAndView? {
        logger.error("Exception during execution of SpringSecurity application", throwable)

        val sb = StringBuffer()
        sb.append("Exception during execution of Spring Security application!  ")
            .append(if (throwable?.message != null) throwable.message else "Unknown error")
            .append(", root cause: ")
            .append(if (throwable?.cause != null) throwable.cause else "Unknown cause")

        return ModelAndView()
            .apply {
                addObject("error", sb.toString())
                viewName = "error"
            }
    }
}