package com.bifos.springinaction.domain.entity

import java.time.LocalDateTime
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class Taco {
    var id: Long? = null

    var createdAt: LocalDateTime? = null

    // 해당 어노테이션이 필드에 적용되도록 함
    @field:NotNull
    @field:Size(min = 5, message = "이름은 최소한 5자 이상이어야 합니다")
    var name: String? = null

    // todo 왜 바인딩이 안될까..?
    @field:Size(min = 1, message = "최소한 한 개의 구성요소는 선택해야 합니다")
    var ingredients: List<Ingredient> = mutableListOf()

    override fun toString(): String {
        return "Taco(name='$name', ingredients=$ingredients)"
    }
}