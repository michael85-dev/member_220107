package com.ex.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 요소가 하나도 없는 기본 생성자 생성
@AllArgsConstructor  // 모든 요소를 사용하는 기본 생성자 생성
// preference - build - complier - annotation~~ 코드 동작을 시키기 위해서 무조건 작동 시켜야 함.
public class MemberSaveDTO {
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    // windows의 경우 alt + insert

}
