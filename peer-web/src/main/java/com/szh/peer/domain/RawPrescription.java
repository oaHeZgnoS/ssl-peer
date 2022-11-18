package com.szh.peer.domain;

import com.szh.peer.commons.hibernate.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

/**
 *
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_raw_prescription", indexes = {@Index(name = "idx_raw_prescription_code", columnList = "code")})
@Where(clause = "logic_delete = 0")
@Slf4j
public class RawPrescription extends BaseEntity {

    private static final long serialVersionUID = -4176480093649949170L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", columnDefinition = "varchar(64) COMMENT '编号：mblogid'")
    private String code;

    @Column(name = "author_id", columnDefinition = "varchar(255) COMMENT '作者ID：user.idstr'")
    private String authorId;

    @Column(name = "author_name", columnDefinition = "varchar(255) COMMENT '作者名称：user.screen_name'")
    private String authorName;

    @Column(name = "publish_time", columnDefinition = "datetime COMMENT '发布时间：created_at'")
    private Date publishTime;

    @Column(name = "pics", columnDefinition = "text COMMENT '附图'")
    private String pics;

    @Column(name = "request_url", columnDefinition = "text COMMENT '分页内容请求路径'")
    private String requestUrl;

    @Column(name = "page_num", columnDefinition = "bigint(20) COMMENT '分页参数'")
    private Long pageNum;

    @Column(name = "batch_id", columnDefinition = "bigint(20) COMMENT '任务批次ID'")
    private Long batchId;

    @Column(name = "response_body", columnDefinition = "text COMMENT '分页内容响应体'")
    private String responseBody;

}
