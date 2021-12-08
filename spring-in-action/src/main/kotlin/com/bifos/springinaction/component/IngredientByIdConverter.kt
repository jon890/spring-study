package com.bifos.springinaction.component

import com.bifos.springinaction.domain.entity.Ingredient
import com.bifos.springinaction.domain.repository.IngredientRepository
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class IngredientByIdConverter(
    val ingredientRepository: IngredientRepository
) : Converter<String, Ingredient?> {

    override fun convert(source: String): Ingredient? {
        return ingredientRepository.findById(source).orElseGet(null)
    }
}