package com.ex.member;

import com.ex.member.dto.MemberDetailDTO;
import com.ex.member.dto.MemberMapperDTO;
import com.ex.member.dto.MemberSaveDTO;
import com.ex.member.repository.MemberMapperRepository;
import com.ex.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.IntStream;

public class MemberTest {
    @Autowired
    private MemberService ms;
    @Autowired
    private MemberMapperRepository mmr;

    @Test
    @DisplayName("회원 데이터 생성")
    public void newMembers() {
        //테스트를 위해서 회원을 넣게 하자
        IntStream.rangeClosed(1, 15).forEach(i -> {
            ms.save(new MemberSaveDTO("mEmail"+i, "pw"+i, "name"+i));
        });
    }

    /*
        회원삭제 테스트 코드 짜보기
        회원 삭제 시나리오를 작성 및 코드 짜보기
        1. 시나리오.
            1. 회원 가입하기
            2. 그 중에 하나 선택해서 삭제 시키기
            3. 삭제가 완료되었을 때 결과 값 list의 size가 기존보다 1개 적으면 성공. 그 이상 또는 그 이하면 실패
     */
    // My Sol
    @Test
    @Rollback
    @DisplayName("삭제")
    public void checkDeleteMember() {
        IntStream.rangeClosed(1, 15).forEach(i -> {
           ms.save(new MemberSaveDTO("mEail"+i, "mPw" + i, "mName"+i)) ;
        });

        List<MemberSaveDTO> before = new ArrayList<>();

        long bCount = before.size();

        // memberId 즉 1개의 선택지를 어떻게 가지고 와야할까?
        long memberId = (long)((double)before.size() / 2);
        ms.deleteById(memberId);

        List<MemberSaveDTO> after = new ArrayList<>();

        long aCount = after.size();

        if (bCount - 1 == aCount) {
            System.out.println("테스트 성공");
        } else {
            System.out.println("Test 실패");
        }
    }

    // T Sol
    @Test
    @Rollback
    @Transactional
    @DisplayName("삭제임 By T")
    public void memberDeleteTest() {
        // 신규 등록.
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO("삭제용 회원 이메일1", "삭제용 회원 비번1", "삭제용 회원 이름1");
        Long memberId = ms.save(memberSaveDTO);

        // 등록 되었는지 확인
        System.out.println(ms.findById(memberId));

        ms.deleteById(memberId);

        // 삭제되었기에 조회는 안될 것임.-> NoSuchElementException 발생됨.
//        System.out.println(ms.findById(memberId));

        //삭제한 회원의 id로 조회를 시도했을 때 null 이어야 테스트 통과됨 -> try catch?
        // NoSuchElementException은 무시하고 테스트 수행
        assertThrows(NoSuchElementException.class, () -> {
           assertThat(ms.findById(memberId)).isNull(); // 삭제회원 id 조회결과가 null이면 테스트 통과
        });

    }

    @Test
    @Rollback
    @Transactional // DB에 데이터를 테스트를 통해서 만들어진 것은 유지할 것인ㄴ지 아니면 후에 삭제할 것인지에 대한 것.
    @DisplayName("회원 수정 테스트")
    public void memberUpdateTest() {
        /*
            1. 신규회원 등록
            2. 신규등록한 회원에 대한 수정
            3. 신규등록시 사요안 이름과 DB에 저장된 이름이 일치하느지 판단.
            4. 일치하지 않아야 테스트 통과
         */
        // 1.
        String mEmail = "수정회원1";
        String mPassword = "수정비번1";
        String mName = "수정이름1";

        MemberSaveDTO memberSaveDTO = new MemberSaveDTO(mEmail, mPassword, mName);
        Long saveMemberId = ms.save(memberSaveDTO);

        // 가입돈 이름 ㅈ회
        String saveMemberName = ms.findById(saveMemberId).getMemberName();

        // 2.
        String updateName = "수정완료";
        MemberDetailDTO updateMember = new MemberDetailDTO(saveMemberId, mEmail, mPassword, updateName);
        ms.update(updateMember);
        // 수정 후 이름 조회
        String updateMemberName = ms.findById(saveMemberId).getMemberName();

        // 3. 4.
        assertThat(saveMemberName).isNotEqualTo(updateMemberName);
    }

    @Test
    @DisplayName("Mybatis 목록 출력 테스트")
    public void memberListTest() {
        List<MemberMapperDTO> memberList = mmr.memberList();

        for (MemberMapperDTO m: memberList) {
            System.out.println("m.toString() = " + m.toString());
        }

        List<MemberMapperDTO> memberList2 = mmr.memberList();

        for (MemberMapperDTO m: memberList2) {
            System.out.println("m.toString() = " + m.toString());
        }
    }

    @Test
    @DisplayName("mybatis 회원가입 테스트")
    public void memberSaveTest() {
        MemberMapperDTO memberMapperDTO = new MemberMapperDTO("회원이메일1", "회원비번1", "회원이름1");
        mmr.save(memberMapperDTO);

        MemberMapperDTO memberMapperDTO2 = new MemberMapperDTO("회원이메일12", "회원비번12", "회원이름12");
        mmr.save2(memberMapperDTO);
    }

}
