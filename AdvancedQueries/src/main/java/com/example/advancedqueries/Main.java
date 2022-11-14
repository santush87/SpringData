package com.example.advancedqueries;

import com.example.advancedqueries.entities.Shampoo;
import com.example.advancedqueries.services.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Main implements CommandLineRunner {
    private final ShampooService shampooService;

    @Autowired
    public Main(ShampooService shampooService) {
        this.shampooService = shampooService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();

        List<String> ingridiants = new ArrayList<>();

        while (!text.isBlank()){
            ingridiants.add(text);
        }

        for (Shampoo shampoo : this.shampooService.findByIngredient(ingridiants)){
            System.out.println(shampoo);
        }
    }
}
