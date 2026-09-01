package org.example.spring;

import org.example.IUserRepository;
import org.example.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController{
    private final IUserRepository userRepo;

    public UserController(IUserRepository userRepo){
        this.userRepo = userRepo;
    }

    @GetMapping
    public List<User> list(){
        return userRepo.findAll();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable String id){
        Optional<User> user = userRepo.findById(id);

        if(user.isPresent()){
            return user.get();
        }else{
            throw new IllegalArgumentException("Użytkownik nie istnieje");
        }
    }
}