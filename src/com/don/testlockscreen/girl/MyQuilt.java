package com.don.testlockscreen.girl;


import com.don.testlockscreen.R;
import com.don.testlockscreen.R.drawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

public class MyQuilt extends RelativeLayout {
	private static String TAG5 = "myRelativeLayout";
	public Bitmap dragBitmap;
	private Context mContext;
	private int mLastMoveY;
	private int initCorY =0; //what initCorY is perfect?
	

	public MyQuilt(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		initDragBitmap();
	}

	public MyQuilt(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		initDragBitmap();
	}

	public MyQuilt(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		mContext = context;
		initDragBitmap();
	}

	private void initDragBitmap() {
		// TODO Auto-generated method stub
		initCorY=dip2px(mContext, 300); 
		if (dragBitmap == null)
			Log.w(TAG5, "initBitmap");
		dragBitmap = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.quilt);

	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Log.e(TAG5, "onDraw ######");
		// ͼƬ���������ƶ�
		invalidateDragImg(canvas);
	}

	private void invalidateDragImg(Canvas canvas) {
		// TODO Auto-generated method stub
		Log.i(TAG5, "handleActionUpEvent:invalidateDragImg");
		int drawYCor = mLastMoveY;
		int drawXCor = 0;
		Log.i(TAG5, "invalidateDragImg" + "drawXCor" + drawXCor
				+ "and drawYCor " + drawYCor);
		canvas.drawBitmap(dragBitmap, drawXCor,
				drawYCor < initCorY ? initCorY  : drawYCor, null);
	}

	int getCorY(int y) {
		return mLastMoveY = y;
	}
	
	public static int dip2px(Context context, float dipValue) {
		final float scale=context.getResources().getDisplayMetrics().density;
		return (int)(dipValue*scale+0.5f);
	}
	


}
