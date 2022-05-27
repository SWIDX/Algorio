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
    private String imageLink;
    private String tag;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author, String imageLink, String tag) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.imageLink = imageLink;
        this.tag = tag;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .imageLink(imageLink)
                .tag(tag)
                .build();
    }
}
