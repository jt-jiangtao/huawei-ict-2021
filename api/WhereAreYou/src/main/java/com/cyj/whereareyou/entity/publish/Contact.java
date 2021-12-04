package com.cyj.whereareyou.entity.publish;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author: jiangtao
 * @Date: 2021/11/15 16:07
 */
@Data
public class Contact {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String phone;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String location;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String relation;
}
