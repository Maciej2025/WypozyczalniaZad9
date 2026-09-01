package org.example;
import org.example.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<User, String>{
    Optional<User> findByLogin(String login);
}