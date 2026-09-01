package org.example;

import com.google.gson.reflect.TypeToken;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("json")
public class JsonVehicleRepository implements IVehicleRepository{

    private final JsonFileStorage<Vehicle> storage;

    public JsonVehicleRepository(){
        Type type = new TypeToken<List<Vehicle>>(){}.getType();
        this.storage = new JsonFileStorage<>("vehicles.json", type);
    }

    @Override
    public List<Vehicle> findAll(){
        return storage.load();
    }

    @Override
    public Optional<Vehicle> findById(String id){
        List<Vehicle> vehicles = findAll();

        for(Vehicle x : vehicles){
            if(x.getId().equals(id)){
                return Optional.of(x);
            }
        }
        return Optional.empty();
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        List<Vehicle> vehicles = findAll();

        if(vehicle.getId() == null || vehicle.getId().isEmpty()){
            vehicle.setId(UUID.randomUUID().toString());
            vehicles.add(vehicle);
        }else{
            for(int i = 0; i < vehicles.size(); i++){
                if(vehicles.get(i).getId().equals(vehicle.getId())){
                    vehicles.set(i, vehicle);
                    break;
                }
            }
        }
        storage.save(vehicles);
        return vehicle;
    }

    @Override
    public void deleteById(String id){
        List<Vehicle> vehicles = findAll();
        Vehicle doUsuniecia = null;

        for(Vehicle x : vehicles){
            if(x.getId().equals(id)){
                doUsuniecia = x;
                break;
            }
        }

        if(doUsuniecia != null){
            vehicles.remove(doUsuniecia);
            storage.save(vehicles);
        }
    }
}