package com.example.damage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DamageAnalysisResponse {
    private String fileName;
    private String prediction;
    private String status;
}
