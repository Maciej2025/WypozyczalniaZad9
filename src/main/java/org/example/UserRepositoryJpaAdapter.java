package org.example;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
public class UserRepositoryJpaAdapter implements IUserRepository{

    private final SpringDataUserRepository springRepo;

    public UserRepositoryJpaAdapter(SpringDataUserRepository springRepo){
        this.springRepo = springRepo;
    }

    @Override
    public List<User> findAll(){
        return springRepo.findAll();
    }

    @Override
    public Optional<User> findById(String id){
        return springRepo.findById(id);
    }

    @Override
    public Optional<User> findByLogin(String login){
        return springRepo.findByLogin(login);
    }

    @Override
    public User save(User user){
        return springRepo.save(user);
    }

    @Override
    public void deleteById(String id){
        springRepo.deleteById(id);
    }
}