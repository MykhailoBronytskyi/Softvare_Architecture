package org.example;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.util.UUID;
@RestController
public class FacadeService {
    WebClient message_web_client = WebClient.create("http://localhost:8081/");
    WebClient logging_web_client = WebClient.create("http://localhost:8082/");


    @GetMapping("/facade_service")
    public Mono<String> client_web_client() {

        Mono<String> message_mono = message_web_client.get()
                                                      .uri("/message_service")
                                                      .retrieve()
                                                      .bodyToMono(String.class);

        Mono<String> cached_values = logging_web_client.get()
                                                       .uri("/log_service")
                                                       .retrieve()
                                                       .bodyToMono(String.class);


        Mono<String> result = cached_values.zipWith(message_mono, (cached, message) -> cached + ": " + message)
                                           .onErrorReturn("Error: in @GetMapping(\"/facade_service\") in cached_values.zipWith()");

//        System.out.println("Get request: message_mono = " + message_mono.toString());
//        System.out.println("Get request: cached_values = " + cached_values.toString());
//        System.out.println("Get request " + result.toString() + "\n");
        System.out.println("Get request: facade_service");
        return result;

    }

    @PostMapping("/facade_service")
    public Mono<Void> facade_web_client(@RequestBody String text) {

        var message = new Message(UUID.randomUUID(), text);

        Mono<String> message_mono = message_web_client.post()
                                                      .uri("/message_service")
                                                      .retrieve()
                                                      .bodyToMono(String.class);

        Mono<Void> logging_mono = logging_web_client.post()
                                                      .uri("/log_service")
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .body(Mono.just(message), Message.class)
                                                      .retrieve()
                                                      .bodyToMono(Void.class);

        System.out.println("Post request: " + message.toString());

        return message_mono.then(logging_mono);
    }
}
