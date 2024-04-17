package com.podkaster.aiservice.chatservice.openai.song_recommend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.podkaster.aiservice.chatservice.openai.service.SongRecommendService;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OpenAIRecommendService implements SongRecommendService {

    OpenAiChatClient chatClient;
    ObjectMapper objectMapper = new ObjectMapper();

    @Value("${systemText}")
    private String systemText;

    @Autowired
    public OpenAIRecommendService(OpenAiChatClient chatClient){
        this.chatClient = chatClient;
    }

    private Message createUserMessage(String userInput) {
        return new UserMessage(userInput);
    }

    private Message createSystemMessage(String systemText) {
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemText);
        return systemPromptTemplate.createMessage();
    }

    @Override
    public String processRequestWithJson(String userInput, String gptModel) throws JsonProcessingException {
        Message userMessage = createUserMessage(userInput);
        Message systemMessage = createSystemMessage(systemText);
        Prompt prompt = new Prompt(
                List.of(userMessage, systemMessage),
                OpenAiChatOptions.builder()
                        .withModel(gptModel)
                        .withTemperature(0.4f)
                        .build()
        );
        List<Generation> response = chatClient.call(prompt).getResults();
        String content = response.getFirst().getOutput().getContent();
        JsonNode contentJson = objectMapper.readTree(content);
        return contentJson.toString();
    }
}
