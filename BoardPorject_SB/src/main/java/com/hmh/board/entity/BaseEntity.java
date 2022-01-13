package com.hmh.board.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class) // 하위 클래스 insert 작업을 계속 확인 (Audit)
@Getter
@MappedSuperclass //
public abstract class BaseEntity {
    // abstract : 추상 메서드란?

    @CreationTimestamp
    @Column(updatable = false) // 수정 불가.
    private LocalDateTime createTime; // 처음 작성된 시간 (insert)

    @UpdateTimestamp
    @Column(insertable = false) // 생성시 안넣기 -> 수정만 가능
    private LocalDateTime updateTime; // 수정 된 시간 (update)
}
