package com.bifos.springsecurity.core.userdetails

import com.bifos.springsecurity.core.authority.CalendarUserAuthorityUtils
import com.bifos.springsecurity.dataaccess.CalendarUserDao
import com.bifos.springsecurity.domain.CalendarUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

/**
 * Integrates with Spring Security using our existing [CalendarUserDao] by looking up a
 * [CalendarUser] and converting it into a [UserDetails] so that Spring Security can do the
 * username/password comparison for us.
 *
 * @author Rob Winch
 * @author BiFoS (jon89071@gmail.com)
 */
@Component
class CalendarUserDetailsService(
    private val calendarUserDao: CalendarUserDao
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        // null을 반환시 UserDetailsService 인터페이스가 중단되므로
        // UsernameNotFoundException 예외를 발생시켜 null을 반환하지 않도록 한다
        val user =
            calendarUserDao.findUserByEmail(username) ?: throw UsernameNotFoundException("Invalid username/password.")
        return CalendarUserDetails(user)
    }

    class CalendarUserDetails(calendarUser: CalendarUser) : CalendarUser(
        firstName = calendarUser.firstName,
        lastName = calendarUser.lastName,
        email = calendarUser.email,
        password = calendarUser.password,
    ), UserDetails {

        init {
            id = calendarUser.id
        }

        override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
            return CalendarUserAuthorityUtils.createAuthorities(this).toMutableSet()
        }

        override fun getPassword(): String {
            return password
        }

        override fun getUsername(): String {
            return email
        }

        override fun isAccountNonExpired(): Boolean {
            return true
        }

        override fun isAccountNonLocked(): Boolean {
            return true
        }

        override fun isCredentialsNonExpired(): Boolean {
            return true
        }

        override fun isEnabled(): Boolean {
            return true
        }
    }
}