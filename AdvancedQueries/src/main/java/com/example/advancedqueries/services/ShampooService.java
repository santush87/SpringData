package com.example.advancedqueries.services;

import com.example.advancedqueries.entities.Shampoo;

import java.util.List;

public interface ShampooService {
    List<Shampoo> findByBrand(String brand);

    List<Shampoo> findByBrandAndSize(String brand, String size);
    List<Shampoo> findBySize(String size);
    List<Shampoo> findBySizeOrderByIdDesc(String parsed);
    List<Shampoo> findByIngredient(String ingridiant);

    List<Shampoo> findByIngredient(List<String> ingridiants);
}
