package dao.cn.com.talkvip.view.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dao.cn.com.talkvip.Constants;
import dao.cn.com.talkvip.R;
import dao.cn.com.talkvip.adapter.CustomAdapter;
import dao.cn.com.talkvip.bean.Data;
import dao.cn.com.talkvip.bean.Message;
import dao.cn.com.talkvip.utils.DebugFlags;
import okhttp3.Call;

/**
 * Description:
 * User: xjp
 * Date: 2015/6/15
 * Time: 15:03
 */

public class NotCallFragment extends Fragment {

    @Bind(R.id.recyclerview1)
    XRecyclerView mRecyclerView;
    @Bind(R.id.progressBar1)
    ProgressBar progressBar1;
    @Bind(R.id.tv_pager)
    TextView tvPager;
    @Bind(R.id.progressBar04_id)
    ProgressBar mP;

    private View view;
    private String str;
    private CustomAdapter mAdapter;
    private List mMP;
    private int mFirstItemPosition;
    private String mTotal;

    private LinearLayoutManager mLayoutManager;
    private int mLastItemPosition;
    private int mF;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView();
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // str = getArguments().getString("content");


    }

    private void initView() {
        progressBar1.setVisibility(View.VISIBLE);


        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mF = mLayoutManager.findFirstVisibleItemPosition();

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, 1);
            }
        });
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    // 获取最后一个可见view的位置
                    mLastItemPosition = linearManager.findLastVisibleItemPosition();
                    int total = linearManager.getItemCount() - 2;
                    // 获取第一个可见view的位置
                    mFirstItemPosition = linearManager.findFirstVisibleItemPosition();
                    int i = mFirstItemPosition + 1;
                    tvPager.setText(mFirstItemPosition + "/" + mMP.size());
                    mP.setProgress(mLastItemPosition);


                }


            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
              /*  RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    // 获取最后一个可见view的位置
                    mLastItemPosition = linearManager.findLastVisibleItemPosition();
                    int total=linearManager.getItemCount()-2;
                    // 获取第一个可见view的位置
                    mFirstItemPosition = linearManager.findFirstVisibleItemPosition();
                    int i = mFirstItemPosition + 1;
                    tvPager.setText(i + "/" + total);
            mP.setProgress(mLastItemPosition*100/total);

  //  DebugFlags.logD("进度条"+   mLastItemPosition  + "   " + mFirstItemPosition + "b=====" +  mLastItemPosition  * 100 / total);


                }*/
            }
        });
    }


    private void initData() {


        getCall("/Callphone/serviceNotCall");


    }


    private void getCall(String url) {
        OkHttpUtils.get()
                .url(Constants.BASE_URL + url)
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
                mTotal = data.getTotal();
                mMP = data.getList();

                tvPager.setText(1 + "/" + mMP.size());
                mP.setProgress(100 / mMP.size());


                mAdapter = new CustomAdapter(getActivity(), R.layout.item_custom, mMP);

                mRecyclerView.setAdapter(mAdapter);


                mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                        Toast.makeText(getActivity(), "pos = " + position, Toast.LENGTH_SHORT).show();
                        //adapter.notifyItemRemoved(position);
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                        return false;
                    }
                });


            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
