package com.cty.whereareyou.entity.publish;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author: jiangtao
 * @Date: 2021/11/14 23:11
 */
@Data
public class SimpleInfo {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer findChildrenNumber;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer findParentNumber;
}
