package org.project.trainticketbookingsystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDTO {
    private String accessToken;
    private Long userId;
}
