package com.bifos.springinaction.controller

import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(HomeController::class)
class HomeControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `홈페이지_테스트`() {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk)
            .andExpect(view().name("home"))
            .andExpect(content().string(containsString("환영합니다..")))
    }
}