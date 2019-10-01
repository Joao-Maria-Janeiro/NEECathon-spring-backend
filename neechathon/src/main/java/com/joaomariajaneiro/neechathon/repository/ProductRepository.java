package com.joaomariajaneiro.neechathon.repository;

import com.joaomariajaneiro.neechathon.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    Product findByName(String name);
}
