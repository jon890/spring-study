package com.bifos.springmaster.controller

import com.bifos.springmaster.service.TodoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TodoController(
    val todoService: TodoService
) {

    @GetMapping("/users/{name}/todos")
    fun retrieveTodos(@PathVariable name : String) = todoService.retrieveTodos(name)
}