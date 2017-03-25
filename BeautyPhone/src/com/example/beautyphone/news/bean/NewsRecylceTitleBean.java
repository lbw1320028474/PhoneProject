package com.example.beautyphone.news.bean;

public class NewsRecylceTitleBean extends PersonalRecycleBaseBean {
	private String showNewsType = "";

	public String getShowNewsType() {
		return showNewsType;
	}

	public void setShowNewsType(String showNewsType) {
		this.showNewsType = showNewsType;
	}

	@Override
	public String toString() {
		return "NewsRecylceTitleBean [showNewsType=" + showNewsType + "]";
	}


}
