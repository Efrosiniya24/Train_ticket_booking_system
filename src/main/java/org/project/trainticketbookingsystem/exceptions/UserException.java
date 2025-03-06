package org.project.trainticketbookingsystem.exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserException extends UsernameNotFoundException {
    public UserException(String message){
        super(message);
    }
}
