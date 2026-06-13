package pank.online.ai.webservice;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*")
public class ChatWebServiceController {

    private final ChatWebService chatWebService;

    public ChatWebServiceController(ChatWebService chatWebService) {
        this.chatWebService = chatWebService;
    }

    @GetMapping("/chat")
    public String chat(
            @RequestParam String message,
            @RequestParam(defaultValue = "default-session") String sessionId) {

        return chatWebService.getChatResponse(sessionId, message);
    }
}
