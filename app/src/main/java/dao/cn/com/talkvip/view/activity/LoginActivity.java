package dao.cn.com.talkvip.view.activity;

import android.content.Intent;
import android.graphics.Paint;
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
    private TextView tvTest;
    private TextView mTverropwd;
    private RelativeLayout mRl;
    private RelativeLayout mAc;
    private RelativeLayout mPwd;

    @Override
    protected void initHead() {

    }

    @Override
    protected int getContentView() {
        return R.layout.login_activity;


    }

    @Override
    protected void initView() {

        tvTest = (TextView) findViewById(R.id.tv_pro);



        tvTest.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
        tvTest.getPaint().setAntiAlias(true);//抗锯齿




        mEtac = (EditText) findViewById(R.id.tv_loginac);
        mEtpwd = (EditText) findViewById(R.id.tv_logpwd);


        mRl = (RelativeLayout) findViewById(R.id.bt_login);
        mAc = (RelativeLayout) findViewById(R.id.rl_ac);
        mPwd = (RelativeLayout) findViewById(R.id.rl_logpwd);
         mEtac.setOnFocusChangeListener(new View.OnFocusChangeListener() {
          @Override
          public void onFocusChange(View v, boolean hasFocus) {
              if (hasFocus){

                  mAc.setBackgroundResource(R.mipmap.loginbg);
              }else{
                  mAc.setBackgroundResource(R.mipmap.lnb);

              }
          }
      });
        mEtpwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){

                    mPwd.setBackgroundResource(R.mipmap.loginbg);
                }else{

                    mPwd.setBackgroundResource(R.mipmap.lnb);
                }
            }
        });


        mRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             /*   Intent intent = new Intent(LoginActivity.this, MainActivity.class);
              //  intent.putExtra("name", name);
                startActivity(intent);
                finish();
*/
                Login();
            }
        });

        tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,Protocol.class));
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
                                String phone = json1.getString("phone");
                                String email = json1.getString("email");
                                SPUtils.putString(LoginActivity.this,"phone",phone);
                                SPUtils.putString(LoginActivity.this,"email",email);
                                Constants.TOKEN = token;
                                SPUtils.putString(LoginActivity.this,"token",token);
                                SPUtils.putString(LoginActivity.this,"ac",ac);
                                SPUtils.putString(LoginActivity.this,"pwd",pwd);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("name", name);
                                startActivity(intent);
                                 finish();
                            } else if ("1001".equals(code)) {

                                ToastUtil.showInCenter("用户名有误");


                            } else if ("1002".equals(code)) {

                                ToastUtil.showInCenter("密码有误");
                            } else if ("1003".equals(code)) {


                                ToastUtil.showInCenter("账号和密码有误");

                            } else if ("1004".equals(code)) {

                                ToastUtil.showInCenter("账号不存在");

                            } else if ("1005".equals(code)) {

                                ToastUtil.showInCenter("账号已关闭");

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
