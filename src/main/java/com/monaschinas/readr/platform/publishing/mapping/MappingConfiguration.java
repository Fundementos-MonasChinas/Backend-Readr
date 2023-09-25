package com.monaschinas.readr.platform.publishing.mapping;

import com.monaschinas.readr.platform.publishing.domain.model.BookLanguage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("publishingMappingConfiguration")
public class MappingConfiguration {
    @Bean
    public GenreMapper genreMapper(){ return new GenreMapper(); }

    @Bean
    public LanguageMapper languageMapper(){ return new LanguageMapper(); }

    @Bean
    public SagaMapper sagaMapper(){ return new SagaMapper(); }

    @Bean
    public SagaStatusMapper sagaStatusMapper(){ return new SagaStatusMapper(); }

    @Bean
    public BookStatusMapper bookStatusMapper() { return new BookStatusMapper(); }

    @Bean
    public BookMapper bookMapper() { return new BookMapper(); }

    @Bean
    public ChapterMapper chapterMapper(){ return new ChapterMapper(); }

    @Bean
    public CommentMapper commentMapper(){ return new CommentMapper(); }

    @Bean
    public BookLanguageMapper bookLanguageMapper(){ return new BookLanguageMapper(); }

    @Bean
    public FavoriteMapper favoriteMapper(){ return new FavoriteMapper(); }
}
