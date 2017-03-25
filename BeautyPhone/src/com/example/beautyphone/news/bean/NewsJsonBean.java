package com.example.beautyphone.news.bean;

import java.util.ArrayList;

public class NewsJsonBean {
	private int size = 0;
	private ArrayList<NewsJsonItemBean> list = null;
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
		return "NewsJsonBean [size=" + size + ", list=" + list + "]";
	}


}
