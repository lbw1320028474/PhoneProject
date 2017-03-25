package com.example.myview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

public class CustomLinearLayoutManager extends LinearLayoutManager {
	private boolean isScrollEnabled = true;
	public CustomLinearLayoutManager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public void setScrollEnabled(boolean isScrollEnabled) {
		this.isScrollEnabled = isScrollEnabled;
	}

	@Override
	public boolean canScrollVertically() {
		// TODO Auto-generated method stub
		return isScrollEnabled && super.canScrollVertically();
	}
}
