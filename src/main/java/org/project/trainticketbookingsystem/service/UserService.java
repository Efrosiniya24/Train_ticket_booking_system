package org.project.trainticketbookingsystem.service;

import org.project.trainticketbookingsystem.entity.UserEntity;

public interface UserService {
    UserEntity findUserByEmail(String email);
}
