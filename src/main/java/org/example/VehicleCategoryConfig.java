package org.example;

import lombok.*;
import java.util.Map;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VehicleCategoryConfig{
    private String category;
    private Map<String, String> attributes;
}