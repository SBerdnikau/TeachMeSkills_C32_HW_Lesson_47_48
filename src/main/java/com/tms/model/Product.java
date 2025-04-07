package com.tms.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
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
@Entity(name = "product")
public class Product {
    @Id
    @SequenceGenerator(name = "product_seq_gen", sequenceName = "product_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "product_seq_gen")
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Double price;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "products")
    private Set<User> users = new HashSet<>();

    @JsonIgnore
    @Column(name = "created", updatable = false)
    private Timestamp created;

    @JsonIgnore
    @Column(name = "updated")
    private Timestamp updated;

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
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(users, product.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, users);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", users=" + users +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
