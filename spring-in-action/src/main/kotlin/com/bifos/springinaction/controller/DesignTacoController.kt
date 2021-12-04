package com.bifos.springinaction.controller

import com.bifos.springinaction.domain.Ingredient
import com.bifos.springinaction.domain.Taco
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*
import javax.validation.Valid

@Controller
@RequestMapping("/design")
class DesignTacoController {

    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun showDesignForm(model: Model): String {
        val ingredients = listOf(
            Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
            Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
            Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
            Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
            Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
            Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
            Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
            Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
            Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
            Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE),
        )

        val types = Ingredient.Type.values()
        types.forEach {
            model.addAttribute(it.toString().lowercase(Locale.getDefault()), filterByType(ingredients, it))
        }

        model.addAttribute("taco", Taco())

        return "design"
    }

    private fun filterByType(ingredients: List<Ingredient>, type: Ingredient.Type): List<Ingredient> {
        return ingredients.filter { it.type == type }
    }

    @PostMapping
    fun processDesign(@Valid design: Taco, errors: Errors): String {
        if (errors.hasErrors()) {
            return "design"
        }

        // 타코 디자인 저장
        // 3장에서 계속..
        logger.info("Process Design : $design")

        return "redirect:/orders/current"
    }
}