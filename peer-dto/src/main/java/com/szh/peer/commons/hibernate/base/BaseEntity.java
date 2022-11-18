package com.szh.peer.commons.hibernate.base;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Where(clause = "logic_delete = 0")
@Setter
@Getter
public abstract class BaseEntity implements Serializable {

    @CreatedDate
    @Column(name = "create_time", nullable = false, columnDefinition = "datetime COMMENT '创建时间'")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createTime = new Date();

    @LastModifiedDate
    @Column(name = "update_time", nullable = false, columnDefinition = "datetime COMMENT '更新时间'")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date updateTime = new Date();

    @Version
    @Column(name = "version", nullable = false, columnDefinition = "bigint DEFAULT 0 COMMENT '版本号'")
    protected Long version;

    @Column(name = "logic_delete", nullable = false, columnDefinition = "bit(1) DEFAULT b'0' COMMENT '是否逻辑删除：1是0否'")
    protected Boolean logicDelete = false;

    @PreUpdate
    public void preUpdate() {
        updateTime = new Date();
    }

}