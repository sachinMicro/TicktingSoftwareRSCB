package com.rsc.bhopal.security.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig { 
  //Configuring HttpSecurity 
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 
      return http.csrf(csrf -> csrf.disable())
              .authorizeHttpRequests(requests -> requests
                      .requestMatchers("/js/**", "/css/**","/images/**").permitAll()
                      .requestMatchers("/manage/**")
                      .hasRole("ADMIN")
               )              
              .authorizeHttpRequests(requests -> requests.anyRequest().authenticated())
              .formLogin(form->{
            	  form
            	    .loginPage("/login")
            	    .defaultSuccessUrl("/home")
            	    .failureUrl("/login?error=true")                  
            	    .permitAll();            	
              })
              .rememberMe(Customizer.withDefaults())
              .logout(logout->{
            	  logout
            	  .logoutSuccessUrl("/login")
            	  .deleteCookies("JSESSIONID")
            	  .invalidateHttpSession(true)
            	  .permitAll();            	 
              })
              .build(); 
  } 

  //Password Encoding 
  @Bean
  public PasswordEncoder passwordEncoder() { 
      return new BCryptPasswordEncoder(); 
  } 
} 