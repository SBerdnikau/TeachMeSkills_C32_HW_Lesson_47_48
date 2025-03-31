package com.tms.service;

import com.tms.model.User;
import com.tms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long id){
        return userRepository.getUserById(id);
    }

    public Optional<User> updateUser(User user){
//        Boolean result = userRepository.updateUser(user);
//        if(result){
//            return getUserById(user.getId());
//        }
        return Optional.empty();
    }

    public Optional<User> deleteUser(Long id){
//        Boolean result = userRepository.deleteUser(id);
//        if(result){
//            return getUserById(id);
//        }
        return Optional.empty();
    }
    
    public Boolean createUser(User user){
        user.setIsDeleted(false);
        return  userRepository.createUser(user);
    }

    public List<User> getAllUsers(){
        List<User> users = userRepository.getAllUsers();
        if(users.isEmpty()){
            return null;
        }
        return users;
    }
}
