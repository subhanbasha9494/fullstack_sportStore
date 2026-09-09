package com.selfpreparation.sportstores.controller;

import com.selfpreparation.sportstores.dto.UserDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("app/dummy")
@Validated
public class DummyController {

    @PostMapping("/create-user")
    public String createUser(@RequestBody UserDto userDto) {
        return "User created successfully";
    }

    @PostMapping("/request-entity")
    public String createUserWithEntity(RequestEntity<UserDto> requestEntity) {
        HttpHeaders header = requestEntity.getHeaders();
        UserDto userDto = requestEntity.getBody();
        return "User created successfully";
    }

    @GetMapping("/headers")
    public String readHeaders(@RequestHeader HttpHeaders headers) {
        List<String> location= headers.get("User-Location");
        return "Recevied headers with value : " + headers.toString();
    }

    @GetMapping("/search")
    public String searchUser(@RequestParam(required = false, defaultValue = "Guest",
            name = "name") String userName) {
        return "Searching for user : " + userName;
    }

    @GetMapping("/multiple-search")
    public String multipleSearch(@RequestParam Map<String,String> params) {
        return "Searching for user : " + params.get("firstName") + " " + params.get("lastName");
    }

    @GetMapping({"/user/{userId}/posts/{postId}", "/user/{userId}"})
    public String getUser(@PathVariable(name = "userId") String id,
                          @PathVariable(required = false) String postId) {
        return "Searching for user : " + id + " and post : " + postId;
    }

    @GetMapping({"/user/map/{userId}/posts/{postId}", "/user/map/{userId}"})
    public String getUserUsingMap(@PathVariable Map<String,String> pathVariables) {
        return "Searching for user : " + pathVariables.get("userId") + " and post : "
                + pathVariables.get("postId");
    }
}
