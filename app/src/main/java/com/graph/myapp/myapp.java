package com.graph.myapp;

import android.app.*;
import com.graph.myapp.error.*;

public class myapp extends Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();
		ExceptionHandler.getInstance().init(this);
	}
}
