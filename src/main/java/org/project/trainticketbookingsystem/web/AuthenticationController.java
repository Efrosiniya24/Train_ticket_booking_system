package org.project.trainticketbookingsystem.web;

import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.ResponseDto;
import org.project.trainticketbookingsystem.dto.SignInRequestDto;
import org.project.trainticketbookingsystem.dto.SignUpRequestDto;
import org.project.trainticketbookingsystem.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/train/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    ResponseEntity<ResponseDto> signUp(@RequestBody SignUpRequestDto requestDTO) {
        return ResponseEntity.ok(authenticationService.signUp(requestDTO));
    }

    @PostMapping("/signIn")
    ResponseEntity<ResponseDto> signIn(@RequestBody SignInRequestDto requestDTO) {
        return ResponseEntity.ok(authenticationService.signIn(requestDTO));
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
        boolean isValid = authenticationService.validateToken(authHeader);
        return isValid ? ResponseEntity.ok("Valid Token")
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
    }
}
