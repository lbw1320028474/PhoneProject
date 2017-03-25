package com.example.BeatyPhoneServer.DB;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.example.BeatyPhoneServer.Bean.BmPayOrderInfoBean;
import com.example.BeatyPhoneServer.Bean.BmobPayOrderRequest;
import com.example.BeatyPhoneServer.Bean.MyOrderRequest;
import com.example.BeatyPhoneServer.util.AppInfo;
import com.example.BeatyPhoneServer.util.HttpUtil;
import com.example.BeatyPhoneServer.util.UnitConverterUtil;
import com.mysql.jdbc.ResultSet;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class PayDButil {
	private static PayDButil payDButil = null;
	private PayDButil(){

	}

	public static PayDButil getInstance(){
		if(payDButil == null){
			payDButil = new PayDButil();
		}
		return payDButil;
	}

	public int addMyPayOrder(Map<String, Object> map){
		if(map != null){
			String sqlOpration = "INSERT INTO MyPayOrder(BmOrderId," +
					"PayAccount,PayMoney,TruePayMoney,IsDealWith,CreateData) VALUES('" + 
					map.get("BmOrderId") + "','" +
					map.get("PayAccount") + "'," +
					map.get("PayMoney") + "," +
					map.get("TruePayMoney") + "," +
					map.get("IsDealWith") + ",'" +
					map.get("CreateData") + "')";
			DButil butil = new DButil();
			int row = butil.dbAdd(sqlOpration);
			if(row <= 0){
				String updatasqlOpration = "UPDATE MyPayOrder SET PayMoney=" + map.get("PayMoney") +
						",TruePayMoney=" + map.get("TruePayMoney") + " where BmOrderId='" + 
						map.get("BmOrderId") + "'";
				row = butil.dbUpdata(updatasqlOpration);
				System.out.println("我的订单提交:" + row);
				return 0;
			}else{
				System.out.println("我的订单详情提交:" + row);
				return row;
			}
		}
		return 0;
	}

	public void addBmOrder(BmobPayOrderRequest bmobPayOrderRequest){
		if(bmobPayOrderRequest != null){
			userMoneyOption(bmobPayOrderRequest);
		}
		String sqlOpration = "INSERT INTO bmorder(StradeStatus,OutTradeNo," +
				" TradeNo,CreateData) VALUES(" + bmobPayOrderRequest.getTrade_status() + ",'" +
				bmobPayOrderRequest.getOut_trade_no() + "','" +
				bmobPayOrderRequest.getTrade_no() + "','" +
				System.currentTimeMillis() + "')";
		DButil butil = new DButil();
		int row = butil.dbAdd(sqlOpration);
		System.out.println("回调订单数据库：" + row);
	}

	private void userMoneyOption(BmobPayOrderRequest bmobPayOrderRequest) {
		// TODO Auto-generated method stub
		if(bmobPayOrderRequest.getTrade_status().equals("1")){
			String selectSql = "select * from MyPayOrder where BmOrderId='" + 
					bmobPayOrderRequest.getOut_trade_no() + "'";
			DButil butil = new DButil();
			ResultSet resultSet = (ResultSet) butil.dbQuery(selectSql);
			try {
				if(resultSet.next()){
					String payAccount = resultSet.getString("PayAccount");
					String payMoney = resultSet.getString("PayMoney");
					int isDealWith = resultSet.getInt("IsDealWith");
					if(isDealWith != 1){
						updataMyPayOrder(payAccount, payMoney,bmobPayOrderRequest.getOut_trade_no());
					}else{
						System.out.println("订单处理重复");
					}
				}else{
					System.out.println("支付完成：查询我的订单数据为空");
					getOrderInfo(bmobPayOrderRequest.getOut_trade_no());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("支付完成：查询我的订单失败:");
				getOrderInfo(bmobPayOrderRequest.getOut_trade_no());
			}
		}
	}

	private void updataMyPayOrder(String payAccount, String payMoney, String orderId) {
		// TODO Auto-generated method stub
		String updatasqlOpration = "UPDATE MyPayOrder SET PayState=" + 1 +
				",IsDealWith=" + 1 + " where BmOrderId='" + 
				orderId + "'";
		DButil butil = new DButil();
		int row = butil.dbUpdata(updatasqlOpration);
		if(row > 0){
			System.out.println("支付完成：修改数据:" + row);
			beginToAddMoney(payAccount, payMoney);
		}else{
			System.out.println("支付完成,修改数据失败:" + row);
		}
	}

	private void beginToAddMoney(String payAccount, String payMoney) {
		// TODO Auto-generated method stub
		String updatasqlOpration = "UPDATE phoneinfo SET BalanceMoney=BalanceMoney+" + payMoney +
				" where UserAccount='" + payAccount + "'";
		DButil butil = new DButil();
		int row = butil.dbUpdata(updatasqlOpration);
		if(row > 0){
			System.out.print("支付完成：用户余额已更新");
		}else{
			System.out.print("支付完成：但用户余额更新失败");
		}
	}

	//https://api.bmob.cn/1/pay/Bmob?out_trade_no=071d5ed34e9054fd6b6364a75c185d35
	public void getOrderInfo(String orderId){
		/*.header("X-Bmob-Application-Id", AppInfo.BM_PAY_APP_ID)
				.header("X-Bmob-REST-API-Key", AppInfo.BM_PAY_REST_KEY)*/
		if(!orderId.isEmpty()){
			String orderInfo = bmOrderInfoGet(orderId);
			System.out.println("订单详情请求:" + orderInfo);
			if(!orderInfo.isEmpty()){
				JSONObject jsonObject = JSONObject.parseObject(orderInfo);
				BmPayOrderInfoBean infoBean = JSONObject.toJavaObject(jsonObject, BmPayOrderInfoBean.class);
				if(infoBean != null){
					Map<String, Object> daMap = new HashMap<String, Object>();
					daMap.put("BmOrderId", infoBean.getOut_trade_no());
					daMap.put("PayAccount", getAccountByBody(infoBean.getBody()));
					daMap.put("PayMoney", getPayMoneyByBody(infoBean.getBody()));
					daMap.put("TruePayMoney", infoBean.getTotal_fee() + "");
					daMap.put("IsDealWith", 0);
					daMap.put("CreateData", System.currentTimeMillis() + "");
					int row = PayDButil.getInstance().addMyPayOrder(daMap);
					addBmPayOrderInfo(infoBean);
					if(row > 0){
						BmobPayOrderRequest bmobPayOrderRequest = new BmobPayOrderRequest();
						bmobPayOrderRequest.setOut_trade_no(infoBean.getOut_trade_no());
						bmobPayOrderRequest.setTrade_no(infoBean.getTransaction_id());
						if(infoBean.getTrade_state().equals("SUCCESS")){
							bmobPayOrderRequest.setTrade_status("1");
						}else{
							bmobPayOrderRequest.setTrade_status("0");
						}
						addBmOrder(bmobPayOrderRequest);
					}else{
						System.out.println("bmob订单详情添加失败");
					}
				}else{
					System.out.println("订单详情json数据转换失败");
				}
			}else{
				System.out.println("订单详情请求失败:返回为空");
			}
		}else{
			System.out.println("订单详情请求失败:订单ID为空");
		}
	}

	public String getAccountByBody(String str){
		if(!str.isEmpty()){
			int line = str.indexOf("_");
			if(line >= 0){
				String account = str.substring(0, line);
				return account;
			}
		}
		return "";

	}

	public float getPayMoneyByBody(String str){
		if(!str.isEmpty()){
			int line = str.indexOf("_");
			int d = str.indexOf("d");
			if(line >= 0){
				String money_str = str.substring(line + 1, str.length());
				float money = Float.parseFloat(money_str.replace("d", "."));
				return money;
			}
		}
		return 0;

	}

	public void addBmPayOrderInfo(BmPayOrderInfoBean infoBean){
		String account = getAccountByBody(infoBean.getBody());
		String money = getPayMoneyByBody(infoBean.getBody()) + "";
		String sqlOpration = "INSERT INTO bmpayorderinfo(PayAccount,Name," +
				"Body,CreateTime,OutTradeNo,TransactionId,PayType,TotalFee,TradeState,CreateData) VALUES('" + 
				account + "','" +
				infoBean.getName() + "','" +
				infoBean.getBody() + "','" +
				infoBean.getCreate_time() + "','" +
				infoBean.getOut_trade_no() + "','" +
				infoBean.getTransaction_id() + "','" +
				infoBean.getPay_type() + "'," +
				infoBean.getTotal_fee() + ",'" +
				infoBean.getTrade_state() + "','" +
				System.currentTimeMillis() + "')";
		DButil butil = new DButil();
		int row = butil.dbAdd(sqlOpration);
		System.out.println("BM订单详情数据库：" + row);
	}

	public static String bmOrderInfoGet(String orderId){
		String url = "https://api.bmob.cn/1/pay/" + orderId;
		try {
			URL connect_url = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) connect_url.openConnection();
			conn.setRequestProperty("X-Bmob-Application-Id", AppInfo.BM_PAY_APP_ID);
			conn.setRequestProperty("X-Bmob-REST-API-Key", AppInfo.BM_PAY_REST_KEY);
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(10000);
			if (conn.getResponseCode() == 200) {
				InputStream iStream = conn.getInputStream();
				byte[] dataByte = UnitConverterUtil.input2byte(iStream);
				if(dataByte != null){
					String dataStr = new String(dataByte);
					return dataStr;
				}else{
					return null;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return null;
	}
}
