package com.awse.commerce.domains.util.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
@Getter
public class BaseEntity {

    @CreatedDate
    @Column(nullable = false, name = "regdate")
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(nullable = false, name = "moddate")
    private LocalDateTime modDate;

}
