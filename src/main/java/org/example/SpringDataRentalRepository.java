package org.example;
import org.example.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataRentalRepository extends JpaRepository<Rental, String>{
    Optional<Rental> findByVehicle_IdAndReturnDateTimeIsNull(String vehicleId);
}