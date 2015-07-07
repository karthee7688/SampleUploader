package com.karthee.sample;

import java.util.Random;

import android.net.Uri;
import android.util.Log;

public class DemoRunnable implements Runnable{

	private Uri uri;
	boolean failure = false;
	public DemoRunnable(Uri uri) {
			
		Random random =new  Random();
		int value = random.nextInt(10);
		if(value %2 == 0) {
			failure = true;
		}
	 this.uri = uri;
	
	
	}
	
	@Override
	public void run() {
		Log.e(">>Karthee ", "Uploads Execute  "+uri);
		try {
			Thread.sleep(30*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(failure) {
			Log.e(">>Karthee ", "Uploads Fails "+uri);
		}
	}

	public Uri getUri() {
		return uri;
	}
	
	public void setSuccess(boolean value) {
		failure = value;
	}
	
	public boolean getFailure() {
		return failure;
	}
}
