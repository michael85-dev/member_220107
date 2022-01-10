package com.ex.member.service;

import com.ex.member.dto.MemberDetailDTO;
import com.ex.member.dto.MemberLoginDTO;
import com.ex.member.dto.MemberSaveDTO;
import com.ex.member.entity.MemberEntity;
import com.ex.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository mr;

    @Override
    public Long save(MemberSaveDTO msDTO) {
        // JpaRepository는 무조건 Entity만 받음.
//        mr.save(msDTO); // ()안에 entity가 아니기 때문에 작동하지 않음 -> 오류 생김

        // MemberSaveDTO -> MemberEntity
        MemberEntity mEntity = MemberEntity.saveMember(msDTO);
        Long memberId = mr.save(mEntity).getId(); // Entity의 데이터를 넘겨주고 거기서 GetId를 해서 Long으로 명명된 Pk값읖 가지고 오는 명령문.

        return memberId;
    }

    @Override
    public boolean login(MemberLoginDTO mlDTO) {
        // MemberEntity -> Member??
        MemberEntity mEntity = mr.findByMemberEmail(mlDTO.getMemberEmail()); // Entity에서 해당 값이 있는지 찾아오기

        if (mEntity != null) {
            if (mlDTO.getMemberPassword().equals(mEntity.getMemberPassword())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<MemberDetailDTO> findAll() {
        List<MemberEntity> mEntityList = mr.findAll();
        List<MemberDetailDTO> mList = new ArrayList<>(); // detailDTO를 담기 위한 사전 작업

        for (MemberEntity m:mEntityList) {
            mList.add(MemberDetailDTO.toMDDTO(m));
        }

        return mList;
    }

    @Override
    public MemberDetailDTO findById(Long memberId) {
        Optional<MemberEntity> member = mr.findById(memberId); // Entity로 부터 데이터를 가지고 온다.

        MemberDetailDTO mdDTO = MemberDetailDTO.toMDDTO(member.get()); // MemberDetailDTO에 가지고 온 데이터를 넣어 준다.

        return mdDTO; // 넣은 데이터를 보낸다.

        //만약 1줄로 줄이면
//        return MemberDetailDTO.toMDDTO(mr.findById(memberId).get());
    }

    @Override
    public void deleteById(Long memberId) {
        mr.deleteById(memberId); //jpa에 명령어가 있으므로 자동 적용됨
    }

    @Override
    public MemberDetailDTO findByEmail(String memberEmail) {
        // Repository에서 가지고오는 것은 무조건 Entity 데이터임.
        MemberEntity memberEntity = mr.findByMemberEmail(memberEmail);
        // Entity에서 가지고온 데이터를 변환 해줘야 함
        MemberDetailDTO mdDTO = MemberDetailDTO.toMDDTO(memberEntity);

        return mdDTO;
    }

    @Override
    public Long update(MemberDetailDTO mdDTO) {
        // update 처리시 save 메서드 호출. -> Jpa가 값을 확인하고 적합한 id값이 있으면 해당 id 값에 덮어쓰기를 진행함. -> 즉 굳이 update 관련을 만들 필요는 없음..
        // MemberDetailDTO -> MemberEntityDTO로 변경해야함.
        // -> 바꾸고자 하는 대상 클래스에 메서드를 만들어 줘야함. : MemberEntity에 메서드 필요?
        MemberEntity memberEntity = MemberEntity.updateMember(mdDTO);
        Long memberId = mr.save(memberEntity).getId();
        return memberId;
    }

//    @Override
//    public MemberSaveDTO findByMemberEmail(HttpSession hs) {
//        return mr.findByMemberEmail(h);
//    }
}
