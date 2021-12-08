package com.bifos.springinaction.domain.repository

import com.bifos.springinaction.domain.entity.Taco
import org.springframework.data.jpa.repository.JpaRepository

interface TacoRepository : JpaRepository<Taco, Long> {

}