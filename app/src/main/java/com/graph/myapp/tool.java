package com.graph.myapp;

import android.graphics.*;
import java.io.*;
import java.nio.channels.*;
import java.util.*;
import android.util.*;

public class tool
{
	public static Bitmap loadbitmap(String name)
	{
		Bitmap bmp=null;
		try
		{
			bmp=BitmapFactory.decodeStream(MainActivity.am.open(name));
		}
		catch (IOException e)
		{}
		return bmp;
	}
	public static Bitmap[] loadbitmaps(String dir)
	{
		Bitmap[] bmp=null;
		try
		{
			String[] file=MainActivity.am.list(dir);
			bmp=new Bitmap[file.length];
			for(int i=0;i<file.length;i++)
				bmp[i]=BitmapFactory.decodeStream(MainActivity.am.open(dir+"/"+file[i]));
		}
		catch (IOException e)
		{}
		return bmp;
	}
	public static String readFileSdcard(String dir) {
		String res = null;
		try {
			String ccs=dir;
			//String dir = Environment.getExternalStorageDirectory().getPath().toString()+"/7exfsz/jl.txt";
			FileInputStream fin = new FileInputStream(ccs);
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			res = new String(buffer, "UTF-8");
			fin.close();
		}
		catch (Exception e){}
		return res;
	}
	public static String readFileAssets(String name) {
		String res = null;
		try {
			InputStream fin = MainActivity.am.open(name);
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			res=new String(buffer, "UTF-8");
			fin.close();
		}
		catch (Exception e){}
		return res;
	}
	public static void shuchu(String mc,String name)
	{
		String ccs=Constant.FILE_SD_THIS+name;
		File file2 = new File(ccs); // 这种路径名格式是在Unix下面的路径格式，在Windows下面的路径格式类似" C:\\test\\  "这样，注意区别
		if(!file2.getParentFile().exists())
			file2.getParentFile().mkdir();
		try {
			FileOutputStream outStream = new FileOutputStream(file2);
			outStream.write(mc.getBytes());
			outStream.close();
			// 文件的创建，注意与文件夹创建的区别
		} catch (IOException e) {
			//System.exit(0);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static double cd(float x,float y,float x1,float y1)
	{
		return Math.sqrt((x1-x)*(x1-x)+(y1-y)*(y1-y));
	}
	public static float scalexy(float point,float center,float bl)
	{
		return ((point-center)/bl+center);
	}
	public static float[] tofloatarray(Vector<Float> arr)
	{
		float[] f=new float[arr.size()];
		for(int i=0;i<f.length;i++)
		{
			f[i]=arr.get(i);
		}
		return f;
	}
	public static <T> int indexof_arr(T[] array,T item){
		for(int i=0;i<array.length;i++){
			if(item.equals(array[i]))return i;
		}
		return -1;
	}
	public static float[] str2float_arr(String[] a){
		float[] b=new float[a.length];
		for(int i=0;i<a.length;i++){
			b[i]=Float.parseFloat(a[i]);
		}
		return b;
	}
	public static float[] getparafloat(){
		return new float[Constant.PARA_strarr.length];
	}
	public static double[] arrayadd(double[] a,float[] b)
	{
		double[] c=new double[a.length+b.length];
		for(int i=0;i<a.length;i++)c[i]=a[i];
		for(int i=0;i<b.length;i++)c[i+a.length]=b[i];
		return c;
	}
	public static String[] arrayadd(String[] a,String[] b)
	{
		String[] c=new String[a.length+b.length];
		for(int i=0;i<a.length;i++)c[i]=a[i];
		for(int i=0;i<b.length;i++)c[i+a.length]=b[i];
		return c;
	}
	
	public static String getsubstring(String str,String a,String b,int index){
		int aa=str.indexOf(a,index);
		if(aa==-1)return "";
		int bb=str.indexOf(b,aa+1);
		if(bb==-1)return "";
		return str.substring(aa+1,bb);
	}
	public static String cutstring(String a,String start,String end){
		return a.substring(0,a.indexOf(start))+a.substring(a.indexOf(end)+1);
	}
	public static void copyBigDataToSD(String strOutFileName,String name) throws IOException 
    {  
        InputStream myInput;  
        OutputStream myOutput = new FileOutputStream(strOutFileName);  
        myInput = MainActivity.am.open(name);  
        byte[] buffer = new byte[1024];  
        int length = myInput.read(buffer);
        while(length > 0)
        {
            myOutput.write(buffer, 0, length); 
            length = myInput.read(buffer);
        }

        myOutput.flush();  
        myInput.close();  
        myOutput.close();        
    }
	public static float fitx(float x){
		return x/(MainActivity.fblx/1080f);
	}
	public static float fity(float y){
		return y/(MainActivity.fbly/1920f);
	}
	public static int[] yuefen(int a,int b){
		int gys=GetZDGY(a,b);
		return new int[]{a/gys,b/gys};
	}
	private static int GetZDGY(int m,int n)//根据欧几里德辗转相除法计算两个数的最大公约数 
	{ 
		if(m%n == 0)return n; 
		return GetZDGY(n,m%n); //递归求最大公约数
	}
	public static void copydata(String file)
	{
		String afi=Constant.FILE_SD_THIS;
		File fias=new File(afi);
		String[] sa=null;
		try
		{
			if (!fias.exists())
				fias.mkdir();
			sa=MainActivity.am.list(file);
		}
		catch (IOException e){Log.e("!!!!","错误");}
		String[] names=readFileAssets("name.txt").split(",");
		for(int i=0;i<sa.length;i++)
		{
			File fii=new File(afi+names[i]);
			if(fii.exists())continue;
			try
			{
				copyBigDataToSD(fii.toString(),file+"/"+sa[i]);
			}
			catch (IOException e)
			{Log.e("!!!!","错误");}
		}
	}
}
