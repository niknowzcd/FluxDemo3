package dly.gank2.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import dly.gank2.data.DateData;
import dly.gank2.data.DayData;
import dly.gank2.utils.AppUtil;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by 19229 on 2016/12/14.
 */
public interface HttpService {

    String BASE_URL = "http://gank.io/api/";

    String DATE_HISTORY = "day/history";

    //获取gank的历史数据
    @GET(DATE_HISTORY)
    Observable<DateData> getDateHistory();

    //获取某一天的数据
    @GET("day/{year}/{month}/{day}")
    Observable<DayData> getDayGank(@Path("year") int year, @Path("month") int month, @Path("day") int day);


    class Factory {
        private static OkHttpClient mOkHttpClient;

        private static final int CACHE_MAX_TIME = 12 * 60 * 60;
        private static final String DATE_PATTERN1 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS";
        private static final String DATE_PATTERN2 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        static {
            //设置缓存策略
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Response response = chain.proceed(request);
                    if (request.url().toString().startsWith(BASE_URL)) {
                        int maxTime = CACHE_MAX_TIME;
                        Date receiveDate = response.headers().getDate("Date");
                        if (null != receiveDate) {
                            //设置缓存的到期时候
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(receiveDate);
                            int hour = calendar.get(Calendar.HOUR_OF_DAY);
                            int min = calendar.get(Calendar.MINUTE);

                            maxTime = 24 * 3600 - hour * 3600 - min * 60;
                        }
                        return response.newBuilder()
                                .header("Cache-Control", "max-age=" + maxTime)
                                .build();
                    }
                    return response;
                }
            };

            File cacheDir = new File(AppUtil.getCacheDir(), "http_reponse");
            mOkHttpClient = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true) //连接失败是否重连
                    .connectTimeout(15, TimeUnit.SECONDS) //超时时间15s
                    .addNetworkInterceptor(interceptor) //把定义好的拦截器加入到okhttp
                    .cache(new Cache(cacheDir, 10 * 1024 * 1024))
                    .build();
        }

        private static final Gson dateGson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer(DATE_PATTERN1, DATE_PATTERN2))  //定义json的解析样本
                .serializeNulls()
                .create();

        private static final HttpService mGankService = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(dateGson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())    //允许以 Observable 形式返回
                .build()
                .create(HttpService.class);

        public static HttpService getGankService() {
            return Factory.mGankService;
        }

    }
}
