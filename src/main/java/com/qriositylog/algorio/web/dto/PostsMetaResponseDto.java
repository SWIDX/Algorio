package com.qriositylog.algorio.web.dto;

import com.qriositylog.algorio.domain.posts.Posts;
import lombok.Getter;

import java.util.List;

@Getter
public class PostsMetaResponseDto {
    private Long id;
    private String title;
    private String content;
    private List<String> tags;

    public PostsMetaResponseDto(Posts entity, List<String> tagList) {
        id = entity.getId();
        title = entity.getTitle();
        content = entity.getContent();
        this.tags = tagList;
    }
}
