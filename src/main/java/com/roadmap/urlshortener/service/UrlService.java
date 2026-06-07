package com.roadmap.urlshortener.service;

import com.roadmap.urlshortener.dto.LongURLDto;
import com.roadmap.urlshortener.entity.UrlEntity;
import com.roadmap.urlshortener.repository.UrlRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;
    public static final String SHORT_URL_PREFIX = "http://localhost:8080/v1/api/";

    @Transactional
    public String createShortUrl(LongURLDto longURLDto) {

        Optional<UrlEntity> existing = urlRepository.findByLongUrl(longURLDto.getLongUrl());

        if(existing.isPresent()) {
            return SHORT_URL_PREFIX + existing.get().getShortCode();
        }

        UrlEntity urlEntity= new UrlEntity();
        urlEntity.setLongUrl(longURLDto.getLongUrl());

        urlEntity = urlRepository.save(urlEntity);

        String shortCode = Base62EncoderService.encode(urlEntity.getId());
        urlEntity.setShortCode(shortCode);
        urlRepository.save(urlEntity);
        return SHORT_URL_PREFIX + shortCode;
    }

    @Transactional
    public String getOriginalUrl(String shortCode) {
        UrlEntity urlEntity= urlRepository.findByShortCode(shortCode).orElseThrow(() -> new RuntimeException("Short Url Not found"));
        urlEntity.setAccessCount(urlEntity.getAccessCount() + 1);
        return urlEntity.getLongUrl();
    }

    @Transactional
    public void deleteUrl(String shortCode) {
        UrlEntity urlEntity = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() ->
                        new RuntimeException("Short URL not found"));

        urlRepository.delete(urlEntity);
    }
}
