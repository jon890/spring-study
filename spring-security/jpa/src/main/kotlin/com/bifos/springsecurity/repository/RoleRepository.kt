package com.bifos.springsecurity.repository

import com.bifos.springsecurity.domain.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Int> {
}