package com.don.testlockscreen;

import com.don.testlockscreen.gesture.GestureSettingActivity;
import com.don.testlockscreen.gesture.SelectGestureActivity;

import android.R.bool;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class ScreenlockMainActivity extends Activity {

	private ToggleButton enableButton;
	private TextView textView;
	public static final String VAR_LOCKSCREEN_STATUS = "LOCKSCREEN_STATUS";
	private boolean lockscreen_enable = false;
	public static final String SETTING_NAME = "MySetting";
	private RadioGroup radioGroup;
	private RadioButton radioButton_girl;
	private RadioButton radioButton_gesture;
	private int SCREENLOCK_ITEM_SELECT;
	public static final int ITEM_SLEEPING_GIRL = 1;
	public static final int ITEM_GESTURE = 2;
	public static final String VAR_LOCKSCREEN_ITEM = "LOCKSCREEN_ITEM";
	private Button gestureSettingButton;
	private int lockscreen_item;
	//private static boolean ON_CREATE_RADIOGROUP;
	
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated constructor stub
		super.onCreate(savedInstanceState);
		//ON_CREATE_RADIOGROUP = true;
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
  
	    
		setContentView(R.layout.launcher);
		enableButton = (ToggleButton) findViewById(R.id.btn_enable);
		//textView = (TextView) findViewById(R.id.txt_enable);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup_screenlock_items);
		radioButton_girl = (RadioButton) findViewById(R.id.radio_girl);
		radioButton_gesture = (RadioButton) findViewById(R.id.radio_gesture);
		gestureSettingButton = (Button) findViewById(R.id.button_gesture);

		setListeners();
		SharedPreferences setting = getSharedPreferences(SETTING_NAME,
				MODE_PRIVATE);

		// ����preference,���������Լ�������ʽ
	
		lockscreen_enable = setting.getBoolean(VAR_LOCKSCREEN_STATUS, false);
		lockscreen_item = setting.getInt(VAR_LOCKSCREEN_ITEM, 1);
		Log.i("LOCKSCREEN_STATUS", Boolean.toString(lockscreen_enable));
		Log.i("LOCKSCREEN_ITEM", Integer.toString(lockscreen_item));
		if(lockscreen_enable){
			Log.i("LOCKSCREEN_ENABLE", "open true");
		}
		else{
			Log.i("LOCKSCREEN_ENABLE", "open false");
		}
		enableButton.setChecked(lockscreen_enable);
		if (lockscreen_item == ITEM_SLEEPING_GIRL) {
			radioButton_girl.setChecked(true);
		} else if (lockscreen_item == ITEM_GESTURE) {
			radioButton_gesture.setChecked(true);
		}
	/*	if(lockscreen_enable)
		{
			textView.setText(R.string.txt_enable);
		}else {
			textView.setText(R.string.txt_disable);
		}*/
	}

	private void setListeners() {
		// TODO Auto-generated method stub
		enableButton.setOnCheckedChangeListener(btn_enable_listener);// ������������

		radioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						if (checkedId == radioButton_girl.getId()) {

							SCREENLOCK_ITEM_SELECT = ITEM_SLEEPING_GIRL;
							lockscreen_item = ITEM_SLEEPING_GIRL;

						} else {

							SCREENLOCK_ITEM_SELECT = ITEM_GESTURE;
							lockscreen_item = ITEM_GESTURE;
						}
					/*	if (ON_CREATE_RADIOGROUP) {
							enableButton.setChecked(false);
						}
						ON_CREATE_RADIOGROUP = false;*/
					}
				});

		// �����������������Button�ļ���
		gestureSettingButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(ScreenlockMainActivity.this,
						SelectGestureActivity.class);
				startActivity(mIntent);
			}
		});

	}

	private OnCheckedChangeListener btn_enable_listener = new ToggleButton.OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if (enableButton.isChecked()) {

					Intent mIntent = new Intent(ScreenlockMainActivity.this,
							donService.class);
					mIntent.putExtra("screenlock_select",
							SCREENLOCK_ITEM_SELECT);
					startService(mIntent); // �����ѡ��SCREENLOCK_ITEM_SELECT,Ҳ��������ѡ��������Ӧ������activity
					//textView.setText(R.string.txt_enable);
					lockscreen_enable = true;

				} else {
					stopService(new Intent(ScreenlockMainActivity.this, donService.class));
					//textView.setText(R.string.txt_disable);
					lockscreen_enable = false;

			}
		}
	};

	
	 public void onSaveInstanceState(Bundle outState) {
	 super.onSaveInstanceState(outState);
	  outState.putBoolean(VAR_LOCKSCREEN_STATUS, lockscreen_enable); 
	  }
	 
	 @Override public void onRestoreInstanceState(Bundle savedInstanceState) {
	 super.onRestoreInstanceState(savedInstanceState); if
	 (savedInstanceState!=null) { if
	 (savedInstanceState.containsKey(VAR_LOCKSCREEN_STATUS)) {
	 lockscreen_enable=savedInstanceState.getBoolean(VAR_LOCKSCREEN_STATUS); }
	 } }
	 

	@Override
	protected void onStop() {
		super.onStop();
		SharedPreferences setting = getSharedPreferences(SETTING_NAME, 0);
		SharedPreferences.Editor editor = setting.edit();
		if (enableButton.isChecked()) {
			Intent mIntent = new Intent(ScreenlockMainActivity.this,
					donService.class);
			mIntent.putExtra("screenlock_select",
					SCREENLOCK_ITEM_SELECT);
			startService(mIntent); // �����ѡ��SCREENLOCK_ITEM_SELECT,Ҳ��������ѡ��������Ӧ������activity
			//textView.setText(R.string.txt_enable);
			lockscreen_enable = true;
		}
		// ���濪�غ�����ѡ������
		editor.putBoolean(VAR_LOCKSCREEN_STATUS, lockscreen_enable);
		if(lockscreen_enable){
			Log.i("LOCKSCREEN_ENABLE", "stop true");
		}
		else{
			Log.i("LOCKSCREEN_ENABLE", "stop false");
		}
		
		editor.putInt(VAR_LOCKSCREEN_ITEM, lockscreen_item);
		editor.commit();
	}
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (enableButton.isChecked()) {
			Intent mIntent = new Intent(ScreenlockMainActivity.this,
					donService.class);
			mIntent.putExtra("screenlock_select",
					SCREENLOCK_ITEM_SELECT);
			startService(mIntent); // �����ѡ��SCREENLOCK_ITEM_SELECT,Ҳ��������ѡ��������Ӧ������activity
			//textView.setText(R.string.txt_enable);
		}
		if(lockscreen_enable){
			Log.i("LOCKSCREEN_ENABLE", "pause true");
		}
		else{
			Log.i("LOCKSCREEN_ENABLE", "pause false");
		}
	}

}
