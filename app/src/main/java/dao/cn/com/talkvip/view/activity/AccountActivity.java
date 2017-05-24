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
 * @time 2017/4/6 13:53
 * @change uway
 * @chang 2017/4/6 13:53
 * @class describe
 */

public class AccountActivity extends BaseActivity {


    private TextView mTvEdit;
    private TextView mSave;
    private EditText mEmil;
    private EditText mPhone;
    private TextView mPhonetext;
    private TextView mEmiltext;

    @Override
    protected int getContentView() {

        return R.layout.account_activity;
    }

    @Override
    protected void initHead() {


        mTvEdit = (TextView) findViewById(R.id.tv_edit);
        mSave = (TextView) findViewById(R.id.tv_save);




        mTvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvEdit.setVisibility(View.GONE);
                mSave.setVisibility(View.VISIBLE);
                mEmil.setFocusable(true);
                mEmil.setFocusableInTouchMode(true);
                mPhone.setFocusable(true);
                mPhone.setFocusableInTouchMode(true);
                mEmil.setTextColor(getResources().getColor(R.color.black));
                mPhone.setTextColor(getResources().getColor(R.color.black));




            }
        });

mSave.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        SaveAcInfo( mEmil.getText().toString(), mPhone.getText().toString());











    }
});

    }

    private void SaveAcInfo(final String s, final String s1) {
        String token=SPUtils.getString(AccountActivity.this,"token","");
        OkHttpUtils.post().url(Constants.BASE_URL+"/Comment/updateAccountInformation")
                .addHeader("Authorization", "Bearer" + " " + token)
                .addParams("phone",s1)
                .addParams("email",s)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                DebugFlags.logD("邮箱电话"+response);
                try {
                    JSONObject j=new JSONObject(response);
                    String code=j.getString("code");

                    if ("1015".equals(code)) {

                        SPUtils.putString(AccountActivity.this,"phone",s1);
                        SPUtils.putString(AccountActivity.this,"email",s);
                        mTvEdit.setVisibility(View.VISIBLE);
                        mSave.setVisibility(View.GONE);
                        mEmil.setFocusable(false);
                        mEmil.setFocusableInTouchMode(false);
                        mPhone.setFocusable(false);
                        mPhone.setFocusableInTouchMode(false);

                        mEmil.setTextColor(getResources().getColor(R.color.item_dc));
                        mPhone.setTextColor(getResources().getColor(R.color.item_dc));
                        ToastUtil.showInCenter("保存成功");



                    }else if ("1013".equals(code)){

                        ToastUtil.showInCenter("邮箱格式错误");
                    }else if ("1014".equals(code)){

                        ToastUtil.showInCenter("电话格式错误");
                    }else{
                        ToastUtil.showInCenter("保存失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });




    }

    @Override
    protected void initView() {
       RelativeLayout iv= ( RelativeLayout) findViewById(R.id.rl_acback);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mEmil = (EditText)findViewById(R.id.tv_email);
        mPhone = (EditText)findViewById(R.id.tv_phone);
        String phone= SPUtils.getString(AccountActivity.this,"phone","");
        String email= SPUtils.getString(AccountActivity.this,"email","");
        if (!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(email)){
            mPhone.setText(phone);
            mEmil.setText(email);


        }

        mEmil.setFocusable(false);
        mEmil.setFocusableInTouchMode(false);
        mPhone.setFocusable(false);
        mPhone.setFocusableInTouchMode(false);



    }




}
