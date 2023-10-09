package net.nvsoftware.springlive.controller;

import net.nvsoftware.springlive.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "hi";
    }

    @RequestMapping("/user")
    public User user() {
        User user = new User();
        user.setId("36090");
        user.setName("Ken");
        user.setEmail("Ken@gmail.com");
        return user;
    }

}
