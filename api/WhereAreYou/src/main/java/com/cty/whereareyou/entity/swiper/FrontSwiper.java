package com.cty.whereareyou.entity.swiper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author: jiangtao
 * @Date: 2021/11/6 16:03
 */
@Data
public class FrontSwiper {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String router;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String picture;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
}
