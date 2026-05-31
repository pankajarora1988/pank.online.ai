package pank.online.ai;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatMemoryConfig {

    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(10)
                .build();
    }

    @Bean
    public Assistant assistant(ChatModel chatModel, ChatMemoryProvider chatMemoryProvider) {
        return AiServices.builder(Assistant.class)
                .chatModel(chatModel)
                .chatMemoryProvider(chatMemoryProvider)
                .build();
    }
}