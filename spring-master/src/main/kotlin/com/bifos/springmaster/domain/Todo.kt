package com.bifos.springmaster.domain

import java.time.LocalDate

data class Todo(
    val id: Int,
    val user: String,
    var desc: String,
    var targetDate: LocalDate,
    var isDone: Boolean
) {

    fun update(todo: Todo) {
        apply {
            desc = todo.desc
            targetDate = todo.targetDate
            isDone = todo.isDone
        }
    }
}