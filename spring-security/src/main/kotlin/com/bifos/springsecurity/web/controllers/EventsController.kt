package com.bifos.springsecurity.web.controllers

import com.bifos.springsecurity.domain.Event
import com.bifos.springsecurity.service.CalendarService
import com.bifos.springsecurity.service.UserContext
import com.bifos.springsecurity.web.model.CreateEventForm
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*
import javax.validation.Valid


@Controller
@RequestMapping("/events")
class EventsController(val calendarService: CalendarService, val userContext: UserContext) {

    @GetMapping("/")
    fun events(model: Model): String {
        model.addAttribute("events", calendarService.getEvents())
        return "events/list"
    }

    @GetMapping("/my")
    fun myEvents(model: Model): String {
        val currentUser = userContext.getCurrentUser()
        val currentUserId = currentUser?.id

        model.addAttribute("currentUser", currentUser)
        currentUserId?.let { model.addAttribute("events", calendarService.findForUser(it)) }
        return "events/my"
    }

    @GetMapping("/{eventId}")
    fun show(@PathVariable eventId: Int, model: Model): String {
        val event = calendarService.getEvent(eventId)
        model.addAttribute("event", event)
        return "events/show"
    }

    @RequestMapping("/form")
    fun createEventForm(@ModelAttribute createEventForm: CreateEventForm?): String {
        return "events/create"
    }

    @PostMapping("/new")
    fun createEvent(
        @Valid createEventForm: CreateEventForm,
        result: BindingResult,
        redirectAttribute: RedirectAttributes
    ): String {
        if (result.hasErrors()) {
            return "events/create"
        }

        val attendee = calendarService.findUserByEmail(createEventForm.attendeeEmail!!)
        if (attendee == null) {
            result.rejectValue(
                "attendeeEmail",
                "attendeeEmail.missing",
                "Could not find a user for the provided Attendee Email"
            )
        }

        if (result.hasErrors()) {
            return "events/create"
        }

        val event = Event(
            attendee = attendee, description = createEventForm.description,
            owner = userContext.getCurrentUser(),
            summary = createEventForm.summary!!,
            `when` = createEventForm.`when`
        )

        calendarService.createEvent(event)
        redirectAttribute.addFlashAttribute("message", "Successfully added the new event")
        return "redirect:/events/my"
    }

    /**
     * Populates the form for creating an event with valid information.
     * Useful so that users do not have to think
     * when filling out the form for testing.
     *
     * @param createEventForm
     */
    @GetMapping(value = ["/new"], params = ["auto"])
    fun createEventFormAutoPopulate(@ModelAttribute createEventForm: CreateEventForm): String {
        // provide default values to make user submission easier
        createEventForm.apply {
            summary = "A new event...."
            description = "This was autopopulated to save time creating a valid event."
            `when` = Calendar.getInstance()
        }

        // make the attendee not the current user
        val currentUser = userContext.getCurrentUser()
        val attendeeId = if (currentUser?.id == 0) 1 else 0
        val attendee = calendarService.getUser(attendeeId)
        createEventForm.attendeeEmail = attendee.email

        return "events/create"
    }
}