package com.hmh.mmp.service;

import com.hmh.mmp.dto.MemberLoginDTO;
import com.hmh.mmp.dto.MemberSaveDTO;
import com.hmh.mmp.entity.MemberEntity;
import com.hmh.mmp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository mr;

    @Override
    public Long save(MemberSaveDTO memberSaveDTO) {
        /*
            1. MemberSaveDTO -> MemberEntity에 옮기기 : msDTO는 더 이상 다음단계에서는 쓰지 않음. 해당 데이터는 Entity에서 다룸
                - 여기서는 MemberEntity의 saveMember 메서드
            2. MemberRepository의 save 메서드 호출하면서 MemberEntity 객체 전달

         */
        MemberEntity mEntity = MemberEntity.saveMember(memberSaveDTO); // 이것을 통해 가입 진행됨

        // 이메일 체크
        MemberEntity emailCheck = mr.findByIdMemberEmail(memberSaveDTO.getMemberEmail()); // 지금 설정하는게 아님...

        if (emailCheck != null) {
            throw new IllegalStateException("중복된 이메일입니다.");
        } else {
            return mr.save(mEntity).getId();
        }

    }

    @Override
    public boolean login(MemberLoginDTO memberLoginDTO) {
        // MemberEntity -> MemberLogin
        MemberEntity memberEntity = mr.findByIdMemberEmail(memberLoginDTO.getMemberEmail()); // 왜냐면 로그인에서 쓴 데이터는 memberLogin에서 받았기 때문에.

        if (memberEntity != null) {
            if (memberLoginDTO.getMemberPassword().equals(memberEntity.getMemberPassword())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
