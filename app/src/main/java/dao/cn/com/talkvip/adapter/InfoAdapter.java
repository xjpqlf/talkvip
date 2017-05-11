package dao.cn.com.talkvip.adapter;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import dao.cn.com.talkvip.R;
import dao.cn.com.talkvip.bean.Custom;
import dao.cn.com.talkvip.bean.CustomFrist;
import dao.cn.com.talkvip.utils.RsaCall;
import dao.cn.com.talkvip.utils.ToastUtil;
import dao.cn.com.talkvip.view.activity.Detail;
import dao.cn.com.talkvip.view.activity.RemarkActivity;
import okhttp3.Call;

/**
 * Created by uway on 2016/9/10.
 */
public class InfoAdapter extends BaseAdapter {

    private List<CustomFrist> list;
    private Context context;

    public InfoAdapter(Context context, List<CustomFrist> list) {
        this.context = context;
        this.list = list;
    }
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_custom, null);

            holder.name = (TextView) convertView
                    .findViewById(R.id.tv_name);
            holder.mobile= (TextView) convertView
                    .findViewById(R.id.tv_mobile);
            holder.dc = (TextView) convertView
                    .findViewById(R.id.tv_dc);
    holder.iv= (ImageView) convertView.findViewById(R.id.iv_item_go);
    holder.guid= (RelativeLayout) convertView.findViewById(R.id.rl_guid);
    holder.rl= (RelativeLayout) convertView.findViewById(R.id.rl_name);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Custom custom=list.get(position).getCustom();

        holder.mobile.setText(custom.getMobile());
        holder.name.setText(custom.getId());
        holder.dc.setText(custom.getInputtime());
        if (list.get(position).isFirst()){

            holder.guid.getChildAt(0).setVisibility(View.VISIBLE);
        }else{
            holder.guid.getChildAt(0).setVisibility(View.GONE);
        }


holder.iv.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent( context, Detail.class);
        intent.putExtra("id",position);

        context.startActivity(intent);
    }
});


      /*  holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              //  getPhoneNum(list.get(position).getCustom().getMobile(),list.get(position).getCustom().getId());







            }
        });*/
        return convertView;
    }
    class ViewHolder {
        TextView name;
        TextView  mobile;
        TextView dc;
        ImageView iv;
      RelativeLayout guid;
      RelativeLayout rl;

    }
    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;

    private void getPhoneNum(String phone,String id) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        Random rand = new Random();
        int i = rand.nextInt(100);

        int mA=rand.nextInt(10000000);

        String accountId = "1";
        String timeStamp = i + str;
        String order = mA + "";
        String sign = accountId + timeStamp + order;


        String mSigns = RsaCall.encryptByPublic(sign);
        Log.d("时间戳", mSigns);
        OkHttpUtils.post()
                .url("http://c1.dev.talkvip.cn/Authorization")
                .addParams("accuntID", accountId)
                .addParams("callingPhone", "13971410254")
                .addParams("calledPhone", "13971410254")
                .addParams("dataID",id )
                .addParams("order", order)
                .addParams("timeStamp", timeStamp)
                .addParams("resultURL", "www.baidu.com")
                .addParams("notifyURL", "www.baidu.com")
                .addParams("remark", "")
                .addParams("type", "2")
                .addParams("line", "E")
                .addParams("signInfo", mSigns).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Log.d("电话", "onResponse: " + response);

                try {
                    JSONObject json=new JSONObject(response);
                    ToastUtil.showInCenter(json.getString("message"));
                    String code=json.getString("resultCode");

                    if ("8888".equals(code)){

                      CallPhone("1397163738");


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }





    private void CallPhone(String phone) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        //url:统一资源定位符
        //uri:统一资源标示符（更广）
        intent.setData(Uri.parse("tel:" + phone));
        //开启系统拨号器
       context. startActivity(intent);


    }







    public class PhoneBroadcastReceiver extends BroadcastReceiver {


        String TAG = "打电话";
        TelephonyManager telMgr;

        @Override
        public void onReceive(Context context, Intent intent) {


            telMgr = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            switch (telMgr.getCallState()) {
                //来电
                case TelephonyManager.CALL_STATE_RINGING:
                    String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    Log.v(TAG, "number:" + number);


                    break;
                //响铃
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                //挂断
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.v(TAG,"电话挂断了");

                    Intent inten1=new Intent(context,RemarkActivity.class);

                   context. startActivity(inten1);


                    break;
            }

        }

    }







}
