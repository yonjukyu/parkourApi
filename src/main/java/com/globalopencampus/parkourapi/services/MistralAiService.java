package com.globalopencampus.parkourapi.services;

import org.springframework.ai.chat.model.ChatResponse;

public interface MistralAiService {
    public String call(String prompt);

}
