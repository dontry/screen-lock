package com.don.testlockscreen.girl;


import java.util.Calendar;

import com.don.testlockscreen.R;
import com.don.testlockscreen.R.id;
import com.don.testlockscreen.R.layout;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AnalogClock;
import android.widget.DigitalClock;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class GirlLockScreenActivity extends Activity{
    /** Called when the activity is first created. */
	private static String TAG="DONLOCK";
	private SliderRelativeLayout sliderLayout=null;

	private Context mContext=null;
	public static int MSG_LOCK_SUCESS=1;
    protected static int screenHeight=0;
    DigitalClock myClock;
	private RelativeLayout layout_bed = null;
	public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	  this.requestWindowFeature(Window.FEATURE_NO_TITLE);
          getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
          getWindow().addFlags(FLAG_HOMEKEY_DISPATCHED);
        getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);         
        super.onCreate(savedInstanceState);
   
        mContext=GirlLockScreenActivity.this;       
      
        screenHeight=getWindowManager().getDefaultDisplay().getHeight();
     
        
   
        setContentView(R.layout.girl);
        initViews();
     
      // startService(new Intent(TestLockScreenActivity.this,donService.class));       
		sliderLayout.setMainHandler(mHandler);
		//timeHandler.postDelayed(AMorPM, 100);

	   }
    
    protected void onResume() {
		super.onResume();
	 /*   this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
		//set animation
	
	}
    
    protected void onPause(){
    	super.onPause();
    }

    protected void onDestory(){
		super.onDestroy();
	}
    
	private void initViews() {
		// TODO Auto-generated method stub
		sliderLayout=(SliderRelativeLayout)findViewById(R.id.slider_layout);
		myClock=(DigitalClock)findViewById(R.id.DigitalClock01);
		layout_bed=(RelativeLayout)findViewById(R.id.layout_bed);
		Log.i(TAG, "init views");
			
	}

/*private Handler timeHandler=new Handler(){	
	public void handleMessage(Message msg) {
	Log.i(TAG, "handleMessage: timeHandler2");
};
*/

private Handler mHandler =new Handler (){
		
		public void handleMessage(Message msg){
			
			Log.i(TAG, "handleMessage :  #### " );
			
			if(MSG_LOCK_SUCESS == msg.what)
				finish(); // �����ɹ�ʱ���������ǵ�Activity����
		}
	};

	
	 //Home public 
	public void onAttachedToWindow() {
		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
	    super.onAttachedToWindow();
	    }
	    
	//disable home key and back key
	public boolean onKeyDown(int keyCode ,KeyEvent event){
	 	 if(event.getKeyCode() == KeyEvent.KEYCODE_BACK) return true ; 
	 	 if(event.getKeyCode() == KeyEvent.KEYCODE_HOME) return true;
	 	 else return	 super.onKeyDown(keyCode, event);
	 
	 }

	/*private Runnable AMorPM=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			my_Hour=myCalendar.get(Calendar.HOUR_OF_DAY);
			if (my_Hour<=18 && my_Hour>=7) {
				layout_bed.setBackgroundColor(R.color.pink);
			} else {
				layout_bed.setBackgroundColor(R.color.deep_blue);
			}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			timeHandler.postDelayed(AMorPM, 1000);
			e.printStackTrace();
		}
		}
	};*/
	

	
}