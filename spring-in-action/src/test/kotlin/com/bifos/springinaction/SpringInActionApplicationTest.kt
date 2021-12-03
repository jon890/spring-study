package com.bifos.springinaction

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SpringInActionApplicationTest {

    /**
     * 스프링 컨텍스트가 잘 로드 되는지 확인하는 테스트
     * 
     * @SpringBootTest 애노테이션이 작업을 수행하게 되어
     * 스프링 애플리케이션 컨텍스트가 로드 된다
     * 
     * 그리고 이때 문제가 없는지 테스트하며,
     * 만일 어떤 문제가 생기면 해당 테스트는 실패한다
     */
    @Test
    fun `스프링_컨텍스트_로드_확인`() {
        
    } 
}