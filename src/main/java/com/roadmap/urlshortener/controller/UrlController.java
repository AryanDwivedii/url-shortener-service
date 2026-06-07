package com.roadmap.urlshortener.controller;

import com.roadmap.urlshortener.dto.LongURLDto;
import com.roadmap.urlshortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody LongURLDto longURLDto) {
       return ResponseEntity.status(HttpStatus.CREATED).body(urlService.createShortUrl(longURLDto));
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> getMappingOfShortUrl(@PathVariable String shortCode){
        String longUrl = urlService.getOriginalUrl(shortCode);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(longUrl))
                .build();
    }

    @DeleteMapping("/{shortCode}")
    public ResponseEntity<Void> deleteShortUrl(@PathVariable String shortCode){
        urlService.deleteUrl(shortCode);
        return ResponseEntity.noContent().build();
    }
}
