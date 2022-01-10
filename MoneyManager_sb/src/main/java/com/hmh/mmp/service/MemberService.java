package com.hmh.mmp.service;

import com.hmh.mmp.dto.MemberLoginDTO;
import com.hmh.mmp.dto.MemberSaveDTO;

public interface MemberService {
    Long save(MemberSaveDTO memberSaveDTO); //Jpa에서 쓰기위해서 Long으로 해야지 받을 수 있음.

    boolean login(MemberLoginDTO memberLoginDTO);
}
