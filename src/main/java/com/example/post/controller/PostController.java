package com.example.post.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.post.dto.Page;
import com.example.post.dto.Post;
import com.example.post.service.PostService;

@Controller
public class PostController {
    private final PostService postService;
    public PostController(PostService postService) { this.postService = postService; }

    @GetMapping("/")
    public String index() { return "index"; }

    @GetMapping("/Post/list")
    public String list(@RequestParam(name = "page", defaultValue = "1") int page,
                       Model model) {
        Page pageInfo = new Page(page, postService.count());
        model.addAttribute("postList", postService.list(pageInfo));
        model.addAttribute("pageInfo", pageInfo);
        return "Post/list";
    }

    @GetMapping("/Post/read")
    public String read(@RequestParam("PostNo") Long postNo, Model model) {
        model.addAttribute("post", postService.read(postNo));
        return "Post/read";
    }

    @GetMapping("/Post/insert")
    public String insertForm(Model model) {
        model.addAttribute("post", new Post());
        return "Post/insert";
    }

    @PostMapping("/Post/insert")
    public String insert(@Valid @ModelAttribute("post") Post post,
                         BindingResult result) {
        if (result.hasErrors()) return "Post/insert";
        postService.insert(post);
        return "redirect:/Post/read?PostNo=" + post.getPostNo();
    }

    @GetMapping("/Post/update")
    public String updateForm(@RequestParam("PostNo") Long postNo, Model model) {
        model.addAttribute("post", postService.read(postNo));
        return "Post/update";
    }

    @PostMapping("/Post/update")
    public String update(@RequestParam("PostNo") Long postNo,
                         @Valid @ModelAttribute("post") Post post,
                         BindingResult result) {
        post.setPostNo(postNo);
        if (result.hasErrors()) return "Post/update";
        postService.update(post);
        return "redirect:/Post/read?PostNo=" + postNo;
    }

    @PostMapping("/Post/delete")
    public String delete(@RequestParam("PostNo") Long postNo) {
        postService.delete(postNo);
        return "redirect:/Post/list";
    }
}
