package org.example;

import org.example.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataVehicleRepository extends JpaRepository<Vehicle, String>{
}