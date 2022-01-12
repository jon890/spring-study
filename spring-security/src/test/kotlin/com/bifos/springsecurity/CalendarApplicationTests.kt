package com.bifos.springsecurity

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

/**
 * https://docs.spring.io/spring-security/site/docs/current/reference/html/test-method.html
 */
@DirtiesContext
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [SpringSecurityApplication::class])
@AutoConfigureMockMvc
@Transactional
class CalendarApplicationTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    @DisplayName("유저1 로그인 테스트")
    fun test_user1_Login() {
        mockMvc.perform(
            post("/login")
                .accept(MediaType.TEXT_HTML)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "user1@example.com")
                .param("password", "user1")
        )
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/default"))
            .andDo(print())
    }
}