package com.don.testlockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class DonBootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		ScreenlockMainActivity mActivity = null;
		// TODO Auto-generated method stub
		SharedPreferences setting=context.getSharedPreferences("MySetting", 0);
		int item_select = setting.getInt(ScreenlockMainActivity.VAR_LOCKSCREEN_ITEM, 0);
		if(setting.getBoolean(ScreenlockMainActivity.VAR_LOCKSCREEN_STATUS, false)){
		Intent mIntent = new Intent(context,donService.class);
		mIntent.putExtra(ScreenlockMainActivity.VAR_LOCKSCREEN_ITEM, item_select);
		context.startService(mIntent);
		Log.d("DonBootReceiver", "onReceived");
		}
	}
		
		

}
