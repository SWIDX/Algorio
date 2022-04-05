package com.qriositylog.algorio.web.dto;

import com.qriositylog.algorio.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;
    private String tag;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author, String tag) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.tag = tag;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .tag(tag)
                .build();
    }
}
