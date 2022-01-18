package com.hmh.board.entity;

import com.hmh.board.dto.BoardDetailDTO;
import com.hmh.board.dto.BoardSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    // 01.17 추가 내용
    // 댓글 연관 관계
    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) // mappedBy에 쓰는 것은 다른 곳에 쓴 엔티티 관련
    // 연관 관계를 설정하며 무엇이 무체인지를 표시하기 위해서 쓰임 (mappedBy)
    private List<CommentEntity> commentEntityList = new ArrayList<>(); // 1 대 다 이기 떄문에 리스트 관계가 적합함.
    // fetch : 캐싱 역활 정도
        // EAGER : 요청시 전부 가져옴
        // LAZY : 필요시 데이터 가지고옴.
    // SP JPA를 쓸 떄는 필요 없으나. 그냥 JPA를 쓰려면 flush, 메모리 관리등을 전부 해줘야함.

    //회원 엔티티와의 연관관계.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

//------ 이전 내용
//    // 글 수정에 따라 정보를 남기기도 함(시간에 대해서)
//    private LocalDateTime boardDate;
    // 관련 내용은 BaseEntity에서 상속받아 옴.

    public static BoardEntity toSaveEntity(BoardSaveDTO boardSaveDTO, MemberEntity memberEntity) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setMemberEntity(memberEntity);
        boardEntity.setBoardWriter(boardSaveDTO.getBoardWriter());
        boardEntity.setBoardPassword(boardSaveDTO.getBoardPassword());
        boardEntity.setBoardTitle(boardSaveDTO.getBoardTitle());
        boardEntity.setBoardContents(boardSaveDTO.getBoardContents());
//        boardEntity.setBoardDate(boardSaveDTO.getBoardDate());
//        boardEntity.setBoardDate(LocalDateTime.now());
        return boardEntity;
    }

    public static BoardEntity updateEntity(BoardDetailDTO boardDetailDTO, MemberEntity memberEntity) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setMemberEntity(memberEntity);
        boardEntity.setId(boardDetailDTO.getBoardId());
        boardEntity.setBoardWriter(boardDetailDTO.getBoardWriter());
        boardEntity.setBoardPassword(boardDetailDTO.getBoardPassword());
        boardEntity.setBoardTitle(boardDetailDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDetailDTO.getBoardContents());
//        boardEntity.setBoardDate(boardDetailDTO.getBoardDate());

        return boardEntity;
    }
}
