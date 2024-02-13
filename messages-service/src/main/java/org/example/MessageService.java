package org.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageService{
    @GetMapping("/message_service")
    public String user(){
        System.out.println("Get request");
        return "hello there :=) -- GET messages-service is not implemented yet";
    }
    @PostMapping("/message_service")
    public String reply_user(){
        System.out.println("Post request");
        return "hello there :=) -- POST messages-service is not implemented yet";
    }
}

