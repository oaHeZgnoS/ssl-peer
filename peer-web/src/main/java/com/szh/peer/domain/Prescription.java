package com.szh.peer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szh.peer.commons.hibernate.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_prescription", indexes = {@Index(name = "idx_prescription_code", columnList = "code")})
@Where(clause = "logic_delete = 0")
@Slf4j
public class Prescription extends BaseEntity {

    private static final long serialVersionUID = -6176480293643949171L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", columnDefinition = "varchar(64) COMMENT '编号：mblogid'")
    private String code;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "detail_content", columnDefinition = "text COMMENT '全文内容'")
    private String detailContent;

    @Column(name = "publish_time", columnDefinition = "datetime COMMENT '发布时间'")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishTime;

    @Column(name = "author_id", columnDefinition = "varchar(255) COMMENT '作者ID'")
    private String authorId;

    @Column(name = "author_name", columnDefinition = "varchar(255) COMMENT '作者名称'")
    private String authorName;

    @Column(name = "pics", columnDefinition = "text COMMENT '附图'")
    private String pics;

    @Column(name = "request_url", columnDefinition = "text COMMENT '全文内容请求路径'")
    private String requestUrl;

    @Column(name = "batch_id", columnDefinition = "bigint(20) COMMENT '任务批次ID'")
    private Long batchId;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "response_body", columnDefinition = "text COMMENT '全文内容响应体'")
    private String responseBody;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "raw_prescription_id", columnDefinition = "bigint(20) COMMENT '关联信息'")
    private RawPrescription rawPrescription;

    @Column(name = "synced", nullable = false, columnDefinition = "bit(1) DEFAULT b'0' COMMENT '是否被同步到es：1是0否'")
    private Boolean synced = false;

}
