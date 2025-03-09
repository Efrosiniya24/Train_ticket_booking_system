package org.project.trainticketbookingsystem.service.impl;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.trainticketbookingsystem.entity.UserEntity;
import org.project.trainticketbookingsystem.enums.Role;
import org.project.trainticketbookingsystem.exceptions.UserException;
import org.project.trainticketbookingsystem.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId(1L);
        user.setName("UserName");
        user.setSurname("UserSurname");
        user.setPatronymic("UserPatronymic");
        user.setEmail("user@gmail.com");
        user.setPassword("password");
        user.setRole(Role.USER);
    }

    @Test
    void findUserByEmail_UserExist() {
        //given
        String email = "user@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(user));

        //when
        UserEntity foundUser = userService.findUserByEmail(email);

        //then
        assertEquals(email, foundUser.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void findUserByEmail_UserNotExist() {
        //given
        String email = "user@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        //when
        //then
        assertThrows(UserException.class, () -> userService.findUserByEmail(email));
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void findUserById_UserExist() {
        //given
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(user));

        //when
        UserEntity foundUser = userService.findById(id);

        //then
        assertEquals(id, foundUser.getId());
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void findUserById_UserNotExist() {
        //given
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        //when
        //then
        assertThrows(UserException.class, () -> userService.findById(id));
        verify(userRepository, times(1)).findById(id);
    }
}