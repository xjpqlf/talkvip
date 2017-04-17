package dao.cn.com.talkvip.view.activity;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import dao.cn.com.talkvip.R;

/**
 * Created by uway on 2016/9/7.
 */
public class Protocol extends BaseActivity {


    @Override
    protected void initHead() {

   ImageView iv= (ImageView) findViewById(R.id.iv_back);
     TextView tv= (TextView) findViewById(R.id.tv_theme);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv.setText("用户协议");
    }

    @Override
    protected int getContentView() {
        return R.layout.protocco;
    }

    @Override
    protected void initView() {

        WebView wb= (WebView) findViewById(R.id.wv_shop_webView);

    }
}





