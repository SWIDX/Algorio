package com.qriositylog.algorio.web.dto;

import com.qriositylog.algorio.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsMetaResponseDto {
    private Long id;
    private String title;
    private String content;
    private String tag;

    public PostsMetaResponseDto(Posts entity) {
        id = entity.getId();
        title = entity.getTitle();
        content = entity.getContent();
        tag = entity.getTag();
    }
}
