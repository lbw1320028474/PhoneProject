package com.example.adapter;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.beautyphone.R;
import com.example.contacts.CardLocationBean;
import com.example.contacts.ContactInfomationCalllogItemBean;
import com.example.contacts.ContactInfomationCllllogBaseBean;
import com.example.contacts.NumberInfomation;
import com.example.db.DataTypeUtil;
import com.example.myDialog.CallTypeChoseDialog;
import com.example.util.NumberLocationUtil;
import com.example.util.DataConverterUtil.DataBean;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class ContactsInfomationCalllogRecycleAdapter extends RecyclerView.Adapter<ViewHolder> implements OnClickListener, OnLongClickListener{
	private LayoutInflater mInflater;
	private ArrayList<ContactInfomationCllllogBaseBean> allListData = null;
	private int position = 0;
	public static Context context = null;
	private CallTypeChoseDialog callTypeChoseDialog = null;
	private onClickLister oncliClickLister = null;
	private onLongClickLister longClickLister = null;


	public ContactsInfomationCalllogRecycleAdapter(Context context, ArrayList<ContactInfomationCllllogBaseBean> listData){
		// TODO Auto-generated constructor stub
		this.allListData = listData;
		this.context = context;
		callTypeChoseDialog = new CallTypeChoseDialog(context);
		mInflater = LayoutInflater.from(context);
	}

	public void setOnClickLister(onClickLister clickLister){
		this.oncliClickLister = clickLister;
	}

	public void setOnLongClickLister(onLongClickLister clickLister){
		this.longClickLister = clickLister;
	}

	class LogItemViewHolder extends ViewHolder{
		public ImageView item_calltype = null;
		public TextView time = null;
		public TextView duration = null;
		public TextView location = null;
		public LogItemViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
			item_calltype = (ImageView)itemView.findViewById(R.id.contactinfomation_calllog_normal_calltype);
			time = (TextView)itemView.findViewById(R.id.contactinfomation_calllog_normal_calltime);
			duration = (TextView)itemView.findViewById(R.id.contactinfomation_calllog_normal_callderact);
			location = (TextView)itemView.findViewById(R.id.contactinfomation_calllog_normal_callsimtype);
		}

	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return allListData.get(position).getItemType();
	}

	class DataItemViewHolder extends ViewHolder{
		public TextView data = null;
		public DataItemViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
			data = (TextView)itemView.findViewById(R.id.contactinfomation_calllog_data_textview);
		}

	}


	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return allListData.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder arg0, int arg1) {
		// TODO Auto-generated method stub
		//Log.e("c", "设置了item: " + allListData.get(arg1).toString());
		if(allListData.get(arg1).getItemType() == 0){
			String data = ((ContactInfomationCalllogItemBean)(allListData.get(arg1))).getNumberAdress();
			((LogItemViewHolder)(arg0)).location.setText(data);
			int duratoin = ((ContactInfomationCalllogItemBean)(allListData.get(arg1))).getDuration();
			if(duratoin > 0){
				((LogItemViewHolder)(arg0)).duration.setText(duratoin/60+ "分" + duratoin%60 + "秒");
			}else{
				((LogItemViewHolder)(arg0)).duration.setText("未接通");
			}
			String hour = ((ContactInfomationCalllogItemBean)(allListData.get(arg1))).getDataBean().hour;
			String min = ((ContactInfomationCalllogItemBean)(allListData.get(arg1))).getDataBean().min;
			((LogItemViewHolder)(arg0)).time.setText(hour + ":" + min);
			switch (((ContactInfomationCalllogItemBean)(allListData.get(arg1))).getLogType()) {
			case 1:
				((LogItemViewHolder)(arg0)).item_calltype.setImageResource(R.drawable.ic_call_incoming_normal);
				break;
			case 2:
				((LogItemViewHolder)(arg0)).item_calltype.setImageResource(R.drawable.ic_call_out);
				break;
			case 3:
				((LogItemViewHolder)(arg0)).item_calltype.setImageResource(R.drawable.ic_call_incoming_missed);
				break;
			}
			if(((ContactInfomationCalllogItemBean)(allListData.get(arg1))).getLogType() == 3){
				((LogItemViewHolder)(arg0)).time.setTextColor(Color.rgb(228, 0, 127));
				((LogItemViewHolder)(arg0)).duration.setTextColor(Color.rgb(228, 0, 127));
			}else{
				((LogItemViewHolder)(arg0)).time.setTextColor(Color.rgb(0, 0, 0));
				((LogItemViewHolder)(arg0)).duration.setTextColor(Color.rgb(206, 206, 206));
			}
		}else if(allListData.get(arg1).getItemType() == 1){
			DataBean dataBean = ((ContactInfomationCalllogItemBean)(allListData.get(arg1))).getDataBean();
			if(dataBean.year.equals(Calendar.getInstance().get(Calendar.YEAR))){
				((DataItemViewHolder)(arg0)).data.setText(dataBean.month + "月" + dataBean.day + "日");
			}else{
				((DataItemViewHolder)(arg0)).data.setText(dataBean.year + "年" + dataBean.month + "月" + dataBean.day + "日");
			}
		}
	}



	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		/*View view = mInflater.inflate(R.layout.contacts_listview_item, arg0, false);  
		MyViewHolder holder= new MyViewHolder(view);*/
		if(arg1 == 0){
			View view = null;
			view = mInflater.inflate(R.layout.contactinfomation_calllog_normal_item, arg0, false);
			LogItemViewHolder logItemViewHolder = new LogItemViewHolder(view);
			return logItemViewHolder;
		}else if(arg1 == 1){
			View view = null;
			view = mInflater.inflate(R.layout.contactinfomation_calllog_data_item, arg0, false);
			DataItemViewHolder logItemViewHolder = new DataItemViewHolder(view);
			return logItemViewHolder;
		}

		/*contactsViewHolder.item_sendmessage_icon.setOnClickListener(this);
		contactsViewHolder.rootView.setOnClickListener(this);
		contactsViewHolder.item_sendmessage_icon.setOnClickListener(this);
		contactsViewHolder.rootView.setOnLongClickListener(this);*/
		return null;
	}

	public interface onClickLister{
		public void click(int position);
	}

	public interface onLongClickLister{
		public void longClick(int position);
	}

	@Override
	public boolean onLongClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.contactinfomation_phone_rootView:
			Log.e("", "长按电话号码");
			break;
		default:
			break;
		}

		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.contactinfomation_phone_rootView:
			Log.e("", "打电话");
			break;
		case R.id.contactinfomation_sendmessage:
			break;
		}

	}

}
