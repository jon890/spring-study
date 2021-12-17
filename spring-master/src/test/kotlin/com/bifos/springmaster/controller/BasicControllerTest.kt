package com.bifos.springmaster.controller

import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@ExtendWith(SpringExtension::class)
@WebMvcTest(controllers = [BasicController::class]) // Controller layer 테스트, 나머지 필요한 bean 들은 직접 셋팅해야 함 => 가볍고 빠르게 테스트 가능
class BasicControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @DisplayName("Welcome 문자열 반환 테스트")
    @Test
    fun welcome() {
        mvc.perform(
            MockMvcRequestBuilders.get("/welcome")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
            .andExpect(content().string(equalTo("Hello World")))
    }

    @DisplayName("Welcome 오브젝트 반환 테스트")
    @Test
    fun welcomeWithObject() {
        mvc.perform(
            MockMvcRequestBuilders.get("/welcome-with-object")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
            .andExpect(content().string(containsString("Hello World")))
    }

    @DisplayName("Welcome Path 매개변수가 있는 오브젝트 반환 테스트")
    @Test
    fun welcomeWithParameter() {
        mvc.perform(
            MockMvcRequestBuilders.get("/welcome-with-parameter/name/BiFoS")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
            .andExpect(content().string(containsString("Hello World, BiFoS!")))
    }
}