package com.bifos.springsecurity.dataaccess.impl

import com.bifos.springsecurity.dataaccess.CalendarUserDao
import com.bifos.springsecurity.domain.CalendarUser
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.ResultSet

/**
 * A jdbc implementation of [com.bifos.springsecurity.dataaccess.CalendarUserDao]
 *
 * @author Rob Winch
 * @author BiFoS (jon89071@gmail.com)
 */
@Repository
class JdbcCalendarUserDao(private val jdbcTemplate: JdbcTemplate) : CalendarUserDao {

    @Transactional(readOnly = true)
    override fun getUser(id: Int): CalendarUser {
        val user = jdbcTemplate.queryForObject("$CALENDAR_USER_QUERY id = ?", CALENDAR_USER_MAPPER, id)
        // JdbcTemplate에서 결과가 1개가 아니라면 예외 처리 됨
        return user!!
    }

    @Transactional(readOnly = true)
    override fun findUserByEmail(email: String): CalendarUser? {
        return try {
            jdbcTemplate.queryForObject("$CALENDAR_USER_QUERY email = ?", CALENDAR_USER_MAPPER, email)
        } catch (notFound: EmptyResultDataAccessException) {
            null
        }
    }

    @Transactional(readOnly = true)
    override fun findUsersByEmail(partialEmail: String): List<CalendarUser> {
        // Kotlin 에서 null 처리를 했으므로
        // "" 빈 문자열 처리만 하자
        if (partialEmail.isBlank()) {
            throw IllegalArgumentException("email cannot be empty string")
        }

        return jdbcTemplate.query(
            "$CALENDAR_USER_QUERY email like ? order by id",
            CALENDAR_USER_MAPPER,
            "${partialEmail}%"
        )
    }

    override fun createUser(user: CalendarUser): Int {
        if (user.id != null) {
            throw IllegalArgumentException("userToAdd.id must be null when creating a ${CalendarUser::class.simpleName}")
        }

        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update({ con ->
            con.prepareStatement(
                "insert into calendar_users (email, password, first_name, last_name) values (?, ?, ?, ?)",
                arrayOf("id")
            ).apply {
                setString(1, user.email)
                setString(2, user.password)
                setString(3, user.firstName)
                setString(4, user.lastName)
            }
        }, keyHolder)

        if (keyHolder.key == null) {
            throw RuntimeException("User Create Exception")
        }

        return keyHolder.key!!.toInt()
    }

    companion object {
        private const val CALENDAR_USER_QUERY =
            "select id, email, password, first_name, last_name from calendar_users where"

        private val CALENDAR_USER_MAPPER = CalendarUserRowMapper("calendar_users.")

        /**
         * Create a new RowMapper that resolvers [com.bifos.springsecurity.domain.CalendarUser]'s
         * given a column label prefix.
         * By allowing the prefix to be specified we can reuse the same [RowMapper] for joins in other tables.
         *
         * @author Rob Winch
         * @author BiFoS (jon89071@gmail.com)
         */
        class CalendarUserRowMapper(private val columnLabelPrefix: String) : RowMapper<CalendarUser> {
            override fun mapRow(rs: ResultSet, rowNum: Int): CalendarUser {
                return CalendarUser(
                    email = rs.getString("${columnLabelPrefix}email"),
                    password = rs.getString("${columnLabelPrefix}password"),
                    firstName = rs.getString("${columnLabelPrefix}first_name"),
                    lastName = rs.getString("${columnLabelPrefix}last_name"),
                ).apply {
                    id = rs.getInt("${columnLabelPrefix}id")
                }
            }
        }
    }
}