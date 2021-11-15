package com.cty.whereareyou.entity.publish;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @Author: jiangtao
 * @Date: 2021/11/15 16:06
 */
@Data
public class DetailLossInfo {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer lossId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer userId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer age;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lossTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lossLocation;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer reportPolice;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sex;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String detailCharacters;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String caseDetail;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer type;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Image> images;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Contact> contacts;
}
