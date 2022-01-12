package com.bifos.springsecurity.web.model

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

class SignupForm(

    @NotEmpty(message = "First Name is required")
    var firstName: String? = null,

    @NotEmpty(message = "Last Name is required")
    var lastName: String? = null,

    @Email(message = "Please provide a valid email address")
    @NotEmpty(message = "Email is required")
    var email: String? = null,

    @NotEmpty(message = "Password is required")
    var password: String? = null
) {
}