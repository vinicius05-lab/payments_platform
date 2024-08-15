package payments_platform.user.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import payments_platform.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import payments_platform.user.dto.AuthRequest;
import payments_platform.user.repository.UserRepository;
import payments_platform.user.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    @Override
    public Instant generateDate() {
        return LocalDateTime
                .now()
                .plusHours(24)
                .toInstant(ZoneOffset.of("-03:00"));
    }

    @Override
    public String getToken(AuthRequest data) {
        return generateToken(userRepository.findByEmail(data.email()));
    }

    @Override
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("my-secret");

            return JWT
                    .create()
                    .withIssuer("payments_platform")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Não foi possível gerar o token");
        }
    }

    @Override
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("my-secret");

            return JWT
                    .require(algorithm)
                    .withIssuer("payments_platform")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token Inválido");
        }
    }
    
}
