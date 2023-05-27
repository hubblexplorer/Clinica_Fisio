package com.example.test_login2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authorize) ->
                        authorize./*requestMatchers("/register/**").hasRole("ADMIN")
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/home").hasAnyRole("ADMIN","MEDICO","RECECIONISTA")
                                .requestMatchers("/users").hasRole("ADMIN")
                                .requestMatchers("/agenda/**").hasAnyRole("ADMIN","MEDICO","RECECIONISTA")
                                .requestMatchers("/add_paciente/**").hasRole("RECECIONISTA")
                                .anyRequest().denyAll()*/
                                anyRequest().permitAll()
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .successHandler((request, response, authentication) -> {
                                    // Get the user's authorities/roles
                                    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

                                    // Redirect the user based on their role
                                    if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                                        response.sendRedirect("/users");
                                    } else if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_MEDICO"))) {
                                        response.sendRedirect("/agenda");
                                    } else if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_RECECIONISTA"))) {
                                        response.sendRedirect("/agenda");
                                    } else {
                                        response.sendRedirect("/default/home");
                                    }
                                })
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()

                );

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}