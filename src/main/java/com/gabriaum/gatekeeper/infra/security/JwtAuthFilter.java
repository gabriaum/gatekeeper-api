package com.gabriaum.gatekeeper.infra.security;

import com.gabriaum.gatekeeper.infra.security.service.TokenService;
import com.gabriaum.gatekeeper.object.user.GateUser;
import com.gabriaum.gatekeeper.object.user.repository.GateUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private final TokenService tokenService;

    @Autowired
    private final GateUserRepository gateUserRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");
            if (token.isEmpty()) return;

            if (tokenService.validateToken(token)) {
                GateUser model = gateUserRepository
                        .findByEmail(tokenService.getSubject(token))
                        .orElse(null);

                if (model == null) return;

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(model, null, model.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}