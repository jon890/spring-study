package com.bifos.springinaction.controller

import com.bifos.springinaction.domain.entity.Order
import com.bifos.springinaction.domain.repository.OrderRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.SessionAttributes
import org.springframework.web.bind.support.SessionStatus
import javax.validation.Valid

@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
class OrderController(
    val orderRepository: OrderRepository
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/current")
    fun orderForm(): String {
        return "orderForm"
    }

    @PostMapping
    fun processOrder(
        @Valid order: Order,
        errors: Errors,
        sessionStatus: SessionStatus
    ): String {
        if (errors.hasErrors()) {
            return "orderForm"
        }

        orderRepository.save(order)

        /**
         * 주문 객체가 DB 에 저장된 후에는 세션에 보존할 필요가 없다
         * 제거하지 않으면 이전 주문 및 연관된 타코가 세션에 남게 되어
         * 다음 주문은 이전 주문에 포함된 타코 객체를 가지고 시작하게 된다
         * 
         * 따라서 setComplete() 를 호출해 세션을 재설정한다
         */
        sessionStatus.setComplete()
        return "redirect:/"
    }
}