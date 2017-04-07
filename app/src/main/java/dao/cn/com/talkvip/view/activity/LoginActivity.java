package dao.cn.com.talkvip.view.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import dao.cn.com.talkvip.Constants;
import dao.cn.com.talkvip.R;
import dao.cn.com.talkvip.utils.DebugFlags;
import dao.cn.com.talkvip.utils.RsaU;
import dao.cn.com.talkvip.utils.SPUtils;
import dao.cn.com.talkvip.utils.ToastUtil;
import okhttp3.Call;

/**
 * @name dao.cn.com.talkvip.view.activity
 * @class name：TalkVip
 * @class describe
 * @anthor uway QQ:343251588
 * @time 2017/4/6 16:55
 * @change uway
 * @chang 2017/4/6 16:55
 * @class describe
 */

public class LoginActivity extends BaseActivity {


    private EditText mEtac;
    private EditText mEtpwd;
    private TextView mTverroac;
    private TextView mTverropwd;
    private RelativeLayout mRl;

    @Override
    protected void initHead() {

    }

    @Override
    protected int getContentView() {
        return R.layout.login_activity;
    }

    @Override
    protected void initView() {
        mEtac = (EditText) findViewById(R.id.tv_loginac);
        mEtpwd = (EditText) findViewById(R.id.tv_logpwd);

        mTverroac = (TextView) findViewById(R.id.tv_erroac);
        mTverropwd = (TextView) findViewById(R.id.tv_erropwd);
        mRl = (RelativeLayout) findViewById(R.id.bt_login);
        mRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });


    }


    private void Login() {

        final String ac = mEtac.getText().toString();
        final String pwd = mEtpwd.getText().toString();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        String sign = ac + "|" + pwd + "|" + str;
        String strs = RsaU.encryptByPublic(sign);


        if (TextUtils.isEmpty(ac) || TextUtils.isEmpty(pwd)) {

            ToastUtil.showInCenter("密码账号不能为空");
            return;
        }

        OkHttpUtils.post()
                .url(Constants.BASE_URL + "/login/loginOn")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")

                .addParams("sign", strs)

                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        DebugFlags.logD("失败" + e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        DebugFlags.logD("登录" + response);

                        try {
                            JSONObject json = new JSONObject(response);

                            String result = json.getString("result");
                            String code = json.getString("code");
                            String data = json.getString("data");


                            if ("8888".equals(code)) {
                                JSONObject json1 = new JSONObject(data);

                                String token = json1.getString("token");
                                String name = json1.getString("realname");
                                Constants.TOKEN = token;
                                SPUtils.putString(LoginActivity.this,"ac",ac);
                                SPUtils.putString(LoginActivity.this,"pwd",pwd);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("name", name);
                                startActivity(intent);

                            } else if ("1001".equals(code)) {
                                mTverropwd.setText("");
                                mTverroac.setText("用户名有误");


                            } else if ("1002".equals(code)) {
                                mTverroac.setText("");
                                mTverropwd.setText("密码有误");
                            } else if ("1003".equals(code)) {

                                mTverropwd.setText("");
                                mTverroac.setText("账号和密码有误");

                            } else if ("1004".equals(code)) {
                                mTverropwd.setText("");
                                mTverroac.setText("账号不存在");

                            } else if ("1005".equals(code)) {
                                mTverropwd.setText("");
                                mTverroac.setText("账号已关闭");

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });


    }
}