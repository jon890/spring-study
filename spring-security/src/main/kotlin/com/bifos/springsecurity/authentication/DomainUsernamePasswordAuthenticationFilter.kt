package com.bifos.springsecurity.authentication

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class DomainUsernamePasswordAuthenticationFilter(authenticationManager: AuthenticationManager) :
    UsernamePasswordAuthenticationFilter(authenticationManager) {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        if (request.method != "POST") {
            throw AuthenticationServiceException("Authentication method not supported: ${request.method}")
        }

        val username = obtainUsername(request) ?: throw AuthenticationServiceException("Username is null")
        val password = obtainPassword(request) ?: throw AuthenticationServiceException("Password is null")
        val domain = request.getParameter("domain")

        // 권한을 지정하지 않았으므로 authRequest.isAuthenticated() = false
        val authRequest = DomainUsernameAuthenticationToken(username, password, domain)
        setDetails(request, authRequest)
        return this.authenticationManager.authenticate(authRequest)
    }
}