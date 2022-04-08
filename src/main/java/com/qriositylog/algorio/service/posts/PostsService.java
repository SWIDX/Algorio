package com.qriositylog.algorio.service.posts;

import com.qriositylog.algorio.domain.posts.Posts;
import com.qriositylog.algorio.domain.posts.PostsRepository;
import com.qriositylog.algorio.web.dto.PostsMetaResponseDto;
import com.qriositylog.algorio.web.dto.PostsResponseDto;
import com.qriositylog.algorio.web.dto.PostsSaveRequestDto;
import com.qriositylog.algorio.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );

        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );
        return new PostsResponseDto(entity);
    }

    public List<PostsMetaResponseDto> findAll() {
        List<Posts> entities = postsRepository.findAll();
        List<PostsMetaResponseDto> metaList = new ArrayList<>(entities.size());
        entities.forEach(entity -> metaList.add(new PostsMetaResponseDto(entity)));
        return metaList;
    }
}
