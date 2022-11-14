package com.example.advancedqueries.services;

import com.example.advancedqueries.entities.Shampoo;
import com.example.advancedqueries.entities.Size;
import com.example.advancedqueries.repositories.ShampooRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShampooServiceImpl implements ShampooService {
    private final ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> findByBrand(String brand) {
        return this.shampooRepository.findByBrand(brand);
    }

    @Override
    public List<Shampoo> findByBrandAndSize(String brand, String size) {
        Size parsed = Size.valueOf(size.toUpperCase());
        return this.shampooRepository.findByBrandAndSize(brand, parsed);
    }

    @Override
    public List<Shampoo> findBySize(String size) {
        Size parsed = Size.valueOf(size.toUpperCase());
        return this.shampooRepository.findBySize(parsed);
    }

    @Override
    public List<Shampoo> findBySizeOrderByIdDesc(String size) {
        Size parsed = Size.valueOf(size.toUpperCase());
        return this.shampooRepository.findBySize(parsed);
    }

    @Override
    public List<Shampoo> findByIngredient(String ingridiant) {
        return this.shampooRepository.findByIngredient(ingridiant);
    }

    @Override
    public List<Shampoo> findByIngredient(List<String> ingridiants) {
        return this.shampooRepository.findByIngredients(ingridiants);
    }


}
