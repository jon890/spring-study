package com.bifos.springsecurity.service

import com.bifos.springsecurity.domain.CalendarUser

/**
 * Managers the current [CalendarUser]. This demonstrates how in larger applications it is good to abstract out
 * accessing the current user to return the application specific user rather than interacting with Spring Security
 * classes directly
 *
 * @author Rob Winch (converted by BiFoS)
 */
interface UserContext {

    /**
     * Gets the currently logged in [CalendarUser] or null if there is no authenticated user.
     *
     * @return
     */
    fun getCurrentUser(): CalendarUser?

    /**
     * Sets the currently logged in [CalendarUser].
     * @param user the logged in [CalendarUser]. Cannot be null.
     * @throws IllegalArgumentException if the [CalendarUser] is null.
     */
    fun setCurrentUser(user: CalendarUser)
}