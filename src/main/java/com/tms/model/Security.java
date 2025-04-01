package com.tms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Scope("prototype")
@Component
@Entity(name = "security")
public class Security {
    @Id
    @SequenceGenerator(name = "security_seq_gen", sequenceName = "security_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "security_seq_gen")
    private Long id;
    private String login;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "created", insertable = false, updatable = false)
    private Timestamp created;
    @Column(name = "updated", insertable = false, updatable = true)
    private Timestamp updated;

    @Column(name = "user_id")
    private Long userId;
}
