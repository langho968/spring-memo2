package com.hanghae99.springweek01.domain;

import com.hanghae99.springweek01.dto.MemoRequestDto;
import lombok.Getter;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Memo extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Long userId;

    public Memo(String username, String title, String contents,Long userId) {
        this.userId  = userId;
        this.username = username;
        this.title = title;
        this.contents = contents;
    }

    public Memo(MemoRequestDto requestDto, Long userId) {
        this.userId  = userId;
        this.username = requestDto.getUsername();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void update(MemoRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
    }

}