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
	 * @param pattern ���ڸ�ʽ
	 * @param dateTime ���ڵ�int��������
	 * @return ���������͵�ʱ��
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
			return "�ո�";//С��һ����  ��ʾ�ո�  
			//callLagBean.setDate("�ո�");  
		}else if(now - (60 * 60 * 1000)< dateTime){              //һСʱ��      ��ʾʱ��  
			return Long.valueOf(now - dateTime) / (1000 * 6 * 6) +"����ǰ";  
		}else if(calendarNow.get(Calendar.MONTH) == callTime.get(Calendar.MONTH)  //��ʾ����  
				&& calendarNow.get(Calendar.DAY_OF_MONTH) == callTime.get(Calendar.DAY_OF_MONTH)  
				){  
			return hour+":" + minute;  
		}else{                                              //���գ�ʱ��  
			return (callTime.get(Calendar.MONTH) + 1)  
					+ "/" + callTime.get(Calendar.DAY_OF_MONTH);  
		} 
	}  

	public static String getMinOfSec(int minu){
		if(minu < 60 && minu > 0){
			return minu + "��";
		}else if(minu >= 60){
			return ((int)(minu/60)) + "��" + minu%60 + "��"; 
		}else{
			return "δ��ͨ";
		}
	}
}
