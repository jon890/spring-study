package com.bifos.springsecurity.web.configuration

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor


/**
 * Spring Security Config Class
 * @see WebSecurityConfigurerAdapter
 */
@Configuration
@EnableWebSecurity
class SecurityConfig(private val calendarUserDetailsService: UserDetailsService) : WebSecurityConfigurerAdapter() {

    companion object {
        private val logger = LoggerFactory.getLogger(SecurityConfig::class.java)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(calendarUserDetailsService)
            .passwordEncoder(passwordEncoder())
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    /**
     * HTTP Security configuration
     *
     * <http auto-config="true"> is equivalent to:
     * <pre>
     *  <http>
     *      <form-login />
     *      <http-basic />
     *      <logout />
     *  </http>
     * </pre>
     *
     * Which is equivalent to the following JavaConfig:
     *
     * <pre>
     *     http.formLogin()
     *          .and().httpBasic()
     *          .and().logout();
     * </pre>
     *
     * @param http HttpSecurity configuration.
     * @throws Exception Authentication configuration exception
     *
     * @see <a href="http://docs.spring.io/spring-security/site/migrate/current/3-to-4/html5/migrate-3-to-4-jc.html">
     *     Spring Security 3 to 4 migration</a>
     */
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            // FIXME: TODO: Allow anyone to use H2 (NOTE: NOT FOR PRODUCTION USE EVER !!! )
            .antMatchers("/admin/h2/**").permitAll()

            .antMatchers("/").permitAll()
            .antMatchers("/login/*").permitAll()
            .antMatchers("/logout/*").permitAll()
            .antMatchers("/signup/*").permitAll()
            .antMatchers("/errors/**").permitAll()
            .antMatchers("/admin/*").hasRole("ADMIN")
            .antMatchers("/events/").hasRole("ADMIN")
            .antMatchers("/**").hasRole("USER")

            .and().exceptionHandling().accessDeniedPage("/errors/403")

            .and().formLogin()
            .loginPage("/login/form")
            .loginProcessingUrl("/login")
            .failureUrl("/login/form?error")
            .usernameParameter("username")
            .passwordParameter("password")
            // default로 로그인하기 전의 페이지로 이동하지만
            // alwaysUse를 true로 주면 defaultSuccessUrl로 무조건 이동한다
            .defaultSuccessUrl("/default", true)
            .permitAll() // 무슨 의미일까?

            .and().logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login/form?logout")
            .permitAll()

            .and().anonymous()

            // CSRF is enabled by default, with Java Config
            .and().csrf().disable()

        // Enable <frameset> in order to use H2 web console
        http.headers().frameOptions().disable()
    }

    /**
     * This is the equivalent to:
     * <pre><http pattern="/resources/\\**" security="none"/></pre>
     *
     * @param web
     * @throws Exception
     */
    override fun configure(web: WebSecurity) {
        web.ignoring()
            .antMatchers("/static/**")
            .antMatchers("/webjars/**")

        // Thymeleaf needs to use the Thymeleaf configured FilterSecurityInterceptor
        // and not the default Filter from AutoConfiguration.
        val http = http
        web.postBuildAction {
            web.securityInterceptor(http.getSharedObject(FilterSecurityInterceptor::class.java))
        }
    }
}