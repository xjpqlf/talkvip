package dao.cn.com.talkvip.view.activity;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import dao.cn.com.talkvip.Constants;
import dao.cn.com.talkvip.R;
import dao.cn.com.talkvip.utils.DebugFlags;
import okhttp3.Call;

/**
 * Created by uway on 2016/9/7.
 */
public class Protocol extends BaseActivity {


    private WebView mWebView;

    @Override
    protected void initHead() {

        RelativeLayout iv = (RelativeLayout) findViewById(R.id.rl_back);
        TextView tv = (TextView) findViewById(R.id.tv_theme);
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


        init();


    }

    private void init() {

        String url = Constants.BASE_URL+"/Agreement/getAgreement";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("did", "225")

                .build()
                .execute(new StringCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {

                             }

                             @Override
                             public void onResponse(String response, int id) {
                                 DebugFlags.logD("我的协议"+response);

                                 try {

                                     JSONObject j=new JSONObject(response);
                                     String text1=j.getString("data");
                                     JSONObject json=new JSONObject(text1);
                                     String text=json.getString("text");
                                     WebSettings webSettings = mWebView.getSettings();
                                     webSettings.setDefaultTextEncodingName("UTF-8");
                                     mWebView.loadData(text, "text/html; charset=UTF-8", null);
                                     DebugFlags.logD("我的协议"+text);
                                 } catch (JSONException e) {
                                     e.printStackTrace();
                                 }

                             }
                         }

                );


    }


}



