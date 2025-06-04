package com.globalopencampus.parkourapi.dto.model;

public record ParkourSpotSuggestionDto(
    String country,
    String city,
    boolean isIndoor,
    String language
){}
