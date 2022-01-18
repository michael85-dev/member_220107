package com.hmh.board.entity;

import ch.qos.logback.classic.net.SMTPAppender;
import com.hmh.board.dto.MemberSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "member_table")
public class MemberEntity extends BaseEntity {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // strategy 가 없으면 모든 데이터가 단독적이 아닌 연관적으로 번호가 생김
    // sequence는 오라클에 쓰는것.
    private Long id;

    @Column(length = 30)
    private String memberEmail;
    @Column(length = 20)
    private String memberPassword;

    private String memberName;

    // 1번째 시도. 하기 2개에 대해서 적지 않음 (22.01.18)
    // 이유 : 적지 않아도 member에 대해서 참조를 하는것이 가능
    @OneToMany(mappedBy = "memberEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardEntity> boardEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> commentEntityList = new ArrayList<>(); // 상황에 따라 맵도 가능.
    // 위에 2개가 없으면 회원 삭제가 안됨. 왜냐면 연관 관계 때문에. 하지만 위에 설정은 회원 연관 데이터가 전부 사라지는 행위....



    public static MemberEntity toSaveEntity(MemberSaveDTO memberSaveDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberSaveDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberSaveDTO.getMemberPassword());
        memberEntity.setMemberName(memberSaveDTO.getMemberName());

        return memberEntity;
    }

    // on delete set null 일 경우
    // 밑에 forRemove와 같이 쓰는 것.
//    @OneToMany(mappedBy = "memberEntity", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = false)
//    private List<BoardEntity> boardEntityList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "memberEntity", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = false)
//    private List<CommentEntity> commentEntityList = new ArrayList<>(); // 상황에 따라 맵도 가능.
//
//    @PreRemove
//    private void preRemove() {
//        System.out.println("MemberEntity.preRemove");
//        boardEntityList.forEach(board -> board.setMemberEntity(null));
//            /* 같은 의미.
//            for (BoardEntity board: boardEntityList) {
//                board.setMemberEntity();
//            }
//             */
//        commentEntityList.forEach(comment -> comment.setMemberEntity(null));
//        // 부모 자료가 지워져도 자식자료가 살아남게 하려면 적는 코드.
//    }
}
