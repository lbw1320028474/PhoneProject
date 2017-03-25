package com.example.BeatyPhoneServer.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.example.BeatyPhoneServer.Bean.FYCallLogPushRequest;
import com.example.BeatyPhoneServer.Bean.FYCallLogPushResponse;
import com.example.BeatyPhoneServer.DB.BeautyPhoneDbAction;
import com.example.BeatyPhoneServer.DB.FyDbAction;

public class CallLogPush extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CallLogPush() {
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
		FYCallLogPushRequest callLogPushRequest = getCallLogPushBean(request);
		FyDbAction.getInstance().addCallLogPush(callLogPushRequest);
		BeautyPhoneDbAction.getInstance().haveNewCallLog(callLogPushRequest);
		System.out.print("\n" + callLogPushRequest.toString());
		PrintWriter out = response.getWriter();
		FYCallLogPushResponse callLogPushResponse = new FYCallLogPushResponse();
		callLogPushResponse.setResultCode("0");
		callLogPushResponse.setResultMsg("接收成功");
		String jsonObject = JSONObject.toJSONString(callLogPushResponse);
		System.out.print("\n" + jsonObject);
		out.print(jsonObject);
		out.flush();
		out.close();
	}

	private FYCallLogPushRequest getCallLogPushBean(HttpServletRequest request) {
		// TODO Auto-generated method stub
		FYCallLogPushRequest callLogPushRequest = new FYCallLogPushRequest();
		callLogPushRequest.setAction(request.getParameter("action"));
		callLogPushRequest.setAppId(request.getParameter("appId"));
		callLogPushRequest.setAppCallId(request.getParameter("appCallId"));
		callLogPushRequest.setFyCallId(request.getParameter("fyCallId"));
		callLogPushRequest.setAppServerExtraData(request.getParameter("appServerExtraData"));
		callLogPushRequest.setCallbackFirstStartTime(request.getParameter("callbackFirstStartTime"));
		callLogPushRequest.setCallbackFirstEndTime(request.getParameter("callbackFirstEndTime"));
		callLogPushRequest.setCallStartTime(request.getParameter("callStartTime"));
		callLogPushRequest.setCallEndTime(request.getParameter("callEndTime"));
		String duration = (Long.parseLong(callLogPushRequest.getCallEndTime()) - 
				Long.parseLong(callLogPushRequest.getCallStartTime())) + "";
		callLogPushRequest.setCallDuration(duration);
		callLogPushRequest.setStopReason(request.getParameter("stopReason"));
		callLogPushRequest.setTrueShowNumberType(request.getParameter("trueShowNumberType"));
		callLogPushRequest.setTrueIfRecord(request.getParameter("trueIfRecord"));
		callLogPushRequest.setTime(request.getParameter("ti"));
		callLogPushRequest.setAu(request.getParameter("au"));
		return callLogPushRequest;
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
