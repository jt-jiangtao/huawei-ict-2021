package com.cyj.whereareyou.entity.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author: jiangtao
 * @Date: 2021/11/21 20:59
 */
@Data
public class ArticleNoContent {

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

}
