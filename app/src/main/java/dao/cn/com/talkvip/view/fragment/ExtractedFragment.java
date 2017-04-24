package dao.cn.com.talkvip.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

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

public class ExtractedFragment extends Fragment {
    @Bind(R.id.id_main_lv_lv)
    ListView lv;
    @Bind(R.id.ptr_layout)
    PtrClassicFrameLayout ptrLayout;
    @Bind(R.id.progressBar1)
    ProgressBar progressBar1;
    @Bind(R.id.tv_pager)
    TextView tvPager;
    @Bind(R.id.progressBar04_id)
    ProgressBar progressBar04Id;

    private View mView;
    private List<Custom> mList;
    private InfoAdapter mAdapter;
    private List<CustomFrist> mC;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.item_ex, container, false);


        ButterKnife.bind(this, mView);
        initData();
        initView();
        return mView;


    }


    @Override
    public void onResume() {
        super.onResume();
        initData();
        initView();


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
                }, 1000);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrLayout.refreshComplete();

                    }
                }, 1000);


            }
        });




    }


    private void initData() {
        progressBar1.setVisibility(View.VISIBLE);
         getData();



        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

               

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                progressBar04Id.setMax(totalItemCount);
                tvPager.setText((firstVisibleItem+1+"/"+totalItemCount));
                progressBar04Id.setProgress(firstVisibleItem);
                if (mC!=null){
                    for (int i = 0; i <mC.size() ; i++) {
                        if (firstVisibleItem==i){


                            mC.get(i).setFirst(true);
                            mAdapter.notifyDataSetChanged();
                        }else{


                            mC.get(i).setFirst(false);
                            mAdapter.notifyDataSetChanged();





                        }

                    }


                }





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
                progressBar1.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(String response, int id) {
                DebugFlags.logD("数据" + response);
                progressBar1.setVisibility(View.GONE);
                Message ms = JSON.parseObject(response, Message.class);

                Data data = ms.getData();
                  mList = data.getList();
                tvPager.setText(1+"/"+mList.size());
                mC=new ArrayList<CustomFrist>();
                for (int i = 0; i <mList.size(); i++) {
                    mC.add(new CustomFrist(mList.get(i),false));

                }

                mC.get(0).setFirst(true);

                mAdapter = new InfoAdapter(getActivity(),mC);

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