package com.bifos.springinaction.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    // 간단하게 url ~ view 매핑을 할 때는 컨트롤러 클래스를 작성하지 않고
    // 다음의 Config 를 사용하면 편하다
    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/").setViewName("home")
    }
}