package com.example.BeatyPhoneServer.Bean;

public class BmPayOrderInfoBean {
	private String body = "";
	private String create_time = "";
	private String name = "";
	private String out_trade_no = "";
	private String pay_type = "";
	private float total_fee = 0;
	private String trade_state = "";
	private String transaction_id = "";
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public float getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(float total_fee) {
		this.total_fee = total_fee;
	}
	public String getTrade_state() {
		return trade_state;
	}
	public void setTrade_state(String trade_state) {
		this.trade_state = trade_state;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	@Override
	public String toString() {
		return "BmPayOrderInfoBean [body=" + body + ", create_time="
				+ create_time + ", name=" + name + ", out_trade_no="
				+ out_trade_no + ", pay_type=" + pay_type + ", total_fee="
				+ total_fee + ", trade_state=" + trade_state
				+ ", transaction_id=" + transaction_id + "]";
	}

}
