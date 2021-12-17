package com.bifos.springmaster.controller

import com.bifos.springmaster.domain.Todo
import com.bifos.springmaster.service.TodoService
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@ExtendWith(SpringExtension::class)
@WebMvcTest(controllers = [TodoController::class])
class TodoControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var todoService: TodoService

    private var CREATED_TODO_ID = 3

    @DisplayName("Todo 목록을 반환한다")
    @Test
    fun retrieveTodos() {
        // given
        val mockList = listOf(
            Todo(1, "Jack", "Spring MVC Study", LocalDate.now(), false),
            Todo(2, "Jack", "Spring Boot Study", LocalDate.now(), false),
        )

        `when`(todoService.retrieveTodos(anyString())).thenReturn(mockList)

        // when
        val result = mvc.perform(
            MockMvcRequestBuilders.get("/users/Jack/todos")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andReturn()

        // then
        val expected =
            "[{id:1,user:\"Jack\",desc:\"Spring MVC Study\",done:false},{id:2,user:\"Jack\",desc:\"Spring Boot Study\",done:false}]"
        JSONAssert.assertEquals(expected, result.response.contentAsString, false)
    }

    @DisplayName("Todo를 반환한다")
    @Test
    fun retrieveTodo() {
        // given
        val mockList = listOf(
            Todo(1, "잭", "스프링 MVC 공부", LocalDate.now(), false),
            Todo(2, "잭", "스프링 부트 공부", LocalDate.now(), false),
        )

        `when`(todoService.retrieveTodo(anyInt())).thenReturn(mockList[0])

        // when
        val result = mvc.perform(
            MockMvcRequestBuilders.get("/users/잭/todos/1")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andReturn()

        // then
        val expected =
            "[{id:1,user:\"잭\",desc:\"스프링 MVC 공부\",done:false}]"
        JSONAssert.assertEquals(expected, result.response.contentAsString, false)
    }

    @DisplayName("Todo를 생성한다")
    @Test
    fun createTodo() {
        // given
        val mockTodo = Todo(CREATED_TODO_ID, "잭", "스프링 MVC 공부", LocalDate.now(), false)

        val todo = "{user:\"잭\",desc:\"스프링 시큐리티 공부\",done:false}"

        `when`(todoService.addTodo(anyString(), anyString(), isNull(), anyBoolean())).thenReturn(mockTodo)

        // when
        val result = mvc.perform(
            MockMvcRequestBuilders.post("/users/잭/todos")
                .content(todo)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated)
            .andExpect(header().string("location", containsString("/users/잭/todos/${CREATED_TODO_ID}")))
    }

    @DisplayName("Todo를 생성중 유효성 검사오류")
    @Test
    fun createTodo_withValidationError() {
        // given
        val mockTodo = Todo(CREATED_TODO_ID, "잭", "스프링 MVC 공부", LocalDate.now(), false)

        val todo = "{user:\"잭\",desc:\"스프링\",done:false}"

        `when`(todoService.addTodo(anyString(), anyString(), isNull(), anyBoolean())).thenReturn(mockTodo)

        // when
        mvc.perform(
            MockMvcRequestBuilders.post("/users/잭/todos")
                .content(todo)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().is4xxClientError)
            .andReturn()
    }

    @DisplayName("Todo를 업데이트한다")
    @Test
    fun updateTodo() {
        // given
        val UPDATED_TODO_ID = 1
        val mockTodo = Todo(UPDATED_TODO_ID, "잭", "스프링 MVC 공부 구아아아악", LocalDate.now(), false)
        val todo = "{user:\"잭\",desc:\"스프링 MVC 공부 구아아아악\",done:false}"

        `when`(todoService.update(mockTodo)).thenReturn(mockTodo)

        // when
        val result = mvc.perform(
            MockMvcRequestBuilders.post("/users/잭/todos/${UPDATED_TODO_ID}")
                .content(todo)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andReturn()

        JSONAssert.assertEquals(todo, result.response.contentAsString, false)
    }

    @DisplayName("Todo를 삭제합니다")
    fun deleteTodo() {
        // given
        val mockTodo = Todo(1, "잭", "스프링 MVC 공부", LocalDate.now(), false)
        val todo = "{user:\"잭\",desc:\"스프링 MVC 공부\",done:false}"

        `when`(todoService.deleteById(anyInt())).thenReturn(mockTodo)

        // when
        val result = mvc.perform(
            MockMvcRequestBuilders.post("/users/잭/todos/${mockTodo.id}")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound)
            .andReturn()

        JSONAssert.assertEquals(todo, result.response.contentAsString, false)
    }
}