package com.example.util;

import java.util.Calendar;

public class DataConverterUtil {

	public static class DataBean{
		public String year = "";
		public String month = "";
		public String day = "";
		public String hour = "";
		public String min = "";
		public String second = "";
		@Override
		public String toString() {
			return "DataBean [year=" + year + ", month=" + month + ", day=" + day + ", hour=" + hour + ", min=" + min
					+ ", second=" + second + "]";
		}

	}
	/**
	 * 
	 * @param pattern 日期格式
	 * @param dateTime 日期的int类型数据
	 * @return 年月日类型的时间
	 */

	public static DataBean getData(long dateTime){
		DataBean bean = new DataBean();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dateTime);
		bean.year = calendar.get(Calendar.YEAR) + "";
		bean.month = (calendar.get(Calendar.MONTH) + 1) + "";
		bean.day = calendar.get(Calendar.DAY_OF_MONTH) + "";
		bean.hour = calendar.get(Calendar.HOUR_OF_DAY) + "";
		bean.min = calendar.get(Calendar.MINUTE) + "";
		bean.second = calendar.get(Calendar.SECOND) + "";
		return bean;
	}

	public static String getFormatDateTime(long dateTime){
		/*Date d = new Date(dateTime);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(d);
		 */
		Calendar calendarNow = Calendar.getInstance();
		Calendar callTime = Calendar.getInstance();
		callTime.setTimeInMillis(dateTime);
		int hour = callTime.get(Calendar.HOUR_OF_DAY);
		int minute = callTime.get(Calendar.MINUTE);
		long now = calendarNow.getTimeInMillis();  
		if(now - (60 * 1000) < dateTime){  
			return "刚刚";//小于一分钟  显示刚刚  
			//callLagBean.setDate("刚刚");  
		}else if(now - (60 * 60 * 1000)< dateTime){              //一小时内      显示时间  
			return Long.valueOf(now - dateTime) / (1000 * 6 * 6) +"分钟前";  
		}else if(calendarNow.get(Calendar.MONTH) == callTime.get(Calendar.MONTH)  //显示今天  
				&& calendarNow.get(Calendar.DAY_OF_MONTH) == callTime.get(Calendar.DAY_OF_MONTH)  
				){  
			return hour+":" + minute;  
		}else{                                              //月日：时分  
			return (callTime.get(Calendar.MONTH) + 1)  
					+ "/" + callTime.get(Calendar.DAY_OF_MONTH);  
		} 
	}  

	public static String getMinOfSec(int minu){
		if(minu < 60 && minu > 0){
			return minu + "秒";
		}else if(minu >= 60){
			return ((int)(minu/60)) + "分" + minu%60 + "秒"; 
		}else{
			return "未接通";
		}
	}
}
