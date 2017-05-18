package dao.cn.com.talkvip.view.fragment;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import dao.cn.com.talkvip.Constants;
import dao.cn.com.talkvip.R;
import dao.cn.com.talkvip.adapter.InfoAdapter;
import dao.cn.com.talkvip.bean.Custom;
import dao.cn.com.talkvip.bean.CustomFrist;
import dao.cn.com.talkvip.bean.Data;
import dao.cn.com.talkvip.bean.Message;
import dao.cn.com.talkvip.utils.DebugFlags;
import dao.cn.com.talkvip.utils.Rsa;
import dao.cn.com.talkvip.utils.SPUtils;
import dao.cn.com.talkvip.utils.ToastUtil;
import dao.cn.com.talkvip.view.activity.RemarkActivity;
import dao.cn.com.talkvip.widget.MyAnimationDrawable;
import dao.cn.com.talkvip.widget.MyPtrRefresher;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.Call;

/**
 * Description:
 * User: xjp
 * Date: 2015/6/15
 * Time: 15:03
 */

public class NotCallFragment extends Fragment {
    @Bind(R.id.id_main_lv_lv)
    ListView lv;
    @Bind(R.id.ptr_layout)
    PtrClassicFrameLayout ptrLayout;

    @Bind(R.id.tv_pager)
    TextView tvPager;
    @Bind(R.id.progressBar04_id)
    ProgressBar progressBar04Id;
    @Bind(R.id.iv_loading)
    ImageView image;
    @Bind(R.id.rl_bar)
    RelativeLayout rlBar;
    @Bind(R.id.iv_nodata)
    ImageView ivNodata;


    private View mView;
    private List<Custom> mList;
    private InfoAdapter mAdapter;
    private List<CustomFrist> mC;
    private int mA;
    private GestureDetector  gestureDetector;
    private GestureDetector.OnGestureListener listener;
    private int pager = 1;
    private static final int DATA_LOAD_FAILED = 0X00123311;
    private static final int DATA_LOAD_SUCCESS = 0X43254564;
    private Handler handlers = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case DATA_LOAD_FAILED:
                    Toast.makeText(getActivity(), "服务器连接失败...", Toast.LENGTH_SHORT).show();
                    break;
                case DATA_LOAD_SUCCESS:
                    Message ms = (Message) msg.obj;
                    Data data = ms.getData();
                    if ("0".equals(data.getTotal())) {
                        rlBar.setVisibility(View.GONE);
                        ptrLayout.setMode(PtrFrameLayout.Mode.NONE);
                        ivNodata.setVisibility(View.VISIBLE);
                      return;
                    }
                    mList = data.getList();
                    DebugFlags.logD(  "刷到最后了"+mList.size()+"---"+mList);
                 if (mList.get(0)==null){
                     ToastUtil.showInCenter("没有更多数据了");
                    DebugFlags.logD(mList.size()+"---"+mList);
                     return;
                 }

                    //  tvPager.setText(1 + "/" + mList.size());
                    mC = new ArrayList<CustomFrist>();
                    for (int i = 0; i < mList.size(); i++) {
                        mC.add(new CustomFrist(mList.get(i), false));

                    }

                    mC.get(0).setFirst(true);

                    mAdapter = new InfoAdapter(getActivity(), mC);

                    if (lv != null) {
                        lv.setAdapter(mAdapter);

                    }


            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.item_ex, container, false);


        ButterKnife.bind(this, mView);

        MyAnimationDrawable.animateRawManuallyFromXML(R.drawable.loads, image, new Runnable() {
            @Override
            public void run() {
                // TODO onStart
                // 动画开始时回调
                Log.d("", "start");
            }
        }, new Runnable() {
            @Override
            public void run() {
                // TODO onComplete // 动画结束时回调
                Log.d("", "end");
            }
        });

        initData();
        initView();
        return mView;


    }

    private void showLoadingView() {
        if (image != null) {
            image.setVisibility(View.VISIBLE);

        }
    }

    private void hiddenLoadingView() {
        if (image != null) {
            image.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // image.setVisibility(View.GONE);
        // initData();
        //  initView();








    }


    private void initView() {


        MyPtrRefresher myPtrRefresher = new MyPtrRefresher(getActivity());
        ptrLayout.setHeaderView(myPtrRefresher);
        // ptrLayout.setFooterView(myPtrRefresher);
        ptrLayout.addPtrUIHandler(myPtrRefresher);
        ptrLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {

                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (ptrLayout!=null) {
                            ptrLayout.refreshComplete();
                        }
                    }
                }, 2000);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pager++;
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //ToastUtil.showInCenter(pager+"");
                        if (ptrLayout!=null) {
                            getData(pager);
                            ptrLayout.refreshComplete();
                        }
                    }
                }, 2000);


            }
        });


    }


    private void initData() {
        showLoadingView();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData(pager);
            }
        }, 200);

       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

           }
       });
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    rlBar.setVisibility(View.GONE);

                }else{
                    rlBar.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                progressBar04Id.setMax(totalItemCount);
                tvPager.setText((firstVisibleItem + 1 + "/" + totalItemCount));
                progressBar04Id.setProgress(firstVisibleItem);
                if (mC != null) {
                    for (int i = 0; i < mC.size(); i++) {
                        if (firstVisibleItem == i) {


                            mC.get(i).setFirst(true);
                            mAdapter.notifyDataSetChanged();
                        } else {


                            mC.get(i).setFirst(false);
                            mAdapter.notifyDataSetChanged();


                        }

                    }


                }


            }
        });

        lv.setOnTouchListener(new View.OnTouchListener() { //对ListView注册触屏事件
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    return false;

                }else if (event.getAction() == MotionEvent.ACTION_UP){

              return false;
                }
                return false;
            }
        });


    }

    private void getPhoneNum(String phone) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        Random rand = new Random();
        int i = rand.nextInt(100);

        mA = rand.nextInt(10000000);

        String accountId = "1";
        String timeStamp = i + str;
        String order = mA + "";
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


    private void getData(int page) {
        String token= SPUtils.getString(getActivity(),"token","");
        OkHttpUtils.post()
                .url(Constants.BASE_URL + "/Callphone/serviceNotCall")
                // .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "Bearer" + " " + token)
                .addParams("size", "20")
                .addParams("page", page + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                hiddenLoadingView();

            }

            @Override
            public void onResponse(String response, int id) {
                DebugFlags.logD("数据" + response);
                hiddenLoadingView();
                Message ms = JSON.parseObject(response, Message.class);


                DebugFlags.logD("数据" + ms.getData());
                handlers.obtainMessage(DATA_LOAD_SUCCESS, ms).sendToTarget();
               /* Data data = ms.getData();
                mList = data.getList();
                //tvPager.setText(1 + "/" + mList.size());
                mC = new ArrayList<CustomFrist>();
                for (int i = 0; i < mList.size(); i++) {
                    mC.add(new CustomFrist(mList.get(i), false));

                }

                mC.get(0).setFirst(true);

                mAdapter = new InfoAdapter(getActivity(), mC);

                lv.setAdapter(mAdapter);
*/
            }

        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }




    public class PhoneBroadcastReceiver extends BroadcastReceiver {


        String TAG = "打电话";
        TelephonyManager telMgr;

        @Override
        public void onReceive(Context context, Intent intent) {


            telMgr = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            switch (telMgr.getCallState()) {
                //来电
                case TelephonyManager.CALL_STATE_RINGING:
                    String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    Log.v(TAG, "number:" + number);


                    break;
                //响铃
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                //挂断
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.v(TAG,"电话挂断了");

                    Intent inten1=new Intent(getActivity(),RemarkActivity.class);

                    context. startActivity(inten1);


                    break;
            }

        }

    }












}