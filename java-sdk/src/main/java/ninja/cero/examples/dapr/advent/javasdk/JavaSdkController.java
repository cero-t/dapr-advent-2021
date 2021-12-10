package ninja.cero.examples.dapr.advent.javasdk;

import io.dapr.Topic;
import io.dapr.client.DaprClient;
import io.dapr.client.domain.HttpExtension;
import io.dapr.client.domain.State;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class JavaSdkController {
    private DaprClient daprClient;

    public JavaSdkController(DaprClient daprClient) {
        this.daprClient = daprClient;
    }

    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of("message", "Hello, world!");
    }

    @GetMapping("/invokeHello")
    public Map<String, ?> invokeHello() {
        Map<?, ?> result = daprClient.invokeMethod("java-sdk-app", "hello", null, HttpExtension.GET, Map.class)
                .block();
        return Map.of("remoteMessage", result);
    }

    @PostMapping("/write")
    public void write(@RequestBody List<Map<String, ?>> message) {
        List<State<?>> states = message.stream()
                .map(m -> new State<>(m.get("key").toString(), m.get("value"), null))
                .collect(Collectors.toList());

        Mono<Void> statestore = daprClient.saveBulkState("statestore", states);
        statestore.block();
    }

    @GetMapping("/read/{key}")
    public Object read(@PathVariable String key) {
        Mono<State<Object>> result = daprClient.getState("statestore", key, Object.class);
        return result.blockOptional()
                .map(State::getValue)
                .orElse(null);
    }

    @PostMapping("/publish")
    public void publish(@RequestBody Object message) {
        daprClient.publishEvent("pubsub", "my-message", message)
                .block();
    }

    @Topic(pubsubName = "pubsub", name = "my-message")
    @PostMapping("/subscribe")
    public void subscribe(@RequestBody String message) {
        System.out.println(message);
    }
}
