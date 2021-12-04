package com.bifos.springinaction.domain

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class Taco(

    // 해당 어노테이션이 필드에 적용되도록 함
    @field:NotNull
    @field:Size(min = 5, message = "이름은 최소한 5자 이상이어야 합니다")
    val name: String? = null,

    @field:Size(min = 1, message = "최소한 한 개의 구성요소는 선택해야 합니다")
    val ingredients: List<Ingredient> = mutableListOf()
) {
    override fun toString(): String {
        return "Taco(name='$name', ingredients=$ingredients)"
    }
}