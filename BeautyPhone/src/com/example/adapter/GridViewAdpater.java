package com.example.adapter;

import java.util.ArrayList;
import java.util.zip.Inflater;

import com.example.beautyphone.R;
import com.example.contacts.ContactPeopleBean;
import com.example.contacts.ConverBitmapSourse;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class GridViewAdpater extends BaseAdapter{
	private Context context = null;
	private boolean isDraw = false;
	private LayoutInflater inflater = null;
	private View view = null;
	private ArrayList<ContactPeopleBean> sterredListData = null;
	public GridViewAdpater(Context context, GridView strredGridView, ArrayList<ContactPeopleBean> sterredListData) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.view = strredGridView;
		inflater = LayoutInflater.from(context);
		this.sterredListData = sterredListData;
	}

	/*public void getGridViewHeight(ViewHolder arg0, Context context1, viewHeightOnCallBcak onCallBcak){
		//Log.e("c", (GridViewAdpater.containerLayout == null) + " sss");
		LayoutParams layoutParams = (LayoutParams) GridViewAdpater.containerLayout.getLayoutParams();
		int height = layoutParams.height * ((int)(sterredListData.size() / 4) + 1);
		onCallBcak.callBacked(arg0, height);
	}*/

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return sterredListData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	class ListItem{
		//public LinearLayout
		public ImageView sterrdConver = null;
		public TextView sterrdName = null;
		public ListItem(){

		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ListItem viewTag;
		if (convertView == null)
		{  
			convertView = inflater.inflate(R.layout.starred_item_item_layout, null);  

			// construct an item tag
			viewTag = new ListItem();
			viewTag.sterrdConver = (ImageView)convertView.findViewById(R.id.starreditem_item_conver);
			viewTag.sterrdName = (TextView)convertView.findViewById(R.id.starreditem_item_name);
			convertView.setTag(viewTag);
		} else  
		{ 
			viewTag = (ListItem) convertView.getTag();  
		}  
		if(sterredListData.get(position).getName() != null && 
				!sterredListData.get(position).getName().equals("")){
			viewTag.sterrdName.setText(sterredListData.get(position).getName());
			Bitmap bitmap = sterredListData.get(position).getContactCoverBitmap();
			if(bitmap != null){
				viewTag.sterrdConver.setImageBitmap(bitmap);
			}
		}else{
			viewTag.sterrdName.setText(sterredListData.get(position).getNumberList().get(0));
		}
		return convertView;  
	}

	public interface viewHeightOnCallBcak{
		public void callBacked(ViewHolder arg0, int height);
	}
}
