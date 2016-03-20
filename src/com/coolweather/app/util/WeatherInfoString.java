package com.coolweather.app.util;

import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.model.WeatherInfo;

public class WeatherInfoString {
	
	private String response;
	private String weather_div;
	private String days7_div;
	private String updateTime;
	private String weatherDay;
	private List<WeatherInfo> days7Weather = new ArrayList<WeatherInfo>();
	private WeatherInfo weather = new WeatherInfo();
	
	public String getUpdateTime(){
		return updateTime;
	}
	
	public String getWeatherDay(){
		return weatherDay;
	}
	
	public List<WeatherInfo> getDay7Weather(){
		return days7Weather;
	}
	
	public WeatherInfo getWeather(){
		return weather;
	}
	
	public WeatherInfoString(String response){
		this.response = response;
		init();
	}
	
	private void init(){
		getDiv();
		setTime();
		saveDays7Object();
		saveWeatherInfoObject();
	}
	
	private void setTime(){
		updateTime = weather_div.split("/>")[0].split("\"")[5];
		weatherDay = weather_div.split("<h2>")[1].split("<span>")[0].trim();
	}
	
	private void getDiv(){
		String[] divs = response.split("</div>");
		for (String div : divs) {
			if (div.contains("class=\"days7\"") && div.contains("Ã÷Ìì")){
				days7_div = div;
			}
			if (div.contains("class=\"sk\"")){
				weather_div = div;
			}
		}
	}
	
	private void saveDays7Object(){
		String[] lis = days7_div.split("<li>");
		for (int i = 0; i < lis.length; i++) {
			if (i == 1){
				WeatherInfo wi = new WeatherInfo();
				wi.setWeather1(lis[i].split("/>")[0].split("\"")[3]);
				wi.setTemperature1(lis[i].split("<span>")[1].split("</span>")[0]);
				days7Weather.add(wi);				
			} else if( i > 1){
				WeatherInfo wi = new WeatherInfo();
				String[] imgs = lis[i].split("/>");
				wi.setWeather1(imgs[0].split("\"")[3]);
				wi.setWeather2(imgs[1].split("\"")[3]);
				String span = lis[i].split("<span>")[1].split("</span>")[0];
				String[] tmp = span.split("/");
				wi.setTemperature1(tmp[0]);
				wi.setTemperature2(tmp[1]);
				days7Weather.add(wi);
			}			
		}
	}
	
	private void saveWeatherInfoObject(){
		weather.setCityName(weather_div.split("ÊÐ\">")[1].split("</")[0]);
		String[] tmps = weather_div.split("<tr>")[1].split("</tr>")[0].split("</td>");
		weather.setWeather1(tmps[0].split("\"")[7]);
		weather.setTemperature1(tmps[1].split(">")[1]);
		weather.setWeather2(tmps[2].split("<span>")[1].split("</span>")[0]);
		weather.setWind(tmps[2].split("<span>")[2].split("</span>")[0]);
		weather.setWind(tmps[2].split("<span>")[3].split("</span>")[0]);
	}
}
