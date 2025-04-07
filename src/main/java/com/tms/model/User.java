package com.tms.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @JsonIgnore
    @Column(name = "created", updatable = false)
    private Timestamp created;

    @JsonIgnore
    @Column(name = "updated")
    private Timestamp updated;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @JsonManagedReference
    @OneToOne(mappedBy = "user")
    private Security security;

    @JsonManagedReference
    @JoinTable(
            name = "l_users_product",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        created = new Timestamp(System.currentTimeMillis());
        updated = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(firstname, user.firstname) && Objects.equals(secondName, user.secondName) && Objects.equals(age, user.age) && Objects.equals(email, user.email) && Objects.equals(sex, user.sex) && Objects.equals(telephoneNumber, user.telephoneNumber) && Objects.equals(isDeleted, user.isDeleted) && Objects.equals(security, user.security) && Objects.equals(products, user.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, secondName, age, email, sex, telephoneNumber, isDeleted, security, products);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", secondName='" + secondName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", isDeleted=" + isDeleted +
                ", products=" + products +
                '}';
    }
}