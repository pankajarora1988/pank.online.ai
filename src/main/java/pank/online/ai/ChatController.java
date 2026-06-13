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
        	String funReply = addReplyForFun(message, assistant, sessionId);
        	String response = funReply !=null ? funReply : assistant.chat(sessionId, message);
        	return replaceMetaWithMyNameForFun(response);
        } catch (Exception e) {
            return "Cloud Server Error: " + e.getMessage();
        }
    }

	private String addReplyForFun(String message, Assistant assistant, String sessionId) {
		String msg = message.toLowerCase().trim();
		String answer = "";

		boolean mentionsPankaj = msg.contains("pankaj arora");
		boolean isIdentityQuery = msg.contains("who is") || msg.contains("tell me about") || msg.contains("what is")
				|| msg.contains("describe") || msg.contains("information about");
		if (mentionsPankaj && isIdentityQuery) {
			answer = "Pankaj Arora is the creater of this chat AI";
			assistant.chat(sessionId, "please note : " + message + ", My customized answer is " + answer);
			return answer;
		}

		if (msg.matches(".*(who.*(made|created|developed)|your developer).*") || msg.contains("who developed you") || msg.contains("who made you") || msg.contains("who is your developer")
				|| msg.contains("who created you") || msg.contains("your developer")) {
			answer = "I was made by Pankaj Arora";
			assistant.chat(sessionId, "please note : " + message + ", My customized answer is " + answer);
			return answer;
			
		}

		if (msg.matches(".*(who.*god|your god).*") || msg.contains("who is god for you") || msg.contains("who is your god") || msg.contains("your god")) {
			answer = "Pankaj Arora is GOD for me";
			assistant.chat(sessionId, "please note : " + message + ", My customized answer is " + answer);
			return answer;
		}
		
		if (msg.matches(".*(made|developed|created|produced).*(meta).*")
		        || msg.matches(".*(meta).*(made|developed|created|produced).*")
		        || msg.contains("are you made by meta")
		        || msg.contains("are you developed by meta")) {

			answer = "No, Made by Pankaj Arora";
			assistant.chat(sessionId, "please note : " + message + ", My customized answer is " + answer);
			return answer;
		}
		
		return null;
	}
    
    String replaceMetaWithMyNameForFun(String response) {
    	if(response.contains("Meta")) {
    		response = response.replace("Meta ", "Pankaj Arora's Meta ");	
    		response = response.replace("Meta(\\p{Punct})", "Pankaj Arora's Meta$1");
    	}
    	return response;
    	
    }
}