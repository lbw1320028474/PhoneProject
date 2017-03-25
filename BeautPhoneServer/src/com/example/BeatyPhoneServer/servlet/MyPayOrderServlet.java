package com.example.BeatyPhoneServer.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.BeatyPhoneServer.Bean.MyOrderRequest;
import com.example.BeatyPhoneServer.Bean.MyOrderResponse;
import com.example.BeatyPhoneServer.Bean.UserLoginRequest;
import com.example.BeatyPhoneServer.DB.PayDButil;
import com.example.BeatyPhoneServer.util.ParamChangUtil;
import com.skymobi.pay.tlv.TLVCodeUtil;

public class MyPayOrderServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public MyPayOrderServlet() {
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
		byte[] dataByte = ParamChangUtil.getByteValue(request);  
		MyOrderRequest myOrderRequest = TLVCodeUtil.
				decode(dataByte, MyOrderRequest.class);
		OutputStream out = response.getOutputStream();
		if(myOrderRequest != null){
			Map<String, Object> daMap = new HashMap<String, Object>();
			daMap.put("BmOrderId", myOrderRequest.getPayOrderId());
			daMap.put("PayAccount", myOrderRequest.getUserAccount());
			daMap.put("PayMoney", myOrderRequest.getPayMoney());
			daMap.put("TruePayMoney", myOrderRequest.getTruePayMoney());
			daMap.put("IsDealWith", 0);
			daMap.put("CreateData", System.currentTimeMillis() + "");
			PayDButil.getInstance().addMyPayOrder(daMap);
		}

		MyOrderResponse myOrderResponse = new MyOrderResponse();
		myOrderResponse.setGetOrderState("1");
		out.write(TLVCodeUtil.encode(myOrderResponse));
		out.flush();
		out.close();
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
