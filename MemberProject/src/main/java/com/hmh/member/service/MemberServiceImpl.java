package com.hmh.member.service;

import com.hmh.member.dto.MemberSaveDTO;
import com.hmh.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository mr;

    @Override
    public void save(MemberSaveDTO msDTO) {
        mr.save(msDTO);
    }
}
