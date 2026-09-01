package org.example.jdbc;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.IVehicleRepository;
import org.example.Vehicle;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import org.springframework.jdbc.datasource.DataSourceUtils;
import javax.sql.DataSource;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("jdbc")
public class VehicleJdbcRepository implements IVehicleRepository{
    private final Gson gson = new Gson();
    private final Type attributesType = new TypeToken<Map<String, Object>>(){}.getType();

    private final DataSource dataSource;

    public VehicleJdbcRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Vehicle> findAll(){
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicle";

        Connection connection_C = DataSourceUtils.getConnection(dataSource);

        try(PreparedStatement stmt = connection_C.prepareStatement(sql);
            ResultSet x = stmt.executeQuery()){

            while(x.next()){
                String attributesJson = x.getString("attributes");
                Map<String, Object> attributes = null;

                if(attributesJson != null && attributesJson.equals("null") == false){
                    attributes = gson.fromJson(attributesJson, attributesType);
                }

                vehicles.add(new Vehicle(
                        x.getString("id"),
                        x.getString("category"),
                        x.getString("brand"),
                        x.getString("model"),
                        x.getInt("year"),
                        x.getString("plate"),
                        x.getDouble("price"),
                        attributes
                ));
            }
        }catch(SQLException e){
            throw new RuntimeException("Błąd podczas pobierania pojazdów", e);
        }finally{
            DataSourceUtils.releaseConnection(connection_C, dataSource);
        }
        return vehicles;
    }

    @Override
    public Optional<Vehicle> findById(String id){
        String sql = "SELECT * FROM vehicle WHERE id = ?";

        Connection connection_C = DataSourceUtils.getConnection(dataSource);

        try(PreparedStatement linij = connection_C.prepareStatement(sql)){
            linij.setString(1, id);
            ResultSet x = linij.executeQuery();
            if(x.next()){
                String attributesJson = x.getString("attributes");
                Map<String, Object> attributes = null;

                if(attributesJson != null && attributesJson.equals("null") == false){
                    attributes = gson.fromJson(attributesJson, attributesType);
                }

                return Optional.of(new Vehicle(
                        x.getString("id"),
                        x.getString("category"),
                        x.getString("brand"),
                        x.getString("model"),
                        x.getInt("year"),
                        x.getString("plate"),
                        x.getDouble("price"),
                        attributes
                ));
            }
        }catch(SQLException e){
            throw new RuntimeException("Błąd podczas pobierania pojazdu", e);
        }finally{
            DataSourceUtils.releaseConnection(connection_C, dataSource);
        }
        return Optional.empty();
    }

    @Override
    public Vehicle save(Vehicle vehicle){
        if(vehicle.getId() == null || vehicle.getId().isEmpty()){
            vehicle.setId(UUID.randomUUID().toString());
        }

        String attributesJson = gson.toJson(vehicle.getAttributes());

        String sql = "INSERT INTO vehicle (id, category, brand, model, year, plate, price, attributes) VALUES (?, ?, ?, ?, ?, ?, ?, CAST(? AS JSONB)) ON CONFLICT (id) DO UPDATE SET " +
                "category = EXCLUDED.category, brand = EXCLUDED.brand, model = EXCLUDED.model, year = EXCLUDED.year, plate = EXCLUDED.plate, price = EXCLUDED.price, attributes = EXCLUDED.attributes";

        Connection connection_C = DataSourceUtils.getConnection(dataSource);

        try(PreparedStatement linij = connection_C.prepareStatement(sql)){
            linij.setString(1, vehicle.getId());
            linij.setString(2, vehicle.getCategory());
            linij.setString(3, vehicle.getBrand());
            linij.setString(4, vehicle.getModel());
            linij.setInt(5, vehicle.getYear());
            linij.setString(6, vehicle.getPlate());
            linij.setDouble(7, vehicle.getPrice());
            linij.setString(8, attributesJson);
            linij.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException("Błąd podczas zapisu pojazdu", e);
        }finally{
            DataSourceUtils.releaseConnection(connection_C, dataSource);
        }
        return vehicle;
    }

    @Override
    public void deleteById(String id){
        String sql = "DELETE FROM vehicle WHERE id = ?";

        Connection connection_C = DataSourceUtils.getConnection(dataSource);

        try(PreparedStatement linij = connection_C.prepareStatement(sql)){
            linij.setString(1, id);
            linij.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException("Błąd popdczas usuwania pojazdu", e);
        }finally{
            DataSourceUtils.releaseConnection(connection_C, dataSource);
        }
    }
}