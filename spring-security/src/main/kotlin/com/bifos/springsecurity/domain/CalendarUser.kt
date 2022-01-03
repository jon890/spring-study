package com.bifos.springsecurity.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable

/**
 * {@link CalendarUser} is this applications notion of a user. It is good to use your own objects to interact with a
 * user especially in large applications. This ensures that as you evolve your security requirements (update Spring
 * Security, leverage new Spring Security modules, or even swap out security implementations) you can do so easily.
 *
 * @author Rob Winch (converted by BiFoS)
 *
 */
class CalendarUser(
    var firstName: String,

    var lastName: String,

    var email: String,

    /**
     * Gets the password for this user. In some instances, this password is not actually used. For example, when an in
     * memory authentication is used the password on the spring security User object is used.
     *
     * @return
     */
    // todo kbt : 자바로 변환하여 어디에 애노테이션이 붙어있는지 확인해보자
    @get:JsonIgnore
    var password: String? = null

) : Serializable {

    companion object {
        private const val serialVersionUID = 8433999509932007961L
    }

    var id: Int? = null

    @JsonIgnore
    fun getName(): String {
        return "${lastName}, ${firstName}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CalendarUser) return false

        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (email != other.email) return false
        if (password != other.password) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + (id ?: 0)
        return result
    }

}