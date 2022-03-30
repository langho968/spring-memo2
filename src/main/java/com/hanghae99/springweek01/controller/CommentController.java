package com.hanghae99.springweek01.controller;


import com.hanghae99.springweek01.domain.Comment;
import com.hanghae99.springweek01.domain.Memo;
import com.hanghae99.springweek01.dto.CommentRequestDto;
import com.hanghae99.springweek01.repository.CommentRepository;
import com.hanghae99.springweek01.security.UserDetailsImpl;
import com.hanghae99.springweek01.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @PostMapping("/api/comments")
    public Comment createComment(@RequestBody CommentRequestDto requestDto,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userId = userDetails.getUser().getId();
        Comment comment = new Comment(requestDto, userId);
        return commentRepository.save(comment);
    }
    @GetMapping("/api/comments/{id}")
    public List<Comment> getComments(@PathVariable Long id){
        return commentRepository.findAllByDetailNum( id);
    }

    @PutMapping("/api/comments/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody CommentRequestDto requestDto){
        commentService.update(id, requestDto);
        return id;
    }
    @DeleteMapping("/api/comments/{id}")
    public Long deleteMemo(@PathVariable Long id){
        commentRepository.deleteById(id);
        return id;
    }
}
