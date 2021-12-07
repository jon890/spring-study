package com.bifos.springinaction.domain.repository.jdbc

import com.bifos.springinaction.domain.entity.Order
import com.bifos.springinaction.domain.entity.Taco
import com.bifos.springinaction.domain.repository.OrderRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class JdbcOrderRepository(
    jdbc: JdbcTemplate
) : OrderRepository {

    val orderInserter: SimpleJdbcInsert
    val orderTacoInserter: SimpleJdbcInsert
    val objectMapper: ObjectMapper

    /**
     * Kotlin Init Block
     * 생성자의 코드라고 생각하면 됨
     */
    init {
        // 생성자에서 받은 Jdbc 변수를 사용할 수 있다
        orderInserter = SimpleJdbcInsert(jdbc)
            .withTableName("Taco_Order")
            .usingGeneratedKeyColumns("id")

        orderTacoInserter = SimpleJdbcInsert(jdbc)
            .withTableName("Taco_Order_Tacos")

        objectMapper = ObjectMapper()
    }

    override fun save(order: Order): Order {
        order.placedAt = LocalDateTime.now()
        val orderId = saveOrderDetails(order)
        order.id = orderId

        order.tacos.forEach {
            saveTacoToOrder(it, orderId)
        }

        return order
    }

    private fun saveOrderDetails(order: Order): Long {
        // todo : Generic , Star-Projection 공부
        // Object -> Map 변환
        var values = objectMapper.convertValue(order, Map::class.java) as Map<String, *>
        values = values.toMutableMap()
        values["placedAt"] = order.placedAt

        val orderId = orderInserter.executeAndReturnKey(values).toLong()
        return orderId
    }

    private fun saveTacoToOrder(taco: Taco, orderId: Long) {
        val values = mutableMapOf<String, Any>()
        values["tacoOrder"] = orderId
        values["taco"] = taco.id!!
        orderTacoInserter.execute(values)
    }

}