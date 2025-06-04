package com.globalopencampus.parkourapi.services;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MistralAiServiceImpl implements MistralAiService {

    @Autowired
    private MistralAiChatModel mistralAiChatModel;


    @Override
    public String call(String prompt) {
//        var options = MistralAiChatModel.builder();
        return mistralAiChatModel.call(prompt);
    }
}

