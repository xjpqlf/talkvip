package dao.cn.com.talkvip.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import dao.cn.com.talkvip.Constants;
import dao.cn.com.talkvip.R;
import dao.cn.com.talkvip.utils.DataCleanManager;
import dao.cn.com.talkvip.utils.SPUtils;
import dao.cn.com.talkvip.utils.ToastUtil;
import okhttp3.Call;

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
       RelativeLayout imageView= (RelativeLayout) findViewById(R.id.rl_back);
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
        RelativeLayout about= (RelativeLayout) findViewById(R.id.rl_about);
        RelativeLayout coument= (RelativeLayout) findViewById(R.id.rl_coument);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this,About.class));

            }
        });

        coument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ToastUtil.showInCenter("评价我们");

            }
        });


        try {
            tvahe.setText(DataCleanManager.getTotalCacheSize(SettingActivity.this));


            clean.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DataCleanManager.clearAllCache(SettingActivity.this);
                    ToastUtil.showInCenter("清理完成");
                    tvahe.setText(0+"k");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
      RelativeLayout rl= (RelativeLayout) findViewById(R.id.rl_out);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginout();











            }
        });

    }

    private void loginout() {
        String token= SPUtils.getString(SettingActivity.this,"token","");

        OkHttpUtils.post().url(Constants.BASE_URL+"/login/loginOut")
                .addParams("token",token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        try {
                            JSONObject json=new JSONObject(response);

                            String code=json.getString("code");

                            if ("8888".equals(code)){
                                Intent intent=new Intent(SettingActivity.this,LoginActivity.class);
                                intent. setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();


                            }else
                            {

                                ToastUtil.showInCenter("退出失败");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }


}
