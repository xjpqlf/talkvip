package dao.cn.com.talkvip.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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


    private View mView;
    private List<Custom> mList;
    private InfoAdapter mAdapter;
    private List<CustomFrist> mC;
    private int mA;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.item_ex, container, false);


        ButterKnife.bind(this, mView);

        MyAnimationDrawable.animateRawManuallyFromXML(R.drawable.loadings, image, new Runnable() {
            @Override
            public void run() {
                // TODO onStart
                // 动画开始时回调
                 Log.d("","start"); } }, new Runnable() {
            @Override
            public void run() {
            // TODO onComplete // 动画结束时回调
             Log.d("","end"); } });

        initData();
        initView();
        return mView;


    }

    private void showLoadingView() {
    image.setVisibility(View.VISIBLE);

    }

    private void hiddenLoadingView() {
  image.setVisibility(View.GONE);

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
        ptrLayout.addPtrUIHandler(myPtrRefresher);
        ptrLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {

                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrLayout.refreshComplete();

                    }
                }, 3000);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrLayout.refreshComplete();

                    }
                }, 3000);


            }
        });


    }


    private void initData() {
        showLoadingView();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 4000);


        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {


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


    private void getData() {

        OkHttpUtils.get()
                .url(Constants.BASE_URL + "/Callphone/serviceNotCall")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addParams("token", Constants.TOKEN)
                .addParams("page", "0")
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

                Data data = ms.getData();
                mList = data.getList();
                tvPager.setText(1 + "/" + mList.size());
                mC = new ArrayList<CustomFrist>();
                for (int i = 0; i < mList.size(); i++) {
                    mC.add(new CustomFrist(mList.get(i), false));

                }

                mC.get(0).setFirst(true);

                mAdapter = new InfoAdapter(getActivity(), mC);

                lv.setAdapter(mAdapter);

            }

        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}