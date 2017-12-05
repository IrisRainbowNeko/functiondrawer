package com.graph.myapp;
import android.os.*;

public class Constant
{
	public final static int FUNCTION_NORMAL=0;
	public final static int FUNCTION_PARAMETER=1;
	public final static int FUNCTION_CURVE=2;
	public final static int FUNCTION_POLAR=3;
	public final static String FILE_SD=Environment.getExternalStorageDirectory().getPath().toString();
	public final static String FILE_SD_THIS=Environment.getExternalStorageDirectory().getPath().toString()+"/jhhb/";
	public final static String[] PARA_strarr={"a","b","c","m","n"};
	public final static String[] FUNCTION_NAME="sin,cos,tan,arccos,arcsin,arctan,sinh,cosh,tanh,log,lg,ln,abs,max,min,floor,round,ceil".split(",");
	public final static String[] PREFIXES={"","'","∫"};
}
