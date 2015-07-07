package com.karthee.sample;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.util.Log;

public class SamplethreadPool extends ThreadPoolExecutor{

	public SamplethreadPool(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		
		if(((DemoRunnable)r ).getFailure()) {
			getRejectedExecutionHandler().rejectedExecution(r, this);
			Log.e(">>Karthee ", " Execute  Failure ");
		} else {
			Log.e(">>Karthee ", " Execute  Success ");
		}
	}

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		
	}

	
	

}
