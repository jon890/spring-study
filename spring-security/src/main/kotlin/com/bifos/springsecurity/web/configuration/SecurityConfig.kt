package com.bifos.springsecurity.web.configuration

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager

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
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
            .withUser("user").password("{noop}user").roles("USER")
            .and().withUser("admin").password("{noop}admin").roles("USER", "ADMIN")
            .and().withUser("user1@example.com").password("{noop}user1").roles("USER")
            .and().withUser("admin1@example.com").password("{noop}admin1").roles("USER", "ADMIN")
    }

    /**
     * The parent method from [WebSecurityConfigurerAdapter] [userDetailsService]
     * originally returns a [org.springframework.security.core.userdetails.UserDetailsService],
     * but this needs to boe a [org.springframework.security.provisioning.UserDetailsManager]
     * UserDetailsManager vs UserDetailsService
     *
     */
    @Bean
    override fun userDetailsService(): UserDetailsService {
        return InMemoryUserDetailsManager().apply {
            this.createUser(User.withUsername("user").password("{noop}user").roles("USER").build())
            this.createUser(User.withUsername("admin").password("{noop}admin").roles("USER", "ADMIN").build())
            this.createUser(User.withUsername("user1@example.com").password("{noop}user1").roles("USER").build())
            this.createUser(
                User.withUsername("admin1@example.com").password("{noop}admin1").roles("USER", "ADMIN").build()
            )
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
                .antMatchers("/static/**").permitAll()

                // H2 console:
                .antMatchers("/admin/h2/**").permitAll()

                .antMatchers("/").permitAll()
                // Spring Expression Language SPEL 을 이용하여 판별 가능
//                .antMatchers("/").access("hasAnyRole('ANONYMOUS', 'USER'")

                .antMatchers("/login/*").permitAll()
                .antMatchers("/logout/*").permitAll()
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
                .defaultSuccessUrl("/default", true)
                .permitAll() // 무슨 의미일까?

                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login/form?logout")
                .permitAll()

                .and().httpBasic()

                .and().anonymous()

                // CSRF is enabled by default, with Java Config
                .and().csrf().disable()

            http.headers().frameOptions().disable()
        }
    }
}