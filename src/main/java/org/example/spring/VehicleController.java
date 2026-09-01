package org.example.spring;

import org.example.IVehicleRepository;
import org.example.IVehicleService;
import org.example.Vehicle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController{
    private final IVehicleService vehicleService;
    private final IVehicleRepository vehicleRepo;

    public VehicleController(IVehicleService vehicleService, IVehicleRepository vehicleRepo){
        this.vehicleService = vehicleService;
        this.vehicleRepo = vehicleRepo;
    }

    @GetMapping
    public List<Vehicle> list(@RequestParam(name="available", required=false, defaultValue="false") boolean available){
        if(available){
            return vehicleService.getAvailableVehicles();
        }else{
            return vehicleService.getAllVehicles();
        }
    }

    @GetMapping("/{id}")
    public Vehicle get(@PathVariable String id){
        Optional<Vehicle> pojazd = vehicleRepo.findById(id);

        if(pojazd.isPresent()){
            return pojazd.get();
        }else{
            throw new IllegalArgumentException("Pojazd o podanym ID nie istnieje");
        }
    }

    @PostMapping
    public Vehicle create(@RequestBody Vehicle vehicle){
        vehicleService.addVehicle(vehicle);
        return vehicle;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws Exception{
        vehicleService.removeVehicle(id);
        return ResponseEntity.noContent().build();
    }
}