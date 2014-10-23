package com.don.testlockscreen.gesture;

import java.io.File;
import java.util.ArrayList;

import com.don.testlockscreen.R.id;
import com.don.testlockscreen.R.layout;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class GestureLockScreenActivity extends Activity {

	private String gesPath;
	private File gesFile;
	private GestureLibrary gesLib;
	private GestureOverlayView gesOverlay;
	private static String TAG = "DONLOCK";
	public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
	     getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_FULLSCREEN|
	                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
	   getWindow().addFlags(FLAG_HOMEKEY_DISPATCHED);
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	
		
		
		super.onCreate(savedInstanceState);
	     this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        getWindowManager().getDefaultDisplay().getHeight();
	     
	        
	setContentView(layout.recognize_gesture);
	
	if (!Environment.MEDIA_MOUNTED.equals
			(Environment.getExternalStorageState())) {
		ToastShow("SD card not found. Application failure.",1);
		finish();
	}	
	
	gesPath = new File(Environment.getExternalStorageDirectory(),"gestures")
						.getAbsolutePath();
	
	gesFile = new File(gesPath);
	
	if (!gesFile.exists()) {
		ToastShow("Gestures file not found. Application failure.", 1);
	} 
	
		gesLib = GestureLibraries.fromFile(gesPath);
		if (!gesLib.load()) {
			ToastShow("gesture file cannot load. Application failure ", 1);
			finish();
		}
	gesOverlay = (GestureOverlayView) findViewById(id.gov_recognize);
	gesOverlay.addOnGesturePerformedListener(new MyGestureListener(this,gesLib));

	}
	
	
	

	private void ToastShow(String string, int i) {
		// TODO Auto-generated method stub
		if (i == 1)
			Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(this, string, Toast.LENGTH_LONG).show();

	}

	public class MyGestureListener implements OnGesturePerformedListener {

		private Context mContext;
		private GestureLibrary gesLib;
		
		public MyGestureListener(Context context, GestureLibrary lib) {
			this.mContext = context;
			this.gesLib = lib;
		}
		
		@Override
		public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
			// TODO Auto-generated method stub
			ArrayList<Prediction> predictions = gesLib.recognize(gesture);
			
			if (predictions.size()>0) {
				Prediction prediction = predictions.get(0); 
		
				if (prediction.score>3.0) {
					if (prediction.name.equals(CreateGestureActivity.gesName_unlock)) {
						Toast.makeText(mContext, "Unlock screen!", Toast.LENGTH_SHORT).show();
						finish();
					}
					if (prediction.name.equals(CreateGestureActivity.gesName_contact)) {
						Toast.makeText(mContext, "Contact!", Toast.LENGTH_SHORT).show();
				           Intent intent = new Intent(); 
				           intent.setAction(Intent.ACTION_GET_CONTENT); 
				           intent.setType("vnd.android.cursor.item/phone"); 
				           startActivity(intent); 
				           finish();
					}
					if (prediction.name.equals(CreateGestureActivity.gesName_message)) {
						Toast.makeText(mContext,  "Message", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(Intent.ACTION_VIEW);    
						//intent.putExtra("sms_body", "The SMS text");    
						intent.setType("vnd.android-dir/mms-sms");    
						startActivity(intent); 
						finish();
					}
					if (prediction.name.equals(CreateGestureActivity.gesName_camera)) {
						Toast.makeText(mContext,  "Camera", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						intent.setAction("android.media.action.STILL_IMAGE_CAMERA"); 
						startActivity(intent); 
						finish();
					}
				}else{
					Toast.makeText(mContext, "Gesture not match!", Toast.LENGTH_SHORT).show();
				}			
			} else {
				 Toast.makeText(mContext, "Gesture not exist", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	 //���ε�Home�� public 
	public void onAttachedToWindow() {
		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
	    super.onAttachedToWindow();
	    }

	@Override
    public boolean onKeyDown( int keyCode, KeyEvent event) {
           // TODO Auto-generated method stub
           if (keyCode == event. KEYCODE_HOME) {
                 return true;
          }
           if (keyCode == event. KEYCODE_BACK) {
               return true;
        }
           return super.onKeyDown(keyCode, event);
    }
	
}
