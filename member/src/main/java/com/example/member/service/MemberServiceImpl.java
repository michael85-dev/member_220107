package com.example.member.service;

import com.example.member.dto.MemberDetailDTO;
import com.example.member.dto.MemberLoginDTO;
import com.example.member.dto.MemberSaveDTO;
import com.example.member.entity.MemberEntity;
import com.example.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
//    @Autowired
//    private MemberRepository_mybatis mr; mybatis 방식
    private final MemberRepository mr;

    @Override
    public Long save(MemberSaveDTO msDTO) {
//        mr.save(msDTO);
        /*
            1. MemberSaveDTO -> MemberEntity에 옮기기 : msDTO는 더 이상 다음단계에서는 쓰지 않음. 해당 데이터는 Entity에서 다룸
                - 여기서는 MemberEntity의 saveMember 메서드
            2. MemberRepository의 save 메서드 호출하면서 MemberEntity 객체 전달

         */
        MemberEntity memberEntity = MemberEntity.saveMember(msDTO);// MemberEntity 에 있는 saveMember에서 set으로 설정하는 것을 불러서 보냄.
        // 가입 진행 전에 이메일 체크 단계를 먼저 진행 함. -> 맨 밑에 mr.save~~~~가 가입 진행
        // 사용자가 입력한 이메일 중복 체크 -> STS에서는 idDuplicate라는 메서드를 만들어서 씀.
        MemberEntity emailCheckResult = mr.findByMemberEmail(msDTO.getMemberEmail());
        if (emailCheckResult != null) {
            throw new IllegalStateException("중복된 이메일 입니다."); // 예외 처리 구문.
        } else {

            // MemberEntity에서 return이 있었기 때문에 해당 값을 넣어준 것.
            // static 메서드 이기 때문에 바로 . 을 이용해서 호출이 가능 한것.
            // 만약 static이 없으면 객체를 호출하고 만들어줘야함. (@Autowired ~~~~)

//          mr.save(memberEntity); // 바로 DB에 저장.
            return mr.save(memberEntity).getId(); // 키 값 가지고 오는 방법.
        }
    }

    @Override
    public MemberDetailDTO findById(Long memberId) {
        /*
            1. MemberRepository로 부터 해당 회원의 정보를 MemberEntity로 가져옴
            2. MemberEntity를 MemberDetailDTO로 바꿔서 컨으롤러로 리턴
         */

//        Optional<MemberEntity> member = mr.findById(memberId);
        // 바로 Entity 객체로 보내는 것이 아닌 Null point를 방지하기 위해 Optional 로 감싸서 보냄.

        // 일단 optional은 어렵기 때문에 그걸 제외하고 다른걸 써보자 - 1번
        MemberEntity member = mr.findById(memberId).get();

        // 2
        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(member);
        System.out.println("memberDetailDTO.toString() = " + memberDetailDTO.toString());
        System.out.println("memberDetailDTO = " + memberDetailDTO);

        return memberDetailDTO;
    }

    @Override
    public boolean login(MemberLoginDTO mlDTO) {
        // 1. 사용자가 입력한 이메일을 조건으로 DB에서 조회 (select * from member_table where member_email=?)
        // Repository에서 선언 완료 후 명명
        MemberEntity memberEntity = mr.findByMemberEmail(mlDTO.getMemberEmail()); // 조회결과로 memberEntity로 오겠지.
//        if (memberEntity.equals(NULL))
        // 2. 비밀번호 일치여부 확인

        if (memberEntity != null) {
            if (mlDTO.getMemberPassword().equals(memberEntity.getMemberPassword())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public List<MemberDetailDTO> findAll() {
        List<MemberEntity> memberEntityList = mr.findAll();
        // List<MemberEntity> -> List<MemberDetailDTO> 를 해야 함
        // My -> 틀림.
//        List<MemberDetailDTO> mList = new ArrayList<MemberDetailDTO>();
//        for (MemberEntity e: memberEntityList) {
//            for (int i = 0; i < mList.size(); i++) {
//                mList.get(i).setMemberName(e.getMemberName());
//                mList.get(i).setMemberPassword(e.getMemberPassword());
//                mList.get(i).setMemberEmail(e.getMemberEmail());
//            }
//        }
        // 만약 맞게 하려면 -> 매칭 대문에.
//        List<MemberDetailDTO> mList = new ArrayList<MemberDetailDTO>();
//        for (int j = 0; j < memberEntityList.size(); j++) {
//            for (int i = 0; i < mList.size(); i++) {
//                mList.get(i).setMemberName(memberEntityList.get(i).getMemberName());
//                mList.get(i).setMemberPassword(memberEntityList.get(i).getMemberPassword());
//                mList.get(i).setMemberEmail(memberEntityList.get(i).getMemberEmail());
//            }
//        }

        // T
        // toMemberDetailDTO 라고 만들어두었으니 그걸 사용하자.
        List<MemberDetailDTO> memberList = new ArrayList<>(); // java 8이상 부터 가능
        for (MemberEntity m: memberEntityList) {
            // Entity 객체를 MemberDetailDTO로 변환하고 memberList에 담음
            // method 1
            memberList.add(MemberDetailDTO.toMemberDetailDTO(m));
            // method 2 (1은 2의 방법을 2줄로 줄인것)
//            MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(m);
//            memberList.add(memberDetailDTO);
        }

        return memberList;
    }
}
