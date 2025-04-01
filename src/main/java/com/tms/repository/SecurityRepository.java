package com.tms.repository;

import com.tms.config.SQLQuery;
import com.tms.model.Security;
import com.tms.model.User;
import com.tms.model.dto.RegistrationRequestDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class SecurityRepository {

    private final EntityManager entityManager;
    private final Logger logger = LoggerFactory.getLogger(EntityManager.class);

    @Autowired
    public SecurityRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<User> registration(User user, Security security) throws SQLException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            security.setUserId(user.getId());
            entityManager.persist(security);
            entityManager.getTransaction().commit();
            return Optional.of(entityManager.find(User.class, user.getId()));
        }catch (Exception e) {
            logger.error("Registration is failed -> " + e.getMessage());
            entityManager.getTransaction().rollback();
            return Optional.empty();
        }
    }

    public Boolean isLoginUsed(String login) {
        //entityManager.createNativeQuery(SQLQuery.GET_SECURITY_BY_LOGIN); // черех простой SQL
        Query query =  entityManager.createQuery("SELECT s FROM security s WHERE s.login = :login");
        entityManager.setProperty("login", login);
        return query.getSingleResultOrNull() != null;
    }

}
