package com.example.post.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.example.post.dto.Page;
import com.example.post.dto.Post;

@Mapper
public interface PostMapper {
    List<Post> list(Page page);
    long count();
    Post read(@Param("postNo") Long postNo);
    int insert(Post post);
    int update(Post post);
    int delete(@Param("postNo") Long postNo);
}
