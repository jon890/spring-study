package com.bifos.springsecurity.web.configuration

import com.bifos.springsecurity.authentication.CalendarUserAuthenticationProvider
import com.bifos.springsecurity.authentication.DomainUsernamePasswordAuthenticationFilter
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

/**
 * Spring Security Config Class
 * @see WebSecurityConfigurerAdapter
 */
@Configuration
@EnableWebSecurity
class SecurityConfig(
    val cuap: CalendarUserAuthenticationProvider
) : WebSecurityConfigurerAdapter() {

    companion object {
        private val logger = LoggerFactory.getLogger(SecurityConfig::class.java)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(cuap)
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
            .antMatchers("/").permitAll()
            .antMatchers("/login/*").permitAll()
            .antMatchers("/logout/*").permitAll()
            .antMatchers("/signup/*").permitAll()
            .antMatchers("/admin/*").hasRole("ADMIN")
            .antMatchers("/events/").hasRole("ADMIN")
            .antMatchers("/**").hasRole("USER")

            .and().exceptionHandling().accessDeniedPage("/errors/403")
            .authenticationEntryPoint(loginUrlAuthenticationEntryPoint())

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

            .addFilterAt(domainUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)

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
    }

    @Bean
    fun domainUsernamePasswordAuthenticationFilter(): DomainUsernamePasswordAuthenticationFilter {
        return DomainUsernamePasswordAuthenticationFilter(super.authenticationManagerBean()).apply {
            setFilterProcessesUrl("/login")
            usernameParameter = "username"
            passwordParameter = "password"
            setAuthenticationSuccessHandler(SavedRequestAwareAuthenticationSuccessHandler().apply {
                this.setDefaultTargetUrl(
                    "/default"
                )
            })
            setAuthenticationFailureHandler(SimpleUrlAuthenticationFailureHandler("/login/form?error"))
        }
    }

    @Bean
    fun loginUrlAuthenticationEntryPoint(): LoginUrlAuthenticationEntryPoint {
        return LoginUrlAuthenticationEntryPoint("/login/form")
    }
}