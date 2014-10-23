package com.don.testlockscreen.gesture;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import com.don.testlockscreen.R;
import com.don.testlockscreen.R.id;
import com.don.testlockscreen.R.layout;
import com.don.testlockscreen.R.string;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.opengl.ETC1;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateGestureActivity extends Activity {
	/** Called when the activity is first created. */
	private Gesture ges;
	private GestureLibrary library;
	private GestureOverlayView overlayView;
	private Button btn_ok, btn_cancel;
	private String gesPath;
	private static int gesIdx;
	private static String gesName;
	public static final String gesName_unlock = "Gesture_unlock";
	public static final String gesName_contact = "Gesture_contact";
	public static final String gesName_message = "Gesture_message";
	public static final String gesName_camera = "Gesture_camera";
//	private FileHelper fileHelper;
	private File gesFile;
	public static final String MY_GESTURE_FILE_NAME = "gestures";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_gesture);
		Intent intent = getIntent();
		gesIdx = intent.getIntExtra(SelectGestureActivity.GESTURE_ITEM, 0);

		if (!Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {

			ToastShow("SD card not found! Application cannot run.", 1);
			finish();
		}

		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		overlayView = (GestureOverlayView) findViewById(R.id.gov_gesture);

		/*
		 * gesPath = new File(Environment.getExternalStorageDirectory(),
		 * "gestures").getAbsolutePath();
		 */

	//	fileHelper = new FileHelper();

		/*
		 * if (!fileHelper.isFileExist(MY_LOCKSCREEN_DIR_NAME)) { gesDir =
		 * fileHelper.createSDDir(MY_LOCKSCREEN_DIR_NAME); }else{ gesDir = }
		 */

		// gesName = MY_LOCKSCREEN_DIR_NAME + "/" + MY_LOCKSCREEN_DIR_NAME;

		/*	gesFile = fileHelper.createSDFile(MY_GESTURE_FILE_NAME);
		gesPath = gesFile.toString();*/
		gesPath = new File(Environment.getExternalStorageDirectory(),
				MY_GESTURE_FILE_NAME).getAbsolutePath();  //����gesture���ƿ����ڵľ��·��

		setListener();

	}

	private void setListener() {
		//overlayview�����ƻ������������Ƽ�������
		overlayView
				.addOnGestureListener(new GestureOverlayView.OnGestureListener() {

					@Override
					public void onGestureStarted(GestureOverlayView overlay,
							MotionEvent event) {
						//�����ƻʱ��OK buttonʧ��
						btn_ok.setEnabled(false);
						ges = null;
					}

					@Override
					public void onGestureEnded(GestureOverlayView overlay,
							MotionEvent event) {
						// �������Ƶ�ges
						ges = overlay.getGesture();
						if (ges != null) {
							btn_ok.setEnabled(true); //OK button����
						} else {

						}
					}

					@Override
					public void onGestureCancelled(GestureOverlayView overlay,
							MotionEvent event) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onGesture(GestureOverlayView overlay,
							MotionEvent event) {
						// TODO Auto-generated method stub

					}
				});

		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// String gesName = editxt.getText().toString();
				try {
					//�����ļ����Լ����ƿ�
					gesFile = new File(gesPath);
					library = GestureLibraries.fromFile(gesPath);
					switch (gesIdx) {
					case SelectGestureActivity.ITEM_UNLOCK:
						gesName = gesName_unlock;
						break;
					case SelectGestureActivity.ITEM_CONTACT:
						gesName = gesName_contact;
						break;
					case SelectGestureActivity.ITEM_MESSAGE:
						gesName = gesName_message;
						break;
					case SelectGestureActivity.ITEM_CAMERA:
						gesName = gesName_camera;
						break;
					default:
						break;
					}
					if (!gesFile.exists()) {
						//�����ƿ�����½�������
					
						library.addGesture(gesName, ges);
						if (library.save()) {  //�ж��Ƿ񱣴�ɹ�
							//���overlayView�����ƺۼ�
							overlayView.clear(true);
							btn_ok.setEnabled(false);
							ToastShow(getString(R.string.save_success) + ":"
									+ gesPath, 1);
						} else {
							ToastShow(getString(R.string.save_failed) + ":"
									+ gesPath, 1);
						}
					} else {
						// �ļ�����ʱ�ȶ�ȡ�ѱ����Gesture
						if (!library.load()) {
							ToastShow(getString(R.string.load_failed), 1);  //fail to load library???
						} else {
							Set<String> enSet = library.getGestureEntries(); //��ȡ���ƿ��е����Ƽ�
							if (enSet.contains(gesName)) {  //�������ͬ���ֵ����Ƽ����ǣ������������ơ���������ֻ�ñ���һ������
																				//Ĭ��������Gesture��ÿ���½��Զ������ƶ��Ḳ��ԭ�ȵ�����
								ArrayList<Gesture> al = library
										.getGestures(gesName);
								for (int i = 0; i < al.size(); i++) {
									library.removeGesture(gesName, al.get(i));
								}
							}
							library.addGesture(gesName, ges);
							if (library.save()) {
								btn_ok.setEnabled(false);
								overlayView.clear(true);
								ToastShow(gesName+" "+getString(R.string.save_success)
										+ ":" + gesPath, 1);
							} else {
								ToastShow(gesName+" "+getString(R.string.save_failed) + ":"
										+ gesPath, 1);
							}
						}
					}
					finish();  //�ɹ��������ƺ��˳�
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}
		});

		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btn_ok.setEnabled(false);
				overlayView.clear(true);
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
	
	 //���ε�Home�� public 
		public void onAttachedToWindow() {
			this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
		    super.onAttachedToWindow();
		    }
		    
		//���ε�Back�� 
		public boolean onKeyDown(int keyCode ,KeyEvent event){
		 	 if(event.getKeyCode() == KeyEvent.KEYCODE_BACK) return true ; 
		 	 if(event.getKeyCode() == KeyEvent.KEYCODE_HOME) return true;
		 	 else return	 super.onKeyDown(keyCode, event);
		 
		 }
}

