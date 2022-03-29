package com.hanghae99.springweek01.controller;

import com.hanghae99.springweek01.domain.Memo;
import com.hanghae99.springweek01.repository.MemoRepository;
import com.hanghae99.springweek01.dto.MemoRequestDto;
import com.hanghae99.springweek01.security.UserDetailsImpl;
import com.hanghae99.springweek01.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemoController {

    private final MemoRepository memoRepository;
    private final MemoService memoService;

    @PostMapping("/api/memos")
    public Memo createMemo(@RequestBody MemoRequestDto requestDto,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        Memo memo = new Memo(requestDto, userId);
        return memoRepository.save(memo);
    }
    @GetMapping("/api/memos")
    public List<Memo> getMemos() {
        return memoRepository.findAllByOrderByModifiedAtDesc();
    }

    @PutMapping("/api/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto){
        memoService.update(id, requestDto);
        return id;

    }

    @DeleteMapping("/api/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        memoRepository.deleteById(id);
        return id;
    }

    @GetMapping("/api/memos/detail/{id}")
    public Memo detail(@PathVariable Long id){
       return memoRepository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디가 존재하지 않습니다.")
        );
        }
    }
