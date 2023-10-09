package net.nvsoftware.springlive.service;

import net.nvsoftware.springlive.error.ProductNotFoundException;
import net.nvsoftware.springlive.model.Product;

import java.util.List;

public interface ProductService {
    Product save(Product product);

    List<Product> getAll();
    Product getById(String id) throws ProductNotFoundException;

    String deleteById(String id);
}
