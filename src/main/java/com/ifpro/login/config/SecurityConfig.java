package com.ifpro.login.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] PUBLIC_MATCHERS = {"/webjars/**", "/css/**",
			"/js/**", "/images/**", "/", "/about/**", "/contact/**", "/error/**/*",
			"/console/**", "/signup"};

	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll()
				.anyRequest().authenticated();
		http.csrf().disable().cors().disable().formLogin().loginPage("/index")
				.permitAll().usernameParameter("username")
				.successHandler((request, response, authentication) -> {
					response.setStatus(HttpServletResponse.SC_OK);
					response.sendRedirect("/home");
				}).failureHandler((request, response, exception) -> {
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

				})
				// .failureUrl("/index?error")
				// .defaultSuccessUrl("/userFront").loginPage("/index").permitAll()
				.and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/index?logout").deleteCookies("remember-me")
				.permitAll().and().rememberMe();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password")
				.roles("USER"); // in-memory login user
	}

}
