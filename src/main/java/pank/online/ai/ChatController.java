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
        	informAIAboutPankajArora(message, assistant, sessionId);
        	return assistant.chat(sessionId, message);
        } catch (Exception e) {
            return "Cloud Server Error: " + e.getMessage();
        }
    }
    
	private void informAIAboutPankajArora(String message, Assistant assistant, String sessionId) {
		String msg = message.toLowerCase().trim();
		if (msg.contains("pankaj") || msg.contains("arora") || msg.contains("pankaj arora")) {
			String noteAboutPankaj = "Please take note about Pankaj Arora. He is Male and his Date of Birth is 15 september 1988. His education is MCA. He started his professional work in April 2010. He has made this current chatbot program by which external users are communicating";
			assistant.chat(sessionId, noteAboutPankaj);
		}
	}
}