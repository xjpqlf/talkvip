package dao.cn.com.talkvip.view.activity;

import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.List;

import dao.cn.com.talkvip.Constants;
import dao.cn.com.talkvip.R;
import dao.cn.com.talkvip.TVApplication;
import dao.cn.com.talkvip.adapter.RemarksAdapter;
import dao.cn.com.talkvip.bean.Remark;
import dao.cn.com.talkvip.bean.Remarks;
import dao.cn.com.talkvip.utils.DebugFlags;
import dao.cn.com.talkvip.utils.SPUtils;
import dao.cn.com.talkvip.utils.ToastUtil;
import okhttp3.Call;

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
                    List<Remarks> detail = ( List<Remarks> ) msg.obj;

                   adapter=new RemarksAdapter(Detail.this ,detail);
                    mLv.setAdapter(adapter);





            }
        }
    };
    private String mStatus="0";
    private EditText mEt;
    private String mId;
    private String mName;
    private String mMobile;
    private String mType;

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

      mLv = (ListView) findViewById(R.id.lv);
        if (getIntent()!=null){
            mId = getIntent().getStringExtra("id");
            mName = getIntent().getStringExtra("name");
            mMobile = getIntent().getStringExtra("mobile");
            mType = getIntent().getStringExtra("type");
 }
        TextView id= (TextView) findViewById(R.id.tv_deid);
        TextView name= (TextView) findViewById(R.id.de_name);
        TextView mobile= (TextView) findViewById(R.id.detail_phone);
        TextView type= (TextView) findViewById(R.id.tv_type);

        id.setText(mId);
        name.setText(mName);
        mobile.setText(mMobile);
        type.setText(mType);
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
                .addParams("id","1714")
                .addHeader("Authorization", "Bearer" + " " + Constants.TOKEN)
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
        mEt = (EditText) contentView.findViewById(R.id.tv_edbj);
        TextView save= (TextView) contentView.findViewById(R.id.tv_save);
        final TextView nt= (TextView) contentView.findViewById(R.id.tv_nt);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if (!TextUtils.isEmpty(mEt.getText().toString())&&(!"0".equals(mStatus))) {

                 gotoRemark();
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
                mStatus = "1";
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
                mStatus = "2";
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
                mStatus = "4";
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
        mPopWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
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
                .addParams("id","1714")
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



}


