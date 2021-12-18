package com.bifos.springmaster.controller

import com.bifos.springmaster.SpringMasterApplication
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [SpringMasterApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BasicControllerIntegrationTest {

    @Autowired
    private lateinit var template: TestRestTemplate

    @DisplayName("문자열을 반환한다")
    @Test
    fun welcome() {
        val response = template.getForEntity("/welcome", String::class.java)
        assertThat(response.body, equalTo("Hello World"))
    }

    @DisplayName("오브젝트를 JSON으로 반환한다")
    @Test
    fun welcomeWithObject() {
        val response = template.getForEntity("/welcome-with-object", String::class.java)
        assertThat(response.body, containsString("Hello World"))
    }

    @DisplayName("PathVariable을 받아 오브젝트를 만들고, JSON을 반환한다")
    @Test
    fun welcomeWithParameter() {
        val response = template.getForEntity("/welcome-with-parameter/name/BiFoS", String::class.java)
        assertThat(response.body, containsString("Hello World, BiFoS"))
    }
}