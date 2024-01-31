package com.fcprovin.goal.check.config;

import com.fcprovin.goal.check.config.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomUserDetailsService userDetailsService;

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
						.defaultSuccessUrl("/me", true)
				)
				.rememberMe(withDefaults())
				.logout(logout -> logout
						.logoutSuccessUrl("/login")
						.permitAll()
				)
				.userDetailsService(userDetailsService)
				.build();
    }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
