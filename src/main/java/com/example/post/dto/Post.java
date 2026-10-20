package com.example.post.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Post {
    private Long postNo;
    @NotBlank(message = "제목을 입력하세요.")
    @Size(max = 200, message = "제목은 200자 이내로 입력하세요.")
    private String title;
    @NotBlank(message = "작성자를 입력하세요.")
    @Size(max = 50, message = "작성자는 50자 이내로 입력하세요.")
    private String writer;
    @NotBlank(message = "내용을 입력하세요.")
    @Size(max = 10000, message = "내용은 10000자 이내로 입력하세요.")
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime updDate;

    public Long getPostNo() { return postNo; }
    public void setPostNo(Long postNo) { this.postNo = postNo; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getWriter() { return writer; }
    public void setWriter(String writer) { this.writer = writer; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getRegDate() { return regDate; }
    public void setRegDate(LocalDateTime regDate) { this.regDate = regDate; }
    public LocalDateTime getUpdDate() { return updDate; }
    public void setUpdDate(LocalDateTime updDate) { this.updDate = updDate; }
}
