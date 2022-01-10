package com.ex.member.entity;

import com.ex.member.dto.MemberDetailDTO;
import com.ex.member.dto.MemberSaveDTO;
import com.ex.member.service.MemberService;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="member_table")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 50, unique = true)
    private String memberEmail;

    @Column(length = 30)
    private String memberPassword;

    @Column(length = 20)
    private String memberName;

    // MemberSaveDTO -> MemberEntity 객체로 변환하기 위한 메서드
    public static MemberEntity saveMember(MemberSaveDTO msDTO) {
        MemberEntity mEntity = new MemberEntity();
        String memberEmail = msDTO.getMemberEmail();
        mEntity.setMemberEmail(memberEmail);
        // 위에 2줄을 한줄로 합쳐서 쓴게 아래의 두개
        mEntity.setMemberPassword(msDTO.getMemberPassword());
        mEntity.setMemberName(msDTO.getMemberName());

        return mEntity;
    }

    public static MemberEntity updateMember(MemberDetailDTO mdDTO) {
        MemberEntity mEntity = new MemberEntity();
        String memberEmail = mdDTO.getMemberEmail();
        mEntity.setMemberEmail(memberEmail);
        mEntity.setId(mdDTO.getMemberId());
        mEntity.setMemberName(mdDTO.getMemberName());
        mEntity.setMemberPassword(mdDTO.getMemberPassword());

        return mEntity;
    }
}
