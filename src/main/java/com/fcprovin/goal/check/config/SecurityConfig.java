package com.fcprovin.goal.check.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.core.userdetails.User.withUsername;
import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Value("${user.details.username}")
    private String username;

    @Value("${user.details.password}")
    private String password;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
				.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/static/**", "/favicon.ico", "/assets/**").permitAll()
                        .anyRequest().authenticated()
                )
				.httpBasic(withDefaults())
				.formLogin(formLogin -> formLogin
						.loginPage("/login")
						.defaultSuccessUrl("/member", true)
				)
				.rememberMe(withDefaults())
				.logout(logout -> logout
						.logoutSuccessUrl("/login")
						.permitAll()
				)
				.build();
    }

	@Bean
    public InMemoryUserDetailsManager userDetailsService() {
        return new InMemoryUserDetailsManager(withUsername(username)
                .password(createDelegatingPasswordEncoder().encode(password))
                .roles("ADMIN")
                .build());
    }
}
