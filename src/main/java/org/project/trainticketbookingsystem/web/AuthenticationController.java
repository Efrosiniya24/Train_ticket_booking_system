package org.project.trainticketbookingsystem.web;

import lombok.AllArgsConstructor;
import org.project.trainticketbookingsystem.dto.ResponseDTO;
import org.project.trainticketbookingsystem.dto.SignInRequestDTO;
import org.project.trainticketbookingsystem.dto.SignUpRequestDTO;
import org.project.trainticketbookingsystem.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/train/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    ResponseEntity<ResponseDTO> signUp(@RequestBody SignUpRequestDTO requestDTO) {
        return ResponseEntity.ok(authenticationService.signUp(requestDTO));
    }

    @PostMapping("/signIn")
    ResponseEntity<ResponseDTO> signIn(@RequestBody SignInRequestDTO requestDTO) {
        try {
            return ResponseEntity.ok(authenticationService.signIn(requestDTO));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.clearContext();
        }
        return ResponseEntity.ok("The exit was successful");
    }

    @PostMapping("/validateToken")
    private ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }
        String token = authHeader.replace("Bearer ", "");
        boolean isValid = authenticationService.validateToken(token);

        return isValid ? ResponseEntity.ok("Valid Token")
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
    }
}
