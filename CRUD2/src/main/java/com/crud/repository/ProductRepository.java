package com.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
