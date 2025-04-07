package com.tms.controller;

import com.tms.model.Product;
import com.tms.service.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createProduct(@RequestBody Product product)  {
        logger.info("Received request to create a product: {}", product);
        Boolean result = productService.createProduct(product);
        if(!result){
            logger.error("Failed to create product: {}", product);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("Product created successfully: {}", product.getName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id){
        logger.info("Received request to fetch product by ID: {}", id);
        Optional<Product> product = productService.getProductById(id);
        if(product.isEmpty()){
            logger.warn("Product with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Product with ID {} fetched successfully: {}", id, product.get());
        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }


    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        logger.info("Received request to update product {}", product);
        Optional<Product> updatedProduct = productService.updateProduct(product);
        logger.error("Failed to update product with ID {}: ", product);
        if(updatedProduct.isEmpty()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("Product {} updated successfully ", updatedProduct.get());
        return new ResponseEntity<>(updatedProduct.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id")  Long id)  {
        logger.info("Received request to delete product with ID: {}", id);
        Boolean result = productService.deleteProduct(id);
        if(!result){
            logger.warn("Product with ID {} not found for deletion", id);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("Product with ID {} deleted successfully", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getUserListPage(HttpServletResponse response){
        logger.info("Received request to fetch all products");
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("All products fetched successfully. Total products: {}", products.size());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/{userId}/{productId}")
    public ResponseEntity<HttpStatus> addProductByUser(@PathVariable("userId") Long userId, @PathVariable("productId") Long productId) {
        Boolean result = productService.addProductByUser(userId, productId);
        if (!result) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
