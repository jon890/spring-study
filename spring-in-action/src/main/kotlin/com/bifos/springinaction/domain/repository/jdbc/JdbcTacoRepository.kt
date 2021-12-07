package com.bifos.springinaction.domain.repository.jdbc

import com.bifos.springinaction.domain.entity.Ingredient
import com.bifos.springinaction.domain.entity.Taco
import com.bifos.springinaction.domain.repository.TacoRepository
import com.bifos.springinaction.util.TimeUtil.parseLong
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementCreatorFactory
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import java.sql.Types
import java.time.LocalDateTime

@Repository
class JdbcTacoRepository(
    val jdbc: JdbcTemplate
) : TacoRepository {

    override fun save(taco: Taco): Taco {
        val tacoId = saveTacoInfo(taco)
        taco.id = tacoId
        taco.ingredients.forEach {
            saveIngredientToTaco(it, tacoId)
        }
        return taco
    }

    private fun saveTacoInfo(taco: Taco): Long {
        taco.createdAt = LocalDateTime.now()
        val pscFactory = PreparedStatementCreatorFactory(
            "insert into Taco (name, createdAt) values (?, ?)",
            Types.VARCHAR, Types.TIMESTAMP
        )
        pscFactory.setReturnGeneratedKeys(true)

        val psc = pscFactory.newPreparedStatementCreator(
            listOf(
                taco.name,
                Timestamp(taco.createdAt!!.parseLong())
            )
        )

        val keyHolder = GeneratedKeyHolder()
        jdbc.update(psc, keyHolder)
        return keyHolder.key?.toLong() ?: -1
    }

    private fun saveIngredientToTaco(ingredient: Ingredient, tacoId: Long) {
        jdbc.update(
            "insert into Taco_Ingredients (taco, ingredient) " +
                    "values (?, ?)",
            tacoId, ingredient.id
        )
    }
}