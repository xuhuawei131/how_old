package com.echo.how_old;

import java.io.ByteArrayOutputStream;

import org.json.JSONObject;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;

import android.graphics.Bitmap;
import android.util.Log;

public class FaceppDetect {
	
	public interface CallBack{
		//����һ��JSONObject�����û���������ִ�гɹ�����success
		void success(JSONObject result);
		//FaceppParseException�ڲ�����
		void error(FaceppParseException exception);
	}
	
	public static void detect(final Bitmap bm, final CallBack callBack){
		//��ʱ���� ���������ڲ��࣬���ʹ��final
		new Thread(new Runnable() {
			
			public void run() {
				try {
					// request
					HttpRequests requests = new HttpRequests(Constant.KEY, Constant.SECRET, true, true);
					//��bitmapת��Ϊ�����Ƶ��ֽ�����
					Bitmap bmSmall = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight());
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					bmSmall.compress(Bitmap.CompressFormat.JPEG, 100, stream);
					
					byte[] arrays = stream.toByteArray();
					
					PostParameters params = new PostParameters();
					params.setImg(arrays);
					JSONObject jsonObject = requests.detectionDetect(params);
					
					Log.e("Tag", jsonObject.toString());
					
					if(callBack != null){
						callBack.success(jsonObject);
					}
				} catch (FaceppParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					if (callBack != null) {
						callBack.error(e);
					}
				}
				
			}
		}).start();
		
	}

}
