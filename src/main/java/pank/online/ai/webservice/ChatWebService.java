package pank.online.ai.webservice;

import org.springframework.stereotype.Service;

import pank.online.ai.Assistant;

@Service
public class ChatWebService {

    private final Assistant assistant;

    public ChatWebService(Assistant assistant) {
        this.assistant = assistant;
    }

    public String getChatResponse(String sessionId, String message) {
        return assistant.chat(sessionId, message);
    }
}