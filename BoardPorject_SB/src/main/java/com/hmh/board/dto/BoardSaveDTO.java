package com.hmh.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardSaveDTO {
//    private Long memberId;
//    private Long boardId; // 이걸 만들어야 할 필요가 있을까?
    private String boardWriter;
    private String boardPassword;
    private String boardTitle;
    private String boardContents;
//    private LocalDateTime boardDate;
}
