package com.hmh.board.entity;

import com.hmh.board.dto.CommentSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity {
    // 댓글번호, 작성자, 내용, 원글

    @Column(name = "commnet_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    // 원글의 게시글 번호를 참조하기 위한 설정 (댓글과 게시글의 관계 로 봐야함). -> ManyToOne (설정해주는 입장에서 봐야하는 것)
    @ManyToOne(fetch = FetchType.LAZY) // fetch?
    @JoinColumn(name = "board_id") // 참조할 컬럼의 이름 -> 부모테이블(참조하고자하는)의 pk컬럼이름 (일반적으로)
    private BoardEntity boardEntity; // 엔티티 전체를 필드 이름으로 선언함.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity; // 사실상 필요 없을 거라고 생각되지만 일단은 만들어봄.

    @Column
    private String commentWriter;

    @Column
    private String commentContents;

    public static CommentEntity toSaveEntity(CommentSaveDTO commentSaveDTO, BoardEntity boardEntity, MemberEntity memberEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentContents(commentSaveDTO.getCommentContents());
        commentEntity.setCommentWriter(commentSaveDTO.getCommentWriter());
        commentEntity.setBoardEntity(boardEntity);
        commentEntity.setMemberEntity(memberEntity);

        return commentEntity;

    }
}
