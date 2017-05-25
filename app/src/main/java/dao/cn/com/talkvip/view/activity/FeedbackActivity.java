package dao.cn.com.talkvip.view.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import dao.cn.com.talkvip.Constants;
import dao.cn.com.talkvip.R;
import dao.cn.com.talkvip.utils.DebugFlags;
import dao.cn.com.talkvip.utils.SPUtils;
import dao.cn.com.talkvip.utils.ToastUtil;
import okhttp3.Call;

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


    private TextView mTvEdit;
    private String ets;
    private EditText et;

    @Override
    protected int getContentView() {
        ButterKnife.bind(this);
        return R.layout.feedback_activity;
    }

    @Override
    protected void initHead() {
        TextView tvTheme = (TextView) findViewById(R.id.tv_theme);


        tvTheme.setText("问题反馈");

    }

    @Override
    protected void initView() {
       RelativeLayout iv = (  RelativeLayout) findViewById(R.id.rl_back);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et = (EditText) findViewById(R.id.et_feedback);
        mTvEdit = (TextView) findViewById(R.id.tv_edit);
        mTvEdit.setText("发送");
        ets = et.getText().toString();
        mTvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et.getText().toString())) {
                    ToastUtil.showInCenter(et.getText().toString());
                    return;
                } else if (et.getText().toString().length() > 500) {

                    ToastUtil.showInCenter("字数不能多于500字");
                    return;
                } else {

                    sendfeedback(et.getText().toString());
                }
            }
        });


    }

    private void sendfeedback(String string) {
        String token= SPUtils.getString(FeedbackActivity.this,"token","");
        OkHttpUtils.post().url(Constants.BASE_URL + "/Comment/addFeedBack")
                .addHeader("Authorization", "Bearer" + " " + token)
                .addParams("text", string)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                DebugFlags.logD("反馈" + response);

                try {
                    JSONObject json = new JSONObject(response);
                    String result=json.getString("result");

                    if (result.equals("success")){

                        ToastUtil.showInCenter("发送成功");
                        finish();
                    }else{
                        ToastUtil.showInCenter("发送失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }


}
