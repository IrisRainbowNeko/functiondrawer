package com.graph.myapp.widget;
import java.util.*;
import android.graphics.*;
import android.view.*;
import com.graph.myapp.*;
import android.util.*;

public class LableList
{
	ClickListener cl;
	public float x,y,px,py;
	private float lx,ly,lpx,lpy,lX,lY;
	public Vector<Lable> ve=new Vector<Lable>();
	private int type=1;
	public static final int vertical=1;
	public static final int horizontal=2;
	public boolean swithtype=false,move=false;
	private long ks,js;
	public void draw(Canvas canvas)
	{
		canvas.save();
		canvas.translate(px,py);
		for(int i=0;i<ve.size();i++)ve.get(i).draw(canvas);
		canvas.restore();
	}
	public void setposition(float x,float y){
		this.x=x;this.y=y;
	}
	public void add(Lable la)
	{
		la.textPaint.setStyle(Paint.Style.STROKE);
		la.textPaint.setStrokeWidth(4);
		la.SetwhSurround();
		if(type==1){
			if(ve.size()>0)la.setposition(x,ve.get(ve.size()-1).y+ve.get(ve.size()-1).h);
			else la.setposition(x,y);}
		else if(type==2){
			if(ve.size()>0)la.setposition(ve.get(ve.size()-1).x+ve.get(ve.size()-1).w,y);
			else la.setposition(x,y);
		}
		ve.add(la);
	}
	public void remove(int which){
		if(type==1){
			float h=ve.get(which).h;
			ve.removeElementAt(which);
			for(int i=which;i<ve.size();i++){
				ve.get(i).y-=h;
			}
		}else
		if(type==2){
			float w=ve.get(which).w;
			ve.removeElementAt(which);
			for(int i=which;i<ve.size();i++){
				ve.get(i).y-=w;
			}
		}
	}
	public void SetText(String tex,int which)
	{
		ve.get(which).settext(tex);
		ve.get(which).SetwhSurround();
	}
	public void SetColor(int col,int which)
	{
		ve.get(which).settextcolor(col);
		ve.get(which).setbgcolor((~col&0x00ffffff)|0xff000000);
	}
	public void setClickListener(ClickListener c){cl=c;}
	public boolean touch(float X,float Y)
	{
		for(int i=0;i<ve.size();i++)
		{
			//ve.get(i).drawbg=false;
			if(ve.get(i).click(X,Y)){
				boolean dd=ve.get(i).drawbg;
				rest();
				if(!swithtype)ve.get(i).drawbg=!dd;
				else ve.get(i).drawbg=true;
				cl.onclick(i);
				return true;
			}
		}
		return false;
	}
	public boolean touch(MotionEvent event,float X,float Y)
	{
		boolean inthis=false;
		for(int i=0;i<ve.size();i++)
		{
			if(move||ve.get(i).click(X-px,Y-py)){inthis=true;break;}
		}
		if(!inthis)return false;
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			lX=X;lY=Y;
			lx=event.getX(0)/(MainActivity.fblx/1080f);
			ly=event.getY(0)/(MainActivity.fbly/1920f);
			lpx=px;lpy=py;
			ks=System.currentTimeMillis();
		}else
		if(event.getAction()==MotionEvent.ACTION_MOVE){
			js=System.currentTimeMillis();
			float xx=event.getX(0)/(MainActivity.fblx/1080f),
			yy=event.getY(0)/(MainActivity.fbly/1920f);
			if(!move&&tool.cd(lx,ly,xx,yy)<100&&js-ks>500){
				move=true;
			}
			if(move){
				px=lpx+X-lX;
				py=lpy+Y-lY;
				return true;
			}
		}else
		if(event.getAction()==MotionEvent.ACTION_UP){
			if(!move){
				Log.i(",,,;;","点击lable");
				for(int i=0;i<ve.size();i++)
				{
					if(ve.get(i).click(X-px,Y-py)){
						boolean dd=ve.get(i).drawbg;
						rest();
						if(!swithtype)ve.get(i).drawbg=!dd;
						else ve.get(i).drawbg=true;
						cl.onclick(i);
					}
				}
			}else move=false;
			return true;
		}
		return false;
	}
	public void rest()
	{
		for(int i=0;i<ve.size();i++)
		ve.get(i).drawbg=false;
	}
	public void setOrientation(int a){
		type=a;
	}
	public interface ClickListener{
		void onclick(int which);
	}
}
