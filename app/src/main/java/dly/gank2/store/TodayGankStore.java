package dly.gank2.store;
/*
 * Copyright (C) 2016 Johnny Shieh Open Source Project
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


import java.util.List;

import javax.inject.Inject;

import dly.gank2.action.ActionType;
import dly.gank2.action.Key;
import dly.gank2.action.RxAction;
import dly.gank2.action.RxStoreChange;
import dly.gank2.data.ui.GankItem;
import dly.gank2.dispatcher.Dispatcher;

/**
 * description
 *
 * @author Johnny Shieh (JohnnyShieh17@gmail.com)
 * @version 1.0
 */
public class TodayGankStore extends RxStore {

    //设置ID，分类用
    public static final String ID = "TodayGankStore";

    //保存数据用
    private List<GankItem> mItems;

    @Inject
    public TodayGankStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case ActionType.GET_TODAY_GANK:
                mItems = action.get(Key.DAY_GANK);
                break;
            default:
                return;
        }
        //数据变更，发出对应的Action，通知view刷新
        postChange(new RxStoreChange(ID, action));
    }

    public List<GankItem> getItems() {
        return mItems;
    }
}
