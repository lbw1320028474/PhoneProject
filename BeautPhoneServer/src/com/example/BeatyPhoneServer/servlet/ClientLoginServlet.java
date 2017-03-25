package com.example.BeatyPhoneServer.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.BeatyPhoneServer.Bean.UserLoginRequest;
import com.example.BeatyPhoneServer.Bean.UserLoginResponse;
import com.example.BeatyPhoneServer.DB.BeautyPhoneDbAction;
import com.example.BeatyPhoneServer.util.ParamChangUtil;
import com.skymobi.pay.tlv.TLVCodeUtil;

public class ClientLoginServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ClientLoginServlet() {
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

		System.out.println("---------Login by Post-------------");
		//String data = FYAction.getInstance().addAccount("1320028474", "86+15355450913", "86");
		byte[] dataByte = ParamChangUtil.getByteValue(request);  
		OutputStream out = response.getOutputStream();
		UserLoginRequest loginRequest = TLVCodeUtil.
				decode(dataByte, UserLoginRequest.class);
		UserLoginResponse loginResponse = null;
		Map<String, Object> loginMap = new HashMap<String, Object>();
		loginMap.put("UserAccount", loginRequest.getUserName());
		loginMap.put("UserPassword", loginRequest.getUserPassowrd());
		System.out.println("login-useraccount: " + loginRequest.getUserName());
		System.out.println("login-Password: " + loginRequest.getUserPassowrd());
		int loginCode = BeautyPhoneDbAction.getInstance().loginUser(loginMap);
		if(loginCode == 1){
			loginResponse = BeautyPhoneDbAction.getInstance().getUserLoginedInfo(loginRequest.getUserName());
		}else{
			loginResponse = new UserLoginResponse();
			loginResponse.setLoginState(loginCode);
			if(loginCode == 0){
				loginResponse.setLoginMessage("密码错误");
				System.out.print("密码错误2");
			}else if(loginCode == 2){
				loginResponse.setLoginMessage("用户不存在");
			}
		}
		out.write(TLVCodeUtil.encode(loginResponse));
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
