package com.example.BeatyPhoneServer.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.example.BeatyPhoneServer.Bean.ExtraDataBean;
import com.example.BeatyPhoneServer.Bean.FYCallLogPushRequest;
import com.example.BeatyPhoneServer.Bean.GrantMoneyRequestBean;
import com.example.BeatyPhoneServer.Bean.UserLoginResponse;

public class BeautyPhoneDbAction {
	private static BeautyPhoneDbAction dbAction = null;
	private BeautyPhoneDbAction(){};

	public static BeautyPhoneDbAction getInstance(){
		if(dbAction == null){
			dbAction = new BeautyPhoneDbAction();
		}
		return dbAction;
	}

	public int haveNewCallLog(FYCallLogPushRequest request){
		Long duration = Long.parseLong(request.getCallDuration());
		if(duration == 0){
			return 1;
		}
		int min = (int) (duration/1000/60);
		if(duration/1000%60 > 30){
			min+=1;
		}
		JSONObject jsonObject = JSONObject.parseObject(request.getAppServerExtraData());
		ExtraDataBean extraDataBean = JSONObject.toJavaObject(jsonObject, ExtraDataBean.class);
		String sqlOpration = "UPDATE phoneinfo SET MaxPhoneTime=MaxPhoneTime-" + 
				min + " where UserAccount='" + 
				extraDataBean.getFromAppAccount() + "'";
		DButil butil = new DButil();
		int row = butil.dbUpdata(sqlOpration);
		return row;
	}

