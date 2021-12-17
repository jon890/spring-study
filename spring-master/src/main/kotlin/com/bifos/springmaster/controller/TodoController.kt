package com.bifos.springmaster.controller

import com.bifos.springmaster.domain.Todo
import com.bifos.springmaster.service.TodoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@RestController
class TodoController(
    val todoService: TodoService
) {

    @Operation(
        summary = "받은 이름으로 해당 유저의 모든 Todo 를 가져온다",
        description = "해당되는 todo 가 목록으로 반환된다. 현재 페이징은 지원되지 않는다",
    )
    @ApiResponse(
        responseCode = "200", description = "Todos 목록 반환",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = Todo::class))],
    )
    @GetMapping("/users/{name}/todos")
    fun retrieveTodos(@PathVariable name: String) = todoService.retrieveTodos(name)

    @GetMapping("/users/{name}/todos/{id}")
    fun retrieveTodo(@PathVariable name: String, @PathVariable id: Int): EntityModel<Todo> {
        val todo = todoService.retrieveTodo(id)

        // HATEOAS 링크
        // 모든 todos를 검색하는 링크를 리턴하도록 함
        val entityModel = EntityModel.of(todo)
        val linkTo = linkTo<TodoController> { retrieveTodos(name) }
            .withRel("parent")
        entityModel.add(linkTo)

        return entityModel
    }

    @PostMapping("/users/{name}/todos")
    fun addTodo(@PathVariable name: String, @Valid @RequestBody todo: Todo): ResponseEntity<out JvmType.Object> {
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
        return ResponseEntity.notFound().build()
    }
}
