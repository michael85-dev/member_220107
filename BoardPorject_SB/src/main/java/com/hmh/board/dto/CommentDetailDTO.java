package com.hmh.board.dto;

import com.hmh.board.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDetailDTO {
    private Long commentId;
    private Long boardId;
    private String commentWriter;
    private String commentContents;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public static CommentDetailDTO toCommentDetailDTO(CommentEntity c) {
        CommentDetailDTO commentDetailDTO = new CommentDetailDTO();
        commentDetailDTO.setBoardId(c.getBoardEntity().getId());
        commentDetailDTO.setCommentWriter(c.getCommentWriter());
        commentDetailDTO.setCommentContents(c.getCommentContents());
        commentDetailDTO.setCommentId(c.getId());
        commentDetailDTO.setCreateTime(c.getCreateTime());
        commentDetailDTO.setUpdateTime(c.getUpdateTime());

        return commentDetailDTO;
    }

    // serviceImpl에서 동일하게 가능함. (거기에도 주석 처리 되어있음)
//    public static CommentDetailDTO toCommentDetailDTO(CommentEntity c,  Long boardId) {
//        CommentDetailDTO commentDetailDTO = new CommentDetailDTO();
//        commentDetailDTO.setBoardId(boardId);
//        commentDetailDTO.setCommentWriter(c.getCommentWriter());
//        commentDetailDTO.setCommentContents(c.getCommentContents());
//        commentDetailDTO.setCommentId(c.getId());
//        commentDetailDTO.setCreateTime(c.getCreateTime());
//        commentDetailDTO.setUpdateTime(c.getUpdateTime());
//
//        return commentDetailDTO;
//    }
}
