package dao.cn.com.talkvip.utils;

import android.app.Activity;
import android.os.Build;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import dao.cn.com.talkvip.R;
import dao.cn.com.talkvip.TVApplication;

/**
 * Created by liuhuan-mac on 16/5/3.
 */
public class ToastUtil {
    public static void showInCenter(int strId) {

        TVApplication context = TVApplication.getInstance();

        Toast toast =Toast.makeText(context,strId,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public static void showInCenter(String str) {
        TVApplication context =  TVApplication.getInstance();
        Toast toast =Toast.makeText(context,str,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public static void show(int strId) {
        TVApplication context =  TVApplication.getInstance();
        Toast toast =Toast.makeText(context,strId,Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void show(String str) {
        TVApplication context =  TVApplication.getInstance();
        Toast toast =Toast.makeText(context,str,Toast.LENGTH_SHORT);
        toast.show();
    }
    //设置状态栏一体化
    public static void initSystemBar(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(activity, true);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);

            tintManager.setStatusBarTintEnabled(true);
            // 使用颜色资源
            tintManager.setStatusBarTintResource(R.color.material_primary_color);
        }
    }
    private static void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
