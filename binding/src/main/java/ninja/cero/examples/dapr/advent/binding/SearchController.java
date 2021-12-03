package ninja.cero.examples.dapr.advent.binding;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class SearchController {
    private RestTemplate restTemplate;

    @Value("http://localhost:${DAPR_HTTP_PORT}/v1.0/bindings/twitter-binding")
    private String bindingUrl;

    public SearchController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/search")
    public List<?> search(@RequestParam String query) {
        Map<String, ?> request = Map.of(
                "operation", "get",
                "metadata", Map.of("query", query,
                        "lang", "ja",
                        "result", "recent")
        );

        List<?> result = restTemplate.postForObject(bindingUrl, request, List.class);

        return result.stream()
                .map(e -> (Map<String, ?>) e)
                .map(this::extract)
                .collect(Collectors.toList());
    }

    private Map<String, String> extract(Map<String, ?> message) {
        Map<String, ?> user = (Map<String, ?>) message.get("user");
        String screenName = (String) user.get("screen_name");
        String name = (String) user.get("name");
        String text = (String) message.get("text");
        return Map.of("screen_name", screenName, "name", name, "text", text);
    }
}
