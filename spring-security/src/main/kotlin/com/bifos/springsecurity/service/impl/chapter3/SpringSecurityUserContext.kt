package com.bifos.springsecurity.service.impl.chapter3

import com.bifos.springsecurity.authentication.chapter3.DomainUsernameAuthenticationToken
import com.bifos.springsecurity.core.authority.CalendarUserAuthorityUtils
import com.bifos.springsecurity.domain.CalendarUser
import com.bifos.springsecurity.service.CalendarService
import com.bifos.springsecurity.service.UserContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException

/**
 * An implementation of [UserContext] that looks up the [CalendarUser] using the
 * Spring Security's [org.springframework.security.core.Authentication] by principal name.
 *
 * @author Rob Winch
 * @author BiFoS (jon89071@gmail.com)
 *
 */
//@Component
class SpringSecurityUserContext(val calendarService: CalendarService) : UserContext {

    /**
     * Get the [CalendarUser] by obtaining the currently logged in Spring Security user's
     * [org.springframework.security.core.Authentication.getName] and using that to find the
     * [CalendarUser] by email address (since for our application Spring Security usernames
     * are email addresses).
     */
    override fun getCurrentUser(): CalendarUser? {
        val context = SecurityContextHolder.getContext()
        val authentication = context.authentication as DomainUsernameAuthenticationToken ?: return null

        val username = authentication.principal
        val domain = authentication.domain
        val email = "${username}@${domain}"
        val user = calendarService.findUserByEmail(email)
            ?: throw UsernameNotFoundException("User not found!! Security Error!!")
        return user
    }

    override fun setCurrentUser(user: CalendarUser) {
        val authorities = CalendarUserAuthorityUtils.createAuthorities(user)
        val usernameAndDomain = user.email.split("@")
        val authentication =
            DomainUsernameAuthenticationToken(usernameAndDomain[0], user.password, authorities, usernameAndDomain[1])
        SecurityContextHolder.getContext().authentication = authentication
    }
}