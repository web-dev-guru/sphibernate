package com.demo.controller;

import com.demo.dao.UserRepository;
import com.demo.exception.ResourceNotFoundException;
import com.demo.model.Role;
import com.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/hello")
    public ResponseEntity<Object> hello(){
        Map<String,String> responseMsg= new HashMap<String,String>();
        responseMsg.put("status","OK");
        return new ResponseEntity<>(responseMsg, HttpStatus.OK);

    }
    /**
     * POST: http://localhost:8080/user
     *
     *{
     * 	"firstname": "mark",
     * 	"lastname":"wong",
     * 	"roles": [
     *                 {
     *                    "name": "admin"
     *                 },
     *                 {
     *                     "name": "developer"
     *                 }
     *             ]
     * }
     *
     *
     *
     * if name is null will return message below
     *
     *
     * {
     *      * 	"firstname": "mark",
     *      * 	"lastname":"wong",
     *      * 	"roles": [
     *      *                 {
     *      *
     *      *                 },
     *      *                 {
     *      *                     "name": "developer"
     *      *                 }
     *      *             ]
     *      * }
     * {
     *     "name": "Name is mandatory"
     * }
     * ***/

    @PostMapping("/user")
    public User insert(@Valid @RequestBody User user) {
        if(user.getRoles()!=null){
            user.getRoles().stream().forEach(role->role.setUser(user));
        }
        return userRepository.save(user);
    }


    @GetMapping("/users")
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
/**
 * GET:http://localhost:8080/user/1
 *
 * **/
    @GetMapping("/user/{userId}")
    public User getUsersById(@PathVariable(value = "userId") Long userId) throws Exception {
        return userRepository.findById(userId).orElseThrow(() -> new Exception("UserId " + userId + " not found"));
    }


    @PutMapping("/user/{userId}")
    public User updateUsersById(@PathVariable(value = "userId") Long userId,@Valid @RequestBody User userRequest) throws Exception {
        return userRepository.findById(userId).map(user -> {
            user.setFirstname(userRequest.getFirstname());
            user.setLastname(userRequest.getLastname());
            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("UserId " + userId + " not found"));
    }
    /**
     *
     * DELETE: http://localhost:8080/user/2
     *
     * ***/
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        return userRepository.findById(userId).map(user -> {
            userRepository.delete(user);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("UserId " + userId + " not found"));
    }


}
