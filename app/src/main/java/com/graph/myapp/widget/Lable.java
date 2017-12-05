package com.graph.myapp.widget;
import android.graphics.*;
import android.text.*;

public class Lable
{
	TextPaint textPaint = new TextPaint();
	Paint pa=new Paint();
	String text="";
	float x,y,w,h;
	public boolean drawbg;
	public Lable(){textPaint.setTextSize(50);}
	public Lable(String text){this.text=text;}
	public Lable(String text,float x,float y,float w,float h)
	{this.text=text;this.x=x;this.y=y;this.w=w;this.h=h;}
	public Lable(Lable la){
		text=la.text;x=la.x;y=la.y;w=la.w;h=la.h;
		drawbg=la.drawbg;
		pa=new Paint(la.pa);
		textPaint=new TextPaint(la.textPaint);
	}
	public void settext(String t){text=t;text=text.replaceAll("\n"," ");}
	public void setposition(float x,float y){this.x=x;this.y=y;}
	public void setwh(float w,float h){this.w=w;this.h=h;}
	public void SetwhSurround(){
		Rect re=new Rect();
		textPaint.getTextBounds(text,0,text.length(),re);
		w=textPaint.measureText(text)+20;
		h=textPaint.getTextSize()+4;
	}
	public void setbgcolor(int col){
		pa.setColor(col);
		pa.setStyle(Paint.Style.STROKE);
		pa.setStrokeWidth(3);}
	public void settextcolor(int col){textPaint.setColor(col);}
	public void settextsize(int a){textPaint.setTextSize(a);}
	public void draw(Canvas canvas)
	{
		if(drawbg)canvas.drawRect(x,y,x+w,y+h,pa);
		StaticLayout layout = new StaticLayout(text,textPaint,(int)(w-10),Layout.Alignment.ALIGN_CENTER,1.0F,0.0F,true);
		canvas.save();
		canvas.translate(x+5,y+((h-layout.getHeight())/2));
		layout.draw(canvas);
		canvas.restore();
	}
	public boolean click(float X,float Y)
	{
		if(pz(X,Y,x,y,x+w,y+h)){return true;}
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
