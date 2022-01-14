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
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import javax.sql.DataSource


/**
 * Spring Security Config Class
 * @see WebSecurityConfigurerAdapter
 */
@Configuration
@EnableWebSecurity(debug = true)
class SecurityConfig(private val dataSource: DataSource) : WebSecurityConfigurerAdapter() {

    companion object {
        private val logger = LoggerFactory.getLogger(SecurityConfig::class.java)

        private const val CUSTOM_CREATE_USER_SQL =
            "insert into calendar_users (username, password, enabled) values (?,?,?)"

        private const val CUSTOM_GROUP_AUTHORITIES_BY_USERNAME_QUERY = "select g.id, g.group_name, ga.authority " +
                "from groups g, group_members gm, " +
                "group_authorities ga where gm.username = ? " +
                "and g.id = ga.group_id and g.id = gm.group_id"

        private const val CUSTOM_USERS_BY_USERNAME_QUERY = "select email, password, true " +
                "from calendar_users where email = ?"

        private const val CUSTOM_AUTHORITIES_BY_USERNAME_QUERY = "select cua.id, cua.authority " +
                "from calendar_users cu, calendar_user_authorities " +
                "cua where cu.email = ? " +
                "and cu.id = cua.calendar_user"
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery(CUSTOM_USERS_BY_USERNAME_QUERY)
            .authoritiesByUsernameQuery(CUSTOM_AUTHORITIES_BY_USERNAME_QUERY)
            .passwordEncoder(passwordEncoder())
    }

    /**
     * The parent method from [WebSecurityConfigurerAdapter.userDetailsService]
     * originally returns a [UserDetailsService], but this needs to be a [UserDetailsManager]
     * UserDetailsManager vs UserDetailsService
     */
    @Bean
    override fun userDetailsService(): UserDetailsManager {
        val _dataSource = this.dataSource

        return object : JdbcUserDetailsManager() {
            init {
                setDataSource(_dataSource)
                // Override default SQL for JdbcUserDetailsManager
                setGroupAuthoritiesByUsernameQuery(CUSTOM_GROUP_AUTHORITIES_BY_USERNAME_QUERY)
                usersByUsernameQuery = CUSTOM_USERS_BY_USERNAME_QUERY
                authoritiesByUsernameQuery = CUSTOM_AUTHORITIES_BY_USERNAME_QUERY
                // TODO: This is not available through AuthenticationManagerBuilder
                setCreateUserSql(CUSTOM_CREATE_USER_SQL)
            }
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
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}