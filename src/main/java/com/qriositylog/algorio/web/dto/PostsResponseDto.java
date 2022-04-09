package com.qriositylog.algorio.web.dto;

import com.qriositylog.algorio.domain.posts.Posts;
import lombok.Getter;

import java.util.List;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private List<String> tags;

    public PostsResponseDto(Posts entity, List<String> tagList) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.tags = tagList;
    }
}
