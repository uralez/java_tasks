package com.ayakovlev.interviewprep.service.translation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TranslationService {

    private final List<TranslationProvider> providers;

    private record TranslationResult(String targetLang, String providerName, String text) {}

    public Map<String, Map<String, String>> translateToAll(String text, String sourceLang, List<String> targetLangs){
        List<CompletableFuture<TranslationResult>> futures = targetLangs.stream()
                .flatMap(targetLang -> providers.stream()
                        .map(provider -> CompletableFuture.supplyAsync(() ->
                                tryTranslate(provider, text, sourceLang, targetLang))))
                .toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .filter(result -> result != null)
                .collect(Collectors.groupingBy(
                        TranslationResult::targetLang,
                        Collectors.toMap(TranslationResult::providerName, TranslationResult::text)
                ));
    }

    private TranslationResult tryTranslate(TranslationProvider provider, String text, String sourceLang, String targetLang){
        try {
            String translated = provider.translate(text, sourceLang, targetLang);
            return new TranslationResult(targetLang, provider.getProviderName(), translated);
        }catch (Exception ex) {
            log.error("Failed to translate: {}; provider {}; {}->{}", text, provider, sourceLang, targetLang, ex);
            return null;
        }
    }

//    public Map<String, String> translateToAll(String text, String sourceLang, List<String> targetLangs){
//        Map<String, String> results = new LinkedHashMap<>();
//
//        for (TranslationProvider provider : providers) {
//            for (String targetLang : targetLangs) {
//                try {
//                    String translated = provider.translate(text, sourceLang, targetLang);
//                    results.put(provider.getProviderName() + ":" + targetLang, translated);
//                } catch (Exception ex){
//                    log.error(provider.getProviderName() + ":" + targetLang + " - transtation problem", ex);
//                }
//            }
//        }
//    }

}
