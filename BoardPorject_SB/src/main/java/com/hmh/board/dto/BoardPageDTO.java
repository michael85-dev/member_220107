package com.hmh.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // 모든 필드를 이용한 생성자 생성 요청
@NoArgsConstructor // 기본 생성자 생성 요청
public class BoardPageDTO {
    private Long boardId;
    private String boardWriter;
    private String boardTitle;
}
