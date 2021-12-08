package com.bifos.springinaction.domain.repository

import com.bifos.springinaction.domain.entity.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long> {

}