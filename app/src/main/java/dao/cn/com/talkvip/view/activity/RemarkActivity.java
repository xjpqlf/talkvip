package dao.cn.com.talkvip.view.activity;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import dao.cn.com.talkvip.Constants;
import dao.cn.com.talkvip.R;
import dao.cn.com.talkvip.bean.Custom;
import dao.cn.com.talkvip.bean.Infos;
import dao.cn.com.talkvip.utils.DebugFlags;
import dao.cn.com.talkvip.utils.Rsa;
import dao.cn.com.talkvip.utils.SPUtils;
import dao.cn.com.talkvip.utils.ToastUtil;
import okhttp3.Call;

/**
 * @name dao.cn.com.talkvip.view.activity
 * @class name：TalkVip
 * @class describe
 * @anthor uway QQ:343251588
 * @time 2017/5/9 10:29
 * @change uway
 * @chang 2017/5/9 10:29
 * @class describe
 */

public class RemarkActivity extends BaseActivity implements View.OnClickListener {


    private String status="0";
    private TextView dgx;
    private TextView tq;
    private TextView wyy;
    private TextView wjt;
    private TextView zd;
    private TextView bc;
    private EditText mEt;
    private Custom mCustom;
    private Infos mInfos;
    private CheckBox mCheckBox;
    private int mPostion;
    private RelativeLayout mRl;
    private  PhoneBroadcastReceivers mycodereceivers;
    private TextView mName;
    private TextView mMobile;
    private TextView mMs;

    @Override
    protected void initHead() {

    }

    @Override
    protected int getContentView() {
        return R.layout.remark;
    }

    @Override
    protected void initView() {

        mycodereceivers = new  PhoneBroadcastReceivers();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PHONE_STATE");
        filter.addAction("android.intent.action.NEW_OUTGOING_CALL");
         registerReceiver(mycodereceivers, filter);


        if (getIntent() != null) {
            Intent in = getIntent();
            mInfos = (Infos) in.getSerializableExtra("list");
            mCustom = (Custom) in.getSerializableExtra("p");
            mPostion = in.getIntExtra("postion",0);

       //     Log.v("电话集合", "" + mInfos.getMsg().toString()+ mCustom.toString());

            mName = (TextView) findViewById(R.id.tv_id);
            mMobile = (TextView) findViewById(R.id.detail_phone);
            mMs = (TextView) findViewById(R.id.tv_ms);
            mRl = (RelativeLayout) findViewById(R.id.rl_bjback);
            mCheckBox = (CheckBox) findViewById(R.id.iv_xuan);
            mEt = (EditText) findViewById(R.id.tv_bjhint);
            mCheckBox.setChecked(true);
            mName.setText(mCustom.getId());
            mMobile.setText(mCustom.getMobile());
             mMs.setText(mCustom.getName());

            dgx = (TextView) findViewById(R.id.dgx);
            wjt= (TextView) findViewById(R.id.wjt);
            wyy= (TextView) findViewById(R.id.wyy);
            tq= (TextView) findViewById(R.id.tq);
           zd= (TextView) findViewById(R.id.zd);

             bc= (TextView) findViewById(R.id.tv_bc);
            dgx.setOnClickListener(this);
            wjt.setOnClickListener(this);
            wyy.setOnClickListener(this);
            tq.setOnClickListener(this);
           zd.setOnClickListener(this);
            bc.setOnClickListener(this);
            mRl.setOnClickListener(this);


        }





    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.tq:
            status="5";
                tq.setTextColor(getResources().getColor(R.color.cacecorlor));
                dgx.setTextColor(getResources().getColor(R.color.grey_word1));
                wyy.setTextColor(getResources().getColor(R.color.grey_word1));
                zd.setTextColor(getResources().getColor(R.color.grey_word1));
                wjt.setTextColor(getResources().getColor(R.color.grey_word1));
               tq.setBackgroundDrawable(getResources().getDrawable(R.mipmap.popbg));
                dgx.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                wyy.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                zd.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                wjt.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));




                break;

            case R.id.zd:
                status="1";
                zd.setTextColor(getResources().getColor(R.color.cacecorlor));
                dgx.setTextColor(getResources().getColor(R.color.grey_word1));
                wyy.setTextColor(getResources().getColor(R.color.grey_word1));
                tq.setTextColor(getResources().getColor(R.color.grey_word1));
                wjt.setTextColor(getResources().getColor(R.color.grey_word1));
                zd.setBackgroundDrawable(getResources().getDrawable(R.mipmap.popbg));
                dgx.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                wyy.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                tq.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                wjt.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));




                break;
            case R.id.wyy:
                status="2";
                wyy.setTextColor(getResources().getColor(R.color.cacecorlor));
                dgx.setTextColor(getResources().getColor(R.color.grey_word1));
                tq.setTextColor(getResources().getColor(R.color.grey_word1));
                zd.setTextColor(getResources().getColor(R.color.grey_word1));
                wjt.setTextColor(getResources().getColor(R.color.grey_word1));
                wyy.setBackgroundDrawable(getResources().getDrawable(R.mipmap.popbg));
                dgx.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                tq.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                zd.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                wjt.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));




                break;
            case R.id.dgx:
                status="3";
                dgx.setTextColor(getResources().getColor(R.color.cacecorlor));
                tq.setTextColor(getResources().getColor(R.color.grey_word1));
                wyy.setTextColor(getResources().getColor(R.color.grey_word1));
                zd.setTextColor(getResources().getColor(R.color.grey_word1));
                wjt.setTextColor(getResources().getColor(R.color.grey_word1));
                dgx.setBackgroundDrawable(getResources().getDrawable(R.mipmap.popbg));
                tq.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                wyy.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                zd.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                wjt.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));




                break;
            case R.id.wjt:
                status="4";
                wjt.setTextColor(getResources().getColor(R.color.cacecorlor));
                dgx.setTextColor(getResources().getColor(R.color.grey_word1));
                wyy.setTextColor(getResources().getColor(R.color.grey_word1));
                zd.setTextColor(getResources().getColor(R.color.grey_word1));
                tq.setTextColor(getResources().getColor(R.color.grey_word1));
                wjt.setBackgroundDrawable(getResources().getDrawable(R.mipmap.popbg));
                dgx.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                wyy.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                zd.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                tq.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));




                break;
            case R.id.tv_bc:

                if (!"0".equals(status)&&!TextUtils.isEmpty(mEt.getText().toString())){



                    save();



                    //产生日志








                }else{
                    ToastUtil.showInCenter("保存内容和类型不能为空");

                }

                break;
            case  R.id.rl_bjback:

                finish();

                break;

        }

    }

    private void save() {
String token=SPUtils.getString(RemarkActivity.this,"token","");
        DebugFlags.logD("备注id"+mInfos.getMsg().get(mPostion).getId()+"=="+status);
        OkHttpUtils.post().url(Constants.BASE_URL+"/Comment/phoneNotes")
                .addHeader("Authorization", "Bearer" + " " + token)
                .addParams("id",mInfos.getMsg().get(mPostion).getId())
                .addParams("status",status)
                .addParams("note",mEt.getText().toString())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {


            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    DebugFlags.logD("保存备注"+response);
                    JSONObject jsonObject=new JSONObject(response);
                    String result= jsonObject.getString("result");
                    if ("success".equals(result)){

                       ToastUtil.showInCenter("保存成功");

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmsss");
                        Date curDate = new Date(System.currentTimeMillis());
                        String strs = formatter.format(curDate);
                        Random rand = new Random();
                        int i = rand.nextInt(100000);
                        String order=strs+"1"+i;

                        if (mCheckBox.isChecked()){

                            mPostion++;
                            creatLog(mInfos.getMsg().get(mPostion).getId(),order);
                            DebugFlags.logD("连续拨打日志id"+mInfos.getMsg().get(mPostion).getId());
                            getPhoneNum(mInfos.getMsg().get(mPostion).getMobile(),order);
                            CallPhone(mInfos.getMsg().get(mPostion).getId());



                        }



                    }else{

                        ToastUtil.showInCenter("保存失败");
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void CallPhone(String phone) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        //url:统一资源定位符
        //uri:统一资源标示符（更广）
        intent.setData(Uri.parse("tel:" + phone));
        //开启系统拨号器
     startActivity(intent);


    }

    public class PhoneBroadcastReceivers extends BroadcastReceiver {


        String TAG = "打电话";
        TelephonyManager telMgr;

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
                Log.e("hg", "呼出……OUTING");
            }
            if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
                TelephonyManager tm = (TelephonyManager) context
                        .getSystemService(Service.TELEPHONY_SERVICE);
                switch (tm.getCallState()) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        Log.e("响铃", "电话状态……RINGING");
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        Log.e("挂断", "电话状态……OFFHOOK");
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        Log.e("空闲", "电话状态……IDLE");

                        mCheckBox.setChecked(true);
                        mName.setText(mInfos.getMsg().get(mPostion).getId());
                        mMobile.setText(mInfos.getMsg().get(mPostion).getMobile());
                        mMs.setText(mInfos.getMsg().get(mPostion).getName());
                        mEt.setText("");
                        wjt.setTextColor(getResources().getColor(R.color.grey_word1));
                        dgx.setTextColor(getResources().getColor(R.color.grey_word1));
                        wyy.setTextColor(getResources().getColor(R.color.grey_word1));
                        zd.setTextColor(getResources().getColor(R.color.grey_word1));
                        tq.setTextColor(getResources().getColor(R.color.grey_word1));
                        wjt.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                        dgx.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                        wyy.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                        zd.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                        tq.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));

                        break;
                }
            }
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mycodereceivers);
    }

    private void creatLog(String id, final String order) {

        String token= SPUtils.getString(RemarkActivity.this,"token","");

        DebugFlags.logD("测试"+token+id);
        OkHttpUtils.post()
                .url(Constants.BASE_URL + "/Comment/insertLog")
                // .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "Bearer" + " " + token)
                .addParams("id", id)
                .addParams("order",order)

                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                DebugFlags.logD("日志"+response+"++"+order);

            }
        });




    }

    private void getPhoneNum(String phone,String order) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        Random rand = new Random();
        int i = rand.nextInt(100);

        int mA = rand.nextInt(10000000);

        String accountId = "1";
        String timeStamp = i + str;

        String sign = accountId + timeStamp + order;


        String mSigns = Rsa.encryptByPublic(sign);
        Log.d("时间戳", mSigns);
        OkHttpUtils.post()
                .url("http://c1.dev.talkvip.cn/Authorization")
                .addParams("accuntID", accountId)
                .addParams("callingPhone", "15102720175")
                .addParams("calledPhone", "13659827958")
                .addParams("dataID", 1 + "")
                .addParams("order", order)
                .addParams("timeStamp", timeStamp)
                .addParams("resultURL", "www.baidu.com")
                .addParams("notifyURL", "www.baidu.com")
                .addParams("remark", "")
                .addParams("type", "2")
                .addParams("line", "2")
                .addParams("signInfo", mSigns).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Log.d("电话", "onResponse: " + response);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
