package com.example.adapter;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONObject;
import com.example.activity.LoginActivity;
import com.example.activity.NewsContentShowActivity;
import com.example.activity.PayCenter;
import com.example.beautyphone.R;
import com.example.beautyphone.network.HtmlVideoBean;
import com.example.beautyphone.network.Bean.PersonalMyInfoBean;
import com.example.beautyphone.news.bean.JuHeNewsRecycleItemBean;
import com.example.beautyphone.news.bean.NewsRecylceTitleBean;
import com.example.beautyphone.news.bean.PersonalRecycleBaseBean;
import com.example.myDialog.GrantMoneyDialog;
import com.example.util.ScreenUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.connect.common.Constants;
import com.tencent.open.SocialConstants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.VideoView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class PersonalListAdpater extends RecyclerView.Adapter<ViewHolder> implements OnClickListener{
	private onNewsClickListener newTitleLister = null;
	private ArrayList<PersonalRecycleBaseBean> list = null;
	private Context context = null;
	private LayoutInflater inflater = null;
	private GrantMoneyDialog moneyDialog = null;
	private Tencent tencent = null;
	public PersonalListAdpater(Context context, ArrayList<PersonalRecycleBaseBean> list) {
		// TODO Auto-generated constructor stub
		moneyDialog = new GrantMoneyDialog(context);
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		tencent = Tencent.createInstance("1105092074", context);
	}

	class meItemViewHolder extends ViewHolder{
		public ImageView userConver = null;
		public TextView userName = null;
		public TextView vipDayText = null;
		private LinearLayout vip_layout = null;
		private TextView black_money = null;
		private LinearLayout black_layout = null;
		private TextView black_calltime = null;
		private LinearLayout black_calltime_layout = null;
		private LinearLayout toPay = null;
		public meItemViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
			userConver = (ImageView)itemView.findViewById(R.id.personalfragment_cover_image);
			userName = (TextView)itemView.findViewById(R.id.personalfragment_username_text);
			vipDayText = (TextView)itemView.findViewById(R.id.personalfragment_vip_text);
			vip_layout = (LinearLayout)itemView.findViewById(R.id.personalfragment_vip_layout);
			black_money = (TextView)itemView.findViewById(R.id.personalfragment_blackmoney_text);
			black_layout = (LinearLayout)itemView.findViewById(R.id.personalfragment_blackmoney_layout);
			black_calltime = (TextView)itemView.findViewById(R.id.personalfragment_blacktime_text);
			black_calltime_layout = (LinearLayout)itemView.findViewById(R.id.personalfragment_blacktime_layout);
			toPay = (LinearLayout)itemView.findViewById(R.id.personalfragment_pay_layout);
		}
	}

	class newTitleItemViewHolder extends ViewHolder{
		public TextView video_Text = null;
		public TextView top_Text = null;
		public TextView shehui_Text = null;
		public TextView guonei_Text = null;
		public TextView guoji_Text = null;
		public TextView yule_Text = null;
		public TextView tiyu_Text = null;
		public TextView junshi_Text = null;
		public TextView keji_Text = null;
		public TextView caijing_Text = null;
		public TextView shishang_Text = null;
		public newTitleItemViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
			video_Text = (TextView)itemView.findViewById(R.id.newspagetitle_title_video);
			top_Text = (TextView)itemView.findViewById(R.id.newspagetitle_title_top);
			shehui_Text = (TextView)itemView.findViewById(R.id.newspagetitle_title_shehui);
			guonei_Text = (TextView)itemView.findViewById(R.id.newspagetitle_title_guonei);
			guoji_Text = (TextView)itemView.findViewById(R.id.newspagetitle_title_guoji);
			yule_Text = (TextView)itemView.findViewById(R.id.newspagetitle_title_yule);
			tiyu_Text = (TextView)itemView.findViewById(R.id.newspagetitle_title_tiyu);
			junshi_Text = (TextView)itemView.findViewById(R.id.newspagetitle_title_junshi);
			keji_Text = (TextView)itemView.findViewById(R.id.newspagetitle_title_keji);
			caijing_Text = (TextView)itemView.findViewById(R.id.newspagetitle_title_caijing);
			shishang_Text = (TextView)itemView.findViewById(R.id.newspagetitle_title_shishang);
		}
	}

	class news1ItemViewHolder extends ViewHolder{
		public RelativeLayout rootView = null;
		public TextView newsName = null;
		public TextView newsTime = null;
		public ImageView newsImage = null;
		public TextView newsAuthor = null;
		public news1ItemViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
			rootView = (RelativeLayout)itemView.findViewById(R.id.news_recttype_rootview);
			newsName = (TextView) itemView.findViewById(R.id.news_recttype_new_title);
			newsTime = (TextView) itemView.findViewById(R.id.news_recttype_new_time);
			newsImage = (ImageView) itemView.findViewById(R.id.news_recttype_new_image);
			newsAuthor = (TextView)itemView.findViewById(R.id.news_recttype_new_author);
		}
	}

	class news2ItemViewHolder extends ViewHolder{
		public RelativeLayout rootView = null;
		public TextView newsName = null;
		public TextView newsTime = null;
		public TextView newsAuthor = null;
		public ImageView newsImage = null;
		public ImageView newsImage1 = null;
		public ImageView newsImage2 = null;
		public news2ItemViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
			rootView = (RelativeLayout)itemView.findViewById(R.id.news_squaretype_new_rootview);
			newsName = (TextView) itemView.findViewById(R.id.news_squaretype_new_title);
			newsTime = (TextView) itemView.findViewById(R.id.news_squaretype_new_time);
			newsAuthor = (TextView) itemView.findViewById(R.id.news_squaretype_new_author);
			newsImage = (ImageView) itemView.findViewById(R.id.news_squaretype_new_image);
			newsImage1 = (ImageView) itemView.findViewById(R.id.news_squaretype_new_image1);
			newsImage2 = (ImageView) itemView.findViewById(R.id.news_squaretype_new_image2);
		}
	}

	class videoItemViewHolder extends ViewHolder{
		public JCVideoPlayerStandard playerStandard = null;
		public ImageView videoUserConver = null;
		private TextView videoUserName = null;
		private ImageView sharedVideo = null;
		private TextView videoCreateData = null;
		private ImageView grantMoney = null;


		public videoItemViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
			playerStandard = (JCVideoPlayerStandard)itemView.findViewById(R.id.personalfragment_video_jcvideoview);
			videoUserConver = (ImageView)itemView.findViewById(R.id.personalfragment_video_userconverimage);
			videoUserName = (TextView)itemView.findViewById(R.id.personalfragment_video_username);
			sharedVideo = (ImageView)itemView.findViewById(R.id.personalfragment_video_shard_button);
			videoCreateData = (TextView)itemView.findViewById(R.id.personalfragment_video_createtime);
			grantMoney = (ImageView)itemView.findViewById(R.id.personalfragment_video_grantmoney_button);
		}
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		int itemType = ((list.get(position))).getItemType();
		return itemType;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return list.size();
	}



	@Override
	public void onBindViewHolder(ViewHolder arg0, int arg1) {
		// TODO Auto-generated method stub
		if(arg0.getItemViewType() == 0){
			((meItemViewHolder)arg0).black_money.setText(((PersonalMyInfoBean)(list.get(arg1))).getBlack_money());
			((meItemViewHolder)arg0).black_calltime.setText(((PersonalMyInfoBean)(list.get(arg1))).getBlack_calltime());
			((meItemViewHolder)arg0).vipDayText.setText(((PersonalMyInfoBean)(list.get(arg1))).getVipCount());
			((meItemViewHolder)arg0).userName.setText(((PersonalMyInfoBean)(list.get(arg1))).getUserAccount());
		}else if(arg0.getItemViewType() == 1){
			SetNewsTitleColor((newTitleItemViewHolder)arg0, ((NewsRecylceTitleBean)(list.get(arg1))).getShowNewsType());
		}else if(arg0.getItemViewType() == 2){
			((news1ItemViewHolder)arg0).rootView.setTag(arg1);
			JuHeNewsRecycleItemBean bean = (JuHeNewsRecycleItemBean)(list.get(arg1));
			((news1ItemViewHolder)(arg0)).newsName.setText(bean.getItemBean().getTitle());
			((news1ItemViewHolder)(arg0)).newsTime.setText(bean.getItemBean().getDate());
			((news1ItemViewHolder)(arg0)).newsAuthor.setText(bean.getItemBean().getAuthor_name());
			setImageViewRelativeLayoutParams(((news1ItemViewHolder)(arg0)).newsImage);
			ImageLoader.getInstance().displayImage(bean.getItemBean().getThumbnail_pic_s(), ((news1ItemViewHolder)(arg0)).newsImage, options);   
		}else if(arg0.getItemViewType() == 3){
			((news2ItemViewHolder)arg0).rootView.setTag(arg1);
			JuHeNewsRecycleItemBean bean = (JuHeNewsRecycleItemBean)(list.get(arg1));
			((news2ItemViewHolder)(arg0)).newsName.setText(bean.getItemBean().getTitle());
			((news2ItemViewHolder)(arg0)).newsTime.setText(bean.getItemBean().getDate());
			((news2ItemViewHolder)(arg0)).newsAuthor.setText(bean.getItemBean().getAuthor_name());
			setImageViewLinnerLayoutParams(((news2ItemViewHolder)(arg0)).newsImage);
			setImageViewLinnerLayoutParams(((news2ItemViewHolder)(arg0)).newsImage1);
			setImageViewLinnerLayoutParams(((news2ItemViewHolder)(arg0)).newsImage2);
			if(!bean.getItemBean().getThumbnail_pic_s().isEmpty()){
				ImageLoader.getInstance().displayImage(bean.getItemBean().getThumbnail_pic_s(), ((news2ItemViewHolder)(arg0)).newsImage, options);   
			}
			if(!bean.getItemBean().getThumbnail_pic_s02().isEmpty()){
				ImageLoader.getInstance().displayImage(bean.getItemBean().getThumbnail_pic_s02(), ((news2ItemViewHolder)(arg0)).newsImage1, options);   
			}
			if(!bean.getItemBean().getThumbnail_pic_s03().isEmpty()){
				ImageLoader.getInstance().displayImage(bean.getItemBean().getThumbnail_pic_s03(), ((news2ItemViewHolder)(arg0)).newsImage2, options);   
			}
		}else if(arg0.getItemViewType() == 4){
			videoItemViewHolder videoholder = ((videoItemViewHolder)(arg0));
			HtmlVideoBean bean = (HtmlVideoBean) list.get(arg1);
			videoholder.grantMoney.setTag(arg1);
			videoholder.sharedVideo.setTag(arg1);
			videoholder.videoUserName.setText(bean.getUserName());
			videoholder.videoCreateData.setText(bean.getData_data() + " " + bean.getData_time());
			videoholder.playerStandard.setUp(bean.getVideoUri(), bean.getVideoTitle());
			String videouri = bean.getVideoImage();
			videouri = videouri.replace("mvideo.spriteapp.cn", "svideo.spriteapp.com");
			videouri = videouri.replace("wpcco.", "wpd.");
			ImageLoader.getInstance().displayImage(bean.getUserImageUri(), videoholder.videoUserConver, options);
			ImageLoader.getInstance().displayImage(videouri, videoholder.playerStandard.thumbImageView, options);
		}
	}

	private void setImageViewLinnerLayoutParams(ImageView view) {
		// TODO Auto-generated method stub
		int screenWidth = ScreenUtil.getScreenUtil(context).getScreenWidth();
		int screenHeight = ScreenUtil.getScreenUtil(context).getScreenHeight();
		android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) view.getLayoutParams();
		layoutParams.width = screenWidth / 3 - 20;
		layoutParams.height = (int) ((screenWidth / 3 - 20) / 1.3);
		view.setLayoutParams(layoutParams);
	}

	private void setImageViewRelativeLayoutParams(ImageView view) {
		// TODO Auto-generated method stub
		int screenWidth = ScreenUtil.getScreenUtil(context).getScreenWidth();
		int screenHeight = ScreenUtil.getScreenUtil(context).getScreenHeight();
		LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
		layoutParams.width = screenWidth / 3 - 20;
		layoutParams.height = (int) ((screenWidth / 3 - 20) / 1.3);
		view.setLayoutParams(layoutParams);
	}

	//显示图片的配置  
	DisplayImageOptions options = new DisplayImageOptions.Builder()  
			.showImageOnLoading(R.drawable.test_cover)  
			.showImageOnFail(R.drawable.test_cover)  
			.cacheInMemory(true)  
			.cacheOnDisk(true)
			.bitmapConfig(Bitmap.Config.RGB_565)  
			.build(); 

	private void scrollListener(int arg1) {

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		if(arg1 == 0){
			View view = inflater.inflate(R.layout.personalfragment_money_layout, arg0, false);
			meItemViewHolder holder = new meItemViewHolder(view);
			holder.toPay.setOnClickListener(this);
			holder.vip_layout.setOnClickListener(this);
			holder.userConver.setOnClickListener(this);
			holder.black_layout.setOnClickListener(this);
			return holder;
		}else if(arg1 == 1){
			View view = inflater.inflate(R.layout.news_page_title_layout, arg0, false);
			newTitleItemViewHolder holder = new newTitleItemViewHolder(view);
			holder.video_Text.setOnClickListener(this);
			holder.top_Text.setOnClickListener(this);
			holder.guonei_Text.setOnClickListener(this);
			holder.guoji_Text.setOnClickListener(this);
			holder.shehui_Text.setOnClickListener(this);
			holder.yule_Text.setOnClickListener(this);
			holder.tiyu_Text.setOnClickListener(this);
			holder.junshi_Text.setOnClickListener(this);
			holder.keji_Text.setOnClickListener(this);
			holder.caijing_Text.setOnClickListener(this);
			holder.shishang_Text.setOnClickListener(this);
			return holder;
		}else if(arg1 == 2){
			View view = inflater.inflate(R.layout.news_recttype_image_item, arg0, false);
			news1ItemViewHolder holder = new news1ItemViewHolder(view);
			holder.rootView.setOnClickListener(this);
			return holder;
		}else if(arg1 == 3){
			View view = inflater.inflate(R.layout.news_squaretype_image_item, arg0, false);
			news2ItemViewHolder holder = new news2ItemViewHolder(view);
			holder.rootView.setOnClickListener(this);
			return holder;
		}else if(arg1 == 4){
			View view = inflater.inflate(R.layout.personalfragment_video_layout, arg0, false);
			videoItemViewHolder holder = new videoItemViewHolder(view);
			holder.grantMoney.setOnClickListener(this);
			holder.sharedVideo.setOnClickListener(this);
			return holder;
		}
		return null;
	}

	public void setonNewsTitleClickListener(onNewsClickListener visibeChangeLister){
		this.newTitleLister = visibeChangeLister;
	}

	public interface onClickLister{
		public void click(int position);
	}

	public interface onLongClickLister{
		public void longClick(int position);
	}

	public interface onNewsClickListener{
		public void newsClickListener(int index, String type);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.personalfragment_cover_image:
			Intent loginIntent = new Intent(this.context, LoginActivity.class);
			this.context.startActivity(loginIntent);
			break;
			//新闻标题按钮监听
		case R.id.newspagetitle_title_video:
			if(this.newTitleLister != null){
				this.newTitleLister.newsClickListener(0, "video");
			}
			break;
		case R.id.newspagetitle_title_top:
			if(this.newTitleLister != null){
				this.newTitleLister.newsClickListener(0, "top");
			}
			break;
		case R.id.newspagetitle_title_shehui:
			if(this.newTitleLister != null){
				this.newTitleLister.newsClickListener(0, "shehui");
			}
			break;
		case R.id.newspagetitle_title_guonei:
			if(this.newTitleLister != null){
				this.newTitleLister.newsClickListener(0, "guonei");
			}
			break;
		case R.id.newspagetitle_title_guoji:
			if(this.newTitleLister != null){
				this.newTitleLister.newsClickListener(0, "guoji");
			}
			break;
		case R.id.newspagetitle_title_yule:
			if(this.newTitleLister != null){
				this.newTitleLister.newsClickListener(0, "yule");
			}
			break;
		case R.id.newspagetitle_title_tiyu:
			if(this.newTitleLister != null){
				this.newTitleLister.newsClickListener(0, "tiyu");
			}
			break;
		case R.id.newspagetitle_title_junshi:
			if(this.newTitleLister != null){
				this.newTitleLister.newsClickListener(0, "junshi");
			}
			break;
		case R.id.newspagetitle_title_keji:
			if(this.newTitleLister != null){
				this.newTitleLister.newsClickListener(0, "keji");
			}
			break;
		case R.id.newspagetitle_title_caijing:
			if(this.newTitleLister != null){
				this.newTitleLister.newsClickListener(0, "caijing");
			}
			break;
		case R.id.newspagetitle_title_shishang:
			if(this.newTitleLister != null){
				this.newTitleLister.newsClickListener(0, "shishang");
			}
			break;
			//新闻按钮监听
		case R.id.news_recttype_rootview:
			startReadNews(v);
			break;

		case R.id.personalfragment_pay_layout:
			Intent toPayIntent = new Intent(this.context, PayCenter.class);
			this.context.startActivity(toPayIntent);
			break;
		case R.id.news_squaretype_new_rootview:
			startReadNews(v);
			break;
		case R.id.personalfragment_vip_layout:
			/*Intent videoIntent = new Intent(this.context, VideoTest.class);
			this.context.startActivity(videoIntent);*/
			break;
		case R.id.personalfragment_blackmoney_layout:
			break;
		case R.id.personalfragment_video_grantmoney_button:
			moneyDialog.showDialog((HtmlVideoBean)list.get((int) v.getTag()));
			break;
		case R.id.personalfragment_video_shard_button:
			share(list.get((int) v.getTag()));
			break;
		}
	}

	private void playVieo(final View v) {
		// TODO Auto-generated method stub
		Log.e("c", "开始播放1");
		final VideoView videoview = (VideoView)(v.getTag());
		Uri videUri = Uri.parse(((HtmlVideoBean)(list.get((int) videoview.getTag()))).getVideoUri());
		videoview.setVideoURI(videUri);
		videoview.setMediaController(new com.example.myview.MediaController((Activity) context));
		videoview.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub
				Log.e("c", "开始播放");
				v.setVisibility(View.GONE);
				videoview.start();
			}
		});
	}

	private void startReadNews(View v) {
		Intent newIntent = new Intent(this.context, NewsContentShowActivity.class);
		String jsonData = JSONObject.toJSONString(((JuHeNewsRecycleItemBean)(list.get((int) v.getTag()))).getItemBean());
		newIntent.putExtra("webJson", jsonData);
		this.context.startActivity(newIntent);
	}

	private void SetNewsTitleColor(newTitleItemViewHolder titleText, String type) {
		switch (type) {
		case "video":
			titleText.video_Text.setTextColor(Color.rgb(255, 138, 0));
			titleText.top_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.shehui_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.guonei_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.guoji_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.yule_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.tiyu_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.junshi_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.keji_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.caijing_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.shishang_Text.setTextColor(Color.rgb(102, 102, 102));
			break;
		case "top":
			titleText.video_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.top_Text.setTextColor(Color.rgb(255, 138, 0));
			titleText.shehui_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.guonei_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.guoji_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.yule_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.tiyu_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.junshi_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.keji_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.caijing_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.shishang_Text.setTextColor(Color.rgb(102, 102, 102));
			break;
		case "shehui":
			titleText.video_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.top_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.shehui_Text.setTextColor(Color.rgb(255, 138, 0));
			titleText.guonei_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.guoji_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.yule_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.tiyu_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.junshi_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.keji_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.caijing_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.shishang_Text.setTextColor(Color.rgb(102, 102, 102));
			break;
		case "guonei":
			titleText.video_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.top_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.shehui_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.guonei_Text.setTextColor(Color.rgb(255, 138, 0));
			titleText.guoji_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.yule_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.tiyu_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.junshi_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.keji_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.caijing_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.shishang_Text.setTextColor(Color.rgb(102, 102, 102));
			break;
		case "guoji":
			titleText.video_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.top_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.shehui_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.guonei_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.guoji_Text.setTextColor(Color.rgb(255, 138, 0));
			titleText.yule_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.tiyu_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.junshi_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.keji_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.caijing_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.shishang_Text.setTextColor(Color.rgb(102, 102, 102));
			break;
		case "yule":
			titleText.video_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.top_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.shehui_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.guonei_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.guoji_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.yule_Text.setTextColor(Color.rgb(255, 138, 0));
			titleText.tiyu_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.junshi_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.keji_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.caijing_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.shishang_Text.setTextColor(Color.rgb(102, 102, 102));
			break;
		case "tiyu":
			titleText.video_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.top_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.shehui_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.guonei_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.guoji_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.yule_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.tiyu_Text.setTextColor(Color.rgb(255, 138, 0));
			titleText.junshi_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.keji_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.caijing_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.shishang_Text.setTextColor(Color.rgb(102, 102, 102));
			break;
		case "junshi":
			titleText.video_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.top_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.shehui_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.guonei_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.guoji_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.yule_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.tiyu_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.junshi_Text.setTextColor(Color.rgb(255, 138, 0));
			titleText.keji_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.caijing_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.shishang_Text.setTextColor(Color.rgb(102, 102, 102));
			break;
		case "keji":
			titleText.video_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.top_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.shehui_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.guonei_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.guoji_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.yule_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.tiyu_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.junshi_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.keji_Text.setTextColor(Color.rgb(255, 138, 0));
			titleText.caijing_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.shishang_Text.setTextColor(Color.rgb(102, 102, 102));
			break;
		case "caijing":
			titleText.video_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.top_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.shehui_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.guonei_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.guoji_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.yule_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.tiyu_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.junshi_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.keji_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.caijing_Text.setTextColor(Color.rgb(255, 138, 0));
			titleText.shishang_Text.setTextColor(Color.rgb(102, 102, 102));
			break;
		case "shishang":
			titleText.video_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.top_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.shehui_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.guonei_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.guoji_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.yule_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.tiyu_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.junshi_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.keji_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.caijing_Text.setTextColor(Color.rgb(102, 102, 102));
			titleText.shishang_Text.setTextColor(Color.rgb(255, 138, 0));
			break;
		}
	}
	private void share(PersonalRecycleBaseBean baseBean) {
		HtmlVideoBean bean = (HtmlVideoBean) baseBean;
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		//这条分享消息被好友点击后的跳转URL。
		bundle.putString(SocialConstants.PARAM_TARGET_URL, bean.getVideoUri());
		//分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
		bundle.putString(SocialConstants.PARAM_TITLE, bean.getVideoTitle());
		//分享的图片URL
		bundle.putString(SocialConstants.PARAM_IMAGE_URL, 
				bean.getVideoImage());
		//分享的消息摘要，最长50个字
		//bundle.putString(SocialConstants.PARAM_SUMMARY, bean.getVideoTitle());
		//手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
		bundle.putString(SocialConstants.PARAM_APPNAME, "美天电话");
		//标识该消息的来源应用，值为应用名称+AppId。
		bundle.putString(SocialConstants.PARAM_APP_SOURCE, "星期几" + "1105092074");
		tencent.shareToQQ((Activity) context, bundle, new IUiListener() {
			
			@Override
			public void onError(UiError arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onComplete(Object arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
