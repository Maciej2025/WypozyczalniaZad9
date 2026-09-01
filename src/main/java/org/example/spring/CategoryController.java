package org.example.spring;

import org.example.VehicleCategoryConfig;
import org.example.VehicleCategoryConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController{
    private final VehicleCategoryConfigService configService;

    public CategoryController(VehicleCategoryConfigService configService){
        this.configService = configService;
    }

    @GetMapping
    public List<VehicleCategoryConfig> list(){
        return configService.getAllCategories();
    }

    @GetMapping("/{category}")
    public VehicleCategoryConfig get(@PathVariable String category){
        Optional<VehicleCategoryConfig> config = configService.getByCategory(category);

        if(config.isPresent()){
            return config.get();
        }else{
            throw new IllegalArgumentException("Kategoria nie istnieje");
        }
    }
}