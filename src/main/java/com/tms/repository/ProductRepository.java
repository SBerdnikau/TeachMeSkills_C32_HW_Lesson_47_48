package com.tms.repository;

import com.tms.model.Product;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    public Optional<Product> getProductById(Long id) {
        return Optional.empty();
    }

    public Optional<Product> createProduct(Product product) {
        return Optional.empty();
    }

    public Boolean updateProduct(Product product) {
        return false;
    }

    public Boolean deleteProduct(Long id) {
        return false;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        return products;
    }

    private Optional<Product> parseProduct(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Product product = new Product();
            product.setId(resultSet.getLong("id"));
            product.setName(resultSet.getString("name"));
            product.setPrice(resultSet.getDouble("price"));
            product.setCreated(resultSet.getTimestamp("created"));
            product.setUpdated(resultSet.getTimestamp("updated"));
            return Optional.of(product);
        }
        return Optional.empty();
    }
}


