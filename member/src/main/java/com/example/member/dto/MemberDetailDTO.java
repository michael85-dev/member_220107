package com.example.member.dto;

import com.example.member.entity.MemberEntity;
import lombok.Data;

import javax.persistence.Id;

@Data
public class MemberDetailDTO {
    private Long memberId;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    public static MemberDetailDTO toMemberDetailDTO(MemberEntity member) {
        MemberDetailDTO memberDetailDTO = new MemberDetailDTO(); // 생성자 호출
        memberDetailDTO.setMemberId(member.getId());
        memberDetailDTO.setMemberName(member.getMemberName());
        memberDetailDTO.setMemberPassword(member.getMemberPassword());
        memberDetailDTO.setMemberEmail(member.getMemberEmail());

        return memberDetailDTO;
    }
}
