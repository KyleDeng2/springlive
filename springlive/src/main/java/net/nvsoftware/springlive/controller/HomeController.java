package net.nvsoftware.springlive.controller;

import net.nvsoftware.springlive.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "hi";
    }

    @RequestMapping(value="/user", method= RequestMethod.POST)
    public User user() {
        User user = new User();
        user.setId("36090");
        user.setName("Ken");
        user.setEmail("Ken@gmail.com");
        return user;
    }

    @GetMapping("/user/{id}/{username}")
    public User userByPathVariable(@PathVariable String id, @PathVariable("username") String name) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail("info@gmail.com");
        return user;
    }
    
}
