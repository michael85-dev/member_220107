package com.example.member.repository;

import com.example.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<해당Entity클래스이름, PK타입> -> Repository 어노테이션은 필요 없음
// <> : Generic
public interface MemberRepository extends JpaRepository<MemberEntity, Long> { // JPA 사용
    // 여기에 save 메서드가 없지만 문제가 없는 이유는 Jpa~~ 문구가 save 등에 대한 메서드를 가지고 있음

    // 로그인 관련 정의
    // 이메일을 조건으로 회원 조회
    /*
        메서드 리턴타입: MemberEntity
        메서드 이름: findByMemberEmail
        메서드 매개변수: String memberEmail
     */
    MemberEntity findByMemberEmail(String memberEmail); // 인터페이스니까.
}
