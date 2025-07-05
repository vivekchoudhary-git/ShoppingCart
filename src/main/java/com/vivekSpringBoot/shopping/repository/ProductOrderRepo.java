package com.vivekSpringBoot.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivekSpringBoot.shopping.model.ProductOrder;

public interface ProductOrderRepo extends JpaRepository<ProductOrder, Integer> {

}
