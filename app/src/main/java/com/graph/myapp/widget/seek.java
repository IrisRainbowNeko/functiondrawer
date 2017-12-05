package com.graph.myapp.widget;

import android.graphics.*;
import android.view.*;
import com.graph.myapp.*;

public class seek extends progress
{
	boolean isseek;
	public seek(float x,float y,float w,float h)
	{super(x,y,w,h);paint.setTextSize(40);}
	@Override
	public void draw(Canvas canvas)
	{
		super.draw(canvas);
		canvas.drawText(gra.para_lim[0]+index/100+"",x+5,y-20,paint);
	}
	public boolean onseek(MotionEvent event)
	{
		float X=event.getX()/(MainActivity.fblx/1080f),
			Y=event.getY()/(MainActivity.fbly/1920f);
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			if(pz(X,Y,x,y,x+w,y+h))
			{isseek=true;index=Math.round((X-x)/(w/max));return true;}
		}
		if(event.getAction()==MotionEvent.ACTION_MOVE){
			if(isseek){
				index=Math.round((X-x)/(w/max));
				index=index<0?0:index;
				index=index>max?max:index;
				return true;
			}
		}
		if(event.getAction()==MotionEvent.ACTION_UP){
			if(isseek){
				isseek=false;
				index=Math.round((X-x)/(w/max));
				index=index<0?0:index;
				index=index>max?max:index;
				return true;
			}
		}
		return false;
	}
	public boolean pz(float ox,float oy,float px,float py,float px1,float py1)
	{
		if(ox<px)
			return false;
		else if(ox>px1)
			return false;
		else if(oy<py)
			return false;
		else if(oy>py1)
			return false;
		else
			return true;
	}
}
