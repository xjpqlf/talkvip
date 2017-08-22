package dao.cn.com.talkvip.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import dao.cn.com.talkvip.Constants;
import dao.cn.com.talkvip.R;
import dao.cn.com.talkvip.TVApplication;
import dao.cn.com.talkvip.adapter.RemarksAdapter;
import dao.cn.com.talkvip.bean.Custom;
import dao.cn.com.talkvip.bean.Infos;
import dao.cn.com.talkvip.bean.Remark;
import dao.cn.com.talkvip.bean.Remarks;
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
 * @time 2017/4/10 10:31
 * @change uway
 * @chang 2017/4/10 10:31
 * @class describe
 */

public class Detail  extends BaseActivity {
    private SweetAlertDialog dialog;
    private RelativeLayout mIvback;
    private RelativeLayout medit;
    private ListView mLv;
    private List<Remark> mList;
    private PopupWindow mPopWindow;
    private static final int DATA_LOAD_FAILED = 0X00123311;
    private static final int DATA_LOAD_SUCCESS = 0X43254564;
    private RemarksAdapter adapter;
    private Handler handlers = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case DATA_LOAD_FAILED:
                    Toast.makeText(TVApplication.getAppContext(), "服务器连接失败...", Toast.LENGTH_SHORT).show();
                    break;
                case DATA_LOAD_SUCCESS:

                    iv.setVisibility(View.GONE);
                    List<Remarks> detail = ( List<Remarks> ) msg.obj;

                   adapter=new RemarksAdapter(Detail.this ,detail);
                    mLv.setAdapter(adapter);





            }
        }
    };
    private String mStatus="0";
    private String status="0";
    private EditText mEt;
    private String mId;
    private String mName;
    private String mMobile;
    private String mType;
    private String sid;
    private  ImageView iv;
    private TextView mType1;
    private  ImageView  ivcall;
    private int mA;
    private Custom mCustom;
    private Infos mInfos;
    private int mPostion=-1;
    private PhoneReceivers mycodereceiver;
    @Override
    protected void initHead() {
        mIvback = (RelativeLayout) findViewById(R.id.rl_ivback);
        medit = (RelativeLayout) findViewById(R.id.rl_remark);
        mIvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mType1 = (TextView) findViewById(R.id.tv_type);


        medit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });


    }

    @Override
    protected int getContentView() {
        return R.layout.detail_activity;
    }

    @Override
    protected void initView() {

        dialog = new SweetAlertDialog(Detail.this);
        dialog.setTitleText("拨打中...");
        mycodereceiver = new  PhoneReceivers();
        final IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PHONE_STATE");
        filter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        filter.addAction("android.intent.action.BOOT_COMPLETED");
        registerReceiver(mycodereceiver, filter);



   iv= (ImageView) findViewById(R.id.detail_empty);



      mLv = (ListView) findViewById(R.id.lv);
        if (getIntent()!=null){
            mId = getIntent().getStringExtra("id");
            mName = getIntent().getStringExtra("name");
            mMobile = getIntent().getStringExtra("mobile");
            mType = getIntent().getStringExtra("type");
           sid = getIntent().getStringExtra("sid");

            mInfos = (Infos) getIntent().getSerializableExtra("list");
            mCustom = (Custom) getIntent().getSerializableExtra("p");
            mPostion = getIntent().getIntExtra("postion",0);


 }
        TextView id= (TextView) findViewById(R.id.tv_deid);
        TextView name= (TextView) findViewById(R.id.de_name);
        TextView mobile= (TextView) findViewById(R.id.detail_phone);
        TextView type= (TextView) findViewById(R.id.tv_type);
        ivcall = (ImageView) findViewById(R.id.iv_call);
        id.setText(mId);
        name.setText(mName);
        mobile.setText(mMobile);
        type.setText(mType);
        ivcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivcall.setClickable(false);
                  dialog.show();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmsss");
                Date curDate = new Date(System.currentTimeMillis());
                String strs = formatter.format(curDate);
                Random rand = new Random();
                int i = rand.nextInt(100000);
                String order=strs+"1"+i;
                creatLog(mId,order);
                getPhoneNum(sid,order);

            }
        });





     /*     mList = new ArrayList<>();
        Remark r = new Remark("2018-11-11", "10:11", "电话不多但是是是首饰 是倒计时是撒但是打架大家爱死的大街上了", "10;22", "call");
        Remark r1 = new Remark("2018-11-11", "10:11", "电话不多但是是是首饰 是倒计时是撒但是打架大家爱死的大街上了", "10;22", "notcall");
        Remark r2 = new Remark("2018-11-11", "10:11", "电话不多但是是是首饰 是倒计时是撒但是打架大家爱死的大街上了", "10;22", "call");
        Remark r3 = new Remark("2018-11-11", "10:11", "电话不多但是是是首饰 是倒计时是撒但是打架大家爱死的大街上了", "10;22", "notcall");
        mList.add(r);
        mList.add(r1);
        mList.add(r2);
        mList.add(r3);



        RemarkAdapter adapter = new RemarkAdapter(Detail.this, mList);
        mLv.setAdapter(adapter);*/

       getDetail();



    }



    private void getDetail() {


        OkHttpUtils.post().url(Constants.BASE_URL+"/Comment/getHistoryNotes")
                .addParams("id",mId)
                .addHeader("Authorization", "Bearer" + " " + SPUtils.getString(Detail.this,"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        DebugFlags.logD("备注"+response);


                          //  RemarkDetail detail = JSON.parseObject(response, RemarkDetail.class);
                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            String json=jsonObject.getString("data");
                             JSONObject j=new JSONObject(json);
                            String list=j.getString("list");
                            String infos=j.getString("info");
                               JSONObject json1=new JSONObject(infos);
                            status=json1.getString("status");
                            if ("1".equals(status)){

                                mType1.setText("重   点");

                            } else if("2".equals(status)){
                                mType1.setText("无意愿");

                            }else if("3".equals(status)){
                                mType1.setText("待跟进");

                            }else if("4".equals(status)){
                                mType1.setText("未接通");

                            }else if("0".equals(status)){
                                mType1.setText("未拨打");

                            }else if("5".equals(status)){
                                mType1.setText("已提取");

                            }

                            DebugFlags.logD("备注"+list);
                            if (list.equals("[]")){

                                iv.setVisibility(View.VISIBLE);
                                return;
                            }

                            List<Remarks> detail=JSON.parseArray(list, Remarks.class);
                            handlers.obtainMessage(DATA_LOAD_SUCCESS, detail).sendToTarget();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }





                    }
                });
    }

    private void showPopupWindow() {
        //设置contentView

        View contentView = LayoutInflater.from(Detail.this).inflate(R.layout.layout_popupwindow, null);
        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        //设置各个控件的点击响应
        final TextView nc= (TextView) contentView.findViewById(R.id.tv_nc);
        final TextView im= (TextView) contentView.findViewById(R.id.tv_im);
        final TextView gj= (TextView) contentView.findViewById(R.id.tv_gj);
        final LinearLayout ll= (LinearLayout)contentView.findViewById(R.id.ll_choos);
        mEt = (EditText) contentView.findViewById(R.id.tv_edbj);
        TextView save= (TextView) contentView.findViewById(R.id.tv_save);
        final TextView nt= (TextView) contentView.findViewById(R.id.tv_nt);
        if ("5".equals(status)){

            ll.setVisibility(View.GONE);
            mStatus="5";
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if (!TextUtils.isEmpty(mEt.getText().toString())&&(!"0".equals(mStatus))) {

                 if (Util.isNetwork(Detail.this)){

                 gotoRemark();}else{

                     ToastUtil.show(R.string.netstatu);
                 }
             }else {
                    ToastUtil.showInCenter("备注与类型不能为空");

                }






            }
        });
        final ImageView close= (ImageView) contentView.findViewById(R.id.iv_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
        nc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStatus = "4";
                nc.setTextColor(getResources().getColor(R.color.cacecorlor));
                im.setTextColor(getResources().getColor(R.color.grey_word1));
                gj.setTextColor(getResources().getColor(R.color.grey_word1));
                nt.setTextColor(getResources().getColor(R.color.grey_word1));
                nc.setBackgroundDrawable(getResources().getDrawable(R.mipmap.popbg));
                im.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                gj.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                nt.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));

            }
        });

       im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStatus = "1";
                im.setTextColor(getResources().getColor(R.color.cacecorlor));
                nc.setTextColor(getResources().getColor(R.color.grey_word1));
                gj.setTextColor(getResources().getColor(R.color.grey_word1));
                nt.setTextColor(getResources().getColor(R.color.grey_word1));
                im.setBackgroundDrawable(getResources().getDrawable(R.mipmap.popbg));
                nc.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                gj.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                nt.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));

            }
        });
        gj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStatus = "3";
                gj.setTextColor(getResources().getColor(R.color.cacecorlor));
                im.setTextColor(getResources().getColor(R.color.grey_word1));
                nc.setTextColor(getResources().getColor(R.color.grey_word1));
                nt.setTextColor(getResources().getColor(R.color.grey_word1));
                gj.setBackgroundDrawable(getResources().getDrawable(R.mipmap.popbg));
                im.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                nc.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                nt.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));

            }
        });
        nt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStatus = "2";
                nt.setTextColor(getResources().getColor(R.color.cacecorlor));
                im.setTextColor(getResources().getColor(R.color.grey_word1));
                gj.setTextColor(getResources().getColor(R.color.grey_word1));
                nc.setTextColor(getResources().getColor(R.color.grey_word1));
                nt.setBackgroundDrawable(getResources().getDrawable(R.mipmap.popbg));
                im.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                gj.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));
                nc.setBackgroundDrawable(getResources().getDrawable(R.mipmap.tnb));

            }
        });


         setBackgroundAlpha(0.3f);
       // mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        View rootViews = findViewById(R.id.rl_detail);

        //显示PopupWindow
        View rootview = LayoutInflater.from(Detail.this).inflate(R.layout.detail_activity, null);
     //   mPopWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        mPopWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() { // popupWindow隐藏时恢复屏幕正常透明度

                setBackgroundAlpha(1.0f);
            }
        });

    }



    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = (Detail.this.getWindow().getAttributes());
        lp.alpha = bgAlpha;
        Detail.this.getWindow().setAttributes(lp);
    }


    private void gotoRemark() {

        String token= SPUtils.getString(Detail.this,"token","");
        OkHttpUtils.post().url(Constants.BASE_URL+"/Comment/directNotes")
                .addHeader("Authorization", "Bearer" + " " + token)
                .addParams("id",mId)
                .addParams("status",mStatus)
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
                      if ("1".equals(mStatus)){

                          mType1.setText("未");

                      } else if("2".equals(mStatus)){
                          mType1.setText("重   点");

                      }else if("3".equals(mStatus)){
                          mType1.setText("待跟进");

                      }else if("4".equals(mStatus)){
                          mType1.setText("无意愿");

                      }






                        getDetail();
                        mPopWindow.dismiss();


                    }else{

                        ToastUtil.showInCenter("保存失败");
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void creatLog(String id, final String order) {

        String token= SPUtils.getString(Detail.this,"token","");

        DebugFlags.logD("日志id"+id);
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

        String accountId = "1803c7cadc";
        //沙箱id
       // String accountId = "b6458ae8a4";
        String timeStamp = i + str;

        String sign = accountId + timeStamp + order;


        String mSigns = Rsa.encryptByPublic(sign);
        Log.d("详情",str);
        OkHttpUtils.post()
                .url(Constants.C1_URL+"/Authorization")
                .addParams("accuntID", accountId)
                .addParams("callingPhone", SPUtils.getString(Detail.this,"phone",""))
                .addParams("calledPhone", "")
                .addParams("dataID", id)
                .addParams("order", order)
                .addParams("timeStamp", timeStamp)
                .addParams("resultURL", "www.baidu.com")
                .addParams("notifyURL", SPUtils.getString(Detail.this,"nurl",""))
                .addParams("remark", "")
                .addParams("type", "1")
                .addParams("line", "E")
                .addParams("signInfo", mSigns).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
ivcall.setClickable(true);
            }

            @Override
            public void onResponse(String response, int id) {
                ivcall.setClickable(true);
                dialog.cancel();
                Log.d("单项隐私 电话", "onResponse: " + response);
                try {
                    JSONObject json=new JSONObject(response);
                    String code=json.getString("resultCode");
                    String msg=json.getString("message");
                    if ("8888".equals(code)){

                        String num= json.getString("fromSerNum");
                        CallPhone(num);


                    }else{

                        ToastUtil.show(msg);
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

    public class PhoneReceivers extends BroadcastReceiver {
        private int lastCallState  = TelephonyManager.CALL_STATE_IDLE;
        private boolean isIncoming = false;
        private  String contactNum;
        Intent audioRecorderService;
        OkHttpClient httpClient;
        SharedPreferences sharedPreferences;
        String type;
        Bitmap bitmap;
        Context context;

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
            Intent inten1=new Intent(Detail.this,RemarkActivity.class);
            DebugFlags.logD("电话挂断了详情"+number);
            if(mCustom!=null&&mInfos!=null&&mPostion!=-1&&number!=null){
                DebugFlags.logD("电话挂断了"+number);
                inten1.putExtra("p",mCustom);
                inten1.putExtra("list",mInfos);
                inten1.putExtra("postion",mPostion);

                //   getActivity(). unregisterReceiver(mycodereceiver);
                startActivity(inten1);
                mPostion=-1;
            }


        }

        protected void onIncomingCallReceived(Context context,String number){
            Toast.makeText(context, "4", Toast.LENGTH_SHORT).show();
        }
        protected void onIncomingCallAnswered(Context context, String number) {
            Toast.makeText(context, "5", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        getDetail();
        mPostion= getIntent().getIntExtra("postion",0);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mycodereceiver);

    }
}


