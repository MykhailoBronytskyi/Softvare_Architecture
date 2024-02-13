package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class LoggingService {
    Logger logger = LoggerFactory.getLogger(LoggingService.class);
    private final ConcurrentHashMap<UUID, String> messages = new ConcurrentHashMap<>();

    @GetMapping("/log_service")
    public String list_log() {
        logger.info("\nGet request:  messages.toString() = " + messages.toString() + "\n");
        return messages.values().toString();
    }

    @PostMapping("/log_service")
    public ResponseEntity<Void> log(@RequestBody Message message) {
        logger.info("\nPost request: message = " + message.toString() + "\n");
        messages.put(message.id, message.text);
        return ResponseEntity.ok().build();
    }
}