package com.qriositylog.algorio.web;

import com.qriositylog.algorio.domain.posts.Posts;
import com.qriositylog.algorio.domain.posts.PostsRepository;
import com.qriositylog.algorio.web.dto.PostsSaveRequestDto;
import com.qriositylog.algorio.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    String title = "테스트 타이틀";
    String content = "테스트 내용";
    String imageLink = "https://avatars.githubusercontent.com/u/77003554?v=4";
    String author = "Queue-ri";
    String tag = "tag1, tag2";

    @Test
    public void TestPostsSave() throws Exception {
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .imageLink(imageLink)
                .author(author)
                .tag(tag)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url, requestDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
        assertThat(all.get(0).getImageLink()).isEqualTo(imageLink);
        assertThat(all.get(0).getAuthor()).isEqualTo(author);
        assertThat(all.get(0).getTag()).isEqualTo(tag);
    }

    @Test
    public void TestPostsUpdate() throws Exception {
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .tag(tag)
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "수정된 타이틀";
        String expectedContent = "수정된 내용";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        ResponseEntity<Long> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}
