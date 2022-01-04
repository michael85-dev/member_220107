package com.hmh.member.dto;

import lombok.Data;

@Data
public class MemberSaveDTO {
    private String name;
    private String email;
    private String password;
}
