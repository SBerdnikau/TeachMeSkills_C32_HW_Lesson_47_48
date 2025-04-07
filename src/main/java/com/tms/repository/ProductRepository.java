package com.tms.repository;

import com.tms.config.SQLQuery;
import com.tms.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    public final EntityManager entityManager;
    private static final Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    @Autowired
    public ProductRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Product> getProductById(Long id) {
        return Optional.of(entityManager.find(Product.class, id));
    }

    public Boolean createProduct(Product product) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(product);
            entityManager.getTransaction().commit();
        }catch (Exception e) {
            logger.error(e.getMessage());
            entityManager.getTransaction().rollback();
            return false;
        }
        return true;
    }

    public Optional<Product> updateProduct(Product product) {
       Product updatedProduct = null;
       try {
           entityManager.getTransaction().begin();
           updatedProduct = entityManager.merge(product);
           entityManager.getTransaction().commit();
       }catch (Exception e) {
           logger.error(e.getMessage());
           entityManager.getTransaction().rollback();
       }
       return Optional.of(updatedProduct);
    }

    public Boolean deleteProduct(Long id) {
       try {
           entityManager.getTransaction().begin();
           entityManager.remove(entityManager.find(Product.class, id));
           entityManager.getTransaction().commit();
       }catch (Exception e) {
           logger.error(e.getMessage());
           entityManager.getTransaction().rollback();
           return false;
       }
       return true;
    }

    public List<Product> getAllProducts() {
        return entityManager.createQuery("from product").getResultList();
    }

    public Boolean addProductByUser(Long userId, Long productId) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createNativeQuery(SQLQuery.ADD_PRODUCT_BY_USER);
            query.setParameter(1, productId);
            query.setParameter(2, userId);
            query.executeUpdate();
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            logger.error(e.getMessage());
        }
        return false;
    }
}


