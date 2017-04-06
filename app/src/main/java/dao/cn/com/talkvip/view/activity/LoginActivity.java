package dao.cn.com.talkvip.view.activity;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import dao.cn.com.talkvip.R;

/**
 * @name dao.cn.com.talkvip.view.activity
 * @class name：TalkVip
 * @class describe
 * @anthor uway QQ:343251588
 * @time 2017/4/6 16:55
 * @change uway
 * @chang 2017/4/6 16:55
 * @class describe
 */

public class LoginActivity extends BaseActivity {

    private TextInputLayout mInputLayout1;
    private TextInputLayout mInputLayout2;
    private EditText mMember;

    @Override
    protected void initHead() {

    }

    @Override
    protected int getContentView() {
        return R.layout.login_activity;
    }

    @Override
    protected void initView() {
        mInputLayout1 = (TextInputLayout) findViewById(R.id.textInputLayout1);
        mInputLayout2 = (TextInputLayout) findViewById(R.id.textInputLayout2);

        mMember = mInputLayout1 .getEditText();



    }
}
