package com.example.myview;

import java.util.HashMap;
import java.util.Map;

import com.example.myDialog.SideBarPingyDialog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MySideBar extends View {
	private sideBarPositionListen barPositionListen = null;
	private float startY = 0;
	private float endY = 0;
	private float distanceY = 0;
	private int textSpace = 0;
	private int textSize = 0;
	private SideBarPingyDialog barPingyDialog = null;
	private String[] charArray = new String[]{"#","A","B","C","D","E","F","G","H","I",
			"J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","*",};
	private Paint paint = null;
	private int choosPosition = 4;
	private Map positionMap = null;
	public MySideBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
		paint = new Paint();
		barPingyDialog = new SideBarPingyDialog(context);
		newHasMap();
	}

	public MySideBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		paint = new Paint();
		barPingyDialog = new SideBarPingyDialog(context);
		newHasMap();
	}

	public MySideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		paint = new Paint();
		barPingyDialog = new SideBarPingyDialog(context);
		newHasMap();
	}

	public void newHasMap(){
		positionMap = new HashMap();
		positionMap.put("#", 0);
		positionMap.put("A", 1);
		positionMap.put("B", 2);
		positionMap.put("C", 3);
		positionMap.put("D", 4);
		positionMap.put("E", 5);
		positionMap.put("F", 6);
		positionMap.put("G", 7);
		positionMap.put("H", 8);
		positionMap.put("I", 9);
		positionMap.put("J", 10);
		positionMap.put("K", 11);
		positionMap.put("L", 12);
		positionMap.put("M", 13);
		positionMap.put("N", 14);
		positionMap.put("O", 15);
		positionMap.put("P", 16);
		positionMap.put("Q", 17);
		positionMap.put("R", 18);
		positionMap.put("S", 19);
		positionMap.put("T", 20);
		positionMap.put("U", 21);
		positionMap.put("V", 22);
		positionMap.put("W", 23);
		positionMap.put("X", 24);
		positionMap.put("Y", 25);
		positionMap.put("Z", 26);
		positionMap.put("*", 27);
	}

	public MySideBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		paint = new Paint();
		barPingyDialog = new SideBarPingyDialog(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		int width = getWidth();
		int height = getHeight();
		textSpace = height/charArray.length;
		paint.setAntiAlias(true);
		textSize = textSpace/5*4;
		paint.setTextSize(textSize);
		for(int i = 0; i < charArray.length; ++i){
			if(i == choosPosition){
				paint.setColor(Color.rgb(0, 161, 244));
				canvas.drawText(charArray[i], width/2-textSize/2, textSpace * (i+1) - textSpace/2 + textSize/2, paint);
			}else{
				paint.setColor(Color.rgb(200, 200, 200));
				canvas.drawText(charArray[i], width/2-textSize/2, textSpace * (i+1) - textSpace/2 + textSize/2, paint);
			}
		}
		Log.e("c", "width : " + width);
		Log.e("e", "htight : " + height);
	}

	public void setOnSideBarPositionListener(sideBarPositionListen barPositionListen){
		this.barPositionListen = barPositionListen;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startY = event.getY();
			Log.e("c", "startY:" + startY);
			choosPosition = (int) (startY/textSpace);
			invalidate();
			barPingyDialog.showDialog(charArray[choosPosition]);
			chosed(choosPosition, charArray[choosPosition]);
			break;
		case MotionEvent.ACTION_MOVE:
			endY = event.getY();
			distanceY = endY - startY;
			int nowPostion = (int) ((startY + distanceY)/textSpace);
			if(nowPostion != choosPosition && (nowPostion < charArray.length) && nowPostion >= 0){
				choosPosition = nowPostion;
				invalidate();
				barPingyDialog.showDialog(charArray[choosPosition]);
				chosed(choosPosition, charArray[choosPosition]);
			}
			break;
		case MotionEvent.ACTION_UP:
			choosPosition = -1;
			invalidate();
			barPingyDialog.closeDialog();
			break;
		}
		return true;
	}


	private void chosed(int choosPosition2, String choseChar) {
		// TODO Auto-generated method stub
		if(barPositionListen != null){
			barPositionListen.chosed(choosPosition2, choseChar);
		}
	}

	public void chosedChar(String chosedChar){
		choosPosition = (Integer) positionMap.get(chosedChar);
		invalidate();
	}


	public interface sideBarPositionListen{
		public void chosed(int position, String choseChar);
	}
}
