package dao.cn.com.talkvip.utils;

import android.util.Log;

public class DebugFlags {

	private static final boolean flag = true;

	private static final String TAG = "TalkVip";

	public static void logD(String msg){
		if(flag)
			Log.d(TAG, msg);
	}
	
	public static void logE(String msg){
		if(flag)
			Log.e(TAG, msg);
	}
	
	public static void logV(String msg){
		if(flag)
			Log.v(TAG, msg);
	}
	
	public static void logW(String msg){
		if(flag)
			Log.w(TAG, msg);
	}
	
	public static void logI(String msg){
		if(flag)
			Log.i(TAG, msg);
	}

}
