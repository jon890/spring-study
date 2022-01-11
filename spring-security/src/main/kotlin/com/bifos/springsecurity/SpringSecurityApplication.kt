package com.bifos.springsecurity

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

fun main(args: Array<String>) {
    runApplication<SpringSecurityApplication>(*args)
}

@SpringBootApplication
@EnableTransactionManagement
class SpringSecurityApplication {
}