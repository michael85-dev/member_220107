package com.hmh.mmp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSaveDTO {
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberPhone;
    private String memberMemo;
    private MultipartFile memberPhoto;
    private String memberPhotoName;
}
