package com.echo.how_old;

import org.json.JSONObject;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;

import android.graphics.Bitmap;

public class FaceppDetect {
	
	public interface CallBack{
		//返回一个JSONObject，供用户解析处理，执行成功返回success
		void success(JSONObject result);
		//FaceppParseException内部定义
		void error(FaceppParseException exception);
	}
	
	public static void detect(Bitmap bm, final CallBack callBack){
		//耗时操作 用了匿名内部类，最好使用final
		new Thread(new Runnable() {
			
			public void run() {
				// request
				HttpRequests requests = new HttpRequests(Constant.KEY, Constant.SECRET, true, true);
				//将bitmap转换为二进制的字节数组
			}
		}).start();
		
	}

}
