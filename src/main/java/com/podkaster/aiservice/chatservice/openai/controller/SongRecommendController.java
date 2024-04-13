package com.podkaster.aiservice.chatservice.openai.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.podkaster.aiservice.chatservice.openai.service.SongRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommend")
public class SongRecommendController {

    SongRecommendService aiservice;

    @Autowired
    public SongRecommendController(SongRecommendService aiservice){
        this.aiservice = aiservice;
    }

    @GetMapping("/youtube")
    public String test(){
        return "active";
    }

    @PostMapping("/youtube")
    public String gptRecommend(@RequestBody UserHistory request){
        try{
            return aiservice.processRequestWithJson(request.songHistory(), "gpt-4");
        }
        catch (JsonProcessingException e){
            String errorMessage = """
JsonProcessingException while aiservice.processRequestWithJson(request.songHistory()).
%s
            """.formatted(e.getMessage());
            System.out.println(errorMessage);
            return errorMessage;
        }
    }
}

record UserHistory(String songHistory){}
