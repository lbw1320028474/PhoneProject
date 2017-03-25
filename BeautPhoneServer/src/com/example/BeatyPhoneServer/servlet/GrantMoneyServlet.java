package com.example.BeatyPhoneServer.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.BeatyPhoneServer.Bean.GrantMoneyReponseBean;
import com.example.BeatyPhoneServer.Bean.GrantMoneyRequestBean;
import com.example.BeatyPhoneServer.Bean.MyOrderRequest;
import com.example.BeatyPhoneServer.Bean.UserLoginResponse;
import com.example.BeatyPhoneServer.DB.BeautyPhoneDbAction;
import com.example.BeatyPhoneServer.util.HttpUtil;
import com.example.BeatyPhoneServer.util.ParamChangUtil;
import com.skymobi.pay.tlv.TLVCodeUtil;

public class GrantMoneyServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GrantMoneyServlet() {
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
		GrantMoneyRequestBean moneyRequestBean = TLVCodeUtil.
				decode(dataByte, GrantMoneyRequestBean.class);
		if(moneyRequestBean != null){
			System.out.println(moneyRequestBean.toString());
		}
		GrantMoneyReponseBean reponseBean = new GrantMoneyReponseBean();
		UserLoginResponse loginResponse = BeautyPhoneDbAction.getInstance().grantMoney(moneyRequestBean);
		if(loginResponse != null){
			reponseBean.setBlackMoney(loginResponse.getBalanceMoney());
			reponseBean.setGrantState("1");
			reponseBean.setResultCode("200");
		}else{
			reponseBean.setGrantState("0");
			reponseBean.setResultCode("100");
		}
		OutputStream outputStream = response.getOutputStream();
		outputStream.write(TLVCodeUtil.encode(reponseBean));
		outputStream.close();
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
