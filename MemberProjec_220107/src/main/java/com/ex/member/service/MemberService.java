package com.ex.member.service;

import com.ex.member.dto.MemberDetailDTO;
import com.ex.member.dto.MemberLoginDTO;
import com.ex.member.dto.MemberSaveDTO;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface MemberService {
    Long save(MemberSaveDTO msDTO);

    boolean login(MemberLoginDTO mlDTO);

    List<MemberDetailDTO> findAll();

    MemberDetailDTO findById(Long memberId);

    void deleteById(Long memberId);

    MemberDetailDTO findByEmail(String memberEmail);

    Long update(MemberDetailDTO mdDTO);

//    MemberSaveDTO findByMemberEmail(HttpSession );
}
