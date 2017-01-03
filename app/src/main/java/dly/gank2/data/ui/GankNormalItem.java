package dly.gank2.data.ui;
/*
 * Copyright (C) 2015 Johnny Shieh Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.ArrayList;
import java.util.List;

import dly.gank2.data.GankData;

/**
 * description
 *
 * @author Johnny Shieh
 * @version 1.0
 */
public class GankNormalItem extends GankData implements GankItem {

    public int page = -1;

    public static GankNormalItem newGankBean(GankData gank) {
        GankNormalItem gankBean = new GankNormalItem();
        gankBean._id = gank._id;
        gankBean.createdAt = gank.createdAt;
        gankBean.desc = gank.desc;
        gankBean.publishedAt = gank.publishedAt;
        gankBean.source = gank.source;
        gankBean.type = gank.type;
        gankBean.url = gank.url;
        gankBean.who = gank.who;
        gankBean.used = gank.used;
        return gankBean;
    }

    public static List<GankNormalItem> newGankList(List<GankData> gankList) {
        if (null == gankList || gankList.size() == 0) {
            return null;
        }
        List<GankNormalItem> itemList = new ArrayList<>(gankList.size());
        for (GankData gank : gankList) {
            itemList.add(newGankBean(gank));
        }
        return itemList;
    }

    public static List<GankNormalItem> newGankList(List<GankData> gankList, int pageIndex) {
        if (null == gankList || gankList.size() == 0) {
            return null;
        }
        List<GankNormalItem> itemList = new ArrayList<>(gankList.size());
        for (GankData gank : gankList) {
            GankNormalItem item = newGankBean(gank);
            item.page = pageIndex;
            itemList.add(item);
        }
        return itemList;
    }
}
