package com.example.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor // 기본 생성자 외에 다른 생성자 까지 알아서 생성해주게 하는 것 -> 모든 필드를 매게변수로 하는 생성자 생성해 주는것 -> 기본 생성자는 없음.
@NoArgsConstructor // 기본 생성자 쓸 수 있게 해주는 것 -> 기본 생성자 생성 해주는 것
public class MemberSaveDTO {
    private String memberName;
    @NotBlank(message = "이메일은 필수입니다.") //
    private String memberEmail;
    @NotBlank
    @Length(min = 2, max = 8, message = "2~8자로 입력해주세요")
    private String memberPassword;
}
