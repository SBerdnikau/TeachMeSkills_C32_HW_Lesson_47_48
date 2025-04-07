package com.tms.controller;

import com.tms.exception.LoginUsedException;
import com.tms.model.Security;
import com.tms.model.User;
import com.tms.model.dto.RegistrationRequestDto;
import com.tms.service.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;
import java.util.Optional;


@RestController
@RequestMapping("/security")
public class SecurityController {

    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    public SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Operation(summary = "User registration", description = "Endpoint allows to register a new user. Checks validation. In the database creates 2 new models related to each other (User, Security)")
    @PostMapping("/registration")
    public ResponseEntity<User> registration(@RequestBody @Valid RegistrationRequestDto requestDto,
                                                   BindingResult bindingResult) throws LoginUsedException {
        logger.info("Received registration request for user: {}", requestDto.getLogin());
        if (bindingResult.hasErrors()) {
            logger.warn("Validation errors occurred for user: {}", requestDto.getLogin());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        logger.info("User {} successfully registered", requestDto.getLogin());
        Optional<User> user = securityService.registration(requestDto);

        if (user.isEmpty()) {
            logger.info("Failed to register user: {}", requestDto.getLogin());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            logger.error("User {} successfully registered", requestDto.getLogin());
            return new ResponseEntity<>(user.get() ,HttpStatus.CREATED);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Security> getSecurityById(@PathVariable("id") Long id) {
        Optional<Security> security = securityService.getSecurityById(id);
        if (security.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(security.get(), HttpStatus.OK);
    }
}