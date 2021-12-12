package com.bifos.springmaster

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

fun main(args : Array<String>) {
    runApplication<SpringMasterApplication>(*args)
}

@SpringBootApplication
class SpringMasterApplication {
}
