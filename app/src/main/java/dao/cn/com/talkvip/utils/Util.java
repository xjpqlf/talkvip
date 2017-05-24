package dao.cn.com.talkvip.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @name dao.cn.com.talkvip.utils
 * @class name：TalkVip
 * @class describe
 * @anthor uway QQ:343251588
 * @time 2017/5/22 10:56
 * @change uway
 * @chang 2017/5/22 10:56
 * @class describe
 */

public class Util {


    public static boolean isNetwork(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}
