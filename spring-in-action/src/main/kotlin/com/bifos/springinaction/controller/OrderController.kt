package com.bifos.springinaction.controller

import com.bifos.springinaction.domain.entity.Order
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.Valid

@Controller
@RequestMapping("/orders")
class OrderController {

    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/current")
    fun orderForm(model: Model): String {
        model.addAttribute("order", Order())
        return "orderForm"
    }

    @PostMapping
    fun processOrder(@Valid order: Order, errors: Errors) : String {
        if (errors.hasErrors()) {
            return "orderForm"
        }

        logger.info("Order submitted: $order")
        return "redirect:/"
    }
}