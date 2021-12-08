package com.bifos.springinaction.domain.entity

import org.hibernate.Hibernate
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    var createdAt: LocalDateTime? = null

    // 해당 어노테이션이 필드에 적용되도록 함
    @field:NotNull
    @field:Size(min = 5, message = "이름은 최소한 5자 이상이어야 합니다")
    var name: String? = null

    @ManyToMany(targetEntity = Ingredient::class)
    @field:Size(min = 1, message = "최소한 한 개의 구성요소는 선택해야 합니다")
    var ingredients: List<Ingredient> = mutableListOf()

    @PrePersist
    fun createdAt() {
        this.createdAt = LocalDateTime.now()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Taco

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}