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

public class TabFragment extends Fragment {

    @Bind(R.id.recyclerview1)
    XRecyclerView   mRecyclerView;
    @Bind(R.id.progressBar1)
    ProgressBar progressBar1;
    @Bind(R.id.tv_pager)
    TextView tvPager;
    @Bind(R.id.tv_total)
    TextView tvTotal;
    private View view;
    private String str;
    private CustomAdapter mAdapter;
    private List mMP;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        str = getArguments().getString("content");

        initData();
    }


    private void initData() {

        if ("未拨打".equals(str)) {
            progressBar1.setVisibility(View.VISIBLE);
            getCall("/Callphone/serviceNotCall");


        } else if ("待跟进".equals(str)) {
            progressBar1.setVisibility(View.VISIBLE);
            getCall("/Callphone/serviceFollowUp");

        } else if ("未接通".equals(str)) {
            progressBar1.setVisibility(View.VISIBLE);
            getCall("/Callphone/serviceNotThrough");

        } else if ("无意愿".equals(str)) {
            progressBar1.setVisibility(View.VISIBLE);
            getCall("/Callphone/serviceNoDesire");

        } else if ("已提取".equals(str)) {
            progressBar1.setVisibility(View.VISIBLE);
            getCall("/Callphone/serviceExtracted");
        }



        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {         outRect.set(0, 0, 0, 1);     } });




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
                mMP = data.getList();

                mAdapter = new CustomAdapter(getActivity(), R.layout.item_custom, mMP);
                mRecyclerView.setAdapter(mAdapter);

                mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position)
                    {
                        Toast.makeText(getActivity(), "pos = " + position, Toast.LENGTH_SHORT).show();
                        //adapter.notifyItemRemoved(position);
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position)
                    {
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
