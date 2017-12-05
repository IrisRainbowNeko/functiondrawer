package com.graph.myapp.widget;

import android.view.*;
import android.content.*;
import android.graphics.*;
import com.graph.myapp.*;

public class ColorPickerView extends View
{
	final int size=720;
	final int height=100;
	Paint paH,paS,paV,paA,pa,pai,pp;
	float pos_H,pos_S=size,pos_V=150,pos_A=size;
	int mod=0;
	ColorChangedListener ccl=null;
	Path path=new Path();
	public ColorPickerView(Context context){
		super(context);
		setpaH();
		setpaV();
		pa=new Paint();
		pa.setStyle(Paint.Style.STROKE);
		pa.setStrokeWidth(3);
		pai=new Paint(pa);
		pai.setColor(Color.WHITE);
		pp=new Paint();
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		canvas.save();
		canvas.scale((MainActivity.fblx/1080f),(MainActivity.fbly/1920f));
		canvas.save();
		canvas.translate(10,20);
		drawH(canvas);
		drawS(canvas);
		drawV(canvas);
		drawA(canvas);
		canvas.restore();
		canvas.restore();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		float X,Y;
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			X=getx(event);
			Y=gety(event);
			if(pz(X,Y,0,0,size,height)){mod=1;ccl.startchange();}
			else if(pz(X,Y,0,150,size,150+size)){mod=2;ccl.startchange();}
			else if(pz(X,Y,0,900,size,900+height)){mod=3;ccl.startchange();}
		case MotionEvent.ACTION_MOVE:
			X=getx(event);
			Y=gety(event);
			switch(mod){
				case 1:
					pos_H=limt(X,0,size);
					ccl.oncolorchange(Color.HSVToColor((int)(255*pos_A/size),toHSV(pos_H,pos_S,pos_V)));
					invalidate();
					break;
				case 2:
					pos_S=limt(X,0,size);
					pos_V=limt(Y,150,size+150);
					ccl.oncolorchange(Color.HSVToColor((int)(255*pos_A/size),toHSV(pos_H,pos_S,pos_V)));
					invalidate();
					break;
				case 3:
					pos_A=limt(X,0,size);
					ccl.oncolorchange(Color.HSVToColor((int)(255*pos_A/size),toHSV(pos_H,pos_S,pos_V)));
					invalidate();
					break;
				default:break;
			}
			break;
		case MotionEvent.ACTION_UP:
			mod=0;
			ccl.stopchange();
			break;
		}
		return true;
	}
	
	public void setpaH(){
		paH=new Paint();
		Shader mShader = new LinearGradient(0,0,size,0,new int[]{0xFFFF0000,0xFFFFFF00,
				0xFF00FF00,0xFF00FFFF,0xFF0000FF,0xFFFF00FF,0xFFFF0000},null,Shader.TileMode.MIRROR); 
		paH.setShader(mShader);
	}
	public void setpaS(int col){
		paS=new Paint();
		Shader shader=new LinearGradient(0,150,size,150,Color.WHITE,col,Shader.TileMode.CLAMP);
		paS.setShader(shader);
	}
	public void setpaV(){
		paV=new Paint();
		Shader shader=new LinearGradient(0,150,0,size+150,0x00000000,0xFF000000,Shader.TileMode.CLAMP); 
		paV.setShader(shader);
	}
	public void setpaA(int col){
		paA=new Paint();
		Shader shader=new LinearGradient(0,0,size,0,0,col,Shader.TileMode.CLAMP); 
		paA.setShader(shader);
	}
	public void drawH(Canvas canvas){
		canvas.drawRect(0,0,size,height,paH);
		canvas.drawLine(pos_H,0,pos_H,height,pa);
		path=new Path();
		path.moveTo(pos_H-10,-18);
		path.lineTo(pos_H+10,-18);
		path.lineTo(pos_H,0);
		path.close();
		canvas.drawPath(path,pp);
	}
	public void drawS(Canvas canvas){
		setpaS(Color.HSVToColor(new float[]{360*pos_H/size,1,1}));
		canvas.drawRect(0,150,size,size+150,paS);
	}
	public void drawV(Canvas canvas){
		canvas.drawRect(0,150,size,size+150,paV);
		canvas.drawCircle(pos_S,pos_V,10,pa);
		canvas.drawCircle(pos_S,pos_V,12,pai);
	}
	public void drawA(Canvas canvas){
		setpaA(Color.HSVToColor(toHSV(pos_H,pos_S,pos_V)));
		canvas.drawRect(0,900,size,height+900,paA);
		canvas.drawLine(pos_A,900,pos_A,900+height,pa);
		path=new Path();
		path.moveTo(pos_A-10,900-18);
		path.lineTo(pos_A+10,900-18);
		path.lineTo(pos_A,900);
		path.close();
		canvas.drawPath(path,pp);
	}
	public float[] toHSV(float ph,float ps,float pv){
		return new float[]{360*ph/size,ps/size,1-(pv-150)/size};
	}
	public float limt(float a,float low,float high){
		a=Math.max(a,low);
		a=Math.min(a,high);
		return a;
	}
	public void setcolor(int color){
		float[] hsv=new float[4];
		Color.colorToHSV(color,hsv);
		pos_H=size*hsv[0]/360;
		pos_S=size*hsv[1];
		pos_V=size*(1-hsv[2])+150;
		pos_A=size*Color.alpha(color)/255f;
		invalidate();
	}
	public int getcolor(){
		return Color.HSVToColor((int)(255*pos_A/size),toHSV(pos_H,pos_S,pos_V));
	}
	public void setColorChangedListener(ColorChangedListener ccl){
		this.ccl=ccl;
	}
	public boolean pz(float ox,float oy,float x1,float y1,float x2,float y2){
		return new RectF(x1,y1,x2,y2).contains(ox,oy);
	}
	private float getx(MotionEvent event){
		return tool.fitx(event.getX()-10);
	}
	private float gety(MotionEvent event){
		return tool.fity(event.getY()-20);
	}
	public interface ColorChangedListener{
		void startchange();
		void oncolorchange(int color);
		void stopchange();
	}
}
