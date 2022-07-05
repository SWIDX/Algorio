package com.qriositylog.algorio.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {
    private String title;
    private String content;
    private String imageLink;
    private String tag;

    @Builder
    public PostsUpdateRequestDto(String title, String content, String imageLink, String tag) {
        this.title = title;
        this.content = content;
        this.imageLink = imageLink;
        this.tag = tag;
    }
}