	/**
	 * 0:用户已存在,1:可以注册,2:未知错误
	 * @param number
	 * @return
	 */
	public int identIsCanRegist(String number){
		String sql = "select 1 from user where UserAccount='" + number + "' limit 1";
		DButil butil = new DButil();
		ResultSet resultSet = butil.dbQuery(sql);
		try {
			if(resultSet.next()){
				String isCanRegist = resultSet.getString("1");
				if(isCanRegist.isEmpty()){
					//System.out.println("不存在用户,可注册");
					return 1;
				}else if(isCanRegist.equals("1")){
					//System.out.println("用户已存在");
					return 0;
				}else{
					//System.out.println("可以注册");
					return 1;
				}
			}else{
				//System.out.println("不存在用户,可注册");
				return 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 2;
		}
	}

	public int addRegistUser(Map<String, Object> map){
		String sqlOpration = "INSERT INTO user(AppId, AppToken,ChannelId," +
				" UserAccount,UserPassword,FYAccount,FYPassword,UserState," +
				"CreatDate) VALUES('" + map.get("AppId") + "','" +
				map.get("AppToken") + "','" +
				map.get("ChannelId") + "','" +
				map.get("UserAccount") + "','" +
				map.get("UserPassword") + "','" +
				map.get("FYAccount") + "','" +
				map.get("FYPassword") + "'," +
				map.get("UserState") + ",'" +
				map.get("CreatDate") + "')";
		DButil butil = new DButil();
		int row = butil.dbAdd(sqlOpration);
		System.out.print("添加联系人影响行数：" + row + "\n");
		if(row > 0){
			return addUserInfo(map);
		}else{
			System.out.print("用户添加未完毕,用户已存在\n");
			return 3;
		}
	}

	/**
	 * @param map map需要put进两个特定key值的参数，（1）用户名：“UserAccount”，（2）用户密码：“UserPassword”
	 * @return
	 * 返回0：秘密错误
	 * 返回1：登录成功
	 * 返回2：用户不存在
	 */
	public int loginUser(Map<String, Object> map){
		String sqlOpration = "select UserAccount,UserPassword from user where UserAccount='" +
				map.get("UserAccount") + "'";
		DButil butil = new DButil();
		ResultSet resultSet = butil.dbQuery(sqlOpration);
		if(resultSet == null){
			return 2;
		}else{
			System.out.print("节点不为空" + " + " + map.get("UserAccount") + " + " + 
					map.get("UserPassword"));
			try {
				if(resultSet.first()){
					System.out.print("loginAccount:" + " + " + map.get("UserAccount"));
					String userPassword = resultSet.getString("UserPassword");
					System.out.print("dbAccount:" + " + " + userPassword);
					if(userPassword.equals(map.get("UserPassword"))){
						System.out.print("验证成功");
						return 1;
					}else{
						System.out.print("密码错误1");
						return 0;
					}
				}else{
					System.out.print("不存在数据");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return 2;
			}
		}
		return 2;
	}

	public UserLoginResponse grantMoney(GrantMoneyRequestBean bean){
		if(bean != null){
			String sqlOpration = "select BalanceMoney from phoneinfo where UserAccount='" + 
					bean.getGrantUserAccount() + "'";
			DButil butil = new DButil();
			ResultSet resultSet = butil.dbQuery(sqlOpration);
			if(resultSet != null){
				try {
					if(resultSet.next()){
						if(resultSet.getFloat("BalanceMoney") >= Float.parseFloat(bean.getGrantUserMoney())){
							return updataByGrant(bean);
						}else{
							System.out.println("用户余额不足");
							return null;
						}
					}else{
						System.out.println("用户查询失败");
						return null;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("用户查询异常");
					return null;
				}
			}else{
				System.out.println("用户查询失败1");
				return null;
			}
		}
		return null;
	}

	private UserLoginResponse updataByGrant(GrantMoneyRequestBean bean) {
		// TODO Auto-generated method stub
		String sqlOpration = "UPDATE phoneinfo SET BalanceMoney=BalanceMoney-" + 
				bean.getGrantUserMoney() + " where UserAccount='" + bean.getGrantUserAccount() + "'";
		DButil butil = new DButil();
		int row = butil.dbUpdata(sqlOpration);
		if(row > 0){
			addGrantLog(bean);
			UserLoginResponse loginResponse = getUserLoginedInfo(bean.getGrantUserAccount());
			return loginResponse;
		}else{
			return null;
		}
	}

	private void addGrantLog(GrantMoneyRequestBean bean) {
		// TODO Auto-generated method stub
		DButil butil = new DButil();
		String sqlUserinfoOpration = "INSERT INTO grantmoneylog(" +
				"VideoId,VideoTitle,GrantUser," +
				"GrantUserAccount,GrantUserMoney,GrantTime) VALUES('" + 
				bean.getVideoId() + "','" +
				bean.getVideoTitle() + "','" +
				bean.getGrantUserAccount() + "','" +
				bean.getGrantUserAccount() + "','" +
				bean.getGrantUserMoney() + "','" +
				System.currentTimeMillis() + "')";
		int inforow = butil.dbAdd(sqlUserinfoOpration);
		System.out.print("添加赏赐记录行数：" + inforow);
	}

	public UserLoginResponse getUserLoginedInfo(String userAccount){
		String sqlOpration = "select * from user,userinfo,phoneinfo where `user`.UserAccount='" + 
				userAccount + "' and userinfo.UserAccount='" + userAccount + "' and phoneinfo.UserAccount='" + 
				userAccount + "'";
		DButil butil = new DButil();
		UserLoginResponse loginResponse= new UserLoginResponse();
		ResultSet resultSet = butil.dbQuery(sqlOpration);
		if(resultSet != null){
			try {
				if(resultSet.next()){
					loginResponse.setUserAccount(resultSet.getString("UserAccount"));
					loginResponse.setFyAccount(resultSet.getString("FYAccount"));
					loginResponse.setFyPassword(resultSet.getString("FYPassword"));
					loginResponse.setUserState(resultSet.getInt("UserState") + "");
					loginResponse.setCreatDate(resultSet.getString("CreatDate"));
					loginResponse.setUserName(resultSet.getString("UserName"));
					loginResponse.setUserNikeName(resultSet.getString("UserNikeName"));
					loginResponse.setUserNumber(resultSet.getString("UserNumber"));
					loginResponse.setUserVipState(resultSet.getInt("IsVip") + "");
					loginResponse.setMaxPhoneTime(resultSet.getString("MaxPhoneTime"));
					loginResponse.setLoginState(1);
					loginResponse.setLoginMessage("登录成功");
					loginResponse.setBalanceMoney(resultSet.getFloat("BalanceMoney") + "");
				}else{
					loginResponse.setLoginState(2);
					loginResponse.setLoginMessage("用户不存在");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				loginResponse.setLoginState(0);
				loginResponse.setLoginMessage("密码错误");
			}
		}else{
			loginResponse.setLoginState(0);
			loginResponse.setLoginMessage("密码错误");
		}
		return loginResponse;

	}

	private int addUserInfo(Map<String, Object> map) {
		DButil butil = new DButil();
		String sqlUserinfoOpration = "INSERT INTO userinfo(" +
				"UserAccount, UserName,UserNikeName," +
				" UserNumber,IsVip,CreateDate) VALUES('" + 
				map.get("UserAccount") + "'," +
				"null,null,'" +
				map.get("UserNumber") + "','" +
				0 + "','" +	map.get("CreatDate") + "')";
		int inforow = butil.dbAdd(sqlUserinfoOpration);
		System.out.print("添加详情影响行数：" + inforow);
		if(inforow <= 0){
			String sqldeleteOpration = "delete from user where UserAccount='" + map.get("UserAccount") + "'";
			int deleterow = butil.dbDelete(sqldeleteOpration);
			if(deleterow > 0){
				System.out.print("用户添加未成功,详细填写错误");
				return 2;
			}
			System.out.print("回滚失败，出现垃圾记录");
			return 4;
		}else{
			System.out.print("用户详情添加完毕:" + inforow + "\n");
			return addPhoneInfo(map);
		}
	}

	private int addPhoneInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		DButil butil = new DButil();
		String sqlOpration = "INSERT INTO phoneinfo(" +
				"UserAccount, AppId,ChannelId," +
				"MaxPhoneTime,CreateDate) VALUES('" + 
				map.get("UserAccount") + "','" +
				map.get("AppId") + "','" +
				map.get("ChannelId") + "'," +
				map.get("MaxPhoneTime") + ",'" + map.get("CreatDate") + "')";
		int inforow = butil.dbAdd(sqlOpration);
		if(inforow > 0){
			System.out.print("所有数据添加完毕:" + inforow + "\n");
			return inforow;
		}else{
			String sqldeleteOpration = "delete from user where UserAccount='" + map.get("UserAccount") + "'";
			int deleterow = butil.dbDelete(sqldeleteOpration);
			if(deleterow > 0){
				System.out.print("用户详情添加失败后,用户回滚成功");
			}else{
				System.out.print("回滚失败，出现垃圾数据库信息");
			}
			String sqldeleteOpration1 = "delete from userinfo where UserAccount='" + map.get("UserAccount") + "'";
			int deleterow1 = butil.dbDelete(sqldeleteOpration1);
			if(deleterow1 > 0){
				System.out.print("电话详情添加失败后,用户详情回滚成功");
			}else{
				System.out.print("回滚失败，出现垃圾数据库信息");
			}
			return 0;
		}
	}
}
