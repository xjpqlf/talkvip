package dao.cn.com.talkvip.view.activity;

import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ZoomButtonsController;

import java.lang.reflect.Method;

import dao.cn.com.talkvip.R;

/**
 * @name dao.cn.com.talkvip.view.activity
 * @class name：TalkVip
 * @class describe
 * @anthor uway QQ:343251588
 * @time 2017/6/13 15:35
 * @change uway
 * @chang 2017/6/13 15:35
 * @class describe
 */

public class AssessUsActivity  extends BaseActivity{
    private int webPageCount;
    private WebView  mWebView;
    @Override
    protected void initHead() {
        TextView tvthem= (TextView) findViewById(R.id.tv_theme);
        tvthem.setText("评价我们");
        RelativeLayout iv=(RelativeLayout)findViewById(R.id.rl_back);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.assess_activity;
    }

    @Override
    protected void initView() {

        mWebView = (WebView) findViewById(R.id.webView);

        mWebView.requestFocus();
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setSupportZoom(true);
        //   mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.setWebViewClient(new MyWebViewClient());
        //   mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.loadUrl("http://m.app.so.com/detail/index?pname=dao.cn.com.talkvip&id=3851131");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Use the API 11+ calls to disable the controls
            mWebView.getSettings().setBuiltInZoomControls(true);
            mWebView.getSettings().setDisplayZoomControls(false);
        } else {
            // Use the reflection magic to make it work on earlier APIs
            getControlls();
        }

     /*   webView.setWebViewClient(new WebViewClient() {
            //当点击链接时,希望覆盖而不是打开新窗口
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);  //加载新的url
                return true;    //返回true,代表事件已处理,事件流到此终止
            }
        });*/

        //点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
      /*  mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK &&  mWebView.canGoBack()) {
                        mWebView.goBack();   //后退
                        return true;    //已处理
                    }
                }
                return false;
            }
        });*/


    }


    private final class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webPageCount++;
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
