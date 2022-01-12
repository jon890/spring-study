package com.bifos.springsecurity.authentication.chapter3

import com.bifos.springsecurity.domain.CalendarUser
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class DomainUsernameAuthenticationToken : UsernamePasswordAuthenticationToken {

    val domain: String

    constructor(
        principal: String,
        credentials: String,
        domain: String
    ) : super(principal, credentials) {
        this.domain = domain
    }

    constructor(
        principal: String,
        credentials: String,
        authorities: Collection<out GrantedAuthority>,
        domain: String
    ) : super(principal, credentials, authorities) {
        this.domain = domain
    }
}