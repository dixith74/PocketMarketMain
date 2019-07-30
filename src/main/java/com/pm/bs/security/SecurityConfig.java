package com.pm.bs.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pm.common.security.JwtAuthenticationConfig;
import com.pm.common.security.JwtTokenAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationConfig config;

    @Bean
    public JwtAuthenticationConfig jwtConfig() {
        return new JwtAuthenticationConfig();
    }
    
    @Override
	public void configure(WebSecurity web) throws Exception {
		// @formatter:off
    	web
		.ignoring()
		.antMatchers(HttpMethod.OPTIONS)
		.antMatchers("/v2/api-docs","/products/**","/upload/**", "/downloadFile/**", "/categories/**",
                    "/configuration/ui",
                    "/swagger-resources/**",
                    "/configuration/security/**",
                    "/swagger-ui.html",
                    "/webjars/**");
		// @formatter:on
	}

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
        		//.requestMatcher(EndpointRequest.toAnyEndpoint())
        		//.authorizeRequests().anyRequest().permitAll()
        		//.and()
                .csrf().disable()
                .logout().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .anonymous()
                .and()
                    .exceptionHandling().authenticationEntryPoint(
                            (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                    .addFilterAfter(new JwtTokenAuthenticationFilter(config),
                            UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests().anyRequest().authenticated();
    }
}

