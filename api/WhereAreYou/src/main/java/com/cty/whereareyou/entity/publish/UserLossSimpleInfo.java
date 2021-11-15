package com.cty.whereareyou.entity.publish;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @Author: jiangtao
 * @Date: 2021/11/15 16:57
 */
@Data
public class UserLossSimpleInfo {
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
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sex;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer type;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Image> images;
}
