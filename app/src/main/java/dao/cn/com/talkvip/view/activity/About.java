package dao.cn.com.talkvip.view.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import dao.cn.com.talkvip.Constants;
import dao.cn.com.talkvip.R;
import dao.cn.com.talkvip.TVApplication;
import dao.cn.com.talkvip.utils.DebugFlags;
import dao.cn.com.talkvip.utils.SPUtils;
import okhttp3.Call;

/**
 * @name dao.cn.com.talkvip.view.activity
 * @class name：TalkVip
 * @class describe
 * @anthor uway QQ:343251588
 * @time 2017/4/7 14:06
 * @change uway
 * @chang 2017/4/7 14:06
 * @class describe
 */

public class About extends BaseActivity {
    @Override
    protected void initHead() {
     TextView tvthem= (TextView) findViewById(R.id.tv_theme);
        tvthem.setText("关于");
        RelativeLayout iv=(RelativeLayout)findViewById(R.id.rl_back);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected int getContentView() {
        return R.layout.about_activity;
    }

    @Override
    protected void initView() {
        TextView version=(TextView)findViewById(R.id.tv_version);
         version.setText("客来V"+getVersion());
        DebugFlags.logD(getVersion());
version.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        getData(0);
    }
});

    }



    public String getVersion() {
        TVApplication context = TVApplication.getInstance();
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return  version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private void getData(int page) {
        String token= SPUtils.getString(About.this,"token","");


        OkHttpUtils.post()
                .url(Constants.BASE_URL + "/Callphone/serviceFollowUp")
                // .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "Bearer" + " " + token)
                .addParams("size", "20")
                .addParams("page", page + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                DebugFlags.logD("待跟进错误"+e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                DebugFlags.logD("待跟进测试"+response);

            }
        });

    }
}
