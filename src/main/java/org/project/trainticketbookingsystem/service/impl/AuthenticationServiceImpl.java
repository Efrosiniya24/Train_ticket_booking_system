package org.project.trainticketbookingsystem.service.impl;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.ResponseDTO;
import org.project.trainticketbookingsystem.dto.SignInRequestDTO;
import org.project.trainticketbookingsystem.dto.SignUpRequestDTO;
import org.project.trainticketbookingsystem.entity.UserEntity;
import org.project.trainticketbookingsystem.repository.UserRepository;
import org.project.trainticketbookingsystem.service.AuthenticationService;
import org.project.trainticketbookingsystem.service.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseDTO signUp(SignUpRequestDTO requestDTO) {
        UserEntity user = UserEntity
                .builder()
                .name(requestDTO.getName())
                .surname(requestDTO.getSurname())
                .patronymic(requestDTO.getPatronymic())
                .email(requestDTO.getEmail())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .role(requestDTO.getRole())
                .build();
        userRepository.save(user);

        String token = jwtService.generateAccessToken(user);

        return ResponseDTO.builder()
                .accessToken(token)
                .build();
    }

    @Override
    public ResponseDTO signIn(SignInRequestDTO requestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword())
        );

        UserEntity user = userRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtService.generateAccessToken(user);

        return ResponseDTO.builder()
                .accessToken(token)
                .userId(user.getId())
                .build();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Claims claims = jwtService.extractAllClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
