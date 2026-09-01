package org.example;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleCategoryConfigService{
    private final IVehicleCategoryConfigRepository repo;

    public VehicleCategoryConfigService(IVehicleCategoryConfigRepository repo){
        this.repo = repo;
    }

    public List<VehicleCategoryConfig> getAllCategories(){
        return repo.findAll();
    }

    public Optional<VehicleCategoryConfig> getByCategory(String category){
        for(VehicleCategoryConfig config : repo.findAll()){
            if(config.getCategory().equalsIgnoreCase(category)){
                return Optional.of(config);
            }
        }
        return Optional.empty();
    }
}