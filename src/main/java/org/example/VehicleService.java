package org.example;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService implements IVehicleService{
    private final IVehicleRepository vehicleRepo;
    private final IRentalRepository rentalRepo;
    private final VehicleValidator validator;

    public VehicleService(IVehicleRepository vehicleRepo, IRentalRepository rentalRepo, VehicleValidator validator){
        this.vehicleRepo = vehicleRepo;
        this.rentalRepo = rentalRepo;
        this.validator = validator;
    }
    @Override
    public List<Vehicle> getAvailableVehicles(){
        List<Vehicle> allVehicles = vehicleRepo.findAll();
        List<Vehicle> available = new ArrayList<>();

        for(Vehicle x : allVehicles){
            if(rentalRepo.findByVehicleIdAndReturnDateIsNull(x.getId()).isEmpty()){
                available.add(x);
            }
        }
        return available;
    }
    @Override
    public List<Vehicle> getAllVehicles(){
        return vehicleRepo.findAll();
    }

    @Override
    public void addVehicle(Vehicle vehicle) throws IllegalArgumentException {
        validator.validate(vehicle);
        vehicleRepo.save(vehicle);
    }

    @Override
    public void removeVehicle(String id) throws Exception{
        vehicleRepo.deleteById(id);
    }
}