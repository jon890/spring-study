package com.bifos.springinaction

import com.bifos.springinaction.domain.entity.Ingredient
import com.bifos.springinaction.domain.repository.IngredientRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean


fun main(args: Array<String>) {
    runApplication<SpringInActionApplication>(*args)
}

@SpringBootApplication
class SpringInActionApplication {

    @Bean
    fun dataLoader(ingredientRepository: IngredientRepository): CommandLineRunner {
        return CommandLineRunner {
            ingredientRepository.save(Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP))
            ingredientRepository.save(Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP))
            ingredientRepository.save(Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN))
            ingredientRepository.save(Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN))
            ingredientRepository.save(Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES))
            ingredientRepository.save(Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES))
            ingredientRepository.save(Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE))
            ingredientRepository.save(Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE))
            ingredientRepository.save(Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE))
            ingredientRepository.save(Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE))
        }
    }
}