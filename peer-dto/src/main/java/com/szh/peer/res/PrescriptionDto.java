package com.szh.peer.res;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class PrescriptionDto implements Serializable {

    private static final long serialVersionUID = 8138612353689910301L;

    @JSONField(ordinal = 1)
    private String id;

    @JSONField(ordinal = 2)
    private String code;

    @JSONField(ordinal = 3)
    private String detailContent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(ordinal = 4)
    private Date publishTime;

    @JSONField(ordinal = 5)
    private String authorId;

    @JSONField(ordinal = 6)
    private String authorName;

    @JSONField(ordinal = 7)
    private List<String> picUrls;

    @JSONField(ordinal = 8)
    private String requestUrl;

    @JSONField(ordinal = 9)
    private Long batchId;

    @JSONField(ordinal = 10)
    private String highlightContent;
}
