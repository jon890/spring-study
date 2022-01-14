package com.bifos.springsecurity.domain

import javax.persistence.*

@Entity
@Table(name = "role")
class Role(

    val name: String,

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    val users: MutableSet<CalendarUser> = mutableSetOf()
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int? = null

}