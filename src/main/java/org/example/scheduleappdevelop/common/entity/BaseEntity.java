package org.example.scheduleappdevelop.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 상속을 통해 필드 공유
@EntityListeners(AuditingEntityListener.class) // // 이 어노테이션을 써야 자동으로 시간이 바뀐다.
public abstract class BaseEntity {

    @CreatedDate // 처음 저장될 때 생성 시각을 자동으로 저장
    @Column(updatable = false) // 값이 한 번 저장되면 수정 불가
    private LocalDateTime createdAt;

    @LastModifiedDate// 수정될 때마다 수정 시각을 자동으로 갱신
    private LocalDateTime modifiedAt;
}
