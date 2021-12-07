package com.bifos.springinaction.domain.repository.jdbc

import com.bifos.springinaction.domain.entity.Ingredient
import com.bifos.springinaction.domain.repository.IngredientRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class JdbcIngredientRepository(
    val jdbc: JdbcTemplate
) : IngredientRepository {

    override fun findAll(): Iterable<Ingredient> {
        return jdbc.query("select id, name, type from Ingredient", this::mapRowToIngredient)
    }

    override fun findById(id: String): Ingredient? {
        return jdbc.queryForObject("select id, name, type from Ingredient where id = ?", this::mapRowToIngredient, id)
    }

    override fun save(ingredient: Ingredient): Ingredient {
        jdbc.update(
            "insert into Ingredient (id, name, type) values (?, ?, ?)",
            ingredient.id,
            ingredient.name,
            ingredient.type.name
        )

        return ingredient
    }

    @Throws(SQLException::class)
    private fun mapRowToIngredient(rs: ResultSet, rowNum: Int): Ingredient {
        return Ingredient(
            id = rs.getString("id"),
            name = rs.getString("name"),
            type = Ingredient.Type.valueOf(rs.getString("type"))
        )
    }

}