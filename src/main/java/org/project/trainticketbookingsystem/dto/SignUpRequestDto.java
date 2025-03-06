package org.project.trainticketbookingsystem.dto;

import lombok.Data;
import org.project.trainticketbookingsystem.enums.Role;

@Data
public class SignUpRequestDto {
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String password;
    private Role role;
}
