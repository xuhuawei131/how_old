package com.echo.how_old;

import org.json.JSONObject;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;

import android.graphics.Bitmap;

public class FaceppDetect {
	
	public interface CallBack{
		//����һ��JSONObject�����û���������ִ�гɹ�����success
		void success(JSONObject result);
		//FaceppParseException�ڲ�����
		void error(FaceppParseException exception);
	}
	
	public static void detect(Bitmap bm, final CallBack callBack){
		//��ʱ���� ���������ڲ��࣬���ʹ��final
		new Thread(new Runnable() {
			
			public void run() {
				// request
				HttpRequests requests = new HttpRequests(Constant.KEY, Constant.SECRET, true, true);
				//��bitmapת��Ϊ�����Ƶ��ֽ�����
			}
		}).start();
		
	}

}
