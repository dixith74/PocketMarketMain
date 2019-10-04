/*
package com.pm.bs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.pm.common.security.JwtAuthenticationConfig;

import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
//@EnableReactiveMethodSecurity(proxyTargetClass = true)
public class ReactiveSecurityConfig {

	@Autowired
	private JwtAuthenticationConfig config;

	@Bean
	public JwtAuthenticationConfig jwtConfig() {
		return new JwtAuthenticationConfig();
	}

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

			http.cors().disable()
				.exceptionHandling().authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() -> {
						swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED); }))
				.accessDeniedHandler((swe, e) -> Mono.fromRunnable(() -> {
						swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
				}))
				.and().csrf().disable()
				.authorizeExchange()
				.pathMatchers("/login", "/bye", "/favicon.ico", "/images/**").permitAll()
				.pathMatchers("/products/**")
				.authenticated().and()
				.addFilterAt(bearerAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION);

		return http.build();
	}

	@Bean
	public AuthFilter bearerAuthenticationFilter() {
		return new AuthFilter();
	}
}
*/