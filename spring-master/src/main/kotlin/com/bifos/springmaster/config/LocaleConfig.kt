package com.bifos.springmaster.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.*

@Configuration
class LocaleConfig {

    @Bean
    fun localeResolver(): LocaleResolver {
        return SessionLocaleResolver().apply {
            setDefaultLocale(Locale.KOREA)
        }
    }

    @Bean
    fun messageSource() : ResourceBundleMessageSource {
        return ResourceBundleMessageSource().apply {
            setBasename("messages") // message_kr.properties 메시지 프로퍼티를 사용
            setUseCodeAsDefaultMessage(true) // 메시지를 찾지 못함녀 코드는 기본 메시지를 반환
        }
    }
}