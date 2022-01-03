package com.bifos.springsecurity.service.impl

import com.bifos.springsecurity.dataaccess.CalendarUserDao
import com.bifos.springsecurity.domain.CalendarUser
import com.bifos.springsecurity.service.UserContext
import org.springframework.stereotype.Component

/**
 * Returns the same user for every call to [getCurrentUser].
 * This is used prior to adding security,
 * so that the rest of the application can be used.
 */
@Component
class UserContextStub(
    val userService: CalendarUserDao
) : UserContext {

    /**
     * The [com.bifos.springsecurity.domain.CalendarUser.id] for the user that is
     * representing the currently logged in user.
     * This can be modified using [setCurrentUser]
     */
    private var currentUserId = 0

    override fun getCurrentUser(): CalendarUser? {
        return userService.getUser(currentUserId)
    }

    override fun setCurrentUser(user: CalendarUser) {
        this.currentUserId = user.id ?: 0
    }
}