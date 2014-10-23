package com.don.testlockscreen.gesture;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import com.don.testlockscreen.R;
import com.don.testlockscreen.R.id;
import com.don.testlockscreen.R.layout;
import com.don.testlockscreen.R.string;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GestureSettingActivity extends Activity {

	private Button btn_create;
	private ImageView imgv_ges;
	private ArrayList<Bitmap> gesPics;
	private ArrayList<String> gesNames;
	private GestureLibrary lib;
	private String gesPath;
	private int gesIdx;
	private String gesName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ges_setting);
		Intent intent = getIntent();
		gesIdx = intent.getIntExtra(SelectGestureActivity.GESTURE_ITEM, 0);
		switch (gesIdx) {
		case SelectGestureActivity.ITEM_UNLOCK:
			gesName = CreateGestureActivity.gesName_unlock;
			break;
		case SelectGestureActivity.ITEM_CONTACT:
			gesName = CreateGestureActivity.gesName_contact;
			break;
		case SelectGestureActivity.ITEM_MESSAGE:
			gesName = CreateGestureActivity.gesName_message;
			break;
		case SelectGestureActivity.ITEM_CAMERA:
			gesName = CreateGestureActivity.gesName_camera;
			break;
		default:
			break;
		}
		initView();
		setListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		btn_create = (Button) findViewById(R.id.btn_create_gesture);
		imgv_ges = (ImageView) findViewById(R.id.imgv_gesture_pic);

		loadGesture();
		if (!gesPics.isEmpty()) {
			int idx = gesNames.indexOf(gesName);
			if (idx != -1) {
				imgv_ges.setImageBitmap(gesPics.get(idx)); // show gesture on
															// the screen
			}
		}
	}

	private void loadGesture() { //��ȡ����
		gesPath = new File(Environment.getExternalStorageDirectory(),
				CreateGestureActivity.MY_GESTURE_FILE_NAME).getAbsolutePath();
		gesNames = new ArrayList<String>();
		gesPics = new ArrayList<Bitmap>();

		try {
			File file = new File(gesPath);
			lib = GestureLibraries.fromFile(file);

			if (!lib.load()) {
				ToastShow(getString(R.string.load_failed), 1);
			} else {
				Object[] entries = lib.getGestureEntries().toArray();

				for (int i = 0; i < entries.length; i++) {
					ArrayList<Gesture> al = lib.getGestures(entries[i]
							.toString());
					for (int j = 0; j < al.size(); j++) {
						gesNames.add(entries[i].toString());
						Gesture gs = (Gesture) al.get(j);
						gesPics.add(gs.toBitmap(180, 180, 12, Color.GREEN));
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void setListener() {
		// TODO Auto-generated method stub
		btn_create.setOnClickListener(new OnClickListener() {//�½�����

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(GestureSettingActivity.this,
						CreateGestureActivity.class);
				mIntent.putExtra(SelectGestureActivity.GESTURE_ITEM, gesIdx);
				startActivity(mIntent);
					}
		});

	}

	private void ToastShow(String string, int i) {
		// TODO Auto-generated method stub
		if (i == 1)
			Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(this, string, Toast.LENGTH_LONG).show();
	}
	
	
	
	@Override
	protected void onRestart() {
		// �½����ƺ󷵻ظý���ʱ������ˢ����ʾ���µ����ơ�
		super.onRestart();		
		loadGesture();
		if (!gesPics.isEmpty()) {
			int idx = gesNames.indexOf(gesName);
			if (idx != -1) {
				imgv_ges.setImageBitmap(gesPics.get(idx)); // show gesture on
															// the screen
			}
		}
	}
	
}
