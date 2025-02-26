package org.project.trainticketbookingsystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String password;
}
