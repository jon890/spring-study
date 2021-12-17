package com.bifos.springmaster.controller

import com.bifos.springmaster.SpringMasterApplication
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
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

    @Test
    fun welcome() {
        val response = template.getForEntity("/welcome", String::class.java)
        assertThat(response.body, equalTo("Hello World"))
    }
}