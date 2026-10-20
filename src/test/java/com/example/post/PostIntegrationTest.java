package com.example.post;

import com.example.post.dto.Page;
import com.example.post.dto.Post;
import com.example.post.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class PostIntegrationTest {
    @Autowired PostService service;
    @Autowired MockMvc mvc;
    Post add(String title) {
        Post p = new Post(); p.setTitle(title); p.setWriter("작성자"); p.setContent("본문");
        service.insert(p); return p;
    }
    @Test void crudAndTemplates() throws Exception {
        Post p = add("첫 글");
        assertThat(p.getPostNo()).isPositive();
        assertThat(service.read(p.getPostNo()).getTitle()).isEqualTo("첫 글");
        for (String url : new String[]{"/", "/Post/list", "/Post/insert",
                "/Post/read?PostNo="+p.getPostNo(), "/Post/update?PostNo="+p.getPostNo()}) {
            mvc.perform(get(url)).andExpect(status().isOk());
        }
        mvc.perform(post("/Post/update").param("PostNo", p.getPostNo().toString())
                .param("title", "수정 글").param("writer", "작성자").param("content", "수정 본문"))
                .andExpect(status().is3xxRedirection());
        assertThat(service.read(p.getPostNo()).getTitle()).isEqualTo("수정 글");
        mvc.perform(post("/Post/delete").param("PostNo", p.getPostNo().toString()))
                .andExpect(redirectedUrl("/Post/list"));
        mvc.perform(get("/Post/read").param("PostNo", p.getPostNo().toString()))
                .andExpect(status().isNotFound());
    }
    @Test void pagingAndBounds() {
        for (int i=1; i<=23; i++) add("글 "+i);
        Page first = new Page(1, service.count());
        Page last = new Page(999, service.count());
        assertThat(first.getTotalPages()).isEqualTo(3);
        assertThat(service.list(first)).hasSize(10);
        assertThat(service.list(last)).hasSize(3);
        assertThat(service.list(first).get(0).getTitle()).isEqualTo("글 23");
        assertThat(last.getPage()).isEqualTo(3);
        assertThat(new Page(-10, 0).getOffset()).isZero();
        Page block = new Page(6, 101);
        assertThat(block.getStartPage()).isEqualTo(6);
        assertThat(block.getEndPage()).isEqualTo(10);
    }
    @Test void insertValidationAndEscaping() throws Exception {
        mvc.perform(post("/Post/insert").param("title", " ").param("writer", "작성자").param("content", "본문"))
                .andExpect(status().isOk()).andExpect(model().attributeHasFieldErrors("post", "title"));
        assertThat(service.count()).isZero();
        mvc.perform(post("/Post/insert").param("title", "등록").param("writer", "작성자").param("content", "본문"))
                .andExpect(status().is3xxRedirection());
        Post p = add("<script>alert(1)</script>");
        mvc.perform(get("/Post/read").param("PostNo", p.getPostNo().toString()))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("&lt;script&gt;")));
    }
}
