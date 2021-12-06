package com.bifos.springinaction.domain.repository

import com.bifos.springinaction.domain.entity.Ingredient

interface IngredientRepository {

    fun findAll(): Iterable<Ingredient>

    fun findById(id: String): Ingredient?

    fun save(ingredient: Ingredient): Ingredient
}