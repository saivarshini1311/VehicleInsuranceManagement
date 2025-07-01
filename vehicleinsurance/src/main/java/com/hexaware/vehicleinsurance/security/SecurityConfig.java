package com.hexaware.vehicleinsurance.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()


                .requestMatchers("/api/users/**").hasRole("USER")
                .requestMatchers("/api/officers/**").hasRole("OFFICER")
                .requestMatchers("/api/vehicles/**").hasAnyRole("USER", "OFFICER")
                .requestMatchers("/api/policies/**").hasAnyRole("USER", "OFFICER")
                .requestMatchers("/api/quotes/**").hasAnyRole("USER", "OFFICER")
                .requestMatchers("/api/proposals/**").hasAnyRole("USER", "OFFICER")
                .requestMatchers("/api/claims/**").hasAnyRole("USER", "OFFICER")
                .requestMatchers("/api/payments/**").hasAnyRole("USER", "OFFICER")
                .requestMatchers("/api/documents/**").hasAnyRole("USER", "OFFICER")
                .requestMatchers("/api/reminders/**").hasRole("OFFICER")

                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults()) 
            .csrf(csrf -> csrf.disable());        

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
            .username("user")
            .password("user123")
            .roles("USER")
            .build();

        UserDetails officer = User.withDefaultPasswordEncoder()
            .username("officer")
            .password("officer123")
            .roles("OFFICER")
            .build();

        return new InMemoryUserDetailsManager(user, officer);
    }
}
