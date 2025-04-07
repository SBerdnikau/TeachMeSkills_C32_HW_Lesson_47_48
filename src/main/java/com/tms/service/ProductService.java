package com.tms.service;

import com.tms.model.Product;
import com.tms.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.getProductById(id);
    }

    public Boolean createProduct(Product product){
        return productRepository.createProduct(product);
    }

    public Optional<Product> updateProduct(Product product){
        return productRepository.updateProduct(product);
    }

    public Boolean deleteProduct(Long id){
        return productRepository.deleteProduct(id);
    }

    public List<Product> getAllProducts(){
        return productRepository.getAllProducts();
    }

    public Boolean addProductByUser(Long userId, Long productId){
        return productRepository.addProductByUser(userId, productId);
    }

}
