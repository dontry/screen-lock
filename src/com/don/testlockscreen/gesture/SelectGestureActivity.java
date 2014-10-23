/**
 * 
 */
package com.don.testlockscreen.gesture;

import com.don.testlockscreen.R;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * @author Administrator
 *
 */


public class SelectGestureActivity extends Activity {
	private RadioGroup radioGroup;
	private RadioButton radioButton_unlock;
	private RadioButton radioButton_message;
	private RadioButton radioButton_contact;
	private RadioButton radioButton_camera;
	private Button okButton;
	public static final String GESTURE_ITEM = "GESTURE_ITEM";
	private int GESTURE_ITEM_SELECT;
	public static final int ITEM_UNLOCK = 1;
	public static final int ITEM_CONTACT = 2;
	public static final int ITEM_MESSAGE = 3;
	public static final int ITEM_CAMERA = 4;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.selection_ges);
	radioGroup = (RadioGroup)findViewById(R.id.radioGroup1);
	radioButton_unlock = (RadioButton)findViewById(R.id.radioUnlock);
	radioButton_contact = (RadioButton)findViewById(R.id.radioContact);
	radioButton_message = (RadioButton)findViewById(R.id.radioMessage);
	radioButton_camera = (RadioButton)findViewById(R.id.radioCamera);
	okButton = (Button)findViewById(R.id.button_ok_g);
	okButton.setEnabled(false);
	setListeners();
}
private void setListeners() {
	// TODO Auto-generated method stub
	okButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(SelectGestureActivity.this, GestureSettingActivity.class);
			intent.putExtra(GESTURE_ITEM, GESTURE_ITEM_SELECT);
			startActivity(intent);
		}
	});
	
	radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			if (checkedId == radioButton_unlock.getId() ) {
				GESTURE_ITEM_SELECT = ITEM_UNLOCK;
			}
			if (checkedId == radioButton_contact.getId()) {
				GESTURE_ITEM_SELECT = ITEM_CONTACT;
			}
			if (checkedId == radioButton_message.getId()) {
				GESTURE_ITEM_SELECT = ITEM_MESSAGE;
			}
			if (checkedId == radioButton_camera.getId()) {
				GESTURE_ITEM_SELECT = ITEM_CAMERA;
			}
			okButton.setEnabled(true);
		}
	});
}
}
