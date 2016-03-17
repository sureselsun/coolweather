package com.coolweather.app.util;

public interface HttpCallbackListener {
	
	void onFinish(String response);
	
	void onErroe(Exception e);

}
