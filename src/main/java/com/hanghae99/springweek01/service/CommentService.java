package com.hanghae99.springweek01.service;

import com.hanghae99.springweek01.domain.Comment;
import com.hanghae99.springweek01.dto.CommentRequestDto;
import com.hanghae99.springweek01.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Long update(Long id, CommentRequestDto requestDto){
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new NullPointerException("아이디가 존재하지 않습니다.")
        );
        comment.update(requestDto);
        return id;
    }
}
