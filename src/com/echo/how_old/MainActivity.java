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
	private String mCurrentPhotoStr;//��ǰͼƬ·��
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
	
	//���շ��ص�����
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if(requestCode == PICK_CODE){
			if (intent != null) {
				Uri uri = intent.getData();//����һ��uri
				//���ؽ��Ϊһ���α�
				Cursor cursor = getContentResolver().query(uri, null, null, null, null);
				cursor.moveToFirst();
				
				//����
				int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
				mCurrentPhotoStr = cursor.getString(idx);
				cursor.close();//�ر�cursor
			
				//ѹ����Ƭ
				resizePhoto();
				mPhoto.setImageBitmap(mPhotoImg);
				mTip.setText("Click Dectect==>");
				
			}
		}
		super.onActivityResult(requestCode, resultCode, intent);
	}

	private void resizePhoto() {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;//�������ͼƬ��ֻ�ǻ�ȡ�ߴ�
		
		//ͼƬ·��  options�洢��ͼƬ�ĸ߶ȡ����
		BitmapFactory.decodeFile(mCurrentPhotoStr,options);
		//����ֵ����1024
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
