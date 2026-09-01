package org.example;

import com.google.gson.reflect.TypeToken;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
@Profile("json")
public class JsonRentalRepository implements IRentalRepository {

    private final JsonFileStorage<Rental> storage;

    public JsonRentalRepository() {
        Type type = new TypeToken<List<Rental>>() {
        }.getType();
        this.storage = new JsonFileStorage<>("rentals.json", type);
    }

    @Override
    public List<Rental> findAll() {
        return storage.load();
    }

    @Override
    public Optional<Rental> findById(String id) {
        List<Rental> rentals = findAll();
        for (Rental x : rentals) {
            if (x.getId().equals(id)) {
                return Optional.of(x);
            }
        }
        return Optional.empty();
    }

    @Override
    public Rental save(Rental rental) {
        List<Rental> rentals = findAll();

        if (rental.getId() == null || rental.getId().isEmpty()) {
            rental.setId(UUID.randomUUID().toString());
            rentals.add(rental);
        } else {
            for (int i = 0; i < rentals.size(); i++) {
                if (rentals.get(i).getId().equals(rental.getId())) {
                    rentals.set(i, rental);
                    break;
                }
            }
        }
        storage.save(rentals);
        return rental;
    }

    @Override
    public void deleteById(String id) {
        List<Rental> rentals = findAll();
        Rental doUsuniecia = null;

        for (Rental x : rentals) {
            if (x.getId().equals(id)) {
                doUsuniecia = x;
                break;
            }
        }

        if (doUsuniecia != null) {
            rentals.remove(doUsuniecia);
            storage.save(rentals);
        }
    }

    @Override
    public Optional<Rental> findByVehicleIdAndReturnDateIsNull(String vehicleId) {
        List<Rental> rentals = findAll();
        for (Rental x : rentals) {
            if (x.getVehicleId().equals(vehicleId) && x.isActive()) {
                return Optional.of(x);
            }
        }
        return Optional.empty();
    }
}