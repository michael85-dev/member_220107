package com.example.member.entity;

// 필드 생성시 필드 대로 테이블에 컬럼으로 생성됨
// 여기에 주어지는 것들이 자동으로 db에 데이터가 들어간다고 생각하면 됨

import com.example.member.dto.MemberDetailDTO;
import com.example.member.dto.MemberSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="member_table") // DB에 해당 이름으로 테이블을 생성하겠다.
public class MemberEntity { // Entity는 pk를 무조건 가지고 있어야만 함.
    @Id // pk 지정 어노테이션
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increments의 기능.
    @Column(name = "member_id") // column 이름.
    private Long id; // 왜 long 이 아니라 Long 이지? -> Long member_id로 해도 되지만 많은 경우 다음과 같이 사용하기 때문에 일반적인 사용 방법을 적은 것.
    // long : primitive : 기본형 타입 -> 값을 주지 않을 경우 0이라는 기본 값이 들어감
    // Long : leper : class 타입 -> 값을 주지 않을 경우 기본 값이 아닌 Null로 빈값으로 있음.

    // memberEmail : 크기 50, unique
    @Column(length = 50, unique = true)
    private String memberEmail;

    // memberPassword : 크기 20
    @Column(length = 20)
    private String memberPassword;

    // memberName : 제약조건 x -> default 크기 255로 지정됨.
    private String memberName; // 카멜 케이스로 지정했지만 table 에는 member_name으로 설정됨.
    // 카멜 케이스로 할 경우 대문자가 소문자로 바뀌면서 앞에 _ 가 붙음.

    /*
        DTO 클래스 객체를 전달 받아 Entity 클래스 필드 값으로 세팅하고
        Entity 객체를 리턴하는 메서드 선언.

        static 메서드(정적 메서드) : 클래스 메서드, 객체를 만들지 않고도 바로 호출 가능.
     */
    public static MemberEntity saveMember(MemberSaveDTO msDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(msDTO.getMemberEmail());
        memberEntity.setMemberPassword(msDTO.getMemberPassword());
        memberEntity.setMemberName(msDTO.getMemberName());

        return memberEntity;
    }
//
//    public static MemberEntity findById(MemberDetailDTO mdDTO) {
//
//    }
}
