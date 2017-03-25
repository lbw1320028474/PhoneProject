package com.example.activity;

import java.util.ArrayList;

import com.example.beautyphone.R;
import com.example.beautyphone.network.BaiSiVideoHtmlUtil;
import com.example.beautyphone.network.HtmlVideoBean;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoTest extends Activity {
	private ArrayList<HtmlVideoBean> arrayList = null;
	private Button play_button = null;
	private VideoView video_view = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_test_layout);
		play_button = (Button)this.findViewById(R.id.play_button);
		video_view = (VideoView)this.findViewById(R.id.video_test_video_view);
		BaiSiVideoHtmlUtil.getInstance().getVideoBenaList(handler);
		play_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
	}

	private void toPlayVideo() {
		if(arrayList != null && arrayList.size() > 0){
			//http://mvideo.spriteapp.cn/video/2017/0318/3f304bbe-0b60-11e7-a68a-d4ae5296039d_wpcco.mp4
			//http://svideo.spriteapp.com/video/2017/0318/3f304bbe-0b60-11e7-a68a-d4ae5296039d_wpd.mp4
			String videouri = arrayList.get(0).getVideoUri();
			videouri = videouri.replace("mvideo.spriteapp.cn", "svideo.spriteapp.com");
			videouri = videouri.replace("wpcco.", "wpd.");
			video_view.setVideoURI(Uri.parse(videouri));
			video_view.setMediaController(new MediaController(VideoTest.this));
			video_view.start();
		}
	}

	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg != null && msg.obj != null){
				arrayList = (ArrayList<HtmlVideoBean>) msg.obj;
				for (HtmlVideoBean h:arrayList){
					Log.e("ceee", h.getUserImageUri() + " + " + h.getUserName());
				}
				toPlayVideo();
			}
		};
	};
}
