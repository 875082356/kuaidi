package com.hzgzsoft.logisticsmanage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by a on 2016/7/18.
 */
public class SP_Utils {

    private static SharedPreferences sharedPreferences;

    public static void putSP_String(Context context, String key,String value) {
        sharedPreferences = context.getSharedPreferences("config",
                Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = sharedPreferences.edit();// 通过sharedPreferences对象获取一个Editor对象
        edit.putString(key, value);
        edit.commit();
    }

    public static String getSP_String(Context context,String key,String value) {
        sharedPreferences = context.getSharedPreferences("config",
                Context.MODE_PRIVATE);// 通过Context对象创建一个SharedPreference对象

        String result = sharedPreferences.getString(key, value);//
        return result;

    }
    public static void putSP_Int(Context context, String key,int value) {
		sharedPreferences = context.getSharedPreferences("config",
				Context.MODE_PRIVATE);

		Editor edit = sharedPreferences.edit();// 通过sharedPreferences对象获取一个Editor对象
		edit.putInt(key, value);
		edit.commit();
	}
	
	
	
	public static int getSP_Int(Context context,String key,int value) {
		sharedPreferences = context.getSharedPreferences("config",
				Context.MODE_PRIVATE);// 通过Context对象创建一个SharedPreference对象

		int result = sharedPreferences.getInt(key, value);//
		return result;

	}
	public static void Delete_String(Context context) {
		sharedPreferences = context.getSharedPreferences("config",
				Context.MODE_PRIVATE);// 通过Context对象创建一个SharedPreference对象
		Editor edit = sharedPreferences.edit();
		//edit.remove(key).commit();
		edit.clear().commit();
	}
}
