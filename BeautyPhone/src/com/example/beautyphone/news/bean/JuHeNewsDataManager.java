package com.example.beautyphone.news.bean;

import com.alibaba.fastjson.JSONObject;
import com.example.beautyphone.network.HttpUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class JuHeNewsDataManager {
	private String baseUrl = "http://v.juhe.cn/toutiao/index?type=";
	private String key = "&key=372773aa188f0b6964e0b544a2ffec72";
	private Handler top_get_handler = null;
	private Handler shehui_get_handler = null;
	private Handler guonei_get_handler = null;
	private Handler guoji_get_handler = null;
	private Handler yule_get_handler = null;
	private Handler tiyu_get_handler = null;
	private Handler junshi_get_handler = null;
	private Handler keji_get_handler = null;
	private Handler caijing_get_handler = null;
	private Handler shishang_get_handler = null;
	/*private Handler ent_get_handler = null;
	private Handler tech_get_handler = null;
	private Handler war_get_handler = null;
	private Handler money_get_handler = null;
	private Handler lady_get_handler = null;*/
	private JuHeNewsJsonData top = null;
	private JuHeNewsJsonData shehui = null;
	private JuHeNewsJsonData guonei = null;
	private JuHeNewsJsonData guoji = null;
	private JuHeNewsJsonData yule = null;
	private JuHeNewsJsonData tiyu = null;
	private JuHeNewsJsonData junshi = null;
	private JuHeNewsJsonData keji = null;
	private JuHeNewsJsonData caijing = null;
	private JuHeNewsJsonData shishang = null;

	public JuHeNewsDataManager(){
		initData();
		loadJuHeTopNewsData();
		loadJuHeShehuiNewsData();
		loadJuHeKejiNewsData();
		/*		loadJuHeTopNewsData();
		loadJuHeTopNewsData();
		loadJuHeTopNewsData();
		loadJuHeTopNewsData();
		loadJuHeTopNewsData();
		loadJuHeTopNewsData();
		loadJuHeTopNewsData();*/
	}

	private void initData() {
		// TODO Auto-generated method stub
		top = new JuHeNewsJsonData();
		shehui = new JuHeNewsJsonData();
		guonei = new JuHeNewsJsonData();
		guoji = new JuHeNewsJsonData();
		yule = new JuHeNewsJsonData();
		tiyu = new JuHeNewsJsonData();
		junshi = new JuHeNewsJsonData();
		keji = new JuHeNewsJsonData();
		caijing = new JuHeNewsJsonData();
		shishang = new JuHeNewsJsonData();
	}

	public void loadJuHeTopNewsData(){
		top.setLoadOk(false);
		String top_url = baseUrl + "top" + key;
		HttpUtil.getInstance().okHttpDoGet(top_url, topHandle);
	}
	public void loadJuHeShehuiNewsData(){
		shehui.setLoadOk(false);
		String top_url = baseUrl + "shehui" + key;
		HttpUtil.getInstance().okHttpDoGet(top_url, shehuiHandle);
	}
	public void loadJuHeGuoneiNewsData(){
		guonei.setLoadOk(false);
		String top_url = baseUrl + "guonei" + key;
		HttpUtil.getInstance().okHttpDoGet(top_url, guoneiHandle);
	}
	public void loadJuHeYuleNewsData(){
		yule.setLoadOk(false);
		String top_url = baseUrl + "yule" + key;
		HttpUtil.getInstance().okHttpDoGet(top_url, yuleHandle);
	}
	public void loadJuHeTiyuNewsData(){
		tiyu.setLoadOk(false);
		String top_url = baseUrl + "tiyu" + key;
		HttpUtil.getInstance().okHttpDoGet(top_url, tiyuHandle);
	}
	public void loadJuHeJunshiNewsData(){
		junshi.setLoadOk(false);
		String top_url = baseUrl + "junshi" + key;
		HttpUtil.getInstance().okHttpDoGet(top_url, junshiHandle);
	}
	public void loadJuHeKejiNewsData(){
		keji.setLoadOk(false);
		String top_url = baseUrl + "keji" + key;
		HttpUtil.getInstance().okHttpDoGet(top_url, kejiHandle);
	}
	public void loadJuHeCaijingNewsData(){
		caijing.setLoadOk(false);
		String top_url = baseUrl + "caijing" + key;
		HttpUtil.getInstance().okHttpDoGet(top_url, caijingHandle);
	}
	public void loadJuHeShishangNewsData(){
		shishang.setLoadOk(false);
		String top_url = baseUrl + "shishang" + key;
		HttpUtil.getInstance().okHttpDoGet(top_url, shishangHandle);
	}
	public void loadJuHeGuojiNewsData(){
		guoji.setLoadOk(false);
		String top_url = baseUrl + "guoji" + key;
		HttpUtil.getInstance().okHttpDoGet(top_url, guojiHandle);
	}



	private Handler topHandle= new Handler(){
		public void handleMessage(android.os.Message msg) {
			try {
				if(!msg.getData().getString("json").isEmpty()){
					try {
						String json = msg.getData().getString("json");
						JSONObject jsonObject = JSONObject.parseObject(json);
						JuHeNewsJsonData bean = JSONObject.toJavaObject(jsonObject, JuHeNewsJsonData.class);
						top = bean;
						top.setNewType("top");
						if(top_get_handler != null){
							Message ent_msg = new Message();
							Bundle bundle = new Bundle();
							bundle.putBoolean("top", true);
							ent_msg.setData(bundle);
							top_get_handler.sendMessage(ent_msg);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					top.setLoadOk(true);
					//Log.e("c", ent.toString());
				}else{
					top.setLoadOk(true);
				}
			} catch (Exception e) {
				// TODO: handle exception
				top.setLoadOk(true);
			}
		};
	};

	private Handler shehuiHandle= new Handler(){
		public void handleMessage(android.os.Message msg) {
			try {
				if(!msg.getData().getString("json").isEmpty()){
					try {
						String json = msg.getData().getString("json");
						JSONObject jsonObject = JSONObject.parseObject(json);
						JuHeNewsJsonData bean = JSONObject.toJavaObject(jsonObject, JuHeNewsJsonData.class);
						shehui = bean;
						shehui.setNewType("shehui");
						if(shehui_get_handler != null){
							Message ent_msg = new Message();
							Bundle bundle = new Bundle();
							bundle.putBoolean("shehui", true);
							ent_msg.setData(bundle);
							shehui_get_handler.sendMessage(ent_msg);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					shehui.setLoadOk(true);
					//Log.e("c", ent.toString());
				}else{
					shehui.setLoadOk(true);
				}
			} catch (Exception e) {
				// TODO: handle exception
				shehui.setLoadOk(true);
			}
		};
	};

	private Handler guoneiHandle= new Handler(){
		public void handleMessage(android.os.Message msg) {
			try {
				if(!msg.getData().getString("json").isEmpty()){
					try {
						String json = msg.getData().getString("json");
						JSONObject jsonObject = JSONObject.parseObject(json);
						JuHeNewsJsonData bean = JSONObject.toJavaObject(jsonObject, JuHeNewsJsonData.class);
						guonei = bean;
						guonei.setNewType("guonei");
						if(guonei_get_handler != null){
							Message ent_msg = new Message();
							Bundle bundle = new Bundle();
							bundle.putBoolean("guonei", true);
							ent_msg.setData(bundle);
							guonei_get_handler.sendMessage(ent_msg);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					guonei.setLoadOk(true);
					//Log.e("c", ent.toString());
				}else{
					guonei.setLoadOk(true);
				}
			} catch (Exception e) {
				// TODO: handle exception
				guonei.setLoadOk(true);
			}
		};
	};

	private Handler guojiHandle= new Handler(){
		public void handleMessage(android.os.Message msg) {
			try {
				if(!msg.getData().getString("json").isEmpty()){
					try {
						String json = msg.getData().getString("json");
						JSONObject jsonObject = JSONObject.parseObject(json);
						JuHeNewsJsonData bean = JSONObject.toJavaObject(jsonObject, JuHeNewsJsonData.class);
						guoji = bean;
						guoji.setNewType("guoji");
						if(guoji_get_handler != null){
							Message ent_msg = new Message();
							Bundle bundle = new Bundle();
							bundle.putBoolean("guoji", true);
							ent_msg.setData(bundle);
							guoji_get_handler.sendMessage(ent_msg);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					guoji.setLoadOk(true);
					//Log.e("c", ent.toString());
				}else{
					guoji.setLoadOk(true);
				}
			} catch (Exception e) {
				// TODO: handle exception
				guoji.setLoadOk(true);
			}
		};
	};
	private Handler yuleHandle= new Handler(){
		public void handleMessage(android.os.Message msg) {
			try {
				if(!msg.getData().getString("json").isEmpty()){
					try {
						String json = msg.getData().getString("json");
						JSONObject jsonObject = JSONObject.parseObject(json);
						JuHeNewsJsonData bean = JSONObject.toJavaObject(jsonObject, JuHeNewsJsonData.class);
						yule = bean;
						yule.setNewType("yule");
						if(yule_get_handler != null){
							Message ent_msg = new Message();
							Bundle bundle = new Bundle();
							bundle.putBoolean("yule", true);
							ent_msg.setData(bundle);
							yule_get_handler.sendMessage(ent_msg);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					yule.setLoadOk(true);
					//Log.e("c", ent.toString());
				}else{
					yule.setLoadOk(true);
				}
			} catch (Exception e) {
				// TODO: handle exception
				yule.setLoadOk(true);
			}
		};
	};
	private Handler tiyuHandle= new Handler(){
		public void handleMessage(android.os.Message msg) {
			try {
				if(!msg.getData().getString("json").isEmpty()){
					try {
						String json = msg.getData().getString("json");
						JSONObject jsonObject = JSONObject.parseObject(json);
						JuHeNewsJsonData bean = JSONObject.toJavaObject(jsonObject, JuHeNewsJsonData.class);
						tiyu = bean;
						tiyu.setNewType("tiyu");
						if(tiyu_get_handler != null){
							Message ent_msg = new Message();
							Bundle bundle = new Bundle();
							bundle.putBoolean("tiyu", true);
							ent_msg.setData(bundle);
							tiyu_get_handler.sendMessage(ent_msg);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					tiyu.setLoadOk(true);
					//Log.e("c", ent.toString());
				}else{
					tiyu.setLoadOk(true);
				}
			} catch (Exception e) {
				// TODO: handle exception
				tiyu.setLoadOk(true);
			}
		};
	};
	private Handler junshiHandle= new Handler(){
		public void handleMessage(android.os.Message msg) {
			try {
				if(!msg.getData().getString("json").isEmpty()){
					try {
						String json = msg.getData().getString("json");
						JSONObject jsonObject = JSONObject.parseObject(json);
						JuHeNewsJsonData bean = JSONObject.toJavaObject(jsonObject, JuHeNewsJsonData.class);
						junshi = bean;
						junshi.setNewType("junshi");
						if(junshi_get_handler != null){
							Message ent_msg = new Message();
							Bundle bundle = new Bundle();
							bundle.putBoolean("junshi", true);
							ent_msg.setData(bundle);
							junshi_get_handler.sendMessage(ent_msg);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					junshi.setLoadOk(true);
					//Log.e("c", ent.toString());
				}else{
					junshi.setLoadOk(true);
				}
			} catch (Exception e) {
				// TODO: handle exception
				junshi.setLoadOk(true);
			}
		};
	};
	private Handler kejiHandle= new Handler(){
		public void handleMessage(android.os.Message msg) {
			try {
				if(!msg.getData().getString("json").isEmpty()){
					try {
						String json = msg.getData().getString("json");
						JSONObject jsonObject = JSONObject.parseObject(json);
						JuHeNewsJsonData bean = JSONObject.toJavaObject(jsonObject, JuHeNewsJsonData.class);
						keji = bean;
						keji.setNewType("keji");
						if(keji_get_handler != null){
							Message ent_msg = new Message();
							Bundle bundle = new Bundle();
							bundle.putBoolean("keji", true);
							ent_msg.setData(bundle);
							keji_get_handler.sendMessage(ent_msg);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					keji.setLoadOk(true);
					//Log.e("c", ent.toString());
				}else{
					keji.setLoadOk(true);
				}
			} catch (Exception e) {
				// TODO: handle exception
				keji.setLoadOk(true);
			}
		};
	};
	private Handler caijingHandle= new Handler(){
		public void handleMessage(android.os.Message msg) {
			try {
				if(!msg.getData().getString("json").isEmpty()){
					try {
						String json = msg.getData().getString("json");
						JSONObject jsonObject = JSONObject.parseObject(json);
						JuHeNewsJsonData bean = JSONObject.toJavaObject(jsonObject, JuHeNewsJsonData.class);
						caijing = bean;
						caijing.setNewType("caijing");
						if(caijing_get_handler != null){
							Message ent_msg = new Message();
							Bundle bundle = new Bundle();
							bundle.putBoolean("caijing", true);
							ent_msg.setData(bundle);
							caijing_get_handler.sendMessage(ent_msg);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					caijing.setLoadOk(true);
					//Log.e("c", ent.toString());
				}else{
					caijing.setLoadOk(true);
				}
			} catch (Exception e) {
				// TODO: handle exception
				caijing.setLoadOk(true);
			}
		};
	};
	private Handler shishangHandle= new Handler(){
		public void handleMessage(android.os.Message msg) {
			try {
				if(!msg.getData().getString("json").isEmpty()){
					try {
						String json = msg.getData().getString("json");
						JSONObject jsonObject = JSONObject.parseObject(json);
						JuHeNewsJsonData bean = JSONObject.toJavaObject(jsonObject, JuHeNewsJsonData.class);
						shishang = bean;
						shishang.setNewType("shishang");
						if(shishang_get_handler != null){
							Message ent_msg = new Message();
							Bundle bundle = new Bundle();
							bundle.putBoolean("shishang", true);
							ent_msg.setData(bundle);
							shishang_get_handler.sendMessage(ent_msg);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					shishang.setLoadOk(true);
					//Log.e("c", ent.toString());
				}else{
					shishang.setLoadOk(true);
				}
			} catch (Exception e) {
				// TODO: handle exception
				shishang.setLoadOk(true);
			}
		};
	};


	public void getTop(Handler handler) {
		//Log.e("c", "");
		this.top_get_handler = handler;
		if(top != null && top.getResult() != null && 
				top.getResult().getData() != null &&
				top.getResult().getData().size() > 0){
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putBoolean("top", true);
			msg.setData(bundle);
			handler.sendMessage(msg);
		}else{
			if(top.isLoadOk()){
				loadJuHeTopNewsData();
			}
		}
	}

	public void getShehui(Handler handler) {
		//Log.e("c", "");
		this.shehui_get_handler = handler;
		if(shehui != null && shehui.getResult() != null && 
				shehui.getResult().getData() != null &&
				shehui.getResult().getData().size() > 0){
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putBoolean("shehui", true);
			msg.setData(bundle);
			handler.sendMessage(msg);
		}else{
			if(shehui.isLoadOk()){
				loadJuHeShehuiNewsData();
			}
		}
	}

	public void getGuonei(Handler handler) {
		//Log.e("c", "");
		this.guonei_get_handler = handler;
		if(guonei != null && guonei.getResult() != null && 
				guonei.getResult().getData() != null &&
				guonei.getResult().getData().size() > 0){
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putBoolean("guonei", true);
			msg.setData(bundle);
			handler.sendMessage(msg);
		}else{
			if(guonei.isLoadOk()){
				loadJuHeGuoneiNewsData();
			}
		}
	}

	public void getGuoji(Handler handler) {
		//Log.e("c", "");
		this.guoji_get_handler = handler;
		if(guoji != null && guoji.getResult() != null && 
				guoji.getResult().getData() != null &&
				guoji.getResult().getData().size() > 0){
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putBoolean("guoji", true);
			msg.setData(bundle);
			handler.sendMessage(msg);
		}else{
			if(guoji.isLoadOk()){
				loadJuHeGuojiNewsData();
			}
		}
	}

	public void getYule(Handler handler) {
		//Log.e("c", "");
		this.yule_get_handler = handler;
		if(yule != null && yule.getResult() != null && 
				yule.getResult().getData() != null &&
				yule.getResult().getData().size() > 0){
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putBoolean("yule", true);
			msg.setData(bundle);
			handler.sendMessage(msg);
		}else{
			if(yule.isLoadOk()){
				loadJuHeYuleNewsData();
			}
		}
	}

	public void getTiyu(Handler handler) {
		//Log.e("c", "");
		this.tiyu_get_handler = handler;
		if(tiyu != null && tiyu.getResult() != null && 
				tiyu.getResult().getData() != null &&
				tiyu.getResult().getData().size() > 0){
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putBoolean("tiyu", true);
			msg.setData(bundle);
			handler.sendMessage(msg);
		}else{
			if(tiyu.isLoadOk()){
				loadJuHeTiyuNewsData();
			}
		}
	}

	public void getJunshi(Handler handler) {
		//Log.e("c", "");
		this.junshi_get_handler = handler;
		if(junshi != null && junshi.getResult() != null && 
				junshi.getResult().getData() != null &&
				junshi.getResult().getData().size() > 0){
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putBoolean("junshi", true);
			msg.setData(bundle);
			handler.sendMessage(msg);
		}else{
			if(junshi.isLoadOk()){
				loadJuHeJunshiNewsData();
			}
		}
	}

	public void getKeji(Handler handler) {
		//Log.e("c", "");
		this.keji_get_handler = handler;
		if(keji != null && keji.getResult() != null && 
				keji.getResult().getData() != null &&
				keji.getResult().getData().size() > 0){
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putBoolean("keji", true);
			msg.setData(bundle);
			handler.sendMessage(msg);
		}else{
			if(keji.isLoadOk()){
				loadJuHeKejiNewsData();
			}
		}
	}

	public void getCaijing(Handler handler) {
		//Log.e("c", "");
		this.caijing_get_handler = handler;
		if(caijing != null && caijing.getResult() != null && 
				caijing.getResult().getData() != null &&
				caijing.getResult().getData().size() > 0){
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putBoolean("caijing", true);
			msg.setData(bundle);
			handler.sendMessage(msg);
		}else{
			if(caijing.isLoadOk()){
				loadJuHeCaijingNewsData();
			}
		}
	}

	public void getShishang(Handler handler) {
		//Log.e("c", "");
		this.shishang_get_handler = handler;
		if(shishang != null && shishang.getResult() != null && 
				shishang.getResult().getData() != null &&
				shishang.getResult().getData().size() > 0){
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putBoolean("shishang", true);
			msg.setData(bundle);
			handler.sendMessage(msg);
		}else{
			if(shishang.isLoadOk()){
				loadJuHeShishangNewsData();
			}
		}
	}

	public JuHeNewsJsonData getTop() {
		return top;
	}

	public void setTop(JuHeNewsJsonData top) {
		this.top = top;
	}

	public JuHeNewsJsonData getShehui() {
		return shehui;
	}

	public void setShehui(JuHeNewsJsonData shehui) {
		this.shehui = shehui;
	}

	public JuHeNewsJsonData getGuonei() {
		return guonei;
	}

	public void setGuonei(JuHeNewsJsonData guonei) {
		this.guonei = guonei;
	}

	public JuHeNewsJsonData getGuoji() {
		return guoji;
	}

	public void setGuoji(JuHeNewsJsonData guoji) {
		this.guoji = guoji;
	}

	public JuHeNewsJsonData getYule() {
		return yule;
	}

	public void setYule(JuHeNewsJsonData yule) {
		this.yule = yule;
	}

	public JuHeNewsJsonData getTiyu() {
		return tiyu;
	}

	public void setTiyu(JuHeNewsJsonData tiyu) {
		this.tiyu = tiyu;
	}

	public JuHeNewsJsonData getJunshi() {
		return junshi;
	}

	public void setJunshi(JuHeNewsJsonData junshi) {
		this.junshi = junshi;
	}

	public JuHeNewsJsonData getKeji() {
		return keji;
	}

	public void setKeji(JuHeNewsJsonData keji) {
		this.keji = keji;
	}

	public JuHeNewsJsonData getCaijing() {
		return caijing;
	}

	public void setCaijing(JuHeNewsJsonData caijing) {
		this.caijing = caijing;
	}

	public JuHeNewsJsonData getShishang() {
		return shishang;
	}

	public void setShishang(JuHeNewsJsonData shishang) {
		this.shishang = shishang;
	}

	@Override
	public String toString() {
		return "JuHeNewsDataManager [top=" + top + ", shehui=" + shehui + ", guonei=" + guonei + ", guoji=" + guoji
				+ ", yule=" + yule + ", tiyu=" + tiyu + ", junshi=" + junshi + ", keji=" + keji + ", caijing=" + caijing
				+ ", shishang=" + shishang + "]";
	}



}
