package dao.cn.com.talkvip.view.activity;

import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ZoomButtonsController;

import java.lang.reflect.Method;

import dao.cn.com.talkvip.R;

/**
 * Created by uway on 2016/9/7.
 */
public class Protocol extends BaseActivity {


    private WebView mWebView;

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

        mWebView = (WebView) findViewById(R.id.wv_shop_webView);


        initWebView();










    }

    private void initWebView() {
        mWebView.requestFocus();
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setSupportZoom(true);
        //   mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.setWebViewClient(new MyWebViewClient());
        //   mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.loadUrl("www.baidu.com");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Use the API 11+ calls to disable the controls
            mWebView.getSettings().setBuiltInZoomControls(true);
            mWebView.getSettings().setDisplayZoomControls(false);
        } else {
            // Use the reflection magic to make it work on earlier APIs
            getControlls();
        }
    }
    private final class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }
    }


    private void getControlls() {
        try {
            Class webview = Class.forName("android.webkit.WebView");
            Method method = webview.getMethod("getZoomButtonsController");
            ZoomButtonsController zoom_controll = (ZoomButtonsController) method.invoke(this, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}






