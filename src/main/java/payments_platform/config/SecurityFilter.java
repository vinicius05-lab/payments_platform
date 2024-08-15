package payments_platform.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import payments_platform.user.model.User;
import payments_platform.user.repository.UserRepository;
import payments_platform.user.service.AuthService;

@Component
public class SecurityFilter extends OncePerRequestFilter{

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenHeader(request);

        if(token != null){
            String email = authService.validateToken(token);
            User user = userRepository.findByEmail(email);

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
        
    }

    private String getTokenHeader(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            var authParts = authHeader.split(" ");
            
            if(authParts.length == 2){
                return authParts[1];
            }
        }

        return null;
    }
    
}
