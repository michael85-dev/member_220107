package com.ex.member;

import com.ex.member.dto.MemberSaveDTO;
import com.ex.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.IntStream;

public class MemberTest {
    @Autowired
    private MemberService ms;

    @Test
    @DisplayName("회원 데이터 생성")
    public void newMembers() {
        //테스트를 위해서 회원을 넣게 하자
        IntStream.rangeClosed(1, 15).forEach(i -> {
            ms.save(new MemberSaveDTO("mEmail"+i, "pw"+i, "name"+i));
        });
    }
}
