package com.don.testlockscreen;

import com.don.testlockscreen.gesture.GestureLockScreenActivity;
import com.don.testlockscreen.girl.GirlLockScreenActivity;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class donService extends Service {

	
	private static String TAG = "donLockService";
	private Intent donLockIntent = null;
	protected static Boolean disableLockscreen_flag;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public void onCreate() {
		super.onCreate();		
		//KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		//KeyguardManager.KeyguardLock mKeyguardLock = mKeyguardManager.newKeyguardLock("donLock 1");
		//mKeyguardLock.disableKeyguard();		
		/* ע��㲥 */
		  IntentFilter mScreenFilter = new IntentFilter();  
	        mScreenFilter.addAction(Intent.ACTION_SCREEN_ON);  
	        mScreenFilter.addAction(Intent.ACTION_SCREEN_OFF);  	      
			donService.this.registerReceiver(mScreenReceiver, mScreenFilter);	
		mScreenFilter.setPriority(android.content.IntentFilter.SYSTEM_HIGH_PRIORITY-1);	
		 registerReceiver(mScreenReceiver, mScreenFilter);
		disableLockscreen_flag=false;	
		 
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
	 int ITEM_SELECT = intent.getIntExtra("screenlock_select", 1);	
     
		//intent ���ͨ�ţ�		
	   TelephonyManager mTelephonyManager=(TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
	   mTelephonyManager.listen(new TeleListener(),PhoneStateListener.LISTEN_CALL_STATE);
/*	   if(intent == null || intent.getAction() == null)
           return START_NOT_STICKY;*/
	   
		   //���ITEM_SELECT������Ӧ�ĵ�����ѡ�
		 if (ITEM_SELECT == ScreenlockMainActivity.ITEM_SLEEPING_GIRL ) {
				donLockIntent = new Intent(donService.this,
						GirlLockScreenActivity.class);
				donLockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		 
		 else  if (ITEM_SELECT == ScreenlockMainActivity.ITEM_GESTURE) {
			 donLockIntent = new Intent(donService.this,
						GestureLockScreenActivity.class);
				donLockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}		
		return Service.START_REDELIVER_INTENT;  //if the service start again, it will deliver the intent. It avoids the error occur.
		
	}

	public void onDestroy() {
		super.onDestroy();
		donService.this.unregisterReceiver(mScreenReceiver);
	//		startService(new Intent(donService.this, donService.class));
	}

	// ��Ļ�䰵/�����Ĺ㲥 �� ����Ҫ����KeyguardManager����Ӧ����ȥ�����Ļ��
	private BroadcastReceiver mScreenReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			//abortBroadcast();
			Log.i(TAG, intent.toString());
			if(action.equals("android.intent.action.SCREEN_ON"))
			{
				startActivity(donLockIntent);
			}
		}

	};
	
	   class TeleListener extends PhoneStateListener {
	    	private static final String TAG = "TELEPHONE";

	    	@Override  
	    	public void onCallStateChanged(int state, 
	    	String incomingNumber) {  
	    	super.onCallStateChanged(state, incomingNumber);  
	    	switch (state) {  
	    	case TelephonyManager.CALL_STATE_IDLE: { 
	    	Log.i(TAG, "CALL_STATE_IDLE");  
	    	donService.disableLockscreen_flag=false;
	    	break;  
	    	}  
	    	case TelephonyManager.CALL_STATE_OFFHOOK: {  
	    	Log.i(TAG, "CALL_STATE_OFFHOOK");  
	    	donService.disableLockscreen_flag=true;
	    	break;  
	    	}  
	    	case TelephonyManager.CALL_STATE_RINGING: {  
	    	Log.e(TAG, "CALL_STATE_RINGING");  
	    	donService.disableLockscreen_flag=true;
	    	break;  
	    	}  
	    	default:  
	    	break;  
	    	}  
	    	}  
	    	}  	   
	
}
