package com.example.contacts;

public class NumberInfomation{
	private String name = "";
	private String number = "";
	private String numberDescript = "";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getNumberDescript() {
		return numberDescript;
	}
	public void setNumberDescript(String numberDescript) {
		this.numberDescript = numberDescript;
	}
	@Override
	public String toString() {
		return "NumberInfomation [name=" + name + ", number=" + number + ", numberDescript=" + numberDescript + "]";
	}

}
