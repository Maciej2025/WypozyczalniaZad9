package org.example;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "vehicle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String category;
    private String brand;
    private String model;
    private int year;
    private String plate;

    @Column(columnDefinition = "NUMERIC")
    private double price;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> attributes = new HashMap<>();

    @Override
    public String toString(){
        return "id: " + id + ", brand: " +  brand +  ", model: " +  model +  ", year: " +  year +  ", price: " +  price;
    }
}