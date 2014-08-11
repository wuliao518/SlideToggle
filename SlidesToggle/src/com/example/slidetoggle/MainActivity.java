package com.example.slidetoggle;

import com.example.slidetoggle.R;
import com.example.slidetoggle.view.SlideToggle;
import com.example.slidetoggle.view.SlideToggle.OnSwitchChangeListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
	private SlideToggle mSlideToggle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		initView();
	}
	private void initView() {
		mSlideToggle=(SlideToggle) findViewById(R.id.slideToggle);
		mSlideToggle.setSwitch(true);
		mSlideToggle.setOnSwitchChangeListener(new OnSwitchChangeListener(){
			@Override
			public void afterChange(Boolean status) {
				if(status){
					Log.i("MainActivity", "´ò¿ª");
				}else{
					Log.i("MainActivity", "¹Ø±Õ");
				}
				
			}});
		
	}
}
