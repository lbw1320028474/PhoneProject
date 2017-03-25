package com.example.util;

import java.io.InputStream;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.ContactsContract;

/**
 * 把图片圆角化的工具类
 * @author Administrator
 *
 */
public class BitmapUtil {

	public static Bitmap getPhoto(Context context, String people_id) {
		ContentResolver cr = context.getContentResolver();  
		Uri uri = ContentUris.withAppendedId(  
				ContactsContract.Contacts.CONTENT_URI, Long.parseLong(people_id));  
		InputStream input = ContactsContract.Contacts
				.openContactPhotoInputStream(cr, uri);  
		Bitmap photo = BitmapFactory.decodeStream(input);
		if(photo != null){
			return toRoundBitmap(photo);
		}else{
			return null;
		}

		/*String[] projection = new String[]{ContactsContract.Data.DATA15};
		String selection = ContactsContract.Data._ID + " = " + people_id;
		Cursor cur = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, projection, selection, null, null);
		if(cur != null && cur.getCount() > 0){
			cur.moveToNext();
			Log.e("c", "index: " + cur.getColumnIndex(ContactsContract.Data._ID));
			byte[] contactIcon = cur.getBlob(0);
			System.out.println("conTactIcon:" + contactIcon);
			if (contactIcon == null) {
				return null;
			} else {
				//byte[] photo = getPhoto(contactId);
				Bitmap map = BitmapFactory.decodeByteArray(contactIcon, 0,contactIcon.length);
				return map;
			}
		}
		return null;*/
	}
	/** 
	 * 转换图片成圆形 
	 *  
	 * @param bitmap 
	 *            传入Bitmap对象 
	 * @return 
	 */  
	public static Bitmap toRoundBitmap(Bitmap bitmap) {  
		int width = bitmap.getWidth();  
		int height = bitmap.getHeight();  
		float roundPx;  
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;  
		if (width <= height) {  
			roundPx = width / 2;  
			left = 0;  
			top = 0;  
			right = width;  
			bottom = width;  
			height = width;  
			dst_left = 0;  
			dst_top = 0;  
			dst_right = width;  
			dst_bottom = width;  
		} else {  
			roundPx = height / 2;  
			float clip = (width - height) / 2;  
			left = clip;  
			right = width - clip;  
			top = 0;  
			bottom = height;  
			width = height;  
			dst_left = 0;  
			dst_top = 0;  
			dst_right = height;  
			dst_bottom = height;  
		}  

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);  
		Canvas canvas = new Canvas(output);  

		final int color = 0xff424242;  
		final Paint paint = new Paint();  
		final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);  
		final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);  
		final RectF rectF = new RectF(dst);  

		paint.setAntiAlias(true);// 设置画笔无锯齿  

		canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas  
		paint.setColor(color);  

		// 以下有两种方法画圆,drawRounRect和drawCircle  
		// canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。  
		canvas.drawCircle(roundPx, roundPx, roundPx, paint);  

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452  
		canvas.drawBitmap(bitmap, src, dst, paint); //以Mode.SRC_IN模式合并bitmap和已经draw了的Circle  

		return output;  
	}  
}
