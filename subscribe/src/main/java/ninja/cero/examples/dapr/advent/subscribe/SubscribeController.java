package ninja.cero.examples.dapr.advent.subscribe;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscribeController {
    @PostMapping("/subscribe")
    public void subscribe(@RequestBody String message) {
        System.out.println(message);
    }
}
