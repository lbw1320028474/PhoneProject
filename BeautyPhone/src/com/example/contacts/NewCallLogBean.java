package com.example.contacts;


public class NewCallLogBean {
	private String number = "";
	private String date = "";
	private Integer duration = 0;
	private Integer type = 0;
	private Integer news = 0;
	private String name = "";
	private Integer numbertype = 0;
	private String numberlabel = "";
	private Integer isRead;
	private String countryiso = "CN";
	private String geocoded_location = "ÖÐ¹ú";
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getNews() {
		return news;
	}
	public void setNews(Integer news) {
		this.news = news;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNumbertype() {
		return numbertype;
	}
	public void setNumbertype(Integer numbertype) {
		this.numbertype = numbertype;
	}
	public String getNumberlabel() {
		return numberlabel;
	}
	public void setNumberlabel(String numberlabel) {
		this.numberlabel = numberlabel;
	}
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public String getCountryiso() {
		return countryiso;
	}
	public void setCountryiso(String countryiso) {
		this.countryiso = countryiso;
	}
	public String getGeocoded_location() {
		return geocoded_location;
	}
	public void setGeocoded_location(String geocoded_location) {
		this.geocoded_location = geocoded_location;
	}
	@Override
	public String toString() {
		return "NewCallLogBean [number=" + number + ", date=" + date + ", duration=" + duration + ", type=" + type
				+ ", news=" + news + ", name=" + name + ", numbertype=" + numbertype + ", numberlabel=" + numberlabel
				+ ", isRead=" + isRead + ", countryiso=" + countryiso + ", geocoded_location=" + geocoded_location
				+ "]";
	}


}
