package com.bifos.springsecurity.web.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletRequest

@Controller
class DefaultController {

    @RequestMapping("/default")
    fun defaultAfterLogin(request: HttpServletRequest): String {
        return if (request.isUserInRole("ADMIN")) {
            "redirect:/events/"
        } else {
            "redirect:/"
        }
    }
}