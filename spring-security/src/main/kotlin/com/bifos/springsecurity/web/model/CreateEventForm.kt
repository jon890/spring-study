package com.bifos.springsecurity.web.model

import org.springframework.format.annotation.DateTimeFormat
import java.util.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

/**
 * A form object that is used for creating na new [com.bifos.springsecurity.domain.Event].
 * Using a different object is one way of preventing malicious users
 * from filling out field that they should not
 * (i.e. fill out a different owner field).
 *
 * @author Rob Winch (converted by BiFoS)
 */
class CreateEventForm(

    @field:NotEmpty(message = "Attendee email is required")
    @field:Email(message = "Attendee Email must be a valid email")
    var attendeeEmail: String,

    @field:NotEmpty(message = "Summary is required")
    var summary: String,

    @field:NotEmpty(message = "Description is required")
    var description: String,

    @field:DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @field:NotNull(message = "Event Date/Time is required")
    var `when`: Calendar
) {

}