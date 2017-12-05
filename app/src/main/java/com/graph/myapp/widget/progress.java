package com.graph.myapp.widget;

import android.graphics.*;

public class progress
{
	float index,max,x,y,w,h;
	Paint paint;
	Bitmap bit;
	public progress(float x,float y,float w,float h)
	{
		this.x=x;this.y=y;this.w=w;this.h=h;
		paint=new Paint();
	}
	public void setmax(int p){max=p;}
	public void setindex(int p){index=p;}
	public int getindex(){return (int)index;}
	public void draw(Canvas canvas)
	{
		canvas.save();
		canvas.clipRect(x,y,x+w*(index/max),y+h);
		canvas.drawBitmap(bit,x,y,paint);
		canvas.restore();
	}
	public void setbitmap(Bitmap bit)
	{
		this.bit=big(bit,w/bit.getWidth(),h/bit.getHeight());
	}
	public Bitmap big(Bitmap bitmap,float na,float nb) {
		Matrix matrix = new Matrix();
		matrix.postScale(na,nb); 
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
		return resizeBmp;
	}
}
