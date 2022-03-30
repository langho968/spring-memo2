package com.hanghae99.springweek01.domain;


import com.hanghae99.springweek01.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Comment extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Long detailNum;

    @Column(nullable = false)
    private Long userId;

    public Comment(String username, String contents,Long userId, Long detailNum) {
        this.userId  = userId;
        this.username = username;
        this.contents = contents;
        this.detailNum = detailNum;
    }
    public Comment(CommentRequestDto requestDto, Long userId){
        this.userId=userId;
        this.username= requestDto.getUsername();
        this.contents=requestDto.getContents();
        this.detailNum=requestDto.getDetailNum();
    }

    public void update(CommentRequestDto requestDto){
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
    }

}
