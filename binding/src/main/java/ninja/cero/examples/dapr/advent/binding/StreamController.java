package ninja.cero.examples.dapr.advent.binding;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StreamController {
    @PostMapping("/twitter-binding")
    public void twitterStream(@RequestBody Map<String, ?> message) {
        Map<String, String> tweet = extract(message);
        System.out.println("@" + tweet.get("screen_name") + " : " + tweet.get("text"));
    }

    private Map<String, String> extract(Map<String, ?> message) {
        Map<String, ?> user = (Map<String, ?>) message.get("user");
        String screenName = (String) user.get("screen_name");
        String name = (String) user.get("name");
        String text = (String) message.get("text");
        return Map.of("screen_name", screenName, "name", name, "text", text);
    }
}
