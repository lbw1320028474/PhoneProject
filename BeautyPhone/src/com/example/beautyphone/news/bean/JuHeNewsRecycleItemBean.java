package com.example.beautyphone.news.bean;

public class JuHeNewsRecycleItemBean extends PersonalRecycleBaseBean{
	private String newsType = "";
	private JuHeNewsJsonItemBean itemBean= null;
	public JuHeNewsRecycleItemBean(){
		itemBean = new JuHeNewsJsonItemBean();
	}
	public String getNewsType() {
		return newsType;
	}
	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}
	public JuHeNewsJsonItemBean getItemBean() {
		return itemBean;
	}
	public void setItemBean(JuHeNewsJsonItemBean itemBean) {
		this.itemBean = itemBean;
	}
	@Override
	public String toString() {
		return "JuHeNewsRecycleItemBean [newsType=" + newsType + ", itemBean=" + itemBean + "]";
	}


}
