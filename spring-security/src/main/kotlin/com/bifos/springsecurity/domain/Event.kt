package com.bifos.springsecurity.domain

import java.util.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

/**
 * An {@link Event} is an item on a calendar that contains an owner (the person who created it), an attendee
 * (someone who was invited to the event), when the event will occur, a summary, and a description. For simplicity, all
 * fields are required.
 *
 * @author Rob Winch (converted by BiFoS)
 *
 */
class Event(
    val summary: String,

    @field:NotEmpty(message = "Summary is required")
    var description: String? = null,

    @field:NotEmpty(message = "Description is required")
    var `when`: Calendar? = null,

    @field:NotNull(message = "When is required")
    var owner: CalendarUser? = null,

    @field:NotNull(message = "Summary is required")
    var attendee: CalendarUser? = null
) {
    var id: Int? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Event) return false

        if (summary != other.summary) return false
        if (description != other.description) return false
        if (`when` != other.`when`) return false
        if (owner != other.owner) return false
        if (attendee != other.attendee) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = summary.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (`when`?.hashCode() ?: 0)
        result = 31 * result + (owner?.hashCode() ?: 0)
        result = 31 * result + (attendee?.hashCode() ?: 0)
        result = 31 * result + (id ?: 0)
        return result
    }

}