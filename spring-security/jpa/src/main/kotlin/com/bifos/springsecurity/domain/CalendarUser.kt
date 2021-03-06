package com.bifos.springsecurity.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import java.security.Principal
import javax.persistence.*

/**
 * [CalendarUser] is this applications notion of a user. It is good to use your own objects to interact with a
 * user especially in large applications. This ensures that as you evolve your security requirements (update Spring
 * Security, leverage new Spring Security modules, or even swap out security implementations) you can do so easily.
 *
 * @author Rob Winch
 * @author BiFoS (jon89071@gmail.com)
 *
 */
@Entity
@Table(name = "calendar_users")
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
    @get:JvmName("getPassword1")
    @get:JsonIgnore
    open var password: String,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: MutableSet<Role> = mutableSetOf()

) : Principal, Serializable {

    companion object {
        private const val serialVersionUID = 8433999509932007961L
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null

    @JsonIgnore
    override fun getName(): String {
        return email
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