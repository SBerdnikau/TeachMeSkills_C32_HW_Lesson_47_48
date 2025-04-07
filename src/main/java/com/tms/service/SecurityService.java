package com.tms.service;

import com.tms.exception.LoginUsedException;
import com.tms.model.Role;
import com.tms.model.Security;
import com.tms.model.User;
import com.tms.model.dto.RegistrationRequestDto;
import com.tms.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public Optional<Security> getSecurityById(Long id) {
        return securityRepository.getSecurityById(id);
    }

    public Optional<User> registration(RegistrationRequestDto registrationRequestDto) throws LoginUsedException {
        try {
            if (securityRepository.isLoginUsed(registrationRequestDto.getLogin())){
                throw new LoginUsedException(registrationRequestDto.getLogin());
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
