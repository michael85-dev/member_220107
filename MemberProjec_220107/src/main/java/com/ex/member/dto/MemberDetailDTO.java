package com.ex.member.dto;

import com.ex.member.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDetailDTO {
    private Long memberId;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    // MemberEntity -> MemberDetailDTO
    public static MemberDetailDTO toMDDTO(MemberEntity mEntity) {
        MemberDetailDTO mdDTO = new MemberDetailDTO();
        mdDTO.setMemberId(mEntity.getId());
        mdDTO.setMemberEmail(mEntity.getMemberEmail());
        mdDTO.setMemberPassword(mEntity.getMemberPassword());
        mdDTO.setMemberName(mEntity.getMemberName());

        return mdDTO;
    }
}
