package com.example.BeatyPhoneServer.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.BeatyPhoneServer.Bean.BmobPayOrderRequest;
import com.example.BeatyPhoneServer.DB.PayDButil;
import com.example.BeatyPhoneServer.util.AppInfo;
import com.example.BeatyPhoneServer.util.HttpUtil;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class BmobPayOrderServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public BmobPayOrderServlet() {
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
		System.out.println("订单回调");
		BmobPayOrderRequest bmobPayOrderRequest = getOrderInfo(request);
		System.out.println("订单回调数据支付成功:" + bmobPayOrderRequest.toString());
		PayDButil.getInstance().addBmOrder(bmobPayOrderRequest);
		//response.getWriter().print("");
		//	Response response2 = HttpUtil.okHttpGet("", request);
		//	PayDButil.getInstance().getOrderInfo(bmobPayOrderRequest);
	}

	private BmobPayOrderRequest getOrderInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		BmobPayOrderRequest bmobPayOrderRequest = new BmobPayOrderRequest();
		if(request != null){
			bmobPayOrderRequest.setTrade_status(request.getParameter("trade_status"));
			bmobPayOrderRequest.setOut_trade_no(request.getParameter("out_trade_no"));
			bmobPayOrderRequest.setTrade_no(request.getParameter("trade_no"));
		}
		return bmobPayOrderRequest;
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
