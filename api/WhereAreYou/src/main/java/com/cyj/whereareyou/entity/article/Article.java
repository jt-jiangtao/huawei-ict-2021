package com.cyj.whereareyou.entity.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author: jiangtao
 * @Date: 2021/11/4 20:24
 */
@Data
public class Article {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer sortId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String author;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String headPicture;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String content;
}
