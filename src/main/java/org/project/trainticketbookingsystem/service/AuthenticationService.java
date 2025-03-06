package org.project.trainticketbookingsystem.service;

import org.project.trainticketbookingsystem.dto.ResponseDto;
import org.project.trainticketbookingsystem.dto.SignInRequestDto;
import org.project.trainticketbookingsystem.dto.SignUpRequestDto;

public interface AuthenticationService {
    ResponseDto signUp(SignUpRequestDto signUpRequestDTO);
    ResponseDto signIn(SignInRequestDto requestDTO);
    boolean validateToken(String token);
}
