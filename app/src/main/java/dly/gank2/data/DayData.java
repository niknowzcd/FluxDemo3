package dly.gank2.data;

/**
 * Created by 19229 on 2016/12/14.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 接口url 示例
 * http://GankData.io/api/day/2016/12/14
 */


public class DayData extends BaseData {

    public List<String> category;
    public Result results;

    public static class Result {
        @SerializedName("Android") public List<GankData> androidList;
        @SerializedName("iOS") public List<GankData> iosList;
        @SerializedName("福利") public List<GankData> welfareList;
        @SerializedName("拓展资源") public List<GankData> extraList;
        @SerializedName("前端") public List<GankData> frontEndList;
        @SerializedName("瞎推荐") public List<GankData> casualList;
        @SerializedName("App") public List<GankData> appList;
        @SerializedName("休息视频") public List<GankData> videoList;
    }
}
