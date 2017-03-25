package com.example.myview;

import java.util.ArrayList;

import com.example.util.UnitConverterUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class MyHorizontalChoseView extends View {
	private int view_width = 0;
	private int view_height = 0;
	private int normalTextSize = 0;
	private int selectedTextSize = 0;
	private ArrayList<String> dataList = null;
	private Context context = null;
	private int[] itemWidth;
	private Paint paint = null;

	public void setTextData(ArrayList<String> dataList){
		this.dataList = dataList;
		itemWidth = new int[dataList.size()];
	}

	public MyHorizontalChoseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
		this.context = context;
		normalTextSize = UnitConverterUtil.dip2px(context, 15);
		selectedTextSize = UnitConverterUtil.dip2px(context, 17);
		setTextSize();
	}

	private void setTextSize() {
		// TODO Auto-generated method stub
		paint.setTextSize(normalTextSize);
	}

	public MyHorizontalChoseView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public MyHorizontalChoseView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyHorizontalChoseView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		view_height = getHeight();
		view_width = getWidth();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
