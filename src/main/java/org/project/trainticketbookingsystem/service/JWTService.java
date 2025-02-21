package org.project.trainticketbookingsystem.service;

import org.project.trainticketbookingsystem.entity.UserEntity;

public interface JWTService {
    String generateAccessToken(UserEntity user);
}
