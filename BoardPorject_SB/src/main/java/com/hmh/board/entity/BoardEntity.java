package com.hmh.board.entity;

import com.hmh.board.dto.BoardSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "board_table")
public class BoardEntity {
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
    private LocalDateTime boardDate;

    public static BoardEntity toSaveEntity(BoardSaveDTO boardSaveDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardSaveDTO.getBoardWriter());
        boardEntity.setBoardPassword(boardSaveDTO.getBoardPassword());
        boardEntity.setBoardTitle(boardSaveDTO.getBoardTitle());
        boardEntity.setBoardContents(boardSaveDTO.getBoardContents());
//        boardEntity.setBoardDate(boardSaveDTO.getBoardDate());
        boardEntity.setBoardDate(LocalDateTime.now());
        return boardEntity;
    }
}
