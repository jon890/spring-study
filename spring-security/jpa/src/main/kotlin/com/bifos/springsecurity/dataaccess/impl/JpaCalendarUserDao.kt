package com.bifos.springsecurity.dataaccess.impl

import com.bifos.springsecurity.dataaccess.CalendarUserDao
import com.bifos.springsecurity.domain.CalendarUser
import com.bifos.springsecurity.domain.Role
import com.bifos.springsecurity.repository.CalendarUserRepository
import com.bifos.springsecurity.repository.RoleRepository
import org.springframework.stereotype.Repository
import javax.persistence.EntityNotFoundException

@Repository
class JpaCalendarUserDao(
    private val calendarUserRepository: CalendarUserRepository,
    private val roleRepository: RoleRepository
) : CalendarUserDao {

    override fun getUser(id: Int): CalendarUser {
        return calendarUserRepository.findById(id).orElseThrow { EntityNotFoundException("User not Found!! : id $id") }
    }

    override fun findUserByEmail(email: String): CalendarUser? {
        return calendarUserRepository.findByEmail(email).orElseGet { null }
    }

    override fun findUsersByEmail(partialEmail: String): List<CalendarUser> {
        return calendarUserRepository.findByEmailStartsWith(partialEmail)
    }

    override fun createUser(user: CalendarUser): Int {
        val roles = hashSetOf<Role>()
        roles.add(roleRepository.findById(0).orElseThrow { EntityNotFoundException("Role not Found!! : id 0") })

        user.roles.clear()
        user.roles.addAll(roles)

        val result = calendarUserRepository.save(user)
        calendarUserRepository.save(user)
        return result.id!!
    }
}