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
 * @time 2017/4/6 13:53
 * @change uway
 * @chang 2017/4/6 13:53
 * @class describe
 */

public class AccountActivity extends BaseActivity {




    @Override
    protected int getContentView() {

        return R.layout.account_activity;
    }

    @Override
    protected void initHead() {

        TextView tvTheme= (TextView) findViewById(R.id.tv_theme);
        TextView tvEdit= (TextView) findViewById(R.id.tv_edit);

        tvEdit.setText("编辑");
        tvTheme.setText("账户信息");
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
