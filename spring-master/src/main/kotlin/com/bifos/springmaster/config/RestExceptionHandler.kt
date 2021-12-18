package com.bifos.springmaster.config

import com.bifos.springmaster.dto.ExceptionResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
@RestController
class RestExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(RuntimeException::class)
    fun entityNotFound(ex: RuntimeException): ResponseEntity<*> {
        val exceptionResponse = ExceptionResponse(message = ex.message, details = "Any details you would want to add")
        return ResponseEntity(exceptionResponse, HttpHeaders(), HttpStatus.NOT_FOUND)
    }
}