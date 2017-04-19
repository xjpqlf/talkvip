package dao.cn.com.talkvip.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import dao.cn.com.talkvip.R;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * @name dao.cn.com.talkvip.widget
 * @class name：TalkVip
 * @class describe
 * @anthor uway QQ:343251588
 * @time 2017/4/18 11:44
 * @change uway
 * @chang 2017/4/18 11:44
 * @class describe
 */

public class MyPtrHandler implements PtrUIHandler {
    private Context context;

    private ImageView img;
    private TextView tip;

    public MyPtrHandler(Context context, ViewGroup parent) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_ptrrefresher, parent);
        this.img = (ImageView) view.findViewById(R.id.id_header_iv_img);
        this.tip = (TextView) view.findViewById(R.id.id_header_tv_tip);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        tip.setText("Pull To Refresh");
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        tip.setText("Loading......");
        RotateAnimation animation = new RotateAnimation(0, 360, img.getPivotX(), img.getPivotY());
        animation.setFillAfter(false);
        animation.setDuration(1000);
        animation.setRepeatMode(Animation.RESTART);
        img.startAnimation(animation);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame, boolean isHeader) {
        tip.setText("Load Complete");
        Toast.makeText(context, "Load Complete!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
    }
}