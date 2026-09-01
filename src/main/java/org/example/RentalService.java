package org.example;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RentalService implements IRentalService{
    private final IRentalRepository rentalRepo;

    public RentalService(IRentalRepository rentalRepo){
        this.rentalRepo = rentalRepo;
    }

    @Override
    public boolean rentVehicle(String userId, String vehicleId) {
        if(rentalRepo.findByVehicleIdAndReturnDateIsNull(vehicleId).isPresent()){
            return false;
        }

        User userRef = User.builder().id(userId).build();
        Vehicle vehicleRef = Vehicle.builder().id(vehicleId).build();

        Rental rental = Rental.builder()
                .id(null)
                .user(userRef)
                .vehicle(vehicleRef)
                .rentDateTime(LocalDateTime.now().toString())
                .build();

        rentalRepo.save(rental);
        return true;
    }
    @Override
    public boolean returnVehicle(String userId){
        List<Rental> rentals = rentalRepo.findAll();
        Rental activeRental = null;

        for(Rental x : rentals){
            if (x.getUser().getId().equals(userId) && x.isActive()){
                activeRental = x;
                break;
            }
        }

        if(activeRental != null){
            activeRental.setReturnDateTime(LocalDateTime.now().toString());
            rentalRepo.save(activeRental);
            return true;
        }
        return false;
    }
}