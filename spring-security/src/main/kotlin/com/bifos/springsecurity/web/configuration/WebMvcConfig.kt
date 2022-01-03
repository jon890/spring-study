package com.bifos.springsecurity.web.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.*


/**
 *
 *
 * Here we leverage Spring's [EnableWebMvc] support. This allows more powerful configuration but still be
 * concise about it. Specifically it allows overriding [requestMappingHandlerMapping].
 * Note that this class is loaded via the WebAppInitializer
 *
 *
 * @author Rob Winch
 * @author Mick Knutson
 * converted by (BiFos)
 */
@Configuration
@EnableWebMvc
class WebMvcConfig : WebMvcConfigurer {

    // 간단하게 url ~ view 매핑
    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/").setViewName("index")
    }
}