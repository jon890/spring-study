package com.bifos.springinaction.domain.repository

import com.bifos.springinaction.domain.entity.Order

interface OrderRepository {

    fun save(order: Order): Order
}