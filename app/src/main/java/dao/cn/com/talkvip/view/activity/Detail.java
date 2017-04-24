package dao.cn.com.talkvip.view.activity;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import dao.cn.com.talkvip.R;
import dao.cn.com.talkvip.adapter.RemarkAdapter;
import dao.cn.com.talkvip.bean.Remark;

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

    private ImageView mIvback;
    private ImageView medit;
    private ListView mLv;
    private List<Remark> mList;
    private PopupWindow mPopWindow;

    @Override
    protected void initHead() {
        mIvback = (ImageView) findViewById(R.id.iv_backb);
        medit = (ImageView) findViewById(R.id.iv_edit);
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
        mList = new ArrayList<>();
        Remark r = new Remark("2018-11-11", "10:11", "电话不多但是是是首饰 是倒计时是撒但是打架大家爱死的大街上了", "10;22", "call");
        Remark r1 = new Remark("2018-11-11", "10:11", "电话不多但是是是首饰 是倒计时是撒但是打架大家爱死的大街上了", "10;22", "notcall");
        Remark r2 = new Remark("2018-11-11", "10:11", "电话不多但是是是首饰 是倒计时是撒但是打架大家爱死的大街上了", "10;22", "call");
        Remark r3 = new Remark("2018-11-11", "10:11", "电话不多但是是是首饰 是倒计时是撒但是打架大家爱死的大街上了", "10;22", "notcall");
        mList.add(r);
        mList.add(r1);
        mList.add(r2);
        mList.add(r3);
        RemarkAdapter adapter = new RemarkAdapter(Detail.this, mList);
        mLv.setAdapter(adapter);


    }

    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(Detail.this).inflate(R.layout.layout_popupwindow, null);
        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        //设置各个控件的点击响应
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

}


