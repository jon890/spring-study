package com.bifos.springsecurity.web.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
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
 * converted by BiFoS (jon89071@gmail.com)
 */
@Configuration
@EnableWebMvc
class WebMvcConfig : WebMvcConfigurer {

    companion object {
        private val CLASSPATH_RESOURCE_LOCATIONS = listOf(
            "classpath:/META-INF/resources/", "classpath:/resources",
            "classpath:/static/", "classpath:/public"
        )
    }

    // 간단하게 url ~ view 매핑
    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/").setViewName("index")
        registry.addViewController("/login/form").setViewName("login")
        registry.addViewController("/errors/403").setViewName("/errors/403")

        registry.setOrder(Ordered.HIGHEST_PRECEDENCE)
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/resources/**")
            .addResourceLocations("/resources")
            .setCachePeriod(0) // Set to 0 in order to send cache headers that prevent caching

        // Add WebJars for Bootstrap & jQuery
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/"
            ).resourceChain(true)
        }

        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**").addResourceLocations(*CLASSPATH_RESOURCE_LOCATIONS.toTypedArray())
        }
    }
}