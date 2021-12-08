package com.bifos.springinaction.domain.repository

import com.bifos.springinaction.domain.entity.Ingredient
import org.springframework.data.jpa.repository.JpaRepository

interface IngredientRepository : JpaRepository<Ingredient, String> {

}