package com.hmh.mmp.repository;

import com.hmh.mmp.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
}
