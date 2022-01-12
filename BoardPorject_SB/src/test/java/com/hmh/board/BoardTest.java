package com.hmh.board;

import com.hmh.board.dto.BoardSaveDTO;
import com.hmh.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.IntStream;

public class BoardTest {
    @Autowired
    private BoardService bs;

    @Test
    @DisplayName("게시판 생성")
    public void newBoard() {
        IntStream.rangeClosed(1, 30).forEach(i -> {
            bs.save(new BoardSaveDTO("writer"+i, "pass"+i, "title"+i, "contents"+i));
        });
    }
}
