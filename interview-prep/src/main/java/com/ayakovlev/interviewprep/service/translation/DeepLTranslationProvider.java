package com.ayakovlev.interviewprep.service.translation;

import com.deepl.api.DeepLClient;
import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DeepLTranslationProvider implements TranslationProvider{
    private final DeepLClient client;

    public DeepLTranslationProvider(@Value("${deepl.api.key}") String apiKey) {
        System.out.println("DeepL key loaded: [" + apiKey + "]");
        this.client = new DeepLClient(apiKey);
    }

    @Override
    public String translate(String text, String sourceLang, String targetLang) {
        try {
            String normalizedTarget = normalizeTargetLang(targetLang);
            TextResult result = client.translateText(text, sourceLang, normalizedTarget);
            return result.getText();
        }catch (DeepLException | InterruptedException ex){
            throw new TranslationException("DeepL translation failed", ex);
        }
    }

    private String normalizeTargetLang(String lang) {
        if (lang.equalsIgnoreCase("EN")) {
            return "EN-US"; // EN-GB
        }
        return lang;
    }


    @Override
    public String getProviderName() {
        return "DeepL";
    }
}
