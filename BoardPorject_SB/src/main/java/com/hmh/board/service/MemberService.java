package com.hmh.board.service;

import com.hmh.board.dto.MemberLoginDTO;
import com.hmh.board.dto.MemberSaveDTO;

public interface MemberService {
    Long save(MemberSaveDTO memberSaveDTO);

    boolean login(MemberLoginDTO memberLoginDTO);

    Long findById(Long memberId);

    MemberLoginDTO findByEmail(String memberEmail);
}
