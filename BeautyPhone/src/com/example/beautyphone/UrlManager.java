package com.example.beautyphone;

public class UrlManager {
	public static final String BASE_URL = "http://115.200.21.182:8080/BeautPhoneServer/";
	private static String numberLocationUrl = "http://ws.webxml.com.cn/webservices/MobileCodeWS.asmx/getMobileCodeInfo?mobileCode=";
	public static String getNumberLocationUrl(String number){
		return numberLocationUrl + number + "&userID=";
	}

	public static final String REGIST_URL = BASE_URL + "ClientRegistServlet";
	public static final String LOGIN_URL = BASE_URL + "ClientLoginServlet";
	public static final String SUBMIT_ORDER_URL = BASE_URL + "MyPayOrderServlet";
	public static final String GRANT_VIDEO_MONEY = BASE_URL + "GrantMoneyServlet";
	public static final String IDENT_IS_CANREGIST = BASE_URL + "IdentCanRegistServlet";
}
