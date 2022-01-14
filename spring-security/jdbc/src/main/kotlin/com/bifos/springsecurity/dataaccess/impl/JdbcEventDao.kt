package com.bifos.springsecurity.dataaccess.impl

import com.bifos.springsecurity.dataaccess.EventDao
import com.bifos.springsecurity.domain.Event
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.Date
import java.util.*

/**
 * A jdbc implementation of [com.bifos.springsecurity.dataaccess.EventDao]
 *
 * @author Rob Winch
 * @author BiFoS (jon89071@gmail.com)
 */
@Repository
class JdbcEventDao(private val jdbcTemplate: JdbcTemplate) : EventDao {

    @Transactional(readOnly = true)
    override fun getEvent(eventId: Int): Event {
        val event = jdbcTemplate.queryForObject("${EVENT_QUERY} and e.id = ?", EVENT_ROW_MAPPER, eventId)

        if (event == null) {
            throw RuntimeException("Event not Found")
        }

        return event
    }

    override fun createEvent(event: Event): Int {
//        if (event.id == null) {
//            throw IllegalArgumentException("event.id must be null when creating a new Message")
//        }

        if (event.owner == null) {
            throw IllegalArgumentException("event.owner cannot be null")
        }

        if (event.attendee == null) {
            throw IllegalArgumentException("event.attendee cannot be null")
        }

        if (event.`when` == null) {
            throw IllegalArgumentException("event.when cannot be null")
        }

        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update({ con ->
            con.prepareStatement(
                "insert into events (when,summary,description,owner,attendee) values (?, ?, ?, ?, ?)",
                arrayOf("id")
            ).apply {
                setDate(1, Date(event.`when`!!.timeInMillis))
                setString(2, event.summary)
                setString(3, event.description)
                setInt(4, event.owner!!.id!!)
                setObject(5, if (event.attendee == null) null else event.attendee!!.id)
            }
        }, keyHolder)

        if (keyHolder.key == null) {
            throw RuntimeException("Event Create Exception")
        }

        return keyHolder.key!!.toInt()
    }

    override fun findForUser(userId: Int): List<Event> {
        return jdbcTemplate.query(
            "${EVENT_QUERY} and (e.owner = ? or e.attendee = ?) order by e.id",
            EVENT_ROW_MAPPER,
            userId,
            userId
        )
    }

    override fun getEvents(): List<Event> {
        return jdbcTemplate.query("${EVENT_QUERY} order by e.id", EVENT_ROW_MAPPER)
    }

    companion object {
        private val EVENT_ROW_MAPPER = RowMapper<Event> { rs, rowNum ->
            Event(
                summary = rs.getString("events.summary"),
                description = rs.getString("events.description"),
                attendee = ATTENDEE_ROW_MAPPER.mapRow(rs, rowNum),
                owner = OWNER_ROW_MAPPER.mapRow(rs, rowNum),
            ).apply {
                id = rs.getInt("events.id")
                `when` = Calendar.getInstance().apply {
                    this.time = rs.getDate("events.when")
                }
            }
        }

        private val ATTENDEE_ROW_MAPPER = JdbcCalendarUserDao.Companion.CalendarUserRowMapper("attendee_")
        private val OWNER_ROW_MAPPER = JdbcCalendarUserDao.Companion.CalendarUserRowMapper("owner_")

        private const val EVENT_QUERY = "select e.id, e.summary, e.description, e.when, " +
                "owner.id as owner_id, owner.email as owner_email, owner.password as owner_password, owner.first_name as owner_first_name, owner.last_name as owner_last_name, " +
                "attendee.id as attendee_id, attendee.email as attendee_email, attendee.password as attendee_password, attendee.first_name as attendee_first_name, attendee.last_name as attendee_last_name " +
                "from events as e, calendar_users as owner, calendar_users as attendee " +
                "where e.owner = owner.id and e.attendee = attendee.id"
    }
}