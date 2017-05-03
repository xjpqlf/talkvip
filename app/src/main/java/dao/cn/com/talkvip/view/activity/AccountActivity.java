package dao.cn.com.talkvip.view.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import dao.cn.com.talkvip.R;

/**
 * @name dao.cn.com.talkvip.view.activity
 * @class name：TalkVip
 * @class describe
 * @anthor uway QQ:343251588
 * @time 2017/4/6 13:53
 * @change uway
 * @chang 2017/4/6 13:53
 * @class describe
 */

public class AccountActivity extends BaseActivity {


    private TextView mTvEdit;
    private TextView mSave;
    private EditText mEmil;
    private EditText mPhone;
    private TextView mPhonetext;
    private TextView mEmiltext;

    @Override
    protected int getContentView() {

        return R.layout.account_activity;
    }

    @Override
    protected void initHead() {


        mTvEdit = (TextView) findViewById(R.id.tv_edit);
        mSave = (TextView) findViewById(R.id.tv_save);




        mTvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvEdit.setVisibility(View.GONE);
                mSave.setVisibility(View.VISIBLE);
                mEmil.setFocusable(true);
                mEmil.setFocusableInTouchMode(true);
                mPhone.setFocusable(true);
                mPhone.setFocusableInTouchMode(true);



            }
        });

mSave.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mTvEdit.setVisibility(View.VISIBLE);
        mSave.setVisibility(View.GONE);

        mEmil.setFocusable(false);
        mEmil.setFocusableInTouchMode(false);
        mPhone.setFocusable(false);
        mPhone.setFocusableInTouchMode(false);

    }
});

    }

    @Override
    protected void initView() {
        ImageView iv= (ImageView) findViewById(R.id.iv_back);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mEmil = (EditText)findViewById(R.id.tv_email);
        mPhone = (EditText)findViewById(R.id.tv_phone);
        mEmil.setFocusable(false);
        mEmil.setFocusableInTouchMode(false);
        mPhone.setFocusable(false);
        mPhone.setFocusableInTouchMode(false);



    }




}
