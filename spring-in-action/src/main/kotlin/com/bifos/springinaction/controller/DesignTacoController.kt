package com.bifos.springinaction.controller

import com.bifos.springinaction.domain.entity.Ingredient
import com.bifos.springinaction.domain.entity.Order
import com.bifos.springinaction.domain.entity.Taco
import com.bifos.springinaction.domain.repository.IngredientRepository
import com.bifos.springinaction.domain.repository.TacoRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
/*
 * 하나의 세션에서 생성되는 Taco 와 다르게
 * 주문은 다수의 HTTP 요청에 걸쳐 존재해야한다
 * 다수의 타코를 생성하고 그것을 하나의 주문으로 추가할 수 있게 하기 위해서다
 * 이때 클래스 수준의 @SessionAttributes 애노테이션을 주문과 같은 모델 객체에 지정하면 된다
 * 그러면 세션에서 계속 보존되면서 다수의 요청에 걸쳐 사용될 수 있다
 */
class DesignTacoController(
    private val ingredientRepository: IngredientRepository,
    private val tacoRepository: TacoRepository
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun showDesignForm(model: Model): String {
        val ingredients = mutableListOf<Ingredient>()
        ingredientRepository.findAll().forEach { ingredients.add(it) }

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

    // 객체가 모델에 생성되게 함
    @ModelAttribute(name = "order")
    fun order(): Order {
        return Order()
    }

    @ModelAttribute(name = "taco")
    fun taco(): Taco {
        return Taco()
    }

    @PostMapping
    fun processDesign(
        @Valid design: Taco,
        errors: Errors,
        /**
         * 해당 매개변수는 모델로부터 전달되어야함
         * 스프링 MVC 가 이매개변수에 요청 매개변수를 바인딩하지 않아야한다는 것을 명시
         */
        @ModelAttribute order: Order
    ): String {
        logger.info("Your Taco ===> $design")

        if (errors.hasErrors()) {
            return "design"
        }

        val newTaco = tacoRepository.save(design)
        order.addDesign(newTaco)

        return "redirect:/orders/current"
    }
}