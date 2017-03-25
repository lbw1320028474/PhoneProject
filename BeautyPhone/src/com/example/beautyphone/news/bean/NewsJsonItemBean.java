package com.example.beautyphone.news.bean;

public class NewsJsonItemBean {
	private String imgurl = "";
	private boolean has_content;
	private String docurl = "";
	private int id = 0;
	private String time = "";
	private String title = "";
	private String channelname = "";
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public boolean isHas_content() {
		return has_content;
	}
	public void setHas_content(boolean has_content) {
		this.has_content = has_content;
	}
	public String getDocurl() {
		return docurl;
	}
	public void setDocurl(String docurl) {
		this.docurl = docurl;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getChannelname() {
		return channelname;
	}
	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}
	@Override
	public String toString() {
		return "NewsItemBean [imgurl=" + imgurl + ", has_content=" + has_content + ", docurl=" + docurl + ", id=" + id
				+ ", time=" + time + ", title=" + title + ", channelname=" + channelname + "]";
	}
	
	
}
