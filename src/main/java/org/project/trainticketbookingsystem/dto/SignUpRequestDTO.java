package org.project.trainticketbookingsystem.dto;

import lombok.Data;

@Data
public class SignUpRequestDTO {
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String password;
}
