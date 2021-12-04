package com.cyj.whereareyou.entity.publish;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author: jiangtao
 * @Date: 2021/11/15 15:19
 */
@Data
public class Image {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer lossId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String imageUrl;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer type;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer userId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private double similarity;
}
