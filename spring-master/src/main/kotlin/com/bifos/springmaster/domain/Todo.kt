package com.bifos.springmaster.domain

import java.time.LocalDate
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class Todo(
    val id: Int = -1,

    @NotNull
    val user: String,

    @Size(min = 9, message = "최소 10자는 입력해주세요")
    var desc: String,

    var targetDate: LocalDate = LocalDate.now(),

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