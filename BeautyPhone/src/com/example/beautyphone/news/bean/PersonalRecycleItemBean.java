package com.example.beautyphone.news.bean;

public class PersonalRecycleItemBean extends PersonalRecycleBaseBean{
	private String newsType = "";
	private NewsJsonItemBean itemBean= null;
	public String getNewsType() {
		return newsType;
	}
	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}
	public NewsJsonItemBean getItemBean() {
		return itemBean;
	}
	public void setItemBean(NewsJsonItemBean itemBean) {
		this.itemBean = itemBean;
	}
	@Override
	public String toString() {
		return "PersonalRecycleItemBean [newsType=" + newsType + ", itemBean=" + itemBean + "]";
	}

}
