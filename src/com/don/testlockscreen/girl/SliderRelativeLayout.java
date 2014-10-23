package com.don.testlockscreen.girl;

import com.don.testlockscreen.R;
import com.don.testlockscreen.R.anim;
import com.don.testlockscreen.R.drawable;
import com.don.testlockscreen.R.id;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SliderRelativeLayout extends RelativeLayout {

	private static String TAG = "SliderRelativeLayout";
	private static String TAG2 = "ActionEvent";
	private static String TAG3 = "Girl";
	// private TextView tv_slider_icon=null; //intial control, determine if it
	// is dragged or not
	private ImageView imgv_slider_icon = null;
	private MyQuilt layout_quilt = null;
//	private Bitmap dragBitmap = null;//
	private Context mContext = null;// Bitmap object for bitmap dragging
	private ImageView girl = null;
	private Handler mainHandler = null;// Handler object to communicate with
										// main activity
	private AnimationDrawable animzzzDrawable = null;
	private ImageView imgView_zzzImageView;// 动画图片
	private int init_down_CorY;  //finger initial down coordination Y
	private int slide_distance_threshold=GirlLockScreenActivity.screenHeight/4;
	private  int initCorY;  // initial coordination Y of quilt
	private ImageView sock;
	private boolean messageReceived=false;

	public SliderRelativeLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	public SliderRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	public SliderRelativeLayout(Context context, AttributeSet attrs,
			int defStyle) {
		// TODO Auto-generated constructor stub
		super(context, attrs, defStyle);
		mContext = context;

	}

	protected void onFinishInflate() {
		super.onFinishInflate();
		// this control determine if it is in slider area. visible when sliding
		imgv_slider_icon = (ImageView) findViewById(R.id.quilt1);
		girl = (ImageView) findViewById(R.id.girl);
		imgView_zzzImageView = (ImageView) findViewById(R.id.zzz_sign);
		animzzzDrawable = (AnimationDrawable) imgView_zzzImageView
				.getBackground();
		mHandler.postDelayed(AnimationDrawableTask, 300); // start animation
		layout_quilt = (MyQuilt) findViewById(R.id.mylayout);
		sock=(ImageView)findViewById(R.id.sock);
		mContext.registerReceiver(messageReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
		init_down_CorY=dip2px(mContext, 300);
	}

	// private int mLastMoveX=1000; //initial value is large enough to be
	// invisible
		private int mLastMoveY = initCorY;

	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
	
		Log.i(TAG, "onTouchEvent" + "X is " + x + " Y is " + y);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastMoveY = (int) event.getY();
			layout_quilt.getCorY(mLastMoveY);
			return handleActionDownEvent(event);
		case MotionEvent.ACTION_MOVE:
			mLastMoveY = y;
			layout_quilt.getCorY(mLastMoveY);
			handleActionMoveEvent(event);
			return true;
		case MotionEvent.ACTION_UP:
			// 处理Action_Up事件： 判断是否解锁成功，成功则结束我们的Activity ；否则 ，缓慢回退该图片。
			handleActionUpEvent(event);
			return true;

		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	private void handleActionMoveEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.e(TAG, "onMovingEvent");
		mHandler.post(dragImgTask);
	}

	private static int BACK_DURATION = 20; // 20ms
	private static float VE_VERTICAL = 0.7f;// 0.1dip/ms

	// 重置初始的状态，显示tv_slider_icon图像，使bitmap不可见
	private void resetViewState() {
		// TODO Auto-generated method stub
		mLastMoveY = initCorY;
		layout_quilt.getCorY(mLastMoveY);
		imgv_slider_icon.setVisibility(View.VISIBLE);
		layout_quilt.invalidate();
	}

	private Runnable dragImgTask = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.e(TAG, "DraggingImage");
			layout_quilt.invalidate();// repaint
		}
	};

	private Runnable backDragImgTask = new Runnable() {

		public void run() {
			// TODO Auto-generated method stub
			mLastMoveY = mLastMoveY - (int) (BACK_DURATION * VE_VERTICAL);
			layout_quilt.getCorY(mLastMoveY);
			Log.i(TAG2, "BackDragImgTask******mLastMoveY " + mLastMoveY);
			layout_quilt.invalidate();// repaint
			// determine if it needs repaint or not. ending when go back to
			// initial position
			boolean shouldEnd = Math.abs(mLastMoveY - getHeight()) <= 8;

			if (!shouldEnd) {
				mHandler.postDelayed(backDragImgTask, BACK_DURATION);
			} else {
				resetViewState();
			}
		}
	};

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(TAG, "handleMessage: mHandler");
		}
	};

	private Handler mHandler2 = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(TAG, "handleMessage: mHandler2");
		}
	};

	public void setMainHandler(Handler handler) {
		mainHandler = handler;// handler object of main activity
	}

	// determine if unlock
	private void handleActionUpEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int y = (int) event.getY();
		int slide_distance=y-init_down_CorY;
		Log.i(TAG2, "handleActionUpEvent:y-->" + y + "getBottom()"
				+ imgv_slider_icon.getBottom());
	
		// animzzzDrawable.start();
		// distance less than 30 dip means unlock successfully
		//boolean isSuccess = Math.abs(y - imgv_slider_icon.getBottom()) <= 40;
			boolean isSuccess=slide_distance>=slide_distance_threshold;
		if (isSuccess) {
			Toast.makeText(mContext, "Unlock Completes", 1000).show();
			resetViewState();
			animzzzDrawable.stop();
			// end our main activity
			mainHandler.obtainMessage(GirlLockScreenActivity.MSG_LOCK_SUCESS)
					.sendToTarget();
		} else {
			// if unlock fail, go back
			mLastMoveY = y; // record current coordination
			layout_quilt.getCorY(mLastMoveY);
			int distance = y - getBottom();
			// only retrieve when having enough distance
			Log.i(TAG, "HandleActionUpEvent:mLastMoveY-->" + mLastMoveY
					+ "sliding distance-->" + distance);
			if (distance >=0) {
				mHandler.postDelayed(backDragImgTask, BACK_DURATION);
			} else {
				resetViewState();
				girl.setBackgroundResource(R.drawable.doll0);
				mHandler2.postDelayed(motionUp, 300);
				imgView_zzzImageView.setVisibility(VISIBLE);
			}
		}
	}

	// if press in valid area

	private boolean handleActionDownEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		Rect rect = new Rect();
		Rect sock_area=new Rect();
		imgv_slider_icon.getHitRect(rect);
		sock.getHitRect(sock_area);
		boolean isHit_quilt = rect.contains((int) event.getX(), (int) event.getY());
		boolean isHit_sock=sock_area.contains((int)event.getX(), (int)event.getY());
	
//		boolean isHit = event.getY()>400;
		if (isHit_quilt) {
			girl.setBackgroundResource(R.drawable.doll1);
			mHandler2.post(motionDown);
				Log.i(TAG, "HandleActionDownEvent is Hit Quilt" + isHit_quilt);
			
		}
		if (isHit_sock && messageReceived ) {
			sock.setBackgroundResource(R.drawable.sock2);
			Intent intent = new Intent(Intent.ACTION_VIEW);
			ComponentName cmp=new ComponentName("com.android.mms","com.android.mms.ui.ConversationList");
		 intent.setType("vnd.android-dir/mms-sms");
			
		 messageReceived=false;
		intent.setComponent(cmp);
		 intent.setData(Uri.parse("content://mms-sms/conversations"));
			mContext.startActivity(intent);
			//this.startActivity(intent);
			Log.i(TAG, "HandleActionDownEvent is Hit Sock"+isHit_sock);
		}

	
		
		
		
		return isHit_quilt;
	}

	// 震动一下下咯
	private void vribrate1() {
		Vibrator vibrator = (Vibrator) mContext
				.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(200);
	}

	public Runnable motionUp = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.i(TAG3, "handsUP");

		}
	};

	public Runnable motionDown = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.i(TAG3, "handsDown");

			Animation shake = AnimationUtils.loadAnimation(mContext,
					R.anim.shake);
			startAnimation(shake);
			
			Log.w(TAG, "anim stop");
			imgView_zzzImageView.setVisibility(INVISIBLE);
			imgv_slider_icon.setVisibility(View.INVISIBLE);
			vribrate1(); // 震动一下

		}
	};
	private Runnable AnimationDrawableTask = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			animzzzDrawable.start();
			mHandler.postDelayed(AnimationDrawableTask, 300);
		}
	};
	
	public static int dip2px(Context context, float dipValue) {
		final float scale=context.getResources().getDisplayMetrics().density;
		return (int)(dipValue*scale+0.5f);
	}
	
	private BroadcastReceiver messageReceiver =new BroadcastReceiver(){
		private static final String mACTION="android.provider.Telephony.SMS_RECEIVED";

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(intent.getAction().equals(mACTION))
			{
				sock.setBackgroundResource(R.drawable.sock3);
				Log.i(TAG, "RECEIVED MESSAGE");
				messageReceived=true;
			}		
		}		
	};
}
