package com.bifos.springinaction.domain.repository

import com.bifos.springinaction.domain.entity.Taco

interface TacoRepository {

    fun save(design : Taco) : Taco
}