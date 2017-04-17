package dao.cn.com.talkvip.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dao.cn.com.talkvip.R;
import dao.cn.com.talkvip.utils.DataCleanManager;
import dao.cn.com.talkvip.utils.ToastUtil;

/**
 * @name dao.cn.com.talkvip.view.activity
 * @class name：TalkVip
 * @class describe
 * @anthor uway QQ:343251588
 * @time 2017/4/6 15:46
 * @change uway
 * @chang 2017/4/6 15:46
 * @class describe
 */

public class SettingActivity  extends BaseActivity {
    @Override
    protected void initHead() {
        TextView tvTheme= (TextView) findViewById(R.id.tv_theme);
        TextView tvEdit= (TextView) findViewById(R.id.tv_edit);
        ImageView imageView= (ImageView) findViewById(R.id.iv_back);
        tvTheme.setText("设置");
        tvEdit.setText("");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected int getContentView() {
        return R.layout.setting_activity;
    }

    @Override
    protected void initView() {

        final TextView tvahe= (TextView) findViewById(R.id.cache);
        RelativeLayout clean= (RelativeLayout) findViewById(R.id.tv_cleans);

        try {
            tvahe.setText(DataCleanManager.getTotalCacheSize(SettingActivity.this));


            clean.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DataCleanManager.clearAllCache(SettingActivity.this);
                    ToastUtil.showInCenter("清理完成");
                    tvahe.setText(0+"K");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
      RelativeLayout rl= (RelativeLayout) findViewById(R.id.rl_out);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SettingActivity.this,LoginActivity.class));
                finish();
            }
        });

    }







}
