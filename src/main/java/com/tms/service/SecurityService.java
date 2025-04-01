package com.tms.service;

import com.tms.model.Role;
import com.tms.model.Security;
import com.tms.model.User;
import com.tms.model.dto.RegistrationRequestDto;
import com.tms.repository.SecurityRepository;
import com.tms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

@Component
public class SecurityService {
    
    public final SecurityRepository securityRepository;
    private User user;
    private Security security;

    @Autowired
    public SecurityService(SecurityRepository securityRepository, User user, Security security) {
        this.securityRepository = securityRepository;
        this.user = user;
        this.security = security;
    }

    public Optional<User> registration(RegistrationRequestDto registrationRequestDto) throws LoginException {
        try {
            if (securityRepository.isLoginUsed(registrationRequestDto.getLogin())){
                throw new LoginException(registrationRequestDto.getLogin());
            }

            user.setFirstname(registrationRequestDto.getFirstname());
            user.setSecondName(registrationRequestDto.getSecondName());
            user.setEmail(registrationRequestDto.getEmail());
            user.setUpdated(new Timestamp(System.currentTimeMillis()));
            user.setAge(registrationRequestDto.getAge());
            user.setSex(registrationRequestDto.getSex());
            user.setTelephoneNumber(registrationRequestDto.getTelephoneNumber());

            security.setLogin(registrationRequestDto.getLogin());
            security.setPassword(registrationRequestDto.getPassword());
            security.setUpdated(new Timestamp(System.currentTimeMillis()));
            security.setRole(Role.USER);

            return  securityRepository.registration(user, security);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}
