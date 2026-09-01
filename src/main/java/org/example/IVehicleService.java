package org.example;

import java.util.List;

public interface IVehicleService{
    List<Vehicle> getAvailableVehicles();
    List<Vehicle> getAllVehicles();
    void addVehicle(Vehicle vehicle) throws IllegalArgumentException;
    void removeVehicle(String id) throws Exception;
}