package com.hanghae99.springweek01.repository;

import com.hanghae99.springweek01.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByDetailNum(Long id);
}
