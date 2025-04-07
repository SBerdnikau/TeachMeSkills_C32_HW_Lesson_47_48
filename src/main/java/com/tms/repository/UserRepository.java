package com.tms.repository;

import com.tms.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        entityManager.clear();
       return Optional.of(entityManager.find(User.class, id));
    }
    
    public Boolean deleteUser(Long id){
      try {
          entityManager.getTransaction().begin();
          entityManager.remove(entityManager.find(User.class, id));
          entityManager.getTransaction().commit();
      }catch(Exception e){
          logger.error("Deleted is failed -> {}", e.getMessage());
          entityManager.getTransaction().rollback();
          return false;
      }
      return true;
    }
    
    public Boolean createUser(User user){
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            logger.error("Exception create user -> {}", e.getMessage());
            return false;
        }
        return true;
    }

    public Optional<User> updateUser(User user) {
        User existingUser  = entityManager.find(User.class, user.getId());

        if (existingUser == null) {
            throw new EntityNotFoundException("User with id " + user.getId() + " not found");
        }

        existingUser.setFirstname(user.getFirstname());
        existingUser.setSecondName(user.getSecondName());
        existingUser.setAge(user.getAge());
        existingUser.setSex(user.getSex());
        existingUser.setTelephoneNumber(user.getTelephoneNumber());
        existingUser.setEmail(user.getEmail());
        existingUser.setIsDeleted(user.getIsDeleted());

        try {
            entityManager.getTransaction().begin();
            entityManager.merge(existingUser);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            logger.error(e.getMessage());
        }
        return Optional.of(existingUser);
    }

    public List<User> getAllUsers() {
        return entityManager.createQuery("from users").getResultList();
    }

}