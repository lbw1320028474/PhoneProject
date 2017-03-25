package com.example.myview;

import com.example.util.ScreenUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class MyDrawerLayout extends HorizontalScrollView {
	/** 
	 * 屏幕宽度 
	 */  
	private int mScreenWidth;
	/** 
	 * dp 
	 */  
	private int mMenuRightPadding = 50;  
	/** 
	 * 菜单的宽度 
	 */  
	private int mMenuWidth;  
	private int mHalfMenuWidth;  

	private boolean once;  

	public MyDrawerLayout(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
		mScreenWidth = ScreenUtil.getScreenUtil(context).getScreenWidth();
		Log.e("c", "屏幕宽度width=" + mScreenWidth);
	}

	public MyDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		mScreenWidth = ScreenUtil.getScreenUtil(context).getScreenWidth();
		Log.e("c", "屏幕宽度1  width=" + mScreenWidth);
		/*TypedArray a = context.getTheme().obtainStyledAttributes(attrs,  
				R.styleable.SlidingMenu, defStyleAttr, 0);  
		int n = a.getIndexCount();  
		for (int i = 0; i < n; i++)  
		{  
			int attr = a.getIndex(i);  
			switch (attr)  
			{  
			case R.styleable.SlidingMenu_rightPadding:  
				// 默认50  
				mMenuRightPadding = a.getDimensionPixelSize(attr,  
						(int) TypedValue.applyDimension(  
								TypedValue.COMPLEX_UNIT_DIP, 50f,  
								getResources().getDisplayMetrics()));// 默认为10DP  
								break;  
			}  
		}  
		a.recycle();  */
	}

	public MyDrawerLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mScreenWidth = ScreenUtil.getScreenUtil(context).getScreenWidth();
		Log.e("c", "屏幕宽度width=" + mScreenWidth);
	}

	public MyDrawerLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mScreenWidth = ScreenUtil.getScreenUtil(context).getScreenWidth();
		Log.e("c", "屏幕宽度width=" + mScreenWidth);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		/** 
		 * 显示的设置一个宽度
		 */  

		if (!once)  
		{  
			LinearLayout wrapper = (LinearLayout) getChildAt(0);  
			ViewGroup menu = (ViewGroup) wrapper.getChildAt(0);  
			ViewGroup content = (ViewGroup) wrapper.getChildAt(1);  
			// dp to px  装换dp2px
			mMenuRightPadding = (int) TypedValue.applyDimension(  
					TypedValue.COMPLEX_UNIT_DIP, mMenuRightPadding, content  
					.getResources().getDisplayMetrics());  

			mMenuWidth = mScreenWidth - mMenuRightPadding;  
			mHalfMenuWidth = mMenuWidth / 2;  
			menu.getLayoutParams().width = mMenuWidth;  
			content.getLayoutParams().width = mScreenWidth;  

		}  
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
	}

	@Override  
	protected void onLayout(boolean changed, int l, int t, int r, int b)  
	{  
		super.onLayout(changed, l, t, r, b);  
		if (changed)  
		{  
			// 将菜单隐藏  
			Log.e("c", "mMenuWidth=" + mMenuWidth);
			this.scrollTo(mMenuWidth, 0);  
			once = true;  
		}  
	}  

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		int action = ev.getAction();  
		switch (action)  
		{  
		// Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏  
		case MotionEvent.ACTION_UP:  
			int scrollX = getScrollX();  
			if (scrollX > mHalfMenuWidth/2)  
				this.smoothScrollTo(mMenuWidth, 0);  
			else  
				this.smoothScrollTo(0, 0);  
			return true;  
		}  
		return super.onTouchEvent(ev);  
	}
}
