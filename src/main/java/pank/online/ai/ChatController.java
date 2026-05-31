package pank.online.ai;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ChatController {

    private final Assistant assistant;

    public ChatController(Assistant assistant) {
        this.assistant = assistant;
    }

    @GetMapping("/chat")
    public String askAI(
            @RequestParam String message,
            @RequestParam(defaultValue = "default-session") String sessionId) {
        try {
            return assistant.chat(sessionId, message);
        } catch (Exception e) {
            return "Cloud Server Error: " + e.getMessage();
        }
    }
}