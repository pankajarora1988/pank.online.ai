package pank.online.ai;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ChatController {

    private final Assistant assistant;
    private final Map<String, SessionInfo> sessionCounters = new ConcurrentHashMap<>();
    static class SessionInfo {
        int count;
        long lastAccessTime;

        SessionInfo(int count) {
            this.count = count;
            this.lastAccessTime = System.currentTimeMillis();
        }
    }
    

    public ChatController(Assistant assistant) {
        this.assistant = assistant;
    }
    

    @GetMapping("/chat")
    public String askAI(
            @RequestParam String message,
            @RequestParam(defaultValue = "default-session") String sessionId) {
    	
    	SessionInfo info = sessionCounters.compute(sessionId, (id, existing) -> {
            if (existing == null) {
                return new SessionInfo(1);
            }
            existing.count++;
            existing.lastAccessTime = System.currentTimeMillis();
            return existing;
        });
    	if (info.count==1 || info.count % 20 == 0) {
    		assistant.chat(sessionId, "Today's date is " + Calendar.getInstance().getTime());
    	}
    	
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
			String noteAboutPankaj = "This is your core memory to remember about Pankaj Arora. He is Male and his Date of Birth is 15 september 1988. His education is MCA. He started his professional work in April 2010. He has made this you and this current chatbot program by which external users are communicating. He currently lives in Singapore and his home town is India.";
			assistant.chat(sessionId, noteAboutPankaj);
		}
	}
	
	@Scheduled(fixedRate = 10 * 60 * 1000 * 3) // every 30 minutes
	public void cleanupOldSessions() {

		long cutoff = System.currentTimeMillis() - (2L * 60 * 60 * 1000);

		sessionCounters.entrySet().removeIf(entry -> entry.getValue().lastAccessTime < cutoff);

		System.out.println("Expired sessions cleaned.");
	}

}