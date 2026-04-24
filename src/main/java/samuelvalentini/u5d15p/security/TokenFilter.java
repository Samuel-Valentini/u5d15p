package samuelvalentini.u5d15p.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import samuelvalentini.u5d15p.entity.Utente;
import samuelvalentini.u5d15p.exception.UnauthorizedException;
import samuelvalentini.u5d15p.service.UtenteService;


import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private final TokenTool tokenTools;
    private final UtenteService utenteService;

    public TokenFilter(TokenTool tokenTool, UtenteService utenteService) {
        this.tokenTools = tokenTool;
        this.utenteService = utenteService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            throw new UnauthorizedException("Token assente");
        if (!authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Token non corretto");

        String accessToken = authHeader.substring(7);

        tokenTools.verifyToken(accessToken);

        //1. estraiamo id dal token
        Long userId = this.tokenTools.extractIdFromToken(accessToken);
        // 2. find
        Utente authenticatedUtente = this.utenteService.findById(userId);

        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedUtente, null, authenticatedUtente.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override

    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {


        return new AntPathMatcher().match("/auth/**", request.getServletPath());


    }
}