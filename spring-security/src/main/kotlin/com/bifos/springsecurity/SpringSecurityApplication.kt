package com.bifos.springsecurity

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

fun main(args: Array<String>) {
    runApplication<SpringSecurityApplication>(*args)
}

@SpringBootApplication
class SpringSecurityApplication {
}