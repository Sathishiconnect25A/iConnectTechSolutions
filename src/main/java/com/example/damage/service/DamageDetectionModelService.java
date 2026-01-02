package com.example.damage.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Random;

@Service
public class DamageDetectionModelService {

    private final Random random = new Random();

    public DamageDetectionModelService() {
        // Standard Java Service - No heavy initialization
    }

    public String predict(File imageFile) throws IOException {
        if (!imageFile.exists())
            return "File not found";

        // MOCK LOGIC for Standard Java Implementation
        // Randomly determine if the image is damaged or not.
        boolean isDamaged = random.nextBoolean();

        // Generate a random confidence score between 70% and 99%
        double confidence = 0.70 + (random.nextDouble() * 0.29);

        if (isDamaged) {
            return "Damaged (Confidence: " + String.format("%.2f", confidence * 100) + "%)";
        } else {
            return "Intact (Confidence: " + String.format("%.2f", confidence * 100) + "%)";
        }
    }

    public String trainModel() {
        // MOCK LOGIC
        return "Training Simulation Completed Successfully.";
    }
}
