package dao.cn.com.talkvip.view.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dao.cn.com.talkvip.R;
import dao.cn.com.talkvip.TVApplication;

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
}
