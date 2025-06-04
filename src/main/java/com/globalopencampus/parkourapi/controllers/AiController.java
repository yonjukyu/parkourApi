package com.globalopencampus.parkourapi.controllers;

import com.globalopencampus.parkourapi.dto.model.ParkourSpotSuggestionDto;
import com.globalopencampus.parkourapi.services.MistralAiService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AiController {
    MistralAiService mistralAiService;

    public AiController(MistralAiService mistralAiService) {
        this.mistralAiService = mistralAiService;
    }

    @PostMapping("/chat")
    public String mistral(@RequestBody String prompt) {
        return mistralAiService.call(prompt);
    }

    @PostMapping("/spot-suggestions")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMINISTRATOR')")
    public String getParkourSpotSuggestionsFromAI(@RequestBody ParkourSpotSuggestionDto spot) {
        String language = spot.language() != null ? spot.language() : "French";
        StringBuilder prompt  = new StringBuilder();
        prompt.append("I need parkour exercises suggestions for parkour spots following these criteria : ")
                .append("Country: " + spot.country() + ", ")
                .append("City: " + spot.city() + ", ")
                .append(spot.isIndoor() ? "and indoor: " : "and outdoor. ")
                .append("Please provide a detailed description in " + language);
        return mistralAiService.call(prompt.toString());
    }
}