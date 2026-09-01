package org.example;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
public class RentalRepositoryJpaAdapter implements IRentalRepository{

    private final SpringDataRentalRepository springRepo;

    public RentalRepositoryJpaAdapter(SpringDataRentalRepository springRepo){
        this.springRepo = springRepo;
    }

    @Override
    public List<Rental> findAll(){
        return springRepo.findAll();
    }

    @Override
    public Optional<Rental> findById(String id){
        return springRepo.findById(id);
    }

    @Override
    public Rental save(Rental rental){
        return springRepo.save(rental);
    }

    @Override
    public void deleteById(String id){
        springRepo.deleteById(id);
    }

    @Override
    public Optional<Rental> findByVehicleIdAndReturnDateIsNull(String vehicleId){
        return springRepo.findByVehicle_IdAndReturnDateTimeIsNull(vehicleId);
    }
}