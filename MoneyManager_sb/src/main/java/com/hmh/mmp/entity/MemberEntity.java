package com.hmh.mmp.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="member_table")
public class MemberEntity {
    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increments)
    @Column(name="member_id")
    private Long id;
    @Column(length = 50, unique = true)
    private String memberEmail;
    @Column(length = 30)
    private String memberPassword;
    @Column(length = 30)
    private String memberName;
    @Column(length = 20)
    private String memberPhone;
    @Column(length = 1000)
    private String memberMemo;
    @Column
    private String memberPhotoName;
}
