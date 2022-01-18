package com.hmh.board.repository;

import com.hmh.board.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    MemberEntity findByEmail(String memberEmail);

    MemberEntity findByMemberEmail(String boardWriter);
}
