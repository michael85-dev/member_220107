package com.hmh.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentSaveDTO {
    private Long boardId;
    private String commentWriter;
    private String commentContents;
}
