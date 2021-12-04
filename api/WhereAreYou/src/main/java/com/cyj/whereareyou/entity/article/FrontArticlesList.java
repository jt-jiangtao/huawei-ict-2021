package com.cyj.whereareyou.entity.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jiangtao
 * @Date: 2021/11/6 13:48
 */
// 前端文章列表对象
@Data
public class FrontArticlesList {
    public List<Items> items = new ArrayList<>();

    public FrontArticlesList(List<SelectItem> items) {
        items.forEach(item -> {
            addSelectItem(item);
        });
    }

    private void addSelectItem(SelectItem item) {
        int sortIndex = sortIndex(item);
        if (sortIndex == -1){
            items.add(new Items(item.sortId, item.sortTitle, new Item(item.articleId, item.articleTitle, item.author, item.headPicture)));
        }else {
            items.get(sortIndex).items.add(new Item(item.articleId, item.articleTitle, item.author, item.headPicture));
        }
    }

    private int sortIndex(SelectItem item) {
        int flag = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).sortId.equals(item.sortId)) {
                flag = i;
                break;
            }
        }
        return flag;
    }

    // 一个分类对象
    @Data
    public static class Items{
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer sortId;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String sortTitle;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private List<Item> items = new ArrayList<>();

        public Items(Integer sortId, String sortTitle, Item item) {
            this.sortId = sortId;
            this.sortTitle = sortTitle;
            this.items.add(item);
        }
    }

    // 一个文章简介对象
    @Data
    public static class Item{
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer articleId;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String articleTitle;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String author;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String headPicture;

        public Item(Integer articleId, String articleTitle, String author, String headPicture) {
            this.articleId = articleId;
            this.articleTitle = articleTitle;
            this.author = author;
            this.headPicture = headPicture;
        }
    }

    // 查询到的对象
    @Data
    public static class SelectItem{
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer sortId;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer articleId;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String sortTitle;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String articleTitle;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String author;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String headPicture;
    }
}
