package org.example;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class VehicleValidator{
    private final VehicleCategoryConfigService configService;


    public VehicleValidator(VehicleCategoryConfigService configService){
        this.configService = configService;
    }

    public void validate(Vehicle vehicle) throws IllegalArgumentException{

        if(vehicle.getBrand() == null || vehicle.getBrand().isBlank()) throw new IllegalArgumentException("Marka nie może być pusta");
        if(vehicle.getModel() == null || vehicle.getModel().isBlank()) throw new IllegalArgumentException("Model nie może być pusty");
        if(vehicle.getYear() <= 1900) throw new IllegalArgumentException("Tego roku jeszcze nie istniały samochody");
        if(vehicle.getPrice() < 0) throw new IllegalArgumentException("Cena jest ujemna");

        Optional<VehicleCategoryConfig> configOpt = configService.getByCategory(vehicle.getCategory());
        if(configOpt.isEmpty()){
            throw new IllegalArgumentException("Nieznana kategoria pojazdu: " + vehicle.getCategory());
        }

        VehicleCategoryConfig config = configOpt.get();
        Map<String, Object> attributes = vehicle.getAttributes();

        if(config.getAttributes() != null){
            for(Map.Entry<String, String> reguly : config.getAttributes().entrySet()){
                String wymaganyKlucz = reguly.getKey();
                String oczekiwanyTyp = reguly.getValue().toLowerCase();

                if(attributes == null || attributes.containsKey(wymaganyKlucz) == false){
                    throw new IllegalArgumentException("Kategoria " + config.getCategory() + " wymaga atrybutu: " + wymaganyKlucz);
                }

                Object wpisanaWartosc = attributes.get(wymaganyKlucz);

                if(oczekiwanyTyp.equals("number") && (wpisanaWartosc instanceof Number) == false){
                    throw new IllegalArgumentException("Atrybut " + wymaganyKlucz + " musi być liczbą");
                }
                if(oczekiwanyTyp.equals("boolean") && (wpisanaWartosc instanceof Boolean) == false) {
                    throw new IllegalArgumentException("Atrybut " + wymaganyKlucz + " musi być typu prawda/fałsz");
                }
            }
        }
    }
}