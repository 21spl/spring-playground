package io.github.spl21.microproject2.controller;



import io.github.spl21.microproject2.entity.User;
import io.github.spl21.microproject2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // method to add user
    @PostMapping
    public String addUser(@RequestBody String name, @RequestParam String email){
        User user = new User();

        user.setName(name);
        user.setEmail(email);
        userRepository.save(user);
        return "Saved";
    }

    // method to get all users
    @GetMapping("/all")
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

}
