package org.example.spring;

import org.example.IRentalRepository;
import org.example.IRentalService;
import org.example.Rental;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rentals")
public class RentalController{
    private final IRentalService rentalService;
    private final IRentalRepository rentalRepo;

    public RentalController(IRentalService rentalService, IRentalRepository rentalRepo){
        this.rentalService = rentalService;
        this.rentalRepo = rentalRepo;
    }

    @GetMapping
    public List<Rental> list(){
        return rentalRepo.findAll();
    }

    @GetMapping("/users/{userId}")
    public List<Rental> userRentals(@PathVariable String userId){
        List<Rental> userRentals = new ArrayList<>();

        for(Rental x : rentalRepo.findAll()){
            if(x.getUser() != null && x.getUser().getId().equals(userId)){
                userRentals.add(x);
            }
        }
        return userRentals;
    }

    @PostMapping("/users/{userId}/rent/{vehicleId}")
    public Rental rent(@PathVariable String userId, @PathVariable String vehicleId){
        if(rentalService.rentVehicle(userId, vehicleId) == false){
            throw new IllegalStateException("Nie można wypożyczyć tego pojazdu (jest już zajęty)");
        }

        Optional<Rental> rental = rentalRepo.findByVehicleIdAndReturnDateIsNull(vehicleId);

        if(rental.isPresent()){
            return rental.get();
        }else{
            throw new IllegalStateException("Błąd odczytu wypożyczenia z bazy");
        }
    }

    @PostMapping("/users/{userId}/return")
    public Rental returnVehicle(@PathVariable String userId){
        if(rentalService.returnVehicle(userId) == false){
            throw new IllegalStateException("Brak aktywnego wypożyczenia dla tego użytkownika");
        }

        Rental lastReturned = null;
        for(Rental x : rentalRepo.findAll()){
            if(x.getUser() != null && x.getUser().getId().equals(userId) && x.isActive() == false){
                lastReturned = x;
            }
        }
        return lastReturned;
    }
}