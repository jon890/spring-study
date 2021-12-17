package com.bifos.springmaster.dto

import java.time.LocalDateTime

class ExceptionResponse(
    val timestamp: LocalDateTime = LocalDateTime.now(),

    val message: String?,

    val details: String,

    ) {
}