package dao.cn.com.talkvip.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import dao.cn.com.talkvip.utils.Util;
import dao.cn.com.talkvip.widget.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.OkHttpClient;

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
    private String s;
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
    private  PhoneReceiverfre mycodereceivers;
    private TextView mName;
    private TextView mMobile;
    private TextView mMs;
    private  LinearLayout ls;
    private int mA;
    private SweetAlertDialog dialog;
    private SweetAlertDialog dialog1;
    private RelativeLayout mMrlc;

    @Override
    protected void initHead() {

    }

    @Override
    protected int getContentView() {
        return R.layout.remark;
    }

    @Override
    protected void initView() {
        dialog = new SweetAlertDialog(this);
        dialog.setTitleText("拨打中...");
        dialog1 = new SweetAlertDialog(this);
        dialog1.setTitleText("保存中...");

        mName = (TextView) findViewById(R.id.tv_detailids);
        mMobile = (TextView) findViewById(R.id.detail_phone);
        mMs = (TextView) findViewById(R.id.tv_ms);
        ls= (LinearLayout) findViewById(R.id.ll_chooes);
        mRl = (RelativeLayout) findViewById(R.id.rl_bjback);
        mMrlc = (RelativeLayout) findViewById(R.id.rl_check);
        mCheckBox = (CheckBox) findViewById(R.id.iv_xuan);
        mEt = (EditText) findViewById(R.id.tv_bjhint);
        mycodereceivers = new   PhoneReceiverfre();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.BOOT_COMPLETED");
        filter.addAction("android.intent.action.PHONE_STATE");
        filter.addAction("android.intent.action.NEW_OUTGOING_CALL");
         registerReceiver(mycodereceivers, filter);


        if (getIntent() != null) {
            Intent in = getIntent();
            mInfos = (Infos) in.getSerializableExtra("list");
            mCustom = (Custom) in.getSerializableExtra("p");
            mPostion = in.getIntExtra("postion",0);
               s=in.getStringExtra("status");

                DebugFlags.logD(s+"==标记");
          // Log.v("电话集合", "" + mInfos.getMsg().toString()+ mCustom.toString());
          String ccall= SPUtils.getString(RemarkActivity.this,"cc","");

                if (!TextUtils.isEmpty(ccall)){
                    if ("0".equals(ccall)) {
                        DebugFlags.logD("不联播");
                        mCheckBox.setChecked(false);
                        mCheckBox.setSelected(false);

                    }else if ("1".equals(ccall)){
                        mCheckBox.setChecked(true);
                        mCheckBox.setSelected(true);

                    }
                    }







            if (mName!=null&&mMobile!=null&&mMs!=null) {
               // mCheckBox.setChecked(true);
                mName.setText(mCustom.getId());
                mMobile.setText(mCustom.getMobile());
                mMs.setText(mCustom.getName());
            }
            dgx = (TextView) findViewById(R.id.dgx);
            wjt= (TextView) findViewById(R.id.wjt);

            wyy= (TextView) findViewById(R.id.wyy);
            tq= (TextView) findViewById(R.id.tq);
           zd= (TextView) findViewById(R.id.zd);

             bc= (TextView) findViewById(R.id.tv_bc);
            if ("1".equals(SPUtils.getString(RemarkActivity.this, "extract_state",""))){

tq.setVisibility(View.GONE);

            }




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

                 if (Util.isNetwork(this)) {

                  if (mCheckBox.isChecked()){
                      dialog.show();

                  } else{

                      dialog1.show();
                  }



                     bc.setClickable(false);
                     save();
                     setLb();

                 }else{
                     bc.setClickable(true);
                     ToastUtil.show(R.string.netstatu);
                 }

                    //产生日志








                }else{
                    bc.setClickable(true);
                    ToastUtil.showInCenter("保存内容和类型不能为空");

                }

                break;
            case  R.id.rl_bjback:

                finish();

                break;

        }

    }

    private void setLb() {

        String token=SPUtils.getString(RemarkActivity.this,"token","");
        DebugFlags.logD("备注id"+(mCheckBox.isChecked()?"1":"0"));
        OkHttpUtils.post().url(Constants.BASE_URL+"/Comment/modifyState")
                .addHeader("Authorization", "Bearer" + " " + token)

                .addParams("continuous_call",(mCheckBox.isChecked()?"1":"0"))

                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {

                DebugFlags.logD("更改状态"+response);
                try {
                    JSONObject js=new JSONObject(response);

                    String code=js.getString("code");
                    if ("1030".equals(code)) {
                        SPUtils.putString(RemarkActivity.this,"cc",(mCheckBox.isChecked()?"1":"0"));
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });





    }

    private void save() {
    /*  String ss="1";
        if (!mCheckBox.isChecked()){
            ss="0";
        }*/

String token=SPUtils.getString(RemarkActivity.this,"token","");
        DebugFlags.logD("备注id"+(mCheckBox.isChecked()?"1":"0"));
        OkHttpUtils.post().url(Constants.BASE_URL+"/Comment/phoneNotes")
                .addHeader("Authorization", "Bearer" + " " + token)
                .addParams("id",mInfos.getMsg().get(mPostion).getId())
                .addParams("status",status)

                .addParams("note",mEt.getText().toString())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                bc.setClickable(true);

            }

            @Override
            public void onResponse(String response, int id) {

                bc.setClickable(true);
                try {
                    DebugFlags.logD("保存备注"+response);
                    JSONObject jsonObject=new JSONObject(response);
                    String result= jsonObject.getString("result");
                    String msg= jsonObject.getString("msg");
                    if ("success".equals(result)){



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

                            getPhoneNum(mInfos.getMsg().get(mPostion).getSourceid(),order);
                           // CallPhone(mInfos.getMsg().get(mPostion).getId());



                        }else{
                             dialog1.cancel();
                            finish();
                        }

                        ToastUtil.showInCenter("保存成功");

                    }else{

                        ToastUtil.showInCenter(msg);
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
        dialog.cancel();
     startActivity(intent);


    }

  /*  public class PhoneBroadcastReceivers extends BroadcastReceiver {


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
                        Log.e("空闲", "电话状态……OFFHOOK");
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        Log.e("挂断", "电话状态……IDLE");

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

    }*/
  public class PhoneReceiverfre extends BroadcastReceiver {
      private int lastCallState  = TelephonyManager.CALL_STATE_IDLE;
      private boolean isIncoming = false;
      private  String contactNum;
      Intent audioRecorderService;
      OkHttpClient httpClient;
      SharedPreferences sharedPreferences;
      String type;
      Bitmap bitmap;
      Context  context;

      @Override
      public void onReceive(Context context, Intent intent) {
          this.context =context;
          Constants.isCall=true;  //判断是否在通话中
          //如果是去电
          if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){
              contactNum = intent.getExtras().getString(Intent.EXTRA_PHONE_NUMBER);
          }else //android.intent.action.PHONE_STATE.查了下android文档，貌似没有专门用于接收来电的action,所以，非去电即来电.
          {
              String state = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
              String phoneNumber = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
              int stateChange = 0;

              if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                  Constants.isCall =false;
                  //空闲状态
                  stateChange =TelephonyManager.CALL_STATE_IDLE;
                  if (isIncoming){
                      onIncomingCallEnded(context,phoneNumber);
                  }else {
                      onOutgoingCallEnded(context,phoneNumber);
                  }
              }else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                  //摘机状态
                  Constants.isCall =false;
                  stateChange = TelephonyManager.CALL_STATE_OFFHOOK;
                  if (lastCallState != TelephonyManager.CALL_STATE_RINGING){
                      //如果最近的状态不是来电响铃的话，意味着本次通话是去电
                      isIncoming =false;
                      onOutgoingCallStarted(context,phoneNumber);
                  }else {
                      //否则本次通话是来电
                      isIncoming = true;
                      onIncomingCallAnswered(context, phoneNumber);
                  }
              }else if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                  //来电响铃状态
                  Constants.isCall =false;
                  stateChange = TelephonyManager.CALL_STATE_RINGING;
                  lastCallState = stateChange;
                  onIncomingCallReceived(context,contactNum);
              }
          }
      }

      protected void onIncomingCallStarted(Context context,String number){
          Toast.makeText(context, "0", Toast.LENGTH_SHORT).show();
      }
      protected void onOutgoingCallStarted(Context context,String number){
          //正在通话中会走这个方法。在这里处理自己的逻辑
          //  putRed(context);
      }
      protected void onIncomingCallEnded(Context context,String number){
          Toast.makeText(context,"end", Toast.LENGTH_SHORT).show();
      }
      protected void onOutgoingCallEnded(Context context,String number){
          //电话挂断时候会走这个方法。在这里处理自己的逻辑

          Constants.isCall =true;

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


      }
      protected void onIncomingCallReceived(Context context,String number){
          Toast.makeText(context, "4", Toast.LENGTH_SHORT).show();
      }
      protected void onIncomingCallAnswered(Context context, String number) {
          Toast.makeText(context, "5", Toast.LENGTH_SHORT).show();
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

    private void getPhoneNum(String id,String order) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        Random rand = new Random();
        int i = rand.nextInt(100);

        mA = rand.nextInt(10000000);

        String accountId ="1803c7cadc";
        //沙箱id
     //  String accountId = "b6458ae8a4";
        String timeStamp = i + str;

        String sign = accountId + timeStamp + order;


        String mSigns = Rsa.encryptByPublic(sign);
        Log.d("时间戳", mSigns);
        OkHttpUtils.post()
                .url(Constants.C1_URL+"/Authorization")
                .addParams("accuntID", accountId)
                .addParams("callingPhone", SPUtils.getString(RemarkActivity.this,"phone",""))
                .addParams("calledPhone", "")
                .addParams("dataID", id)
                .addParams("order", order)
                .addParams("timeStamp", timeStamp)
                .addParams("resultURL", "www.baidu.com")
                .addParams("notifyURL",SPUtils.getString(RemarkActivity.this,"nurl",""))
                .addParams("remark", "")
                .addParams("type", "1")
                .addParams("line", "E")
                .addParams("signInfo", mSigns).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Log.d("单项隐私 电话", "onResponse: " + response);
                try {
                    JSONObject json=new JSONObject(response);
                    String code=json.getString("resultCode");
                    String msg=json.getString("message");
                    if ("8888".equals(code)){



                        String num= json.getString("fromSerNum");
                        CallPhone(num);


                    }else{
                        //TODO
                        ToastUtil.show(msg);
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
        finish();
    }
}
