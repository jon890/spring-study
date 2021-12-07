package com.bifos.springinaction.domain.entity

import org.hibernate.validator.constraints.CreditCardNumber
import java.time.LocalDateTime
import javax.validation.constraints.Digits
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class Order(

    var id: Long? = null,

    var placedAt: LocalDateTime? = null,

    @field:NotBlank(message = "이름은 필수여야 합니다")
    var deliveryName: String? = null,

    @field:NotBlank(message = "거리명은 필수여야 합니다")
    val deliveryStreet: String? = null,

    @field:NotBlank(message = "도시명은 필수여야 합니다")
    val deliveryCity: String? = null,

    @field:NotBlank(message = "주는 필수여야 합니다")
    val deliveryState: String? = null,

    @field:NotBlank(message = "우편변호는 필수여야 합니다")
    val deliveryZip: String? = null,

    // 룬(Luhn) 알고리즘 검사에 합격한 유효한 신용 카드 번호인지 확인
    // 하지만 실재로 존재하는 카드인지, 대금 지불에 사용할 수 있는지는 확인할 수 없음
    @field:CreditCardNumber(message = "유효하지 않은 신용카드 입니다")
    val ccNumber: String? = null,

    @field:Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message = "MM/YY 형식으로 입력해주세요")
    val ccExpiration: String? = null,

    @field:Digits(integer = 3, fraction = 0, message = "잘못된 CVV 입니다")
    val ccCVV: String? = null,

    val tacos: MutableList<Taco> = mutableListOf()
) {
    override fun toString(): String {
        return "Order(deliveryName='$deliveryName', deliveryStreet='$deliveryStreet', deliveryCity='$deliveryCity', deliveryState='$deliveryState', deliveryZip='$deliveryZip', ccNumber='$ccNumber', ccExpiration='$ccExpiration', ccCVV='$ccCVV')"
    }

    fun addDesign(taco: Taco) {
        tacos.add(taco)
    }
}
