package com.ecommercewebsite.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.ecommercewebsite.security.JwtAuthenticationEntryPoint;
import com.ecommercewebsite.security.JwtAuthenticationFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Configuration
	public class SecurityConfiguration {

		@Autowired
		private CustomUserDetailService custom;

		@Autowired
		private JwtAuthenticationFilter jwtfilter;

		@Autowired
		private JwtAuthenticationEntryPoint jwtentry;

		@Autowired
		private UserDetailsService userdetail;

		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

		@Bean
		public DaoAuthenticationProvider getDaoAuthProvider() {
			DaoAuthenticationProvider daoauth = new DaoAuthenticationProvider();
			daoauth.setUserDetailsService(custom);
			daoauth.setPasswordEncoder(passwordEncoder());
			return daoauth;
		}

		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			http.csrf(csrf -> csrf.disable())
					.authorizeHttpRequests(auth -> auth.requestMatchers("/home").authenticated().requestMatchers("/**")
							.permitAll().anyRequest().authenticated())

					.cors().configurationSource(new CorsConfigurationSource() {

						@Override
						public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

							CorsConfiguration config = new CorsConfiguration();
							config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200"));
							config.setAllowedMethods(Collections.singletonList("*"));
							config.setAllowCredentials(true);
							config.setAllowedHeaders(Collections.singletonList("*"));
							config.setExposedHeaders(Arrays.asList("Authorization"));
							config.setMaxAge(3600L);

							return config;
						}
					});

			http.exceptionHandling(ex -> ex.authenticationEntryPoint(jwtentry))
					.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

			http.addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);
			// .httpBasic();
			http.authenticationProvider(getDaoAuthProvider());
			return http.build();
		}

		@Bean
		public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
			return configuration.getAuthenticationManager();
		}

	}

}
