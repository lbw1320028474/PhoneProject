package com.example.beautyphone.network;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class BaiSiVideoHtmlUtil {
	private Handler handler = null;
	private String videoHtmlUri = "http://www.budejie.com/video/";
	private static BaiSiVideoHtmlUtil htmlUtil = null;
	private int nowPage = 1;
	private int willeToGetPage = 1;
	private static ArrayList<HtmlVideoBean> videoList = null;
	private BaiSiVideoHtmlUtil(){

	}

	public static BaiSiVideoHtmlUtil getInstance(){
		if(htmlUtil == null){
			htmlUtil = new BaiSiVideoHtmlUtil();
			videoList = new ArrayList<HtmlVideoBean>();
		}
		return htmlUtil;
	}

	public void getVideoBenaList(Handler handler){
		this.handler = handler;
		if(videoList != null && videoList.size() > 0){
			Message message = new Message();
			message.obj = videoList;
			handler.sendMessage(message);
			return;
		}
		willeToGetPage = 1;
		getDataByThread(willeToGetPage);
	}

	public void getMoreVideoBenaList(Handler handler){
		if(nowPage > 50){
			Message message = new Message();
			message.what = 2;
			handler.sendMessage(message);
			return;
		}
		this.handler = handler;
		willeToGetPage = nowPage + 1;
		getDataByThread(willeToGetPage);
	}

	private void getDataByThread(final int pagepage) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Document doc;
				try {
					doc = Jsoup.connect(videoHtmlUri + pagepage).get();
					getbean(doc);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e("c", "Á´½ÓhtmlÊ§°Ü");
				}
			}

			private void getbean(Document doc) {
				// TODO Auto-generated method stub
				Element body = doc.body();
				Elements videoElementList = body.getElementsByClass("j-r-list-c");
				Elements userElementList = body.getElementsByClass("j-list-user");
				if(videoElementList != null && videoElementList.size() > 0){
					for (int i = 0; i < videoElementList.size(); ++i){
						Element e = videoElementList.get(i);
						HtmlVideoBean videoBean = new HtmlVideoBean();
						Elements titleElement = e.getElementsByClass("j-r-list-c-desc");
						videoBean.setVideoTitle(titleElement.get(0).select("a[href]").text());
						Elements bodyElement = e.getElementsByClass("j-video-c");
						videoBean.setData_id(bodyElement.get(0).attr("data-id"));
						videoBean.setData_title(bodyElement.get(0).attr("data-title"));
						videoBean.setData_data(bodyElement.get(0).attr("data-date"));
						videoBean.setData_time(bodyElement.get(0).attr("data-time"));
						videoBean.setData_videoMlen(bodyElement.get(0).attr("data-time"));
						Element viodeBodyElement = bodyElement.get(0).getElementById("j-v-" + videoBean.getData_id());
						videoBean.setVideoImage(viodeBodyElement.attr("data-poster"));
						videoBean.setVideoUri(viodeBodyElement.attr("data-mp4"));
						Elements subUser = userElementList.get(i).getElementsByClass("u-img");
						Elements userImageUri = subUser.get(0).getElementsByTag("a");//.get(0).getElementsByClass("u-logo lazy").attr("data-original");
						Elements sub = userImageUri.get(0).getElementsByTag("img");
						String imageUri = sub.attr("data-original");
						String username = sub.attr("alt");
						videoBean.setUserName(username);
						videoBean.setUserImageUri(imageUri);
						videoBean.setItemType(4);
						videoList.add(videoBean);
					}
				}
				Message message = new Message();
				message.obj = videoList;
				message.what = 0;
				nowPage = willeToGetPage;
				handler.sendMessage(message);
			}
		}).start();
	}

	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}


}
