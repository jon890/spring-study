package com.bifos.springsecurity.service

import com.bifos.springsecurity.domain.CalendarUser
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component


/**
 * An implementation of [UserContext] that looks up the [CalendarUser] using the
 * Spring Security's [org.springframework.security.core.Authentication] by principal name.
 *
 * @author Rob Winch
 * @author BiFoS (jon89071@gmail.com)
 *
 */
@Component
class SpringSecurityUserContext(
    private val calendarService: CalendarService,
    private val userDetailsService: UserDetailsService
) : UserContext {

    /**
     * Get the [CalendarUser] by obtaining the currently logged in Spring Security user's
     * [org.springframework.security.core.Authentication.getName] and using that to find the
     * [CalendarUser] by email address (since for our application Spring Security usernames
     * are email addresses).
     */
    override fun getCurrentUser(): CalendarUser? {
        val context = SecurityContextHolder.getContext()
        val authentication = context.authentication ?: return null

        val user = authentication.principal as User
        val email = user.username ?: return null

        return calendarService.findUserByEmail(email)
            ?: throw IllegalStateException("Spring Security is not in synch with CalendarUsers. Could not find user with email $email")
    }

    override fun setCurrentUser(user: CalendarUser) {
        val userDetails = userDetailsService.loadUserByUsername(user.email)
        val authentication = UsernamePasswordAuthenticationToken(
            userDetails,
            user.password, userDetails.authorities
        )
        SecurityContextHolder.getContext().authentication = authentication
    }
}