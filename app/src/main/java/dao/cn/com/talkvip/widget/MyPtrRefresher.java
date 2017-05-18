package dao.cn.com.talkvip.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import dao.cn.com.talkvip.R;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class MyPtrRefresher extends FrameLayout implements PtrUIHandler {

    private View view;
    private ImageView iv_refresh;
    private TextView tv_refresh;

    private AnimationDrawable ad;

    public MyPtrRefresher(Context context) {
        this(context, null);
    }

    public MyPtrRefresher(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyPtrRefresher(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.view_ptrrefresher, this, false);
        iv_refresh = (ImageView) view.findViewById(R.id.id_header_iv_img);
        tv_refresh = (TextView) view.findViewById(R.id.id_header_tv_tip);
        iv_refresh.setBackgroundResource(R.drawable.loadings);
        AnimationDrawable anim = (AnimationDrawable) iv_refresh.getBackground();
        anim.start();
       ad = (AnimationDrawable) iv_refresh.getDrawable();

        addView(view);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        tv_refresh.setText("加载中");
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        if (ad != null && !ad.isRunning()) {
            ad.start();
        }
        iv_refresh.setVisibility(VISIBLE);
        tv_refresh.setText("努力加载中");
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame, boolean isHeader) {
        if (ad != null && ad.isRunning()) {
            ad.stop();
        }
        iv_refresh.setVisibility(GONE);
        tv_refresh.setText("刷新完成");
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();

        if (currentPos < mOffsetToRefresh) {
            //未到达刷新线
            if (status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                tv_refresh.setText("下拉刷新");
            }
        } else if (currentPos > mOffsetToRefresh) {
            //到达或超过刷新线
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                tv_refresh.setText("正在刷新" +
                        "" +
                        "");
            }
        }
    }
}
