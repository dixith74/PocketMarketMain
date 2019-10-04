/*
package com.pm.bs.security;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.pm.common.beans.JwtUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

//@Component
public class AuthFilter implements WebFilter {
	
	public AuthFilter() {
		
	}

	@Override
	public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
		serverWebExchange.getResponse().getHeaders().add("web-filter", "web-filter-test");
		String token = serverWebExchange.getRequest().getHeaders().getFirst("Authorization");
		System.out.println(serverWebExchange.getRequest().getURI());
		if (token != null && token.startsWith("Bearer ")) {
			token = token.replace("Bearer ", "");
			byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("PM");
			try {
				Claims claims = Jwts.parser().setSigningKey(apiKeySecretBytes).parseClaimsJws(token).getBody();

				String userId = claims.getSubject();
				String mobileNumber = (String) claims.get("mobile_number");
				String email = (String) claims.get("email");
				String fullName = (String) claims.get("full_name");

				@SuppressWarnings("unchecked")
				List<String> scopes = (ArrayList<String>) claims.get("user_role");

				JwtUser jwtUser = new JwtUser(userId, mobileNumber, "MOBILE", email, fullName);
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(jwtUser, null,
						scopes.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
				auth.setDetails(jwtUser);
				SecurityContextHolder.getContext().setAuthentication(auth);
			} catch (Exception ignore) {
				SecurityContextHolder.clearContext();
			}
			return webFilterChain.filter(serverWebExchange);
		} else {
			serverWebExchange.getResponse();
			//rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing Auth header");
		}
		return null;
		
	}
}*/