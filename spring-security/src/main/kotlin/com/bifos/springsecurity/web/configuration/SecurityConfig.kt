package com.bifos.springsecurity.configuration

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

/**
 * Spring Security Config Class
 * @see WebSecurityConfigurerAdapter
 */
@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    companion object {
        private val logger = LoggerFactory.getLogger(SecurityConfig::class.java)
    }

    /**
     * Configure AuthenticationManager with inMemory credentials.
     *
     * @param auth : AuthenticationManagerBuilder
     * @throws Exception Authentication exception
     */
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.let {
            it.inMemoryAuthentication()
                .withUser("user1@example.com").password("user1").roles("USER")

            logger.info("***** Password for user 'user1@example.com' is 'user1'");
        }
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
    override fun configure(http: HttpSecurity?) {

        http?.let {
            it.authorizeRequests()
                .antMatchers("/**").access("hasRole('USER')")
                .and().formLogin()
                .and().httpBasic()
                .and().logout()

                // CSRF is enabled by default, with Java Config
                .and().csrf().disable()
        }
    }
}