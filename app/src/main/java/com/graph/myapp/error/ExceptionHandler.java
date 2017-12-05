package com.graph.myapp.error;

import android.content.*;
import java.io.*;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler
{
	private static ExceptionHandler exceptionHandler;
	public static ExceptionHandler getInstance()
	{
		if(exceptionHandler==null)
			exceptionHandler=new ExceptionHandler();
		return exceptionHandler;
	}
	private boolean inited=false;
	private Context ctx;
	private Thread.UncaughtExceptionHandler defaultHandler;
	public void init(Context ctx)
	{
		if(inited)return;
		this.ctx=ctx;
		defaultHandler=Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	@Override
	public void uncaughtException(Thread p1,Throwable p2)
	{
		StringWriter stringWriter=new StringWriter();
		PrintWriter printWriter=new PrintWriter(stringWriter);
		p2.printStackTrace(printWriter);
		String filename=Long.toHexString(System.currentTimeMillis());
		File parent=ctx.getDir("error",0);
		File file=new File(parent,filename);
		try{
			FileOutputStream fos=new FileOutputStream(file);
			fos.write(stringWriter.toString().getBytes());
			fos.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		defaultHandler.uncaughtException(p1,p2);
	}
}
