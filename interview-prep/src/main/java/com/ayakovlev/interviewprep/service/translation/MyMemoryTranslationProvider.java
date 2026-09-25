package com.ayakovlev.interviewprep.service.translation;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class MyMemoryTranslationProvider implements TranslationProvider {

    private final RestClient restClient = RestClient.create("https://api.mymemory.translated.net");

    @Override
    public String translate(String text, String sourceLang, String targetLang){
        String langpair = sourceLang.toLowerCase() + "|" + targetLang.toLowerCase();

        Map<String, Object> response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/get")
                        .queryParam("q", text)
                        .queryParam("langpair", langpair)
                        .build())
                .retrieve()
                .body(Map.class);

        Map<String, Object> responseData = (Map<String, Object>) response.get("responseData");
        return (String) responseData.get("translatedText");
    }

    @Override
    public String getProviderName() {
        return "MyMemory";
    }
}
