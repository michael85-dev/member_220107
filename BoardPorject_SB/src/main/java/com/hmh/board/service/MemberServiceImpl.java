package com.hmh.board.service;

import com.hmh.board.dto.MemberDetailDTO;
import com.hmh.board.dto.MemberLoginDTO;
import com.hmh.board.dto.MemberSaveDTO;
import com.hmh.board.entity.MemberEntity;
import com.hmh.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository mr;

    @Override
    public Long save(MemberSaveDTO memberSaveDTO) {
        MemberEntity memberEntity = MemberEntity.toSaveEntity(memberSaveDTO);

        Long memberId = mr.save(memberEntity).getId();

        return memberId;
    }

    @Override
    public boolean login(MemberLoginDTO memberLoginDTO) {
        MemberEntity memberEntity = mr.findByEmail(memberLoginDTO.getMemberEmail());

        if (memberEntity != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Long findById(Long memberId) {


        return null;
    }

    @Override
    public MemberLoginDTO findByEmail(String memberEmail) {
        MemberEntity memberEntity = mr.findByEmail(memberEmail);
        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toLoginDTO(memberEntity);

        return null;
    }
}
