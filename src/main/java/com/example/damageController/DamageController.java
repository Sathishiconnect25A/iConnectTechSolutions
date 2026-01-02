package com.example.damageController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.damage.dto.DamageAnalysisResponse;
import com.example.damage.service.DamageDetectionModelService;
import com.example.damageEntity.DamageRecord;
import com.example.damageRepository.DamageRepository;

@RestController
@RequestMapping("/api/damage")
public class DamageController {

    private final DamageDetectionModelService modelService;
    private final DamageRepository damageRepository;

    @Autowired
    public DamageController(DamageDetectionModelService modelService, DamageRepository damageRepository) {
        this.modelService = modelService;
        this.damageRepository = damageRepository;
    }

    @PostMapping("/detect")
    public ResponseEntity<DamageAnalysisResponse> detectDamage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(new DamageAnalysisResponse(null, null, "File is empty"));
        }

        try {
            // 1. Save file temporarily
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            File tempFile = File.createTempFile("road_damage_", fileName);
            Files.copy(file.getInputStream(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // 2. Call Service to get prediction (Simulation)
            String result = modelService.predict(tempFile);

            // 3. Save Record to Database (H2)
            DamageRecord record = new DamageRecord(file.getOriginalFilename(), result);
            damageRepository.save(record);

            // 4. Cleanup temp file
            tempFile.delete();

            // 5. Return Response
            return ResponseEntity.ok(new DamageAnalysisResponse(file.getOriginalFilename(), result, "Success"));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(new DamageAnalysisResponse(file.getOriginalFilename(),
                    null, "Processing failed: " + e.getMessage()));
        }
    }

    @PostMapping("/train")
    public ResponseEntity<String> trainModel() {
        String result = modelService.trainModel();
        return ResponseEntity.ok(result);
    }
}
