package com.bifos.springsecurity

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * https://docs.spring.io/spring-security/site/docs/current/reference/html/test-method.html
/Users/gimbyeongtae/Downloads/Spring Security Code_Revised/chapter02/chapter02.00-calendar/src/test/java/com/packtpub/springsecurity/CalendarApplicationTests.java */
@ExtendWith(SpringExtension::class)
@SpringBootTest
class CalendarApplicationTests {

    @Autowired
    lateinit var mvc: MockMvc

    @Test
    @DisplayName("시큐리티 적용 확인")
    fun securityEnabled() {
        mvc.perform(
            get("/admin/h2")
                .header("X-Requested-With", "XMLHttpRequest")
        ).andExpect(status().isUnauthorized)
    }

    @Test
    @DisplayName("익명 사용자로 이벤트 페이지 접속 테스트")
    fun test_events_WithAnonymousUser() {
        mvc.perform(
            get("/events/")
        ).andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("http://localhost/login/form"))
    }
}