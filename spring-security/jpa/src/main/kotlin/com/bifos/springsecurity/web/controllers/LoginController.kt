package com.bifos.springsecurity.web.controllers

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class LoginController {

    companion object {
        private val logger = LoggerFactory.getLogger(LoginController::class.java)
    }

    @GetMapping("/login")
    fun login(
        @RequestParam(value = "error", required = false) error: String?,
        @RequestParam(value = "logout", required = false) logout: String?,
        model: Model
    ): String {
        logger.info("******login(error): {} ***************************************", error)
        logger.info("******login(logout): {} ***************************************", logout)

        if (error != null) {
            model.addAttribute("error", "Invalid username and password!")
        }

        if (logout != null) {
            model.addAttribute("message", "You've been logged out successfully.")
        }

        return "login"
    }
}