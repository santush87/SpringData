package com.example.advancedqueries.repositories;

import com.example.advancedqueries.entities.Shampoo;
import com.example.advancedqueries.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {

    List<Shampoo> findByBrand(String brand);

    List<Shampoo> findByBrandAndSize(String brand, Size size);

    List<Shampoo> findBySize(Size parsed);

    List<Shampoo> findBySizeOrderByIdDesc(String parsed);

    @Query("SELECT s FROM Shampoo AS s " +
            "JOIN s.ingredients AS i " +
            "WHERE i.name = :name")
    List<Shampoo> findByIngredient(@Param("name") String ingridiant);

    @Query("SELECT s FROM Shampoo AS s " +
            "JOIN s.ingredients AS i " +
            "WHERE i.name IN :ingridiants")
    List<Shampoo> findByIngredients(List<String> ingridiants);
}
