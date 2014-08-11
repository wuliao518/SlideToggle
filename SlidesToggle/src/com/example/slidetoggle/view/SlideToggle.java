package com.example.slidetoggle.view;

import com.example.slidetoggle.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * @author 伍燎
 * @version 1.0
 * 滑动按钮
 */
public class SlideToggle extends View implements OnTouchListener{
	//当前状态
	private Boolean currentStatus=true;
	private Boolean isListener=false;
	//按下的位置和当前的位置
	private float startX,currentX;
	private OnSwitchChangeListener listener;
	//记录是否移动
	private Boolean isMove=false;
	//是否按下
	private Boolean isStart=false;
	private Bitmap mSwitchOn,mSwitchOff,mSwitch;
	public SlideToggle(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}
	private void initView() {
		mSwitchOn=BitmapFactory.decodeResource(getResources(), R.drawable.switch_on);
		mSwitchOff=BitmapFactory.decodeResource(getResources(), R.drawable.switch_off);
		mSwitch=BitmapFactory.decodeResource(getResources(), R.drawable.button);
		setOnTouchListener(this);
		
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isStart=true;
			isMove=true;
			startX=event.getX();
			currentX=startX;
			break;
		case MotionEvent.ACTION_MOVE:
			currentX=event.getX();
			break;
		case MotionEvent.ACTION_UP:
			isMove=false;
			break;

		default:
			break;
		}
		invalidate();
		return true;
	}

	public void setSwitch(boolean status) {
		currentStatus=status;
		
	}
	@Override
	protected void onDraw(Canvas canvas) {
		int x=0;
		Matrix matrix=new Matrix();
		Paint paint=new Paint();	
		if(currentStatus){			
			canvas.drawBitmap(mSwitchOn, matrix, paint);
			x=mSwitchOn.getWidth()-mSwitch.getWidth();
			if(isStart&&startX<(mSwitchOn.getWidth()-mSwitch.getWidth()/2)){
				x=(int) (startX-mSwitch.getWidth()/2);
			}
			if(isMove){				
				x=(int) (currentX-mSwitch.getWidth()/2);
			}else{
				if(currentX<mSwitchOn.getWidth()/2){
					currentStatus=false;
					x=0;
					canvas.drawBitmap(mSwitchOff, matrix, paint);
					if(listener!=null){						
						listener.afterChange(currentStatus);
					}
				}else{
					x=mSwitchOn.getWidth()-mSwitch.getWidth();
				}
			}
		}else{
			canvas.drawBitmap(mSwitchOff, matrix, paint);
			x=0;
			if(isStart&&startX>mSwitch.getWidth()/2){
				x=(int) (startX-mSwitch.getWidth()/2);
			}
			if(isMove){
				x=(int) (currentX-mSwitch.getWidth()/2);
			}else{
				if(currentX>mSwitchOff.getWidth()/2){
					currentStatus=true;
					x=mSwitchOff.getWidth()-mSwitch.getWidth();
					canvas.drawBitmap(mSwitchOn, matrix, paint);
					if(listener!=null){						
						listener.afterChange(currentStatus);
					}
				}else{
					x=0;
				}
			}
		}
		if(x<0){
			x=0;
		}
		if(x>mSwitchOn.getWidth()-mSwitch.getWidth()){
			x=mSwitchOn.getWidth()-mSwitch.getWidth();
		}	
		canvas.drawBitmap(mSwitch, x, 0, paint);
		super.onDraw(canvas);
	}
	//设置监听事件
	public void setOnSwitchChangeListener(OnSwitchChangeListener listener) {
		this.listener=listener;
		isListener=true;
	}
	public interface OnSwitchChangeListener{
		public void afterChange(Boolean status);
	}

}
