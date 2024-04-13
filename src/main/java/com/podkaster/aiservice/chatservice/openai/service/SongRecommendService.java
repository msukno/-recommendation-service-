package com.podkaster.aiservice.chatservice.openai.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface SongRecommendService {
    String processRequestWithJson(String userInput, String gptModel) throws JsonProcessingException;
}
