package com.bifos.springsecurity.dataaccess

import com.bifos.springsecurity.domain.CalendarUser
import org.springframework.dao.EmptyResultDataAccessException


/**
 * An interface for managing {@link CalendarUser} instances.
 *
 * @author Rob Winch (converted by BiFoS)
 */
interface CalendarUserDao {

    /**
     * Gets a [CalendarUser] for a specific [CalendarUser.id].
     *
     * @param id
     * the [CalendarUser.id] of the [CalendarUser] to find.
     * @return a [CalendarUser] for the given id. Cannot be null.
     * @throws EmptyResultDataAccessException
     * if the [CalendarUser] cannot be found
     */
    fun getUser(id: Int): CalendarUser

    /**
     * Finds a given [CalendarUser] by email address.
     *
     * @param email
     * the email address to use to find a [CalendarUser]. Cannot be null.
     * @return a [CalendarUser] for the given email or null if one could not be found.
     */
    fun findUserByEmail(email: String): CalendarUser?

    /**
     * Finds any [CalendarUser] that has an email that starts with `partialEmail`.
     *
     * @param partialEmail
     * the email address to use to find [CalendarUser]s. Cannot be null or empty String.
     * @return a List of [CalendarUser]s that have an email that starts with given partialEmail. The returned value
     * will never be null. If no results are found an empty List will be returned.
     * @throws IllegalArgumentException
     * if email is null or empty String.
     */
    fun findUsersByEmail(partialEmail: String): List<CalendarUser>

    /**
     * Creates a new [CalendarUser].
     *
     * @param user
     * the new [CalendarUser] to create. The [CalendarUser.id] must be null.
     * @return the new [CalendarUser.id].
     * @throws IllegalArgumentException
     * if [CalendarUser.id] is non-null.
     */
    fun createUser(user: CalendarUser): Int
}