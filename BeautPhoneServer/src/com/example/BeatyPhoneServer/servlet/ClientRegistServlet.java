package com.example.BeatyPhoneServer.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.BeatyPhoneServer.Bean.FYAddAccountRequestBean;
import com.example.BeatyPhoneServer.Bean.RegistRequestBean;
import com.example.BeatyPhoneServer.Bean.RegistResponseBean;
import com.example.BeatyPhoneServer.DB.BeautyPhoneDbAction;
import com.example.BeatyPhoneServer.FYutil.FYAction;
import com.example.BeatyPhoneServer.util.ParamChangUtil;
import com.skymobi.pay.tlv.TLVCodeUtil;

public class ClientRegistServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ClientRegistServlet() {
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
		System.out.println("---------Regist by Post-------------");
		byte[] dataByte = ParamChangUtil.getByteValue(request);  
		RegistRequestBean registRequest = TLVCodeUtil.
				decode(dataByte, RegistRequestBean.class);
		FYAddAccountRequestBean add_FyAccount_data = FYAction.getInstance().addAccount(registRequest.getUserNumber(), "86+" + registRequest.getUserNumber(), "86");
		System.out.print(add_FyAccount_data.toString());
		RegistResponseBean loginResponse = new RegistResponseBean();
		if(!add_FyAccount_data.getResultCode().isEmpty() && add_FyAccount_data.getResultMsg().equals("创建成功")){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("AppId", registRequest.getAppId());
			map.put("AppToken", registRequest.getAppToken());
			map.put("ChannelId", registRequest.getChannelId());
			map.put("UserAccount", registRequest.getUserNumber());
			map.put("UserNumber", registRequest.getUserNumber());
			map.put("UserPassword", registRequest.getUserPassword());
			map.put("FYAccount", add_FyAccount_data.getResult().getFyAccountId());
			map.put("FYPassword", add_FyAccount_data.getResult().getFyAccountPwd());
			map.put("UserState", add_FyAccount_data.getResult().getStatus());
			map.put("CreatDate", add_FyAccount_data.getResult().getAddDate());
			map.put("MaxPhoneTime", 10);
			int addResultCode = BeautyPhoneDbAction.getInstance().addRegistUser(map);
			switch (addResultCode) {
			case 3:
				loginResponse.setResultCode(3 + "");
				loginResponse.setRegistResult("用户已存在");
				break;
			case 1:
				loginResponse.setResultCode(1 + "");
				loginResponse.setRegistResult("注册成功");
				loginResponse.setFyAccount(add_FyAccount_data.getResult().getFyAccountId());
				loginResponse.setFyPassword(add_FyAccount_data.getResult().getFyAccountPwd());
				loginResponse.setUserAccount(registRequest.getUserNumber());
				loginResponse.setUserNumber(registRequest.getUserNumber());
				break;
			case 2:
				loginResponse.setResultCode(2 + "");
				loginResponse.setRegistResult("信息有误");
				break;
			default:
				loginResponse.setResultCode(0 + "");
				loginResponse.setRegistResult("信息有误");
				break;
			}
		}else{
			System.out.print("\n飞语云账户添加失败:" + add_FyAccount_data.getResultMsg());
			loginResponse.setResultCode(3 + "");
			loginResponse.setRegistResult("用户已存在");
		}
		System.out.print("\nloginResponse:" + loginResponse.toString());
		OutputStream out = response.getOutputStream();
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
