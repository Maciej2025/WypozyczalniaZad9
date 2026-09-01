package org.example;

import org.example.IVehicleRepository;
import org.example.SpringDataVehicleRepository;
import org.example.Vehicle;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
public class VehicleRepositoryJpaAdapter implements IVehicleRepository{

    private final SpringDataVehicleRepository springRepo;

    public VehicleRepositoryJpaAdapter(SpringDataVehicleRepository springRepo){
        this.springRepo = springRepo;
    }

    @Override
    public List<Vehicle> findAll(){
        return springRepo.findAll();
    }

    @Override
    public Optional<Vehicle> findById(String id){
        return springRepo.findById(id);
    }

    @Override
    public Vehicle save(Vehicle vehicle){
        return springRepo.save(vehicle);
    }

    @Override
    public void deleteById(String id){
        springRepo.deleteById(id);
    }
}