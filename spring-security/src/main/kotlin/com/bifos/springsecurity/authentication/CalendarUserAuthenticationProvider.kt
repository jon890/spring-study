package com.bifos.springsecurity.authentication

import com.bifos.springsecurity.core.authority.CalendarUserAuthorityUtils
import com.bifos.springsecurity.service.CalendarService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class CalendarUserAuthenticationProvider(
    val calendarService: CalendarService
) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication?): Authentication {
        val token = authentication as DomainUsernameAuthenticationToken

        val username = token.name
        val domain = token.domain
        val email = "${username}@${domain}"

        val user = calendarService.findUserByEmail(email)
            ?: throw UsernameNotFoundException("Invalid username/password")

        val password = user.password
        if (password != token.credentials) {
            throw BadCredentialsException("Invalid username/password")
        }

        val authorities = CalendarUserAuthorityUtils.createAuthorities(user)
        return DomainUsernameAuthenticationToken(username, password, authorities, domain)
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return DomainUsernameAuthenticationToken::class.java == authentication
    }
}