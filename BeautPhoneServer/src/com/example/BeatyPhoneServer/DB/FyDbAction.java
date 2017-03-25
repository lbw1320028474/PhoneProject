package com.example.BeatyPhoneServer.DB;

import com.example.BeatyPhoneServer.Bean.FYCallLogPushRequest;
import com.example.BeatyPhoneServer.Bean.FYEnpowerRequestBean;
import com.example.BeatyPhoneServer.Bean.FYEnpowerResponseBean;

public class FyDbAction {
	private static FyDbAction action = null;
	private FyDbAction(){};

	public static FyDbAction getInstance(){
		if(action == null){
			action = new FyDbAction();
		}
		return action;
	}

	public int addFyAskEnpowerLog(FYEnpowerRequestBean bean){
		DButil butil = new DButil();
		String sqlOpration = "INSERT INTO fyaskempowerlog(Action, CallType,ShowNumberType," +
				"Caller,Callee,AppExtraData,FyCallId,FyAccountId," +
				"AppAccountId,AppId,ChannelId,IfRecord,Time,Au,CreateDate) VALUES('" + 
				bean.getAction() + "'," +
				bean.getCallType() + "," +
				bean.getShowNumberType() + ",'" +
				bean.getCaller() + "','" +
				bean.getCallee() + "','" +
				bean.getAppExtraData() + "','" +
				bean.getFyCallId() + "','" +
				bean.getFyAccountId() + "','" +
				bean.getAppAccountId() + "','" +
				bean.getAppId() + "','" +
				bean.getChannelId() + "'," +
				bean.getIfRecord() + ",'" +
				bean.getTi() + "','" +
				bean.getAu() + "','" +
				System.currentTimeMillis() + "')";
		int row = butil.dbAdd(sqlOpration);
		return row;
	}

	public int addCallLogPush(FYCallLogPushRequest bean){
		DButil butil = new DButil();
		String sqlOpration = "INSERT INTO calllog(Action,AppId,AppCallId," +
				"FyCallId,AppServerExtraData,CallbackFirstStartTime,CallbackFirstEndTime,CallStartTime," +
				"CallEndTime,Duration,StopReason,TrueShowNumberType,TrueIfRecord,time,Au,CreateDate) VALUES('" + 
				bean.getAction() + "','" +
				bean.getAppId() + "','" +
				bean.getAppCallId() + "','" +
				bean.getFyCallId() + "','" +
				bean.getAppServerExtraData() + "','" +
				bean.getCallbackFirstStartTime() + "','" +
				bean.getCallbackFirstEndTime() + "','" +
				bean.getCallStartTime() + "','" +
				bean.getCallEndTime() + "','" +
				bean.getCallDuration() + "','" +
				bean.getStopReason() + "'," +
				bean.getTrueShowNumberType() + "," +
				bean.getTrueIfRecord() + ",'" +
				bean.getTime() + "','" +
				bean.getAu() + "','" +
				System.currentTimeMillis() + "')";
		int row = butil.dbAdd(sqlOpration);
		return row;
	}

	public int addFyAnswerEnpowerLog(FYEnpowerResponseBean bean){
		DButil butil = new DButil();
		String sqlOpration = "INSERT INTO aswerfyempowerlog(ResultCode,ResultMsg,AppCallId," +
				"MaxCallMinute,AppServerExtraData,ShowNumberType,IfRecord,CreatDate) VALUES('" + 
				bean.getResultCode() + "','" +
				bean.getResultMsg() + "','" +
				bean.getResult().getAppCallId() + "'," +
				bean.getResult().getMaxCallMinute() + ",'" +
				bean.getResult().getAppServerExtraData() + "'," +
				bean.getResult().getShowNumberType() + "," +
				bean.getResult().getIfRecord() + ",'" +
				System.currentTimeMillis() + "')";
		int row = butil.dbAdd(sqlOpration);
		return row;
	}
}
