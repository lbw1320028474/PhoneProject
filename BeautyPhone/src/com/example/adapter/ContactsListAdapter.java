package com.example.adapter;

import java.util.ArrayList;

import com.example.activity.ContactsInfomationActivity;
import com.example.activity.ContactsInfomationActivity.ContactChangeBroadCase;
import com.example.beautyphone.R;
import com.example.contacts.ContactPeopleBean;
import com.example.contacts.ConverBitmapSourse;
import com.example.db.ContactsUtil;
import com.example.myDialog.CallTypeChoseDialog;
import com.example.util.ScreenUtil;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class ContactsListAdapter extends RecyclerView.Adapter<ViewHolder> implements OnClickListener, OnLongClickListener{
	private LayoutInflater mInflater;
	private ArrayList<ContactPeopleBean> allListData = null;
	private int position = 0;
	private ArrayList<ContactPeopleBean> sterredListData = null;
	public static Context context = null;
	private onClickLister oncliClickLister = null;
	private onLongClickLister longClickLister = null;
	private View showSubView = null;
	private CallTypeChoseDialog callTypeChoseDialog = null;

	public static enum ITEM_TYPE {
		ITEM_TYPE_STARRED, ITEM_TYPE_BEAUTYFRIEND, ITEM_TYPE_NORMAL
	}

	public ContactsListAdapter(Context context, ArrayList<ContactPeopleBean> listData) {
		// TODO Auto-generated constructor stub
		this.allListData = listData;
		this.context = context;
		mInflater = LayoutInflater.from(context);
		callTypeChoseDialog = new CallTypeChoseDialog(context);
	}

	public void setOnClickLister(onClickLister clickLister){
		this.oncliClickLister = clickLister;
	}

	public void setOnLongClickLister(onLongClickLister clickLister){
		this.longClickLister = clickLister;
	}

	class MyContactsViewHolder extends ViewHolder{
		public RelativeLayout relativeLayout = null; 
		public TextView pinyin = null;
		public ImageView contactsCover = null;
		public TextView contactsName = null;
		private LinearLayout subItem_rootView = null;
		private LinearLayout subItem_call = null;
		private LinearLayout subItem_message = null;
		private LinearLayout subItem_delete = null;
		private LinearLayout subItem_edit = null;
		private LinearLayout subItem_share = null;
		public MyContactsViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
			relativeLayout = (RelativeLayout)itemView.findViewById(R.id.contactsfragment_normalitem_rootview);
			pinyin = (TextView)itemView.findViewById(R.id.contactsfragment_pinytextview);
			contactsCover = (ImageView)itemView.findViewById(R.id.contactsfragment_peoplevonver);
			contactsName = (TextView)itemView.findViewById(R.id.contactsfragment_beautyFirend);
			subItem_rootView = (LinearLayout)itemView.findViewById(R.id.contactsfragment_item_subaction_rootview);
			subItem_call = (LinearLayout)itemView.findViewById(R.id.contactsfragment_item_subaction_call);
			subItem_message = (LinearLayout)itemView.findViewById(R.id.contactsfragment_item_subaction_message);
			subItem_delete = (LinearLayout)itemView.findViewById(R.id.contactsfragment_item_subaction_delete);
			subItem_edit = (LinearLayout)itemView.findViewById(R.id.contactsfragment_item_subaction_edit);
			subItem_share = (LinearLayout)itemView.findViewById(R.id.contactsfragment_item_subaction_share);
		}

	}

	private void showSubItemView(View view){
		if(view.getVisibility() == View.VISIBLE){
			return;
		}
		view.setVisibility(View.VISIBLE);
		showSubView = view;
		Log.e("c", "动画开始了");
		view.clearAnimation();
		Animation animationSet = AnimationUtils.loadAnimation(this.context, R.anim.contact_item_subview_anim_entry);
		/*AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
		alphaAnimation.setDuration(300);
		TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -30);
		translateAnimation.setDuration(300);
		animationSet.addAnimation(alphaAnimation);
		animationSet.addAnimation(translateAnimation);*/
		animationSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub

			}
		});
		view.startAnimation(animationSet);
	}

	private void closeSubItemView(View view){
		if(view.getVisibility() == View.GONE){
			return;
		}
		showSubView = view;
		Log.e("c", "动画开始了");
		view.clearAnimation();
		Animation animationSet = AnimationUtils.loadAnimation(this.context, R.anim.contact_item_subview_anim_exit);
		/*AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
		alphaAnimation.setDuration(300);
		TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -30);
		translateAnimation.setDuration(300);
		animationSet.addAnimation(alphaAnimation);
		animationSet.addAnimation(translateAnimation);*/
		animationSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				showSubView.setVisibility(View.GONE);
			}
		});
		view.startAnimation(animationSet);
	}

	class MyBeautyFriendViewHolder extends ViewHolder{
		public ImageView converImage = null;
		public TextView name = null;
		public TextView tip = null;

		public MyBeautyFriendViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
			converImage = (ImageView) itemView.findViewById(R.id.beauty_friend_conver);
			name = (TextView)itemView.findViewById(R.id.beauty_friend_beautyFirend);
			tip = (TextView)itemView.findViewById(R.id.beauty_friend_tip);
		}

	}

	//
	class MyStrredViewHolder extends RecyclerView.ViewHolder{
		//	public LinearLayout containerView = null;
		public GridView strredGridView = null;
		public MyStrredViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
			strredGridView = (GridView)itemView.findViewById(R.id.starredlayout_gridview);
		}
	}



	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return allListData.get(position).getItemType();
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return allListData.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder arg0, int arg1) {
		// TODO Auto-generated method stub
		if(showSubView != null){
			closeSubItemView(showSubView);
		}
		if(arg0.getItemViewType() == 0){
			this.sterredListData = this.allListData.get(0).getSterredList();
			GridViewAdpater adpater = new GridViewAdpater(ContactsListAdapter.context, ((MyStrredViewHolder)(arg0)).strredGridView , this.sterredListData);
			((MyStrredViewHolder)(arg0)).strredGridView.setAdapter(adpater);
			LayoutParams layoutParams = (LayoutParams) ((MyStrredViewHolder)(arg0)).strredGridView.getLayoutParams();
			float rowHeight = ScreenUtil.getScreenUtil(context).getScreenWidth()/4;
			if(this.sterredListData.size() % 4 == 0){
				layoutParams.height = (int) (rowHeight * ((int)(this.sterredListData.size()/4)) + 10);
			}else{
				layoutParams.height = (int) (rowHeight * ((int)(this.sterredListData.size()/4) + 1) + 10);
			}
			((MyStrredViewHolder)(arg0)).strredGridView.setLayoutParams(layoutParams);
		}else if(arg0.getItemViewType() == 1){

		}else if(arg0.getItemViewType() == 2){
			((MyContactsViewHolder)(arg0)).subItem_call.setTag(arg1);
			((MyContactsViewHolder)(arg0)).subItem_delete.setTag(arg1);
			((MyContactsViewHolder)(arg0)).subItem_edit.setTag(arg1);
			((MyContactsViewHolder)(arg0)).subItem_message.setTag(arg1);
			((MyContactsViewHolder)(arg0)).subItem_share.setTag(arg1);
			((MyContactsViewHolder)(arg0)).subItem_rootView.setTag(arg1);
			((MyContactsViewHolder)(arg0)).relativeLayout.setTag(((MyContactsViewHolder)(arg0)).subItem_rootView);
			((MyContactsViewHolder)(arg0)).subItem_rootView.setBackgroundColor(allListData.get(arg1).getConverColor());
			((MyContactsViewHolder)(arg0)).contactsName.setText(allListData.get(arg1).getName());
			((MyContactsViewHolder)(arg0)).pinyin.setText(allListData.get(arg1).getIsFirstItemShowChar());
			if(allListData.get(arg1).getContactCoverBitmap() != null){
				((MyContactsViewHolder)(arg0)).contactsCover.setImageBitmap(allListData.get(arg1).getContactCoverBitmap());
			}
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		/*View view = mInflater.inflate(R.layout.contacts_listview_item, arg0, false);  
		MyViewHolder holder= new MyViewHolder(view);*/
		View view = null;
		if(arg1 == ITEM_TYPE.ITEM_TYPE_STARRED.ordinal()){
			view = mInflater.inflate(R.layout.starred_layou_titem, arg0, false);
			MyStrredViewHolder myStrredViewHolder = new MyStrredViewHolder(view);
			myStrredViewHolder.strredGridView.setSelector(R.drawable.recycleview_item_click_bg);
			myStrredViewHolder.strredGridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					Log.e("c", "点击了收藏列表" + position);
					Intent intent = new Intent(ContactsListAdapter.this.context, ContactsInfomationActivity.class);
					intent.putExtra("data1",sterredListData.get(position).getContactId() + "");
					intent.putExtra("intentType", "contactMainItem");
					intent.putExtra("styleColor", sterredListData.get(position).getConverColor());
					ContactsListAdapter.this.context.startActivity(intent);
				}
			});
			return myStrredViewHolder;
		}else if(arg1 == ITEM_TYPE.ITEM_TYPE_BEAUTYFRIEND.ordinal()){
			view = mInflater.inflate(R.layout.beauty_friend_item, arg0, false);
			MyBeautyFriendViewHolder beautyFriendViewHolder = new MyBeautyFriendViewHolder(view);
			return beautyFriendViewHolder;
		}else if(arg1 == ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal()){
			view = mInflater.inflate(R.layout.contactsfragment_normal_item, arg0, false);
			MyContactsViewHolder contactsViewHolder = new MyContactsViewHolder(view);
			contactsViewHolder.subItem_rootView.setOnLongClickListener(this);
			contactsViewHolder.relativeLayout.setOnClickListener(this);
			contactsViewHolder.relativeLayout.setOnLongClickListener(this);
			contactsViewHolder.subItem_call.setOnClickListener(this);
			contactsViewHolder.subItem_message.setOnClickListener(this);
			contactsViewHolder.subItem_delete.setOnClickListener(this);
			contactsViewHolder.subItem_edit.setOnClickListener(this);
			contactsViewHolder.subItem_share.setOnClickListener(this);
			/*contactsViewHolder.relativeLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					oncliClickLister.click((int) v.getTag());
				}
			});*/
			return contactsViewHolder;
		}
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
		case R.id.contacts_listview_items_mainlayout:
			Log.e("c", "长按rootView");
			if(longClickLister != null){
				longClickLister.longClick((int)((View) arg0.getTag()).getTag());
			}
			break;
		case R.id.contactsfragment_normalitem_rootview:
			Log.e("c", "长按subrootView");
			if(showSubView != null && showSubView.getVisibility() == View.VISIBLE){
				closeSubItemView(showSubView);
				return true;
			}
			View rootView = (View) arg0.getTag();
			showSubItemView(rootView);
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
		case R.id.contacts_listview_items_mainlayout:
			if(showSubView != null && showSubView.getVisibility() == View.VISIBLE){
				closeSubItemView(showSubView);
				return;
			}
			if(oncliClickLister != null){
				oncliClickLister.click((int)((View) arg0.getTag()).getTag());
			}
			break;
		case R.id.contactsfragment_normalitem_rootview:
			if(showSubView != null && showSubView.getVisibility() == View.VISIBLE){
				closeSubItemView(showSubView);
				return;
			}
			if(oncliClickLister != null){
				oncliClickLister.click((int)((View) arg0.getTag()).getTag());
			}
			break;
		case R.id.contactsfragment_item_subaction_rootview:
			if(showSubView != null && showSubView.getVisibility() == View.VISIBLE){
				closeSubItemView(showSubView);
				return;
			}
			if(oncliClickLister != null){
				oncliClickLister.click((int)((View) arg0.getTag()).getTag());
			}
			break;
		case R.id.contactsfragment_item_subaction_call:
			//Toast.makeText(this.context, "拨打电话", 0).show();
			callTypeChoseDialog.showDialog(allListData.get((int)(arg0.getTag())).getNumberList().get(0), 
					allListData.get((int)(arg0.getTag())).getName(),
					"");
			break;
		case R.id.contactsfragment_item_subaction_message:
			if(allListData.get((int)(arg0.getTag())).getNumberList().size() > 0){
				String number = allListData.get((int)(arg0.getTag())).getNumberList().get(0);
				Uri smsToUri = Uri.parse("smsto:" + number);
				Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
				intent.putExtra("sms_body", "");
				context.startActivity(intent);
			}
			//Toast.makeText(this.context, "发消息", 0).show();
			break;
		case R.id.contactsfragment_item_subaction_delete:
			//Toast.makeText(this.context, "删除", 0).show();
			//deleteContact("");
			deleteContact(allListData.get((int) arg0.getTag()).getContactId() + "");
			break;
		case R.id.contactsfragment_item_subaction_edit:
			//Toast.makeText(this.context, "编辑", 0).show();
			editContact((int)(arg0.getTag()));
			break;
		case R.id.contactsfragment_item_subaction_share:
			Toast.makeText(this.context, "分享号码", 0).show();
			break;
		}

	}



	public void deleteContact(String contactId){
		showSubView.setVisibility(View.GONE);
		Uri deleteUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(contactId));
		Uri lookupUri = ContactsContract.Contacts.getLookupUri(this.context.getContentResolver(), deleteUri);
		if (lookupUri != Uri.EMPTY) {
			int del = this.context.getContentResolver().delete(deleteUri, null, null);
		}
	}

	private void editContact(int position) {
		// TODO Auto-generated method stub
		Intent editIntent = new Intent(Intent.ACTION_EDIT,Uri.parse("content://com.android.contacts/contacts/"+
				allListData.get(position).getContactId()));
		this.context.startActivity(editIntent);
	}
}
