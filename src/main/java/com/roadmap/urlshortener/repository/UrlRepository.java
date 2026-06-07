package com.roadmap.urlshortener.repository;

import com.roadmap.urlshortener.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Long> {

    Optional<UrlEntity> findByLongUrl(String longUrl);

    Optional<UrlEntity> findByShortCode(String shortCode);


}
