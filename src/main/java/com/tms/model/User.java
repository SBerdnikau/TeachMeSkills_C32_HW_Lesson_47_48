package com.tms.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Scope("prototype")
@Component
@Entity(name = "users")
public class User {
    @Id
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_seq_gen")
    private Long id;

    @NotBlank(message = "Name can't be empty")
    private String firstname;

    @NotBlank(message = "Lastname can't be empty")
    @Column(name = "second_name")
    private String secondName;

    private Integer age;
    private String email;
    private String sex;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    //insertable = false, updatable = false -> встравляем не состороны Java
    @Column(name = "created", insertable = false, updatable = false)
    private Timestamp created;
    @Column(name = "updated", insertable = false, updatable = false)
    private Timestamp updated;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Transient //заглушка
    private Security securityInfo;
}