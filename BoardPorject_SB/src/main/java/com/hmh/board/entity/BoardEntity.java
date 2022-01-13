package com.hmh.board.entity;

import com.hmh.board.dto.BoardDetailDTO;
import com.hmh.board.dto.BoardSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30)
    private String boardWriter;
    @Column(length = 20)
    private String boardPassword;
    @Column(length = 200)
    private String boardTitle;
    @Column(length = 2000)
    private String boardContents;
//
//    // 글 수정에 따라 정보를 남기기도 함(시간에 대해서)
//    private LocalDateTime boardDate;
    // 관련 내용은 BaseEntity에서 상속받아 옴.

    public static BoardEntity toSaveEntity(BoardSaveDTO boardSaveDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardSaveDTO.getBoardWriter());
        boardEntity.setBoardPassword(boardSaveDTO.getBoardPassword());
        boardEntity.setBoardTitle(boardSaveDTO.getBoardTitle());
        boardEntity.setBoardContents(boardSaveDTO.getBoardContents());
//        boardEntity.setBoardDate(boardSaveDTO.getBoardDate());
//        boardEntity.setBoardDate(LocalDateTime.now());
        return boardEntity;
    }

    public static BoardEntity updateEntity(BoardDetailDTO boardDetailDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDetailDTO.getBoardId());
        boardEntity.setBoardWriter(boardDetailDTO.getBoardWriter());
        boardEntity.setBoardPassword(boardDetailDTO.getBoardPassword());
        boardEntity.setBoardTitle(boardDetailDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDetailDTO.getBoardContents());
//        boardEntity.setBoardDate(boardDetailDTO.getBoardDate());

        return boardEntity;
    }
}
