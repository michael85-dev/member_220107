package com.hmh.board.dto;

import com.hmh.board.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Member;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDetailDTO extends BaseDTO {
    private Long memberId;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    public static MemberDetailDTO toLoginDTO(MemberEntity memberEntity) {
        MemberDetailDTO memberDetailDTO = new MemberDetailDTO();

        memberDetailDTO.setMemberId(memberEntity.getId());
        memberDetailDTO.setMemberName(memberEntity.getMemberName());
        memberDetailDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDetailDTO.setMemberPassword(memberEntity.getMemberPassword());

        return memberDetailDTO;
    }
}
