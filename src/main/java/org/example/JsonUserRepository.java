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
public class JsonUserRepository implements IUserRepository {

    private final JsonFileStorage<User> storage;

    public JsonUserRepository(){
        Type type = new TypeToken<List<User>>(){}.getType();
        this.storage = new JsonFileStorage<>("users.json", type);
    }

    @Override
    public List<User> findAll(){
        return storage.load();
    }

    @Override
    public Optional<User> findById(String id){
        List<User> users = findAll();
        for(User x : users) {
            if(x.getId().equals(id)){
                return Optional.of(x);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLogin(String login){
        List<User> users = findAll();
        for(User x : users){
            if(x.getLogin().equals(login)){
                return Optional.of(x);
            }
        }
        return Optional.empty();
    }

    @Override
    public User save(User user){
        List<User> users = findAll();

        if(user.getId() == null || user.getId().isEmpty()){
            user.setId(UUID.randomUUID().toString());
            users.add(user);
        }else{
            for(int i = 0; i < users.size(); i++){
                if(users.get(i).getId().equals(user.getId())){
                    users.set(i, user);
                    break;
                }
            }
        }
        storage.save(users);
        return user;
    }

    @Override
    public void deleteById(String id){
        List<User> users = findAll();
        User doUsuniecia = null;

        for(User x : users){
            if(x.getId().equals(id)){
                doUsuniecia = x;
                break;
            }
        }

        if(doUsuniecia != null){
            users.remove(doUsuniecia);
            storage.save(users);
        }
    }
}