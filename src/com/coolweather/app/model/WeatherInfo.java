package com.coolweather.app.model;

public class WeatherInfo {
	
	private String day;
	
	private String weather1;
	
	private String weather2;
	
	private String temperature1;
	
	private String temperature2;
	
	private String grade;
	
	private String wind;
	
	private String cityName;
	
	public String getCityName(){
		return cityName;
	}
	
	public void setCityName(String cityName){
		this.cityName = cityName;
	}
	
	public String getWind(){
		return wind;
	}
	
	public void setWind(String wind){
		this.wind = wind;
	}
	
	public String getGrade(){
		return grade;
	}
	
	public void setGrade(String grade){
		this.grade = grade;
	}
	
	public String getDay(){
		return day;
	}
	
	public void setDay(String day){
		this.day = day;
	}
	
	public String getWeather1(){
		return weather1;
	}
	
	public void setWeather1(String weather1){
		this.weather1 = weather1;
	}
	
	public String getWeather2(){
		return weather2;
	}
	
	public void setWeather2(String weather2){
		this.weather2 = weather2;
	}
	
	public String getTemperature1(){
		return temperature1;
	}
	
	public void setTemperature1(String temperature1){
		this.temperature1 = temperature1;
	}
	
	public String getTemperature2(){
		return temperature2;
	}
	
	public void setTemperature2(String temperature2){
		this.temperature2 = temperature2;
	}
}
