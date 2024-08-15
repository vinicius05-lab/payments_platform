package payments_platform.user.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import payments_platform.user.dto.AuthRequest;
import payments_platform.user.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthRequest data) {
        
        var userAuthenticationToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());

        authenticationManager.authenticate(userAuthenticationToken);

        return ResponseEntity.ok(authService.getToken(data));
    }
    
}
