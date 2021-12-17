package com.bifos.springmaster.controller

import com.bifos.springmaster.domain.Todo
import com.bifos.springmaster.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@RestController
class TodoController(
    val todoService: TodoService
) {

    @GetMapping("/users/{name}/todos")
    fun retrieveTodos(@PathVariable name: String) = todoService.retrieveTodos(name)

    @GetMapping("/users/{name}/todos/{id}")
    fun retrieveTodo(@PathVariable name: String, @PathVariable id: Int) = todoService.retrieveTodo(id)

    @PostMapping("/users/{name}/todos")
    fun addTodo(@PathVariable name: String, @RequestBody todo: Todo): ResponseEntity<out JvmType.Object> {
        val newTodo = todoService.addTodo(name, todo.desc, todo.targetDate, todo.isDone)
        val location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newTodo.id)
            .toUri()

        return ResponseEntity.created(location).build()
    }

    @PutMapping("/users/{name}/todos/{id}")
    fun updateTodo(@PathVariable name: String, @PathVariable id: Int, @RequestBody todo: Todo): ResponseEntity<Todo> {
        todoService.update(todo)
        return ResponseEntity(todo, HttpStatus.OK)
    }

    @DeleteMapping("/users/{name}/todos/{id}")
    fun deleteTodo(@PathVariable name: String, @PathVariable id: Int): ResponseEntity<Unit> {
        todoService.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
