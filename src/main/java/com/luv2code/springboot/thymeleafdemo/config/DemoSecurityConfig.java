package com.luv2code.springboot.thymeleafdemo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.luv2code.springboot.thymeleafdemo.service.UserService;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
	

	@Autowired
	DataSource dataSource;
	
	@Autowired
	UserService userService;
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//		// add our users for in memory authentication
//		
//		//UserBuilder users = User.withDefaultPasswordEncoder();
//		
//		auth.jdbcAuthentication().dataSource(dataSource);
//		
////		auth.jdbcAuthentication()
////	      .dataSource(dataSource)
////	      .usersByUsernameQuery("select username,password "
////	        + "from users ")
////	      .authoritiesByUsernameQuery("select user_id,role_id "
////	        + "from users_roles");
//	      
//		/*auth.inMemoryAuthentication()
//			.withUser(users.username("john").password("test123").roles("EMPLOYEE"))
//			.withUser(users.username("mary").password("test123").roles("EMPLOYEE", "MANAGER"))
//			.withUser(users.username("susan").password("test123").roles("EMPLOYEE", "ADMIN"));*/
//	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	  
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	       // registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		http.csrf().disable().authorizeRequests()
		.antMatchers("/employees/showForm*").hasAnyAuthority("MANAGER", "ADMIN")
		.antMatchers("/employees/save*").hasAnyAuthority("MANAGER", "ADMIN")
		.antMatchers("/employees/delete").hasRole("ADMIN")
		.antMatchers("/employees/**").hasRole("EMPLOYEE")
		.antMatchers("/resources/**").permitAll()
		.and()
		.formLogin()  
			.loginPage("/showMyLoginPage")
			.loginProcessingUrl("/authenticateTheUser")
			.permitAll() 
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/access-denied");
		
	}
	
	
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService); //set the custom user details service
		auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
		return auth;
	}
	
	// to apply the dao provider ^^^^ above to the spring config
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
		
}






