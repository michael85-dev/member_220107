package com.example.member;

import com.example.member.dto.MemberDetailDTO;
import com.example.member.dto.MemberLoginDTO;
import com.example.member.dto.MemberSaveDTO;
import com.example.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest // test 코드에 붙이는 어노테이션.
public class MemberTest {
    /*
        MemberServiceImpl.save() 메서드가 잘 동작하는지 테스트

        회원가입 테스트
        save.html 에서 회원정보 입력 후 가입 클릭
        DB 확인
     */
    // Service에 메서드 만들면 그거에 해당하는 Test 메서드가 생기는 것이 정석임.

    @Autowired
    private MemberService ms;

    // 테스트 주관 메서드는 junit -> java가 제공해주는 패키지.
    @Test
    @DisplayName("회원가입 테스트")
    public void memberSaveTest() { // 해당 메서드 실행하기 위해서는 왼쪽 아이콘을 눌러라....
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO();
        memberSaveDTO.setMemberEmail("테스트회원 이메일 1");
        memberSaveDTO.setMemberPassword("테스트 비번 1");
        memberSaveDTO.setMemberName("테스트 회원 이름 1");

        ms.save(memberSaveDTO);
    }

    @Test
    @Transactional // 테스트 시작시 새로운 트랜잭션 시작
    @Rollback // 테스트 종료 후 롤백 수행... (되돌리기... 수행)
    @DisplayName("회원조회 테스트")
    public void memberDetailTest() {
        // Test code 구성 할때 3단계를 이야기함
        // 1. given : 테스트 조건 설정
            // 새로운 회원을 등록하고 해당 회원의 번호(memberId)를 가져옴
        // 테스트 데이터용 객체 생성
        MemberSaveDTO msDTO = new MemberSaveDTO("조회용 회원 이름1", "조회용 회원 이메일1", "조회용 회원 비밀번호1");
        // 위의 생성자 정보로 가입.
        Long saveMemberId = ms.save(msDTO);

        // 2. when : 테스트 수행
            // 위에서 가져온 회원 번호를 가지고 조회 기능 수행
        MemberDetailDTO findMember = ms.findById(saveMemberId);

        // 3. then : 테스트 결과 검증
            // 위에서 가입한 회원의 정보와 가지고 온 정보가 일치 하는지 확인 -> 일치 : 통과 / 불일치 : 실패
        // memberSaveDTO의 이메일 값과 findMember의 이메일 값이 일치하는지 확인
        assertThat(msDTO.getMemberEmail()).isEqualTo(findMember.getMemberEmail());

    }

    @Test
    @Rollback
    @DisplayName("로그인 테스트")
    public void memberLoginTest() {

//        // My
//        //데이터 주입
//        MemberLoginDTO mlDTO = new MemberLoginDTO("로그인용 이메일", "로그인용 비밀번호");
//        // 데이터 있는지 불러오기
//        boolean logMemberEmail = ms.login(mlDTO);
//
//        if (logMemberEmail) {
//            System.out.println("성공");
//        } else {
//            System.out.println("실패");
//        }

        // T
        /*
            절차
            1. 테스트용 회원 가입(MemberSaveDTO)
            2. 로그인용 객체 생성(MemberLoginDTO)
                - 1과 2의 경우 동일한 이메일 패스워드를 사용하도록 함
            3. 로그인 수행
            4. 로그인 수행 결과가 true인지 확인
         */

        // given -> 변수 값 변하지 않길 원하면 final 사용
        String testMemberEmail = "로그인 테스트 이메일";
        String testMemberPassword = "로그인 테스트 비밀번호";
        String testMemberName = "로그인 테스트 이름";
        // 1. 테스트용 회원 가입
        MemberSaveDTO msDTO = new MemberSaveDTO(testMemberName, testMemberPassword, testMemberEmail);
        ms.save(msDTO); // 데이터 주입

        // when
        // 2. 로그인
        MemberLoginDTO mlDTO = new MemberLoginDTO(testMemberEmail, testMemberPassword);
        boolean loginResult = ms.login(mlDTO);

        // then 검증
        assertThat(loginResult).isEqualTo(true);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("회원목록 테스트")
    public void memberListTest() {
        /*
            member_table에 여러명의 데이터를 넣고 그걸 가지고 테스트를 해보기?
         */
//        // My
//        String tMN1 = "테스트 이름1";
//        String tMP1 = "테스트 비번1";
//        String tME1 = "테스트 메일1";
//        String tMN2 = "테스트 이름2";
//        String tMP2 = "테스트 비번2";
//        String tME2 = "테스트 메일2";
//        String tMN3 = "테스트 이름3";
//        String tMP3 = "테스트 비번3";
//        String tME3 = "테스트 메일3";
//
//        // 1. 테스트용 회원 가입
//        MemberSaveDTO msDTO1 = new MemberSaveDTO(tMN1, tMP1, tME1);
//        MemberSaveDTO msDTO2 = new MemberSaveDTO(tMN2, tMP2, tME2);
//        MemberSaveDTO msDTO3 = new MemberSaveDTO(tMN3, tMP3, tME3);
//        ms.save(msDTO1); // 데이터 주입
//        ms.save(msDTO2);
//        ms.save(msDTO3);
//
//        List<MemberDetailDTO> mList = new ArrayList<>();
//        long count = mList.size();
//
//        System.out.println(count);
//        if (count == 3) {
//            System.out.println("테스트 성공");
//        } else {
//            System.out.println("테스트 실패");
//        }
        // 1. 3명의 회원을 가입
        // 1.1 방법
//        MemberSaveDTO memberSaveDTO = new MemberSaveDTO("리스트회원1", "이메일1", "비밀번호1");
//        ms.save(memberSaveDTO);
//        memberSaveDTO = new MemberSaveDTO("리스트회원2", "이메일2", "비밀번호2");
//        ms.save(memberSaveDTO);
//        memberSaveDTO = new MemberSaveDTO("리스트회원3", "이메일3", "비밀번호3");
//        ms.save(memberSaveDTO);
        // 1.2 방법
//        for (int i = 0; i <= 3; i++ ) {
//            MemberSaveDTO memberSaveDTO = new MemberSaveDTO("리스트회원"+i, "이메일"+i, "비밀번호"+i);
//            ms.save(memberSaveDTO);
//        }

        // New 기술 1.3 방법
        // IntStream 방식, Arrow Function(화살표 함수)
        IntStream.rangeClosed(1, 3).forEach(i -> {
            MemberSaveDTO memberSaveDTO = new MemberSaveDTO("리스트회원"+i, "이메일"+i, "비밀번호"+i);
            ms.save(memberSaveDTO);

            List<MemberDetailDTO> list = ms.findAll();
            assertThat(list.size()).isEqualTo(3);
        });
    }
}
