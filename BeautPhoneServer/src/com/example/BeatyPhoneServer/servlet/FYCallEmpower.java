package com.example.BeatyPhoneServer.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.BeatyPhoneServer.Bean.ExtraDataBean;
import com.example.BeatyPhoneServer.Bean.FYEnpowerRequestBean;
import com.example.BeatyPhoneServer.Bean.FYEnpowerResponseBean;
import com.example.BeatyPhoneServer.Bean.FYEnpowerResult;
import com.example.BeatyPhoneServer.Bean.UserLoginResponse;
import com.example.BeatyPhoneServer.DB.BeautyPhoneDbAction;
import com.example.BeatyPhoneServer.DB.FyDbAction;
import com.example.BeatyPhoneServer.util.ParamChangUtil;

public class FYCallEmpower extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public FYCallEmpower() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.print("收到呼叫鉴权请求");
		FYEnpowerRequestBean enpowerRequestBean = getEnpowerRequestbean(request);
		System.out.print(enpowerRequestBean.toString() + "\n");
		FyDbAction.getInstance().addFyAskEnpowerLog(enpowerRequestBean);
		FYEnpowerResponseBean enpowerResponseBean = new FYEnpowerResponseBean();
		UserLoginResponse loginResponse = BeautyPhoneDbAction.getInstance().getUserLoginedInfo(enpowerRequestBean.getAppAccountId());
		ExtraDataBean extraData = new ExtraDataBean();
		extraData.setFromAppAccount(enpowerRequestBean.getAppAccountId());
		extraData.setToAppAccount(enpowerRequestBean.getCallee());
		extraData.setFromFyAccount(enpowerRequestBean.getCaller());
		extraData.setToFyAccount(enpowerRequestBean.getCallee());
		if(loginResponse.getUserState().equals("1")){
			if(!loginResponse.getMaxPhoneTime().isEmpty() && Integer.parseInt(loginResponse.getMaxPhoneTime()) > 0){
				FYEnpowerResult fyEnpowerResult = new FYEnpowerResult();
				fyEnpowerResult.setAppCallId(enpowerRequestBean.getFyCallId());
				extraData.setExtraData("鉴权成功");
				String extraString = JSONObject.toJSONString(extraData);
				fyEnpowerResult.setAppServerExtraData(extraString);
				fyEnpowerResult.setFyCallId(enpowerRequestBean.getFyCallId());
				fyEnpowerResult.setShowNumberType(enpowerRequestBean.getShowNumberType());
				fyEnpowerResult.setMaxCallMinute(Integer.parseInt(loginResponse.getMaxPhoneTime()));
				fyEnpowerResult.setIfRecord(enpowerRequestBean.getIfRecord());
				enpowerResponseBean.setResultCode("0");
				enpowerResponseBean.setResultMsg("授权成功");
				enpowerResponseBean.setResult(fyEnpowerResult);
			}else{
				FYEnpowerResult fyEnpowerResult = new FYEnpowerResult();
				enpowerResponseBean.setResultMsg("用户可用通话分钟数为0");
				enpowerResponseBean.setResultCode("900001");	//用户未启用
				fyEnpowerResult.setAppCallId(enpowerRequestBean.getFyCallId());
				extraData.setExtraData("鉴权失败");
				String extraString = JSONObject.toJSONString(extraData);
				fyEnpowerResult.setAppServerExtraData(extraString);
				fyEnpowerResult.setFyCallId(enpowerRequestBean.getFyCallId());
				fyEnpowerResult.setShowNumberType(enpowerRequestBean.getShowNumberType());
				fyEnpowerResult.setMaxCallMinute(Integer.parseInt(loginResponse.getMaxPhoneTime()));
				fyEnpowerResult.setIfRecord(enpowerRequestBean.getIfRecord());
				enpowerResponseBean.setResult(fyEnpowerResult);
			}
		}else{
			FYEnpowerResult fyEnpowerResult = new FYEnpowerResult();
			enpowerResponseBean.setResultMsg("用户未启用");
			enpowerResponseBean.setResultCode("900002");	//用户未启用
			fyEnpowerResult.setAppCallId(enpowerRequestBean.getFyCallId());
			extraData.setExtraData("鉴权失败");
			String extraString = JSONObject.toJSONString(extraData);
			fyEnpowerResult.setAppServerExtraData(extraString);
			fyEnpowerResult.setFyCallId(enpowerRequestBean.getFyCallId());
			fyEnpowerResult.setShowNumberType(enpowerRequestBean.getShowNumberType());
			fyEnpowerResult.setMaxCallMinute(Integer.parseInt(loginResponse.getMaxPhoneTime()));
			fyEnpowerResult.setIfRecord(enpowerRequestBean.getIfRecord());
			enpowerResponseBean.setResult(fyEnpowerResult);
		}
		FyDbAction.getInstance().addFyAnswerEnpowerLog(enpowerResponseBean);
		String jsonBean = JSONObject.toJSONString(enpowerResponseBean);
		System.out.print(jsonBean);
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(jsonBean);
		out.flush();
		out.close();
	}

	private FYEnpowerRequestBean getEnpowerRequestbean(
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		FYEnpowerRequestBean enpowerRequestBean = new FYEnpowerRequestBean();
		enpowerRequestBean.setAction(request.getParameter("action"));
		enpowerRequestBean.setCallType(request.getParameter("callType"));
		enpowerRequestBean.setShowNumberType(request.getParameter("showNumberType"));
		enpowerRequestBean.setCaller(request.getParameter("caller"));
		enpowerRequestBean.setCallee(request.getParameter("callee"));
		enpowerRequestBean.setAppExtraData(ParamChangUtil.getValue(request, "appExtraData"));
		enpowerRequestBean.setFyCallId(request.getParameter("fyCallId"));
		enpowerRequestBean.setFyAccountId(request.getParameter("fyAccountId"));
		enpowerRequestBean.setAppAccountId(request.getParameter("appAccountId"));
		enpowerRequestBean.setAppId(request.getParameter("appId"));
		enpowerRequestBean.setChannelId(request.getParameter("channelId"));
		enpowerRequestBean.setIfRecord(request.getParameter("ifRecord"));
		enpowerRequestBean.setTi(request.getParameter("ti"));
		enpowerRequestBean.setAu(request.getParameter("au"));
		return enpowerRequestBean;
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}


}
