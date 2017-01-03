package dly.gank2.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dly.gank2.data.DateData;
import dly.gank2.data.DayData;
import dly.gank2.data.GankType;
import dly.gank2.data.ui.GankHeaderItem;
import dly.gank2.data.ui.GankItem;
import dly.gank2.data.ui.GankNormalItem;
import dly.gank2.data.ui.GankTopImageItem;
import dly.gank2.dispatcher.Dispatcher;
import dly.gank2.http.HttpService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 19229 on 2016/12/14.
 */
public class TodayGankActionCreator extends RxActionCreator{

    @Inject
    public TodayGankActionCreator(Dispatcher dispatcher, SubscriptionManager manager) {
        super(dispatcher, manager);
    }


    //定义数据转化模板
    private static SimpleDateFormat sDataFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    public void getTodayGank() {
        //定义rxAction,并给予标识
        final RxAction rxAction=newRxAction(ActionType.GET_TODAY_GANK);

        //RxJava处理数据
        HttpService.Factory.getGankService()
                .getDateHistory()
                .subscribeOn(Schedulers.io())
                .filter(new Func1<DateData, Boolean>() {
                    @Override
                    public Boolean call(DateData dateData) {
                        return (null != dateData && null != dateData.results && dateData.results.size() > 0);//接口请求成功，这边返回true
                    }
                })
                .map(new Func1<DateData, Calendar>() {
                    @Override
                    public Calendar call(DateData dateData) {
                        Calendar calendar = Calendar.getInstance(Locale.CHINA);
                        try {
                            calendar.setTime(sDataFormat.parse(dateData.results.get(0)));  //设置时间为最新一天，一般是今天
                        } catch (ParseException e) {
                            e.printStackTrace();
                            calendar = null;
                        }
                        return calendar;
                    }
                })
                .flatMap(new Func1<Calendar, Observable<DayData>>() {
                    @Override
                    public Observable<DayData> call(Calendar calendar) {
                        return HttpService.Factory.getGankService()        //再次请求数据，获取当天的数据
                                .getDayGank(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
                    }
                })
                .map(new Func1<DayData, List<GankItem>>() {
                    @Override
                    public List<GankItem> call(DayData dayData) {
                        return getGankList(dayData);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<GankItem>>() {
                    @Override
                    public void call(List<GankItem> gankItems) {
                        rxAction.getData().put(Key.DAY_GANK,gankItems);
                        postRxAction(rxAction);
                        //dataChangeListener.postChange(gankItems);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        //数据处理过程中报错时调用

                    }
                });

    }

    private List<GankItem> getGankList(DayData dayData) {
        if (dayData == null || dayData.results == null) {
            return null;
        }
        List<GankItem> gankList = new ArrayList<>();
        if (null != dayData.results.welfareList && dayData.results.welfareList.size() > 0) {
            gankList.add(GankTopImageItem.newImageItem(dayData.results.welfareList.get(0)));
        }
        if (null != dayData.results.androidList && dayData.results.androidList.size() > 0) {
            gankList.add(new GankHeaderItem(GankType.ANDROID));
            gankList.addAll(GankNormalItem.newGankList(dayData.results.androidList));
        }
        if (null != dayData.results.iosList && dayData.results.iosList.size() > 0) {
            gankList.add(new GankHeaderItem(GankType.IOS));
            gankList.addAll(GankNormalItem.newGankList(dayData.results.iosList));
        }
        if (null != dayData.results.frontEndList && dayData.results.frontEndList.size() > 0) {
            gankList.add(new GankHeaderItem(GankType.FRONTEND));
            gankList.addAll(GankNormalItem.newGankList(dayData.results.frontEndList));
        }
        if (null != dayData.results.extraList && dayData.results.extraList.size() > 0) {
            gankList.add(new GankHeaderItem(GankType.EXTRA));
            gankList.addAll(GankNormalItem.newGankList(dayData.results.extraList));
        }
        if (null != dayData.results.casualList && dayData.results.casualList.size() > 0) {
            gankList.add(new GankHeaderItem(GankType.CASUAL));
            gankList.addAll(GankNormalItem.newGankList(dayData.results.casualList));
        }
        if (null != dayData.results.appList && dayData.results.appList.size() > 0) {
            gankList.add(new GankHeaderItem(GankType.APP));
            gankList.addAll(GankNormalItem.newGankList(dayData.results.appList));
        }
        if (null != dayData.results.videoList && dayData.results.videoList.size() > 0) {
            gankList.add(new GankHeaderItem(GankType.VIDEO));
            gankList.addAll(GankNormalItem.newGankList(dayData.results.videoList));
        }

        return gankList;
    }

}
