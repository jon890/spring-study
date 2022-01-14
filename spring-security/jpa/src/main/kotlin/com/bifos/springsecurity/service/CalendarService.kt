package com.bifos.springsecurity.service

import com.bifos.springsecurity.domain.CalendarUser
import com.bifos.springsecurity.domain.Event
import org.springframework.dao.EmptyResultDataAccessException

interface CalendarService {
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
     * @throws IllegalArgumentException
     * if email is null.
     */
    fun findUserByEmail(email: String): CalendarUser?

    /**
     * Finds any [CalendarUser] that has an email that starts with `partialEmail`.
     *
     * @param partialEmail
     * the email address to use to find [CalendarUser]s. Cannot be null or empty String.
     * @return a List of [CalendarUser]s that have an email that starts with given partialEmail. The returned
     * value will never be null. If no results are found an empty List will be returned.
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

    /**
     * Given an id gets an [Event].
     *
     * @param eventId
     * the [Event.id]
     * @return the [Event]. Cannot be null.
     * @throws RuntimeException
     * if the [Event] cannot be found.
     */
    fun getEvent(eventId: Int): Event

    /**
     * Creates a [Event] and returns the new id for that [Event].
     *
     * @param message
     * the [Event] to create. Note that the [Event.id] should be null.
     * @return the new id for the [Event]
     * @throws RuntimeException
     * if [Event.id] is non-null.
     */
    fun createEvent(event: Event): Int

    /**
     * Finds the [Event]'s that are intended for the [CalendarUser].
     *
     * @param userId
     * the [CalendarUser.id] to obtain [Event]'s for.
     * @return a non-null [List] of [Event]'s intended for the specified [CalendarUser]. If the
     * [CalendarUser] does not exist an empty List will be returned.
     */
    fun findForUser(userId: Int): List<Event>

    /**
     * Gets all the available [Event]'s.
     *
     * @return a non-null [List] of [Event]'s
     */
    fun getEvents(): List<Event>

}