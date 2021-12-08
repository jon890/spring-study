package com.bifos.springinaction.domain.entity

import org.hibernate.Hibernate
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id

@Entity
class Ingredient(
    @Id
    var id: String,

    val name: String,

    @Enumerated(EnumType.STRING)
    val type: Type
) {

    enum class Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Ingredient

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

}