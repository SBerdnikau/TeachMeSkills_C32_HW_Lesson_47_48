package com.tms.repository;

import com.tms.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    public final EntityManager entityManager;
    private final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @Autowired
    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<User> getUserById(Long id) {
       return Optional.empty();
    }
    
    public Boolean deleteUser(Long id){
      return false;
    }
    
    public Boolean createUser(User user){
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);//если мы хотим добавлять обьекты, и раньше у нас их не было
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            logger.error("Exception create user -> " + e.getMessage());
            return false;
        }
        return true;
    }

    public Boolean updateUser(User userRequestDto) {
       return false;
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        return  users;
    }

}