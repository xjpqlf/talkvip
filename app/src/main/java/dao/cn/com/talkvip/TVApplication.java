package dao.cn.com.talkvip;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


public class TVApplication extends Application {

    private static TVApplication instance;

    public static Context context = null;

    public static Handler handler = null;

    public static Thread mainThread = null;

    public static int mainThreadId = 0;


    public TVApplication(){instance = this;}
    public static TVApplication getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {

        super.onCreate();


        context = getApplicationContext();
        handler = new Handler();
        mainThread = Thread.currentThread();
        mainThreadId = android.os.Process.myTid();





        init();

    }

    public static Context getAppContext() {
        return context;
    }

    private void init(){
//        initLocationService();





        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);





    }











}
