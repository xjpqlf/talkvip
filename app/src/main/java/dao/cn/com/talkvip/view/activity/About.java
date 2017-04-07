package dao.cn.com.talkvip.view.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dao.cn.com.talkvip.R;

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
        ImageView iv=(ImageView)findViewById(R.id.iv_back);
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

    }
}
