package com.hmh.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSaveDTO extends BaseDTO {
    private String memberEmail;
    private String memberPassword;
    private String memberName;

}
