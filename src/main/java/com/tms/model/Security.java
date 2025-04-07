package com.tms.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @JsonIgnore
    @Column(name = "created", updatable = false)
    private Timestamp created;

    @JsonIgnore
    @Column(name = "updated")
    private Timestamp updated;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    protected void onCreate(){
        created = new Timestamp(System.currentTimeMillis());
        updated = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate(){
        updated = new Timestamp(System.currentTimeMillis());
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Security security = (Security) o;
        return Objects.equals(id, security.id) && Objects.equals(login, security.login) && Objects.equals(password, security.password) && role == security.role && Objects.equals(user, security.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, role, user);
    }

    @Override
    public String toString() {
        return "Security{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
