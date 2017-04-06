package dao.cn.com.talkvip.view.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import dao.cn.com.talkvip.R;

/**
 * @name dao.cn.com.talkvip.view.activity
 * @class name：TalkVip
 * @class describe
 * @anthor uway QQ:343251588
 * @time 2017/4/6 14:21
 * @change uway
 * @chang 2017/4/6 14:21
 * @class describe
 */

public class FeedbackActivity extends BaseActivity {




    @Override
    protected int getContentView() {
        ButterKnife.bind(this);
        return R.layout.feedback_activity;
    }
    @Override
    protected void initHead() {
    TextView tvTheme= (TextView) findViewById(R.id.tv_theme);
    TextView tvEdit= (TextView) findViewById(R.id.tv_edit);


        tvTheme.setText("问题反馈");
        tvEdit.setText("发送");
    }
    @Override
    protected void initView() {
        ImageView iv= (ImageView) findViewById(R.id.iv_back);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }






}
