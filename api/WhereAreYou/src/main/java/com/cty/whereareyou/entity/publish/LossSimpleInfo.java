package com.cty.whereareyou.entity.publish;

import com.cty.whereareyou.entity.article.FrontArticlesList;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jiangtao
 * @Date: 2021/11/15 15:02
 */
@Data
public class LossSimpleInfo {
    private List<Item> items = new ArrayList<>();

    public LossSimpleInfo(List<DatabaseItem> databaseItems) {
        for (int i = 0; i < databaseItems.size(); i++) {
            DatabaseItem item = databaseItems.get(i);
            if (item == null) continue;
            addDataBaseItemToFinalItem(item);
        }
    }

    private void addDataBaseItemToFinalItem(DatabaseItem item) {
        int sortIndex = sortIndex(item);
        if (sortIndex == -1){
            items.add(new Item(item.lossId, item.age, item.lossTime, item.lossLocation, item.name, item.sex, item.type, item.imageUrl));
        }else {
            items.get(sortIndex).images.add(item.imageUrl);
        }
    }

    private int sortIndex(DatabaseItem item) {
        int flag = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getLossId().equals(item.getLossId())) {
                flag = i;
                break;
            }
        }
        return flag;
    }

    @Data
    public static class Item{
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer lossId;

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
        private List<String> images = new ArrayList<>();

        public Item(Integer lossId, Integer age, String lossTime, String lossLocation, String name, String sex, Integer type, String imageUrl) {
            this.lossId = lossId;
            this.age = age;
            this.lossTime = lossTime;
            this.lossLocation = lossLocation;
            this.name = name;
            this.sex = sex;
            this.type = type;
            this.images.add(imageUrl);
        }

        public Item() {
        }
    }

    @Data
    public static class DatabaseItem{
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer lossId;

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
        private String imageUrl;
    }
}
