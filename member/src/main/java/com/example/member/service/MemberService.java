package com.example.member.service;

import com.example.member.dto.MemberDetailDTO;
import com.example.member.dto.MemberLoginDTO;
import com.example.member.dto.MemberSaveDTO;

import java.util.List;

public interface MemberService {
    Long save(MemberSaveDTO msDTO);

    MemberDetailDTO findById(Long memberId);

    boolean login(MemberLoginDTO mlDTO);

    List<MemberDetailDTO> findAll();
}
