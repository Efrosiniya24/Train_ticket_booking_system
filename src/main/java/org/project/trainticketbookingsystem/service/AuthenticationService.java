package org.project.trainticketbookingsystem.service;

import org.project.trainticketbookingsystem.dto.ResponseDTO;
import org.project.trainticketbookingsystem.dto.SignInRequestDTO;
import org.project.trainticketbookingsystem.dto.SignUpRequestDTO;

public interface AuthenticationService {
    ResponseDTO signUp(SignUpRequestDTO signUpRequestDTO);
    ResponseDTO signIn(SignInRequestDTO requestDTO);
}
