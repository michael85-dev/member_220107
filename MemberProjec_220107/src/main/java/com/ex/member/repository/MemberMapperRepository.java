package com.ex.member.repository;


import com.ex.member.dto.MemberDetailDTO;
import com.ex.member.dto.MemberMapperDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberMapperRepository {
    // 이 것이 xml을 호출함.
    // Spring(sts)에서는 MR -> mapper -> DB

    // Spring Boot에서
    // MRM.inter -> xml -> DB

    // 회원 목록 출력
    List<MemberMapperDTO> memberList();

    void save(MemberMapperDTO memberMapperDTO);

    // mapper를 호출하지 않고 여기서 쿼리까지 수행하는 방식
    @Select("select * from member_table")
    List<MemberMapperDTO> memberList2();

    @Insert("insert into member_table(member_email, member_password, member_name) values (#{member_email}, #{member_password}, #{member_name})")
    void save2(MemberMapperDTO memberMapperDTO);
}
