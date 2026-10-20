package com.example.post.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.example.post.dto.Page;
import com.example.post.dto.Post;
import com.example.post.mapper.PostMapper;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {
    private final PostMapper postMapper;
    public PostServiceImpl(PostMapper postMapper) { this.postMapper = postMapper; }
    @Override
    public List<Post> list(Page page) { return postMapper.list(page); }
    @Override
    public long count() { return postMapper.count(); }
    @Override
    public Post read(Long postNo) {
        Post post = postMapper.read(postNo);
        if (post == null) throw notFound();
        return post;
    }
    @Override
    @Transactional
    public void insert(Post post) {
        // 등록 요청에 글번호가 있어도 DB가 생성한 번호를 사용한다.
        post.setPostNo(null);
        postMapper.insert(post);
    }
    @Override
    @Transactional
    public void update(Post post) {
        read(post.getPostNo());
        postMapper.update(post);
    }
    @Override
    @Transactional
    public void delete(Long postNo) {
        if (postMapper.delete(postNo) == 0) throw notFound();
    }
    private ResponseStatusException notFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글이 없습니다.");
    }
}
