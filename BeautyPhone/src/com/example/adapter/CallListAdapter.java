package com.example.adapter;

import java.util.ArrayList;

import com.example.activity.ContactsInfomationActivity;
import com.example.adapter.ContactsListAdapter.MyContactsViewHolder;
import com.example.beautyphone.R;
import com.example.contacts.CallLogBean;
import com.example.contacts.CallSearchResultBean;
import com.example.contacts.ConverBitmapSourse;
import com.example.util.DataConverterUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class CallListAdapter extends RecyclerView.Adapter<ViewHolder> implements OnClickListener, OnLongClickListener{
	private LayoutInflater mInflater;
	public ArrayList<CallLogBean> listData = null;
	private int position = 0;
	private Context context = null;
	private onClickLister oncliClickLister = null;
	private onLongClickLister longClickLister = null;
	private int downToolViewHeight = 0;
	private View itemLayoutView = null;
	private selectedListener itemSelected = null;
	private int moveInIndex = -1;

	public static enum ITEM_TYPE {  
		ITEM_TYPE_NORMAL, ITEM_TYPE_LAST, ITEM_SEARCH_RESULTS
	}

	public CallListAdapter(Context context, ArrayList<CallLogBean> listData, int downToolViewHeight) {
		// TODO Auto-generated constructor stub
		this.listData = listData;
		this.context = context;
		this.downToolViewHeight = downToolViewHeight;
		mInflater = LayoutInflater.from(context);
	}

	public void setonItemSeletedListener(selectedListener seletedList){
		this.itemSelected = seletedList;
	}

	public void setonClickLister(onClickLister clickLister){
		this.oncliClickLister = clickLister;
	}

	public void setonLongClickLister(onLongClickLister clickLister){
		this.longClickLister = clickLister;
	}

	class MyLastViewHolder extends ViewHolder{
		private TextView adjustTextViews = null;
		public MyLastViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
			adjustTextViews = (TextView)itemView.findViewById(R.id.adjustTextView);
		}
	}

	class MySearchResultViewHolder extends ViewHolder{
		private RelativeLayout mainRootView = null;
		private ImageView resultCover = null;
		private TextView resultName = null;
		private TextView resultNumber = null;
		private TextView resultFriendType = null;
		private ImageView resultInfomation = null;
		public MySearchResultViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
			mainRootView = (RelativeLayout)itemView.findViewById(R.id.calllogfragment_serch_mainlayout);
			resultCover = (ImageView)itemView.findViewById(R.id.calllogfragment_serch_peoplevonver);
			resultName = (TextView)itemView.findViewById(R.id.calllogfragment_serch_name);
			resultNumber = (TextView)itemView.findViewById(R.id.calllogfragment_serch_number);
			resultFriendType = (TextView)itemView.findViewById(R.id.calllogfragment_serch_friendtype);
			resultInfomation = (ImageView)itemView.findViewById(R.id.calllogfragment_serch_number_ifomationiconbutton);
		}
	}

	class MyViewHolder extends ViewHolder{  
		private TextView callTime = null;	
		private TextView callname = null;	
		private ImageView callKind = null;
		private TextView callCount = null;	
		private TextView callPrivice = null;	
		private TextView callState = null;		
		private ImageView callInfomation = null;	
		private RelativeLayout main_layout = null;
		private ImageView radioButton = null;

		public MyViewHolder(View view) {  
			super(view);  
			itemLayoutView = view;
			radioButton = (ImageView)view.findViewById(R.id.callnote_listview_itme_radiobutton);
			main_layout = (RelativeLayout)view.findViewById(R.id.callnote_listview_item_mainlayout);
			callTime = (TextView)view.findViewById(R.id.callnote_listview_itme_timedate);
			callname = (TextView)view.findViewById(R.id.callnote_listview_itme_name);
			callCount = (TextView)view.findViewById(R.id.callnote_listview_itme_count);
			callKind = (ImageView)view.findViewById(R.id.callnote_listview_itme_callstate);
			callPrivice = (TextView)view.findViewById(R.id.callnote_listview_itme_province);
			callState = (TextView)view.findViewById(R.id.callnote_listview_itme_callinfomation);
			callInfomation = (ImageView)view.findViewById(R.id.callnote_listview_itme_ifomationicon);
		}
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return listData.get(position).getItemType();
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return listData.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int arg1) {
		Log.e("c", "onCreateViewHolder" + arg1);
		// TODO Auto-generated method stub
		if(holder.getItemViewType() == ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal()){
			((MyViewHolder)holder).radioButton.setTag(arg1);
			((MyViewHolder)holder).main_layout.setTag(arg1);
			((MyViewHolder)holder).callInfomation.setTag(arg1);
			if(listData.get(arg1).getName() != null && !listData.get(arg1).getName().equals("")){
				((MyViewHolder)holder).callname.setText(listData.get(arg1).getName());
			}else{
				((MyViewHolder)holder).callname.setText(listData.get(arg1).getNumber());	//listData.get(arg1).getCallLogdate()
			}
			if(listData.get(arg1).isEdit()){
				((MyViewHolder)holder).radioButton.setVisibility(View.VISIBLE);
			}else{
				((MyViewHolder)holder).radioButton.setVisibility(View.GONE);
			}
			if(listData.get(arg1).isSelected()){
				((MyViewHolder)holder).radioButton.setImageResource(R.drawable.calllog_radiobuggon_select);
			}else{
				((MyViewHolder)holder).radioButton.setImageResource(R.drawable.calllog_radiobuggon_noselect);
			}
			String phoneLocation = listData.get(arg1).getNumberAdress();
			if(phoneLocation != null && !phoneLocation.equals("")){
				((MyViewHolder)holder).callPrivice.setText(phoneLocation);
			}else{
				((MyViewHolder)holder).callPrivice.setText("未知 归属地");
			}
			((MyViewHolder)holder).callTime.setText(DataConverterUtil.getFormatDateTime(Long.parseLong(listData.get(arg1).getCallLogdate())));
			((MyViewHolder)holder).callState.setText(DataConverterUtil.getMinOfSec(listData.get(arg1).getDuration()));
			if(listData.get(arg1).getLogType() == 1){
				((MyViewHolder)holder).callKind.setImageResource(R.drawable.ic_call_incoming_normal);
				((MyViewHolder)holder).callname.setTextColor(context.getResources().getColor(R.color.calllog_item_normalcolor));
			}else if(listData.get(arg1).getLogType() == 2){
				((MyViewHolder)holder).callname.setTextColor(context.getResources().getColor(R.color.calllog_item_normalcolor));
				((MyViewHolder)holder).callKind.setImageResource(R.drawable.ic_call_out);
			}else{
				((MyViewHolder)holder).callname.setTextColor(context.getResources().getColor(R.color.calllog_item_misscallcolor));
				((MyViewHolder)holder).callKind.setImageResource(R.drawable.ic_call_incoming_missed);
			}
			/*((MyViewHolder)arg0).callname.setText(listData.get(arg1));
			Log.e("c", "positon: " + arg1);*/
			position = arg1;
		}else if(holder.getItemViewType() == ITEM_TYPE.ITEM_TYPE_LAST.ordinal()){
			android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) ((MyLastViewHolder)holder).adjustTextViews.getLayoutParams();
			layoutParams.height = downToolViewHeight;
			((MyLastViewHolder)holder).adjustTextViews.setLayoutParams(layoutParams);
			Log.e("c", "高度: " + downToolViewHeight);
		}else if(holder.getItemViewType() == ITEM_TYPE.ITEM_SEARCH_RESULTS.ordinal()){
			((MySearchResultViewHolder)holder).mainRootView.setTag(arg1);
			Bitmap cover = ((CallSearchResultBean)(listData.get(arg1))).getContactCover_Bitmap();
			if(cover != null){
				((MySearchResultViewHolder)holder).resultCover.setImageBitmap(cover);
			}else{
				int chosePotion = (int) (Math.random() * 4);
				((MySearchResultViewHolder)holder).resultCover.setImageBitmap(
						BitmapFactory.decodeResource(this.context.getResources(), 
								ConverBitmapSourse.converBitmapSourse[chosePotion]));
			}
			String serchedNumber = "";
			ArrayList<String> numberList = ((CallSearchResultBean)(listData.get(arg1))).getNumberList();
			String inSearchNum = ((CallSearchResultBean)(listData.get(arg1))).getSerchNumber();
			if(numberList != null && numberList.size() > 0){
				for (String num:numberList){
					if(num.indexOf(inSearchNum) >= 0){
						serchedNumber = num;
						break;
					}
				}
			}
			if(!serchedNumber.equals("")){
				int nameStart = serchedNumber.indexOf(inSearchNum);
				if(nameStart >= 0){
					String text_n1 = serchedNumber.substring(0, nameStart);
					String text_n2 = inSearchNum;
					String text_n3 = serchedNumber.substring(nameStart + inSearchNum.length(), serchedNumber.length());
					((MySearchResultViewHolder)holder).resultNumber.setText(Html.fromHtml(text_n1 + "<font color='#e4007f'>" + text_n2 + "</font>" + text_n3));
				}
			}
			String name = ((CallSearchResultBean)(listData.get(arg1))).getContact_name();
			if(!name.equals("")){
				int nameStart = name.indexOf(inSearchNum);
				if(nameStart >= 0){
					String text_n1 = name.substring(0, nameStart);
					String text_n2 = inSearchNum;
					String text_n3 = name.substring(nameStart + inSearchNum.length(), name.length());
					((MySearchResultViewHolder)holder).resultName.setText(Html.fromHtml(text_n1 + "<font color='#e4007f'>" + text_n2 + "</font>" + text_n3));
				}else{
					((MySearchResultViewHolder)holder).resultName.setText(name);
				}
			}else{
				Log.e("c", "名字为空");
			}

		}
		position = arg1;
		/*arg0.main_layout.setTag(arg1);
		arg0.callname.setText(listData.get(arg1));
		Log.e("c", "positon: " + arg1);
		position = arg1;*/
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		View view = null;
		if(arg1 == ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal()){
			view = mInflater.inflate(R.layout.call_listview_item, arg0, false);
			Log.e("c", "position: " + arg1);
			MyViewHolder holder= new MyViewHolder(view);
			holder.callInfomation.setOnClickListener(this);
			holder.main_layout.setOnClickListener(this);
			holder.main_layout.setOnLongClickListener(this);
			holder.radioButton.setOnClickListener(this);
			holder.callInfomation.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//如果记录类型为2，说明该记录是陌生人
					Log.e("c", "点击详情:" + (int) v.getTag() + " + " + listData.get((int) v.getTag()).getNumberType());
					Intent intent = new Intent(context, ContactsInfomationActivity.class);
					intent.putExtra("data1", listData.get((int) v.getTag()).getNumber());
					intent.putExtra("data2", listData.get((int) v.getTag()).getNumberType());
					intent.putExtra("intentType", "calllogItem");
					context.startActivity(intent);
				}
			});
			return holder;
		}else if(arg1 == ITEM_TYPE.ITEM_TYPE_LAST.ordinal()){
			view = mInflater.inflate(R.layout.downadjustview, arg0, false);
			MyLastViewHolder holder= new MyLastViewHolder(view);
			return holder;
		}else if(arg1 == ITEM_TYPE.ITEM_SEARCH_RESULTS.ordinal()){
			view = mInflater.inflate(R.layout.calllogfragment_serch_item, arg0, false);
			MySearchResultViewHolder holder= new MySearchResultViewHolder(view);
			holder.mainRootView.setOnLongClickListener(this);
			holder.mainRootView.setOnClickListener(this);
			return holder;
		}
		return null;
	}

	public interface onClickLister{
		public void click(int position, int itemType);
	}

	public interface onLongClickLister{
		public void longClick(int position, int itemType);
	}

	@Override
	public boolean onLongClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.callnote_listview_item_mainlayout:
			if(longClickLister != null){
				longClickLister.longClick((Integer) arg0.getTag(), 0);
			}
			break;
		case R.id.calllogfragment_serch_mainlayout:
			if(longClickLister != null){
				longClickLister.longClick((Integer) arg0.getTag(), 1);
			}
			break;
		}

		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.callnote_listview_item_mainlayout:
			if(listData.get((int) arg0.getTag()).isEdit()){
				if(this.itemSelected != null){
					this.itemSelected.seleted((int) arg0.getTag(), true);
				}
			}else{
				if(oncliClickLister != null){
					oncliClickLister.click((Integer) arg0.getTag(), 0);
				}
			}
			break;
		case R.id.calllogfragment_serch_mainlayout:
			if(oncliClickLister != null){
				oncliClickLister.click((Integer) arg0.getTag(), 1);
			}
			break;
		case R.id.callnote_listview_itme_radiobutton:
			if(this.itemSelected != null){
				this.itemSelected.seleted((int) arg0.getTag(), true);
			}
			break;
		}
	}




	public interface selectedListener{
		public void seleted(int selectedPosition, boolean sNs);
	}

}
