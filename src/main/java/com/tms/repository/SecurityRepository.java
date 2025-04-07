package com.tms.repository;

import com.tms.config.SQLQuery;
import com.tms.model.Security;
import com.tms.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
            entityManager.persist(security);
            entityManager.getTransaction().commit();
            return Optional.of(user);
        }catch (Exception e) {
            logger.error("Registration is failed -> {}", e.getMessage());
            entityManager.getTransaction().rollback();
            return Optional.empty();
        }
    }

    public Boolean isLoginUsed(String login) {
        //Query query =  entityManager.createQuery("SELECT s FROM security s WHERE s.login = :login");
        Query query = entityManager.createNativeQuery(SQLQuery.GET_SECURITY_BY_LOGIN);
        query.setParameter("login", login);
        return query.getSingleResultOrNull() != null;
    }

    public Optional<Security> getSecurityById(Long id) {
        return Optional.ofNullable(entityManager.find(Security.class, id));
    }
}
