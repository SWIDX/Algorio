package com.qriositylog.algorio.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void saveTest() {
        String title = "테스트 제목";
        String content = "테스트 내용";
        String imageLink = ""; // intentionally null string
        String author = "Queue-ri";
        String tag = ""; // intentionally null string

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .imageLink(imageLink)
                .author(author)
                .tag(tag)
                .build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
        assertThat(posts.getImageLink()).isEqualTo(imageLink);
        assertThat(posts.getAuthor()).isEqualTo(author);
        assertThat(posts.getTag()).isEqualTo(tag);
    }
}
