package org.project.trainticketbookingsystem.service.impl;

import io.jsonwebtoken.Claims;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.ResponseDto;
import org.project.trainticketbookingsystem.dto.SignInRequestDto;
import org.project.trainticketbookingsystem.dto.SignUpRequestDto;
import org.project.trainticketbookingsystem.entity.UserEntity;
import org.project.trainticketbookingsystem.exceptions.UserException;
import org.project.trainticketbookingsystem.repository.UserRepository;
import org.project.trainticketbookingsystem.service.AuthenticationService;
import org.project.trainticketbookingsystem.service.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseDto signUp(SignUpRequestDto requestDTO) {
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

        return ResponseDto.builder()
                .accessToken(token)
                .build();
    }

    @Override
    public ResponseDto signIn(SignInRequestDto requestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.getEmail(),
                        requestDTO.getPassword())
        );

        UserEntity user = userRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtService.generateAccessToken(user);

        return ResponseDto.builder()
                .accessToken(token)
                .userId(user.getId())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public boolean validateToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UserException("Missing or invalid Authorization header");
        }
        String token = authHeader.replace("Bearer ", "");
        try {
            Claims claims = jwtService.extractAllClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
