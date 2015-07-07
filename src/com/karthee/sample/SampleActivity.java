package com.karthee.sample;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;

public class SampleActivity extends ActionBarActivity {
	
	ArrayList<Uri> mImageList = null;

	GridView mGrid = null;
	ImageAdapter mAdapter;
	File mFile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sample);
		mImageList = new ArrayList<Uri>();
		mGrid = (GridView)findViewById(R.id.photo_grid);
		mAdapter = new ImageAdapter(getApplicationContext(), mImageList);
		mGrid.setAdapter(mAdapter);
		Button uploadBtn = (Button)findViewById(R.id.upload);
		uploadBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
		                Intent.FLAG_GRANT_READ_URI_PERMISSION);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse("file://"+generateFilePath()));
				startActivityForResult(intent, 200);
			}
		});
		
	}
	
	private String generateFilePath() {
		String name = ""+System.currentTimeMillis()+".png";
		File file = new File(Environment.getExternalStorageDirectory(),name);
		mFile = file;
		return file.getAbsolutePath();
	}

	public File getCurrentPath() {
		return mFile;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sample, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e(">>Karthee ", "  Result Not getting Data Outside  "+requestCode);
		if(resultCode == Activity.RESULT_OK  && requestCode == 200) {
			//mImageList.add(Uri.parse(getCurrentPath()));
			//mAdapter.notifyDataSetChanged();
			//Log.e(">>Karthee ", "  Result getting Data Inside  "+Uri.parse(getCurrentPath()));
			
			mAdapter.addImage(Uri.fromFile(getCurrentPath()));
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
}
