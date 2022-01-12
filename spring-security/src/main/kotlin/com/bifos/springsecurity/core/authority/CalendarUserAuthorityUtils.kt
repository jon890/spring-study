package com.bifos.springsecurity.core.authority

import com.bifos.springsecurity.domain.CalendarUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils

/**
 * A utility class used for creating the [org.springframework.security.core.GrantedAuthority]'s
 * given a [com.bifos.springsecurity.domain.CalendarUser].
 *
 * in a real solution this would be looked up in the existing system,
 * but for simplicity our original system had no notion of authorities.
 *
 * @author Rob Winch
 * @author Mick Knutson
 * @author BiFoS (jon89071@gmail.com)
 */
object CalendarUserAuthorityUtils {

    private val ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER")
    private val USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER")

    fun createAuthorities(calendarUser: CalendarUser): Collection<out GrantedAuthority> {
        val username = calendarUser.email
        if (username.startsWith("admin")) {
            return ADMIN_ROLES
        }
        return USER_ROLES
    }
}