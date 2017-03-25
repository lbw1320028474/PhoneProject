package com.example.beautyphone.news.bean;

import java.util.ArrayList;

public class NewsDataItemBean {
	private boolean loadIsOk = true;
	private int pageIndex = 0;
	private String newsType = "";
	private int size = 0;
	private ArrayList<NewsJsonItemBean> list = null;

	public NewsDataItemBean(){
		list = new ArrayList<NewsJsonItemBean>();
	}

	public boolean isLoadIsOk() {
		return loadIsOk;
	}

	public void setLoadIsOk(boolean loadIsOk) {
		this.loadIsOk = loadIsOk;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getNewsType() {
		return newsType;
	}

	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public ArrayList<NewsJsonItemBean> getList() {
		return list;
	}

	public void setList(ArrayList<NewsJsonItemBean> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "NewsDataItemBean [loadIsOk=" + loadIsOk + ", pageIndex=" + pageIndex + ", newsType=" + newsType
				+ ", size=" + size + ", list=" + list + "]";
	}


}
