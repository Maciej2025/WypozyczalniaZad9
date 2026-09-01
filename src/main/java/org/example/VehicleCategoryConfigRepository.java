package org.example;

import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.List;

@Repository
public class VehicleCategoryConfigRepository implements IVehicleCategoryConfigRepository{
    private final JsonFileStorage<VehicleCategoryConfig> storage;

    public VehicleCategoryConfigRepository(){
        Type type = new TypeToken<List<VehicleCategoryConfig>>(){}.getType();
        this.storage = new JsonFileStorage<>("categories.json", type);
    }

    @Override
    public List<VehicleCategoryConfig> findAll(){
        return storage.load();
    }
}