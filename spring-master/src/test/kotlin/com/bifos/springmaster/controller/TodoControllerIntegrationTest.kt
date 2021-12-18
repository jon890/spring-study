package com.bifos.springmaster.controller

import com.bifos.springmaster.SpringMasterApplication
import com.bifos.springmaster.domain.Todo
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.nio.charset.Charset
import java.time.LocalDate
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [SpringMasterApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = [SecurityAutoConfiguration::class]) // Security AutoConfig 해제
class TodoControllerIntegrationTest {

    @Autowired
    private lateinit var template: TestRestTemplate

    val headers = createHeaders("BiFoS", "1234")

    /**
     * 인증 헤더를 만드는 메소드
     */
    private final fun createHeaders(username: String, password: String): HttpHeaders {
        return HttpHeaders().apply {
            val auth = "${username}:${password}"
            val encodedAuth = Base64.getEncoder().encode(auth.toByteArray(Charset.forName("US-ASCII")))
            val authHeader = "Basic $encodedAuth"
            set("Authorization", authHeader)
        }
    }

    @DisplayName("Todo 목록을 반환한다")
    @Test
    fun retrieveTodos() {
        val expected =
            "[{id:1,user:\"포스\",desc:\"스프링 MVC를 배우자\",done:false},{id:2,user:\"포스\",desc:\"스트럿츠를 배우자\",done:false}]"
        val response =
            template.exchange("/users/포스/todos", HttpMethod.GET, HttpEntity(null, headers), String::class.java)
        JSONAssert.assertEquals(expected, response.body, false)
    }

    @DisplayName("Todo를 추가한다")
    @Test
    fun addTodo() {
        val todo = Todo(-1, "kbt", "JPA 공부", LocalDate.now(), false)
        val location = template.postForLocation("/users/kbt/todos", todo)
        assertThat(location.path, containsString("/users/kbt/todos/4"))
    }

    @DisplayName("Todo를 업데이트한다")
    @Test
    fun updateTodo() {
        val expected = "{id:3,user:\"병태\",desc:\"스프링 5를 공부하자\",done:false}"
        val todo = Todo(3, "병태", "스프링 5를 공부하자", LocalDate.now(), false)
        val response = template.exchange(
            "/users/병태/todos/${todo.id}",
            HttpMethod.PUT,
            HttpEntity(todo, headers),
            String::class.java
        )
        JSONAssert.assertEquals(expected, response.body, false)
    }

    @DisplayName("Todo를 삭제한다")
    @Test
    fun deleteTodo() {
        val response =
            template.exchange("/users/병태/todos/3", HttpMethod.DELETE, HttpEntity(null, headers), String::class.java)
        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }
}