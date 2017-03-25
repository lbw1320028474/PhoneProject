package com.example.beautyphone.news.bean;

import com.alibaba.fastjson.JSONObject;
import com.example.beautyphone.network.HttpUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class NewsDataManager {
	private Handler ent_get_handler = null;
	private Handler tech_get_handler = null;
	private Handler war_get_handler = null;
	private Handler money_get_handler = null;
	private Handler lady_get_handler = null;
	private NewsDataItemBean ent = null;
	private NewsDataItemBean tech = null;
	private NewsDataItemBean war = null;
	private NewsDataItemBean money = null;
	private NewsDataItemBean lady = null;

	public NewsDataManager(){
		initData();
		loadEntNewsData(1);
		loadTechNewsData(1);
		loadWarNewsData(1);
		loadMoneyNewsData(1);
		loadLadyNewsData(1);
	}

	private void initData() {
		// TODO Auto-generated method stub
		ent = new NewsDataItemBean();
		tech = new NewsDataItemBean();
		war = new NewsDataItemBean();
		money = new NewsDataItemBean();
		lady = new NewsDataItemBean();
	}

	public void loadEntNewsData(int page){
		ent.setLoadIsOk(false);
		String ent_url = "http://wangyi.butterfly.mopaasapp.com/news/api?type=ent&page=" + page + "&limit=20";
		HttpUtil.getInstance().okHttpDoGet(ent_url, entHandle);
	}

	public void loadTechNewsData(int page){
		tech.setLoadIsOk(false);
		String tech_url = "http://wangyi.butterfly.mopaasapp.com/news/api?type=tech&page=" + page + "&limit=10";
		HttpUtil.getInstance().okHttpDoGet(tech_url, techHandle);
	}

	public void loadWarNewsData(int page){
		war.setLoadIsOk(false);
		String war_url = "http://wangyi.butterfly.mopaasapp.com/news/api?type=war&page=" + page + "&limit=10";
		HttpUtil.getInstance().okHttpDoGet(war_url, warHandle);
	}

	public void loadMoneyNewsData(int page){
		money.setLoadIsOk(false);
		String money_url = "http://wangyi.butterfly.mopaasapp.com/news/api?type=money&page=" + page + "&limit=10";
		HttpUtil.getInstance().okHttpDoGet(money_url, moneyHandle);
	}

	public void loadLadyNewsData(int page){
		lady.setLoadIsOk(false);
		String lady_url = "http://wangyi.butterfly.mopaasapp.com/news/api?type=lady&page=" + page + "&limit=10";
		HttpUtil.getInstance().okHttpDoGet(lady_url, ladyHandle);
	}


	private Handler entHandle= new Handler(){
		public void handleMessage(android.os.Message msg) {
			try {
				if(!msg.getData().getString("json").isEmpty()){
					try {
						String json = msg.getData().getString("json");
						JSONObject jsonObject = JSONObject.parseObject(json);
						NewsJsonBean bean = JSONObject.toJavaObject(jsonObject, NewsJsonBean.class);
						ent.setPageIndex(ent.getPageIndex() + 1);
						ent.getList().addAll(bean.getList());
						ent.setSize(ent.getSize() + bean.getSize());
						ent.setNewsType("ent");
						if(ent_get_handler != null){
							Message ent_msg = new Message();
							Bundle bundle = new Bundle();
							bundle.putBoolean("ent", true);
							ent_msg.setData(bundle);
							ent_get_handler.sendMessage(ent_msg);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}

					ent.setLoadIsOk(true);
					//Log.e("c", ent.toString());
				}else{
					ent.setLoadIsOk(true);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				ent.setLoadIsOk(true);
			}

		};
	};

	private Handler techHandle= new Handler(){
		public void handleMessage(android.os.Message msg) {
			try {
				if(!msg.getData().getString("json").isEmpty()){
					try {
						String json = msg.getData().getString("json");
						JSONObject jsonObject = JSONObject.parseObject(json);
						NewsJsonBean bean = JSONObject.toJavaObject(jsonObject, NewsJsonBean.class);
						tech.setPageIndex(tech.getPageIndex() + 1);
						tech.getList().addAll(bean.getList());
						tech.setSize(tech.getSize() + bean.getSize());
						tech.setNewsType("tech");
						if(tech_get_handler != null){
							Message tech_msg = new Message();
							Bundle bundle = new Bundle();
							bundle.putBoolean("tech", true);
							tech_msg.setData(bundle);
							tech_get_handler.sendMessage(tech_msg);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					tech.setLoadIsOk(true);
					//Log.e("c", tech.toString());
				}else{
					tech.setLoadIsOk(true);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				tech.setLoadIsOk(true);
			}

		};
	};

	private Handler warHandle= new Handler(){
		public void handleMessage(android.os.Message msg) {
			try {
				if(!msg.getData().getString("json").isEmpty()){
					try {
						String json = msg.getData().getString("json");
						JSONObject jsonObject = JSONObject.parseObject(json);
						NewsJsonBean bean = JSONObject.toJavaObject(jsonObject, NewsJsonBean.class);
						war.setPageIndex(war.getPageIndex() + 1);
						war.getList().addAll(bean.getList());
						war.setSize(war.getSize() + bean.getSize());
						war.setNewsType("war");
						if(war_get_handler != null){
							Message war_msg = new Message();
							Bundle bundle = new Bundle();
							bundle.putBoolean("war", true);
							war_msg.setData(bundle);
							war_get_handler.sendMessage(war_msg);
						}
					} catch (Exception e) {
						// TODO: handle exception
						Log.e("c", "战争数据异常");
					}

					war.setLoadIsOk(true);
					//Log.e("c", war.toString());
				}else{
					war.setLoadIsOk(true);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				war.setLoadIsOk(true);
			}

		};
	};

	private Handler moneyHandle= new Handler(){
		public void handleMessage(android.os.Message msg) {
			try {
				if(!msg.getData().getString("json").isEmpty()){
					try {
						String json = msg.getData().getString("json");
						JSONObject jsonObject = JSONObject.parseObject(json);
						NewsJsonBean bean = JSONObject.toJavaObject(jsonObject, NewsJsonBean.class);
						money.setPageIndex(money.getPageIndex() + 1);
						money.getList().addAll(bean.getList());
						money.setSize(money.getSize() + bean.getSize());
						money.setNewsType("money");
						if(money_get_handler != null){
							Message money_msg = new Message();
							Bundle bundle = new Bundle();
							bundle.putBoolean("money", true);
							money_msg.setData(bundle);
							tech_get_handler.sendMessage(money_msg);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}

					money.setLoadIsOk(true);
					//Log.e("c", money.toString());
				}else{
					money.setLoadIsOk(true);
				}
			} catch (Exception e) {
				// TODO: handle exception
				money.setLoadIsOk(true);
			}

		};
	};

	private Handler ladyHandle= new Handler(){
		public void handleMessage(android.os.Message msg) {
			try {
				if(!msg.getData().getString("json").isEmpty()){
					try {
						String json = msg.getData().getString("json");
						JSONObject jsonObject = JSONObject.parseObject(json);
						NewsJsonBean bean = JSONObject.toJavaObject(jsonObject, NewsJsonBean.class);
						lady.setPageIndex(lady.getPageIndex() + 1);
						lady.getList().addAll(bean.getList());
						lady.setSize(lady.getSize() + bean.getSize());
						lady.setNewsType("lady");
						if(lady_get_handler != null){
							Message lady_msg = new Message();
							Bundle bundle = new Bundle();
							bundle.putBoolean("lady", true);
							lady_msg.setData(bundle);
							lady_get_handler.sendMessage(lady_msg);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}

					lady.setLoadIsOk(true);
					//Log.e("c", lady.toString());
				}else{
					lady.setLoadIsOk(true);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				lady.setLoadIsOk(true);
			}

		};
	};

	public NewsDataItemBean getEnt() {
		return ent;
	}
	public void getEnt(Handler handler) {
		//Log.e("c", "");
		this.ent_get_handler = handler;
		if(ent.getList().size() > 0){
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putBoolean("ent", true);
			msg.setData(bundle);
			handler.sendMessage(msg);
		}else{
			if(ent.isLoadIsOk()){
				loadEntNewsData(ent.getPageIndex() + 1);
			}
		}
	}
	public void setEnt(NewsDataItemBean ent) {
		this.ent = ent;
	}

	public void addEnt(NewsJsonBean ent) {
		this.ent.getList().addAll(ent.getList());
		this.ent.setSize(this.ent.getSize() + ent.getSize());
	}

	public NewsDataItemBean getTech() {
		return tech;
	}

	public void getTech(Handler handler) {
		this.tech_get_handler = handler;
		if(tech.getList().size() > 0){
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putBoolean("tech", true);
			msg.setData(bundle);
			handler.sendMessage(msg);
		}else{
			if(tech.isLoadIsOk()){
				loadEntNewsData(tech.getPageIndex() + 1);
			}
		}
	}


	public void setTech(NewsDataItemBean tech) {
		this.tech = tech;
	}
	public NewsDataItemBean getWar() {
		return war;
	}
	public void getWar(Handler handler) {
		this.war_get_handler = handler;
		if(war.getList().size() > 0){
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putBoolean("war", true);
			msg.setData(bundle);
			handler.sendMessage(msg);
		}else{
			if(war.isLoadIsOk()){
				loadEntNewsData(war.getPageIndex() + 1);
			}
		}
	}
	public void setWar(NewsDataItemBean war) {
		this.war = war;
	}
	public NewsDataItemBean getMoney() {
		return money;
	}
	public void getMoney(Handler handler) {
		this.money_get_handler = handler;
		if(money.getList().size() > 0){
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putBoolean("money", true);
			msg.setData(bundle);
			handler.sendMessage(msg);
		}else{
			if(money.isLoadIsOk()){
				loadEntNewsData(money.getPageIndex() + 1);
			}
		}
	}
	public void setMoney(NewsDataItemBean money) {
		this.money = money;
	}
	public NewsDataItemBean getLady() {
		return lady;
	}
	public void getLady(Handler handler) {
		this.lady_get_handler = handler;
		if(lady.getList().size() > 0){
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putBoolean("lady", true);
			msg.setData(bundle);
			handler.sendMessage(msg);
		}else{
			if(lady.isLoadIsOk()){
				loadEntNewsData(lady.getPageIndex() + 1);
			}
		}
	}
	public void setLady(NewsDataItemBean lady) {
		this.lady = lady;
	}


}
