package org.example;

public interface IRentalService{
    boolean rentVehicle(String userId, String vehicleId);
    boolean returnVehicle(String userId);
}