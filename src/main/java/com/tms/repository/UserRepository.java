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
       return Optional.of(entityManager.find(User.class, id));
    }
    
    public Boolean deleteUser(Long id){
      try {
          entityManager.getTransaction().begin();
          entityManager.remove(entityManager.find(User.class, id));
          entityManager.getTransaction().commit();
      }catch(Exception e){
          logger.error("Deleted is failed -> " + e.getMessage());
          entityManager.getTransaction().rollback();
          return false;
      }
      return true;
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

    public Optional<User> updateUser(User userRequestDto) {
        User userUpdated = null;
        try {
            entityManager.getTransaction().begin();
            userUpdated = entityManager.merge(userRequestDto);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            logger.error("Exception update user -> " + e.getMessage());
            entityManager.getTransaction().rollback();
            return Optional.empty();
        }
       return Optional.of(userUpdated);
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        return  users;
    }

}