package org.example.jdbc;

import org.example.IRentalRepository;
import org.example.Rental;
import org.example.User;
import org.example.Vehicle;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import org.springframework.jdbc.datasource.DataSourceUtils;
import javax.sql.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("jdbc")
public class RentalJdbcRepository implements IRentalRepository{

    private final DataSource dataSource;

    public RentalJdbcRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public List<Rental> findAll(){
        List<Rental> rentals = new ArrayList<>();
        String sql = "SELECT * FROM rental";

        Connection connection_C = DataSourceUtils.getConnection(dataSource);

        try(PreparedStatement stmt = connection_C.prepareStatement(sql);
            ResultSet x = stmt.executeQuery()){

            while(x.next()){
                User tymczasUser = User.builder().id(x.getString("user_id")).build();
                Vehicle tymczasVehicle = Vehicle.builder().id(x.getString("vehicle_id")).build();

                rentals.add(Rental.builder()
                        .id(x.getString("id"))
                        .vehicle(tymczasVehicle)
                        .user(tymczasUser)
                        .rentDateTime(x.getString("rent_date"))
                        .returnDateTime(x.getString("return_date"))
                        .build());
            }
        }catch(SQLException e){
            throw new RuntimeException("Błąd podczas pobierania rentals", e);
        }finally{
            DataSourceUtils.releaseConnection(connection_C, dataSource);
        }
        return rentals;
    }

    @Override
    public Optional<Rental> findById(String id){
        String sql = "SELECT * FROM rental WHERE id = ?";

        Connection connection_C = DataSourceUtils.getConnection(dataSource);

        try(PreparedStatement linij = connection_C.prepareStatement(sql)){
            linij.setString(1, id);
            ResultSet x = linij.executeQuery();
            if(x.next()){
                User tymczasUser = User.builder().id(x.getString("user_id")).build();
                Vehicle tymczasVehicle = Vehicle.builder().id(x.getString("vehicle_id")).build();

                return Optional.of(Rental.builder()
                        .id(x.getString("id"))
                        .vehicle(tymczasVehicle)
                        .user(tymczasUser)
                        .rentDateTime(x.getString("rent_date"))
                        .returnDateTime(x.getString("return_date"))
                        .build());
            }
        }catch(SQLException e){
            throw new RuntimeException("Błąd podczas pobierania rental", e);
        }finally{
            DataSourceUtils.releaseConnection(connection_C, dataSource);
        }
        return Optional.empty();
    }

    @Override
    public Rental save(Rental rental){
        if(rental.getId() == null || rental.getId().isEmpty()){
            rental.setId(UUID.randomUUID().toString());
        }

        String sql = "INSERT INTO rental (id, vehicle_id, user_id, rent_date, return_date) VALUES (?, ?, ?, ?, ?) ON CONFLICT (id) DO UPDATE SET " +
                "return_date = EXCLUDED.return_date";

        Connection connection_C = DataSourceUtils.getConnection(dataSource);

        try(PreparedStatement linij = connection_C.prepareStatement(sql)){
            linij.setString(1, rental.getId());
            linij.setString(2, rental.getVehicleId());
            linij.setString(3, rental.getUserId());
            linij.setString(4, rental.getRentDateTime());
            linij.setString(5, rental.getReturnDateTime());
            linij.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException("Błąd podczas zapisu rental", e);
        }finally{
            DataSourceUtils.releaseConnection(connection_C, dataSource);
        }
        return rental;
    }

    @Override
    public void deleteById(String id){
        String sql = "DELETE FROM rental WHERE id = ?";

        Connection connection_C = DataSourceUtils.getConnection(dataSource);

        try(PreparedStatement linij = connection_C.prepareStatement(sql)){
            linij.setString(1, id);
            linij.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException("Błąd podczas usuwania rental", e);
        }finally{
            DataSourceUtils.releaseConnection(connection_C, dataSource);
        }
    }

    @Override
    public Optional<Rental> findByVehicleIdAndReturnDateIsNull(String vehicleId){
        String sql = "SELECT * FROM rental WHERE vehicle_id = ? AND (return_date IS NULL OR return_date = '')";

        Connection connection_C = DataSourceUtils.getConnection(dataSource);

        try(PreparedStatement linij = connection_C.prepareStatement(sql)){
            linij.setString(1, vehicleId);
            ResultSet x = linij.executeQuery();
            if(x.next()){
                User tymczasUser = User.builder().id(x.getString("user_id")).build();
                Vehicle tymczasVehicle = Vehicle.builder().id(x.getString("vehicle_id")).build();

                return Optional.of(Rental.builder()
                        .id(x.getString("id"))
                        .vehicle(tymczasVehicle)
                        .user(tymczasUser)
                        .rentDateTime(x.getString("rent_date"))
                        .returnDateTime(x.getString("return_date"))
                        .build());
            }
        }catch(SQLException e){
            throw new RuntimeException("Błąd podczas wyszukiwania rental po vehicleId", e);
        }finally{
            DataSourceUtils.releaseConnection(connection_C, dataSource);
        }
        return Optional.empty();
    }
}