package com.coolweather.app.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.renderscript.Element;
import android.text.TextUtils;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;
import com.coolweather.app.model.WeatherInfo;

public class Utility {
	
	/**
	 * 解析和处理服务器返回的省级数据
	 * @param coolWeatherDB
	 * @param response
	 * @return
	 */
	public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, String response){
		if(!TextUtils.isEmpty(response)){
			String[] allProvinces = response.split(",");
			if(allProvinces != null && allProvinces.length > 0){
				for (String p : allProvinces){
					String[] array = p.split("\\|");
					Province province = new Province();
					province.setProvinceCode("101" + array[0]);
					province.setProvinceName(array[1]);
					coolWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 解析和处理服务器返回的市级数据	
	 */
	public static boolean handleCitiesResponxe(CoolWeatherDB coolWeatherDB, String response, int provinceId){
		if(!TextUtils.isEmpty(response)){
			response = response.replace("\"", "").replace("{", "").replace("}", "");
			String[] allCities = response.split(",");
			if(allCities != null && allCities.length > 0){
				for (String c : allCities) {
					String[] array = c.split(":");
					City city = new City();
					city.setCityCode(coolWeatherDB.getProvinceCodeFromProviceId(provinceId) + array[0]);	
					city.setCityName(array[1]);
					city.setProvinceId(provinceId);
					coolWeatherDB.saveCity(city);
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 解析和得理服务器返回的县级数据
	 */
	public static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB, String response, int cityId){
		if(!TextUtils.isEmpty(response)){
			response = response.replace("\"", "").replace("{", "").replace("}", "");
			String[] allCounties = response.split(",");
			if(allCounties != null && allCounties.length > 0){
				for (String c : allCounties) {
					String[] array = c.split(":");
					County county = new County();
					county.setCountyCode(coolWeatherDB.getCityCodeFromCityId(cityId) + array[0]);
					county.setCountyName(array[1]);
					county.setCityId(cityId);
					coolWeatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 解析服备器返回的JSON数据，并将解析出的数据存储到本地。
	 */
	public static void handleWeatherResponse(Context context, String response){
		WeatherInfoString weaterhInfoObjcet = new WeatherInfoString(response);
		saveWeatherInfo(context, weaterhInfoObjcet.getWeather(), weaterhInfoObjcet.getUpdateTime(), weaterhInfoObjcet.getWeatherDay());
	}
	
	/**
	 * 将服务器返回的所有天气信息存储到SharedPreferences文件中.
	 */
	private static void saveWeatherInfo(Context context, WeatherInfo weatherInfo, String updateTime, String weatherDay) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", weatherInfo.getCityName());
		editor.putString("temp1", weatherInfo.getTemperature1());
		editor.putString("weather_desp", weatherInfo.getWeather1());
		editor.putString("publish_time", updateTime);
		editor.putString("current_date", weatherDay);
		editor.commit();
	}

}
