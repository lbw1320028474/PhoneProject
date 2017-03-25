package com.example.beautyphone.news.bean;

public class NewsTitleBean {
	private String newsType = "";
	private String titleName = "";
	public String getNewsType() {
		return newsType;
	}
	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	@Override
	public String toString() {
		return "NewsUtlTitleBean [newsType=" + newsType + ", titleName=" + titleName + "]";
	}


}
