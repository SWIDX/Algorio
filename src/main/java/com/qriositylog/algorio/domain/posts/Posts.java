package com.qriositylog.algorio.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String imageLink;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String tag;

    @Builder
    public Posts(String title, String content, String imageLink, String author, String tag) {
        this.title = title;
        this.content = content;
        this.imageLink = imageLink;
        this.author = author;
        this.tag = tag;
    }

    public void update(String title, String content, String imageLink, String tag) {
        this.title = title;
        this.content = content;
        this.imageLink = imageLink;
    }
}
