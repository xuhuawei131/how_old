package com.echo.how_old;

import com.example.how_old.R;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	
	private static final int PICK_CODE = 0x110;
	private ImageView mPhoto;
	private Button mDetect;
	private Button mGetImage;
	private TextView mTip;
	private View mWaitting;
	private String mCurrentPhotoStr;//当前图片路径
	private Bitmap mPhotoImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		initEvents();
	}

	private void initEvents() {
		mGetImage.setOnClickListener(this);
		mDetect.setOnClickListener(this);
		
	}

	private void initViews() {
		mPhoto = (ImageView) findViewById(R.id.id_photo);
		mDetect = (Button) findViewById(R.id.id_detect);
		mGetImage = (Button)findViewById(R.id.id_getImage);
		mTip = (TextView) findViewById(R.id.id_tip);
		mWaitting = findViewById(R.id.id_waitting);		
	}
	
	//接收返回的数据
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if(requestCode == PICK_CODE){
			if (intent != null) {
				Uri uri = intent.getData();//返回一个uri
				//返回结果为一个游标
				Cursor cursor = getContentResolver().query(uri, null, null, null, null);
				cursor.moveToFirst();
				
				//索引
				int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
				mCurrentPhotoStr = cursor.getString(idx);
				cursor.close();//关闭cursor
			
				//压缩照片
				resizePhoto();
				mPhoto.setImageBitmap(mPhotoImg);
				mTip.setText("Click Dectect==>");
				
			}
		}
		super.onActivityResult(requestCode, resultCode, intent);
	}

	private void resizePhoto() {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;//不会加载图片，只是获取尺寸
		
		//图片路径  options存储了图片的高度、宽度
		BitmapFactory.decodeFile(mCurrentPhotoStr,options);
		//不让值超过1024
		double ratio = Math.max(options.outWidth*1.0d/1024f,options.outHeight*1.0d/1024f);
		
		options.inSampleSize = (int) Math.ceil(ratio);
		
		options.inJustDecodeBounds = false;
		mPhotoImg = BitmapFactory.decodeFile(mCurrentPhotoStr,options);
		
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_getImage:
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("image/*");
			startActivityForResult(intent, PICK_CODE);
			break;
		case R.id.id_detect:
			
			break;

		default:
			break;
		}
		
	}

}
