package org.example.jdbc;

import org.example.IUserRepository;
import org.example.Role;
import org.example.User;
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
public class UserJdbcRepository implements IUserRepository{

    private final DataSource dataSource;

    public UserJdbcRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAll(){
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        Connection connection_C = DataSourceUtils.getConnection(dataSource);

        try(PreparedStatement stmt = connection_C.prepareStatement(sql);
            ResultSet x = stmt.executeQuery()){

            while(x.next()){
                users.add(new User(
                        x.getString("id"),
                        x.getString("login"),
                        x.getString("password_hash"),
                        Role.valueOf(x.getString("role"))
                ));
            }
        }catch(SQLException e){
            throw new RuntimeException("Błąd podczas pobierania wszystkich użytkowników", e);
        }finally{
            DataSourceUtils.releaseConnection(connection_C, dataSource);
        }
        return users;
    }

    @Override
    public Optional<User> findById(String id){
        String sql = "SELECT * FROM users WHERE id = ?";

        Connection connection_C = DataSourceUtils.getConnection(dataSource);

        try(PreparedStatement linij = connection_C.prepareStatement(sql)){
            linij.setString(1, id);
            ResultSet x = linij.executeQuery();
            if(x.next()){
                return Optional.of(new User(
                        x.getString("id"),
                        x.getString("login"),
                        x.getString("password_hash"),
                        Role.valueOf(x.getString("role"))
                ));
            }
        }catch(SQLException e){
            throw new RuntimeException("Błąd podczas pobierania użytkownika po ID", e);
        }finally{
            DataSourceUtils.releaseConnection(connection_C, dataSource);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLogin(String login){
        String sql = "SELECT * FROM users WHERE login = ?";

        Connection connection_C = DataSourceUtils.getConnection(dataSource);

        try(PreparedStatement linij = connection_C.prepareStatement(sql)){
            linij.setString(1, login);
            ResultSet x = linij.executeQuery();
            if(x.next()){
                return Optional.of(new User(
                        x.getString("id"),
                        x.getString("login"),
                        x.getString("password_hash"),
                        Role.valueOf(x.getString("role"))
                ));
            }
        }catch(SQLException e){
            throw new RuntimeException("Błąd podczas pobierania użytkownika po loginie", e);
        }finally{
            DataSourceUtils.releaseConnection(connection_C, dataSource);
        }
        return Optional.empty();
    }

    @Override
    public User save(User user){
        if(user.getId() == null || user.getId().isEmpty()){
            user.setId(UUID.randomUUID().toString());
        }

        String sql = "INSERT INTO users (id, login, password_hash, role) VALUES (?, ?, ?, ?) ON CONFLICT (id) DO UPDATE SET " +
                "login = EXCLUDED.login, password_hash = EXCLUDED.password_hash, role = EXCLUDED.role";

        Connection connection_C = DataSourceUtils.getConnection(dataSource);

        try(PreparedStatement linij = connection_C.prepareStatement(sql)){
            linij.setString(1, user.getId());
            linij.setString(2, user.getLogin());
            linij.setString(3, user.getPasswordHash());
            linij.setString(4, user.getRole().toString());
            linij.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException("Błąd podczas zapisu użytkownika", e);
        }finally{
            DataSourceUtils.releaseConnection(connection_C, dataSource);
        }
        return user;
    }

    @Override
    public void deleteById(String id){
        String sql = "DELETE FROM users WHERE id = ?";

        Connection connection_C = DataSourceUtils.getConnection(dataSource);

        try(PreparedStatement linij = connection_C.prepareStatement(sql)){
            linij.setString(1, id);
            linij.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException("Błąd podczas usuwania użytkownika", e);
        }finally{
            DataSourceUtils.releaseConnection(connection_C, dataSource);
        }
    }
}