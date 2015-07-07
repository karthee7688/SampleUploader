package com.karthee.sample;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter  extends BaseAdapter{

	Context mContext;
	SamplethreadPool executor;
	ArrayList<Uri> mList;
	public ImageAdapter(Context context,ArrayList<Uri> uriList) {
		mContext = context;
		mList = uriList;
		initExecutors();
	}
	
	public void addImage(Uri uri) {
		mList.add(uri);
		addTask(new DemoRunnable(uri));
		this.notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		if(mList == null)
			return 0;
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.grid_item, null);
		
		ImageView img=(ImageView)view.findViewById(R.id.item_img);
		
		Log.e(">>Karthee ", " Image  Pathe : "+mList.get(arg0));
		Bitmap bmp = BitmapFactory.decodeFile(mList.get(arg0).getPath());
		//Bitmap bmp1 = Bitmap.createBitmap(100,100, Config.ARGB_8888);
		Bitmap bmp1 = Bitmap.createScaledBitmap(bmp, 200,200,false);
		img.setImageBitmap(bmp1);
		return view;
	}

	
	public void initExecutors() {
		BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(50);
		 
        executor = new SamplethreadPool(10,
                                            20, 30000, TimeUnit.MILLISECONDS, blockingQueue);
		
		executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r,
                    ThreadPoolExecutor executor) {
                Log.e(">>Karthee ", "  Executor Retry "+((DemoRunnable )r).getUri());
                ((DemoRunnable)r).setSuccess(true);
                executor.execute(r);
            }
        });
        // Let start all core threads initially
        executor.prestartAllCoreThreads();
     }
	
	
	public void addTask(DemoRunnable r) {
		executor.execute(r);
	}
	
	
}
