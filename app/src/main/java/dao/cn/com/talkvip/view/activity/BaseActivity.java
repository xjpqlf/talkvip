package dao.cn.com.talkvip.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Description:Activity基础类
 * User: xjp
 * Date: 2015/6/17
 * Time: 16:54
 */

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initView();
        initHead();
    }

    protected abstract void initHead();

    /**
     * 查找view
     */


    /**
     * 加载布局
     *
     * @return
     */
    protected abstract int getContentView();

    /**
     * 初始化view
     */
    protected abstract void initView();

}