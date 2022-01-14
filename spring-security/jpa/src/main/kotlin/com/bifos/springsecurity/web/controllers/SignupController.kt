package com.bifos.springsecurity.web.controllers

import com.bifos.springsecurity.domain.CalendarUser
import com.bifos.springsecurity.service.CalendarService
import com.bifos.springsecurity.service.UserContext
import com.bifos.springsecurity.web.model.SignupForm
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.validation.Valid

@Controller
class SignupController(
    private val userContext: UserContext,
    private val calendarService: CalendarService
) {

    companion object {
        private val logger = LoggerFactory.getLogger(SignupController::class.java)
    }

    @GetMapping("/signup/form")
    fun signup(@ModelAttribute signupForm: SignupForm) = "signup/form"

    @PostMapping("/signup/new")
    fun signup(@Valid signupForm: SignupForm, result: BindingResult, redirectAttributes: RedirectAttributes): String {
        if (result.hasErrors()) {
            return "signup/form"
        }

        val email = signupForm.email!!
        if (calendarService.findUserByEmail(email) != null) {
            result.rejectValue("email", "errors.signup.email", "Email address is already in use. FOO")
            redirectAttributes.addFlashAttribute("error", "Email address in already in use. FOO")
            return "signup/form"
        }

        val user = CalendarUser(
            firstName = signupForm.firstName!!,
            lastName = signupForm.lastName!!,
            email = email,
            password = signupForm.password!!
        ).apply {
            val id = calendarService.createUser(this)
            this.id = id
        }

        userContext.setCurrentUser(user)

        redirectAttributes.addFlashAttribute("message", "You have successfully signed up and logged in.")
        return "redirect:/"
    }
}