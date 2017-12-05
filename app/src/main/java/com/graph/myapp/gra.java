package com.graph.myapp;

import android.content.*;
import android.graphics.*;
import android.media.*;
import android.view.*;
import android.view.SurfaceHolder.*;
import java.text.*;
import android.util.*;
import java.util.*;
import com.graph.myapp.widget.*;
import com.bdsjx.tool.*;
import java.nio.*;
public class gra extends SurfaceView implements Callback,Runnable
{
	public static Vector<Integer> ind_ec=new Vector<Integer>();
	Vector<Float> po_ec=new Vector<Float>();
	public static Express ex=new Express();
	public static Vector<String> suanshi=new Vector<String>(),suan=new Vector<String>();
	public static Vector<Integer> col=new Vector<Integer>();
	public static Vector<Path> path=new Vector<Path>();
	public static Vector<float[]> pos=new Vector<float[]>();
	public static boolean flag,isck,qly,gridopen=true;
	public static int htxc=1,moverate=20;
	public static Vector<float[]> parameter=new Vector<float[]>();
	public static float[] para_lim={0,100};
	public static boolean numon=true,paion=false,fson=false;
	private SurfaceHolder sfh;
	private Thread mm;
	private Canvas canvas;
	Paint paint,pai,pa_mb,pa_tc,pa_grid;
	seek sk;
	public static LableList lal=new LableList(),lal_para=new LableList();
	float jd=0.01f,cc,ddx;
	final int cd=1080*2,cd_y=1920*2,delta_v=5;
	float jd_x=3,jd_ec=3;
	int x0,y0,mood=0,px,py,px1,py1,index_ec;
	int[] para_index={-1,0};
	float tx=1,ty=1,ty1=1,zdx,zdy,vx,vy;
	boolean cktype,drawnew,flag_move;
	long touchtime;
	ecqx[] ee=null;
	DecimalFormat df = new DecimalFormat("0.0000");
	public gra(Context context,AttributeSet attr)
	{
		super(context,attr);
		sfh=this.getHolder();
		sfh.addCallback(this);
		paint=new Paint();
		paint.setColor(Color.GREEN);
		paint.setTextSize(50);
		paint.setStrokeWidth(4);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		pai=new Paint();
		pai.setTextSize(30);
		pai.setStrokeWidth(4);
		pai.setColor(Color.BLACK);
		pa_grid=new Paint();
		pa_grid.setStrokeWidth(2);
		pa_grid.setColor(Color.BLACK);
		pa_grid.setAlpha(120);
		pa_mb=new Paint();
		pa_mb.setTextSize(50);
		pa_mb.setStyle(Paint.Style.STROKE);
		pa_mb.setStrokeWidth(2);
		pa_tc=new Paint();
		pa_tc.setTextSize(50);
		sk=new seek(140,1500,800,50);
		sk.setmax((int)(100*(para_lim[1]-para_lim[0])));
		sk.setbitmap(tool.loadbitmap("bar.png"));
		lal.setClickListener(new LableList.ClickListener(){
				@Override
				public void onclick(int which)
				{
					if(para_index[0]==which){para_index[0]=-1;sk.setindex(0);}
					else {para_index[0]=which;sk.setindex((int)((parameter.get(which)[para_index[1]]-para_lim[0])*100));}
					//Draw(false);
				}
		});
		lal.setposition(650,200);
		lal_para.swithtype=true;
		lal_para.setClickListener(new LableList.ClickListener(){
				@Override
				public void onclick(int which)
				{
					para_index[1]=which;
					sk.setindex((int)((parameter.get(para_index[0])[para_index[1]]-para_lim[0])*100));
				}
			});
		lal_para.setposition(150,1580);
		lal_para.setOrientation(LableList.horizontal);
		for(int i=0;i<5;i++){
			Lable la=new Lable();
			la.settextsize(60);
			lal_para.add(la);
			lal_para.SetText(Constant.PARA_strarr[i],i);
			lal_para.ve.get(i).setbgcolor(Color.BLACK);
		}
		lal_para.ve.get(0).drawbg=true;
		setFocusable(true);
		setFocusableInTouchMode(true);
	}
	@Override
	public void surfaceCreated(SurfaceHolder p1)
	{
		mm= new Thread(this);
		mm.start();
		Draw(true);
		// TODO: Implement this method
	}
	protected void Draw(boolean hhs)
	{
		try{
			canvas=sfh.lockCanvas();
			if(canvas!=null)
			{
				canvas.drawColor(Color.WHITE);
				canvas.save();
				canvas.scale((MainActivity.fblx/1080f),(MainActivity.fbly/1920f));
				canvas.save();
				canvas.scale(tx,tx,540,960);
				canvas.translate(px,py);
				//canvas.drawBitmap(cs,300,300,pai);
				boolean flag_ec = false;
				if(hhs){ind_ec.removeAllElements();
				for(int i=0;i<pos.size();i++)
				pos.setElementAt(new float[0],i);}
				lal.draw(canvas);
				for(int i=0;i<suan.size();i++){
					paint.setColor(col.get(i));
					Path path1=new Path();
					//canvas.drawText(suanshi.get(i),540-(22*suanshi.get(i).length())/2,200,paint);
					//canvas.drawText(suan.get(i),650,(200-(suan.size()*26)+(52*i)),paint);
					int x=0,jishu=0;
					float hx=0,hy=0;
					if (!suanshi.equals("") && hhs) {
						String sstr=suanshi.get(i);
						float[] Dim=new float[]{Float.NEGATIVE_INFINITY,Float.POSITIVE_INFINITY};
						String dstr=tool.getsubstring(suanshi.get(i),"[","]",0);
						if(!dstr.equals("")){
							String[] sdim=dstr.split(",");
							ex.setexpress(new String[]{sdim[0],sdim[1]},Express.arr2buff(parameter.get(i)),Constant.PARA_strarr);
							Dim[0]=ex.calculate(0);
							Dim[1]=ex.calculate(1);
							sstr=tool.cutstring(sstr,"[","]");
						}
						String[] hssz=sstr.split(";");
						if(hssz[0].equals(Constant.FUNCTION_PARAMETER+"")||hssz[0].equals(Constant.FUNCTION_POLAR+"")){
							float jd1 = Float.parseFloat(hssz[3]);
							String[] slim=hssz[4].split(",");
							float[] lim = new float[2];
							ex.setexpress(new String[]{slim[0],slim[1]},Express.arr2buff(parameter.get(i)),Constant.PARA_strarr);
							lim[0]=ex.calculate(0);
							lim[1]=ex.calculate(1);
							DoubleBuffer parabuff=Express.arr2buff(tool.arrayadd(new double[]{0},parameter.get(i)));
							ex.setexpress(new String[]{hssz[1],hssz[2]},parabuff,tool.arrayadd(new String[]{"t"},Constant.PARA_strarr));
							if(suan.get(i).charAt(0)=='\''){
								float x_last=0,y_last=0;
								for (float u=lim[0];u<lim[1];u+=jd1) {
									parabuff.put(0,u);
									float value_x=ex.calculate(0),value_y=ex.calculate(1);
									if(u==lim[0]){
										x_last=value_x;
										y_last=value_y;
										continue;
									}
									float x1=(value_x)/jd+540;
									float y=-((value_y-y_last)/(value_x-x_last))/jd+960;
									if (Float.compare(x1, Float.NaN) == 0 || Float.compare(y, Float.NaN) == 0 || Math.abs(y-540+px)>=1620 || Math.abs(x1-960+py)>=2880) {
										jishu=0;
									} else {
										if (jishu==0) {
											path1.moveTo(x1,y);
										} else {
											path1.lineTo(x1,y);
										}
										jishu++;
									}
									x_last=value_x;
									y_last=value_y;
								}
							}else if(suan.get(i).charAt(0)=='∫'){
								float numy=0,x_last=0;
								for (float u=lim[0];u<lim[1];u+=jd1) {
									parabuff.put(0,u);
									float value_x=ex.calculate(0),value_y=ex.calculate(1);
									if(u==lim[0]){
										x_last=value_x;
										continue;
									}
									float x1=value_x/jd+540;
									numy+=value_y*(value_x-x_last);
									float y=-numy/jd+960;
									if (Float.compare(x1, Float.NaN) == 0 || Float.compare(y, Float.NaN) == 0 || Math.abs(y-540+px)>=1620 || Math.abs(x1-960+py)>=2880) {
										jishu=0;
									} else {
										if (jishu==0) {
											path1.moveTo(x1,y);
										} else {
											path1.lineTo(x1,y);
										}
										jishu++;
									}
									x_last=value_x;
								}
							}else{
								for (float u=lim[0];u<lim[1];u+=jd1) {
									parabuff.put(0,u);
									float x1=ex.calculate(0)/jd+540;
									float y=-ex.calculate(1)/jd+960;
									if (Float.compare(x1, Float.NaN) == 0 || Float.compare(y, Float.NaN) == 0 || Math.abs(y-540+px)>=1620 || Math.abs(x1-960+py)>=2880) {
										jishu=0;
									} else {
										if (jishu==0) {
											path1.moveTo(x1,y);
										} else {
											path1.lineTo(x1,y);
										}
										jishu++;
									}
								}
							}
						} else if(hssz[0].equals(Constant.FUNCTION_NORMAL+"")){
							
							float DL=(-cd/2-px)*jd,
								DR=(cd/2-px)*jd;
							if(!Float.isInfinite(Dim[0])){
								//x+=Dim[0]>DL?(Dim[0]-DL)/jd:0;
								DL=Math.max(DL,Dim[0]);
							}
							if(!Float.isInfinite(Dim[1]))DR=Math.min(DR,Dim[1]);
							x=(int)(DL/jd)+540;
							if(suan.get(i).charAt(0)=='\''){
								float hdy=0,hy1,ddy=1;
								x-=(jd_x/2);
								DoubleBuffer parabuff=Express.arr2buff(tool.arrayadd(new double[]{0},parameter.get(i)));
								ex.setexpress(new String[]{hssz[1]},parabuff,tool.arrayadd(new String[]{"x"},Constant.PARA_strarr));
								for (float u=DL;u<DR;u+=jd*jd_x) {
									parabuff.put(0,u);
									if(jishu==0){
										hy=-ex.calculate(0);
										jishu++;x+=jd_x;
										continue;
									}
									hy1=-ex.calculate(0);
									float y =(hy1-hy)/(jd*jd_x);
									float ddy1=y-hdy;
									if (Float.compare(y,Float.NaN)!=0){
										if (ddy*ddy1>0||Math.abs(ddy1)<=0.2*(jd/0.01)){
											if(jishu!=1)path1.lineTo(x,y/jd+960);
											else path1.moveTo(x,y/jd+960);
										} else {
											jishu=0;
										}
										if (jishu!=1) {
											ddy=y-hdy;
										}
										hdy=y;
										hy=hy1;
										jishu++;
									} else {
										jishu=0;
									}
									x+=jd_x;
								}
							}else if(suan.get(i).charAt(0)=='∫'){
								x=(int)(Dim[0]/jd)+540;
								float y=0;
								DoubleBuffer parabuff=Express.arr2buff(tool.arrayadd(new double[]{0},parameter.get(i)));
								ex.setexpress(new String[]{hssz[1]},parabuff,tool.arrayadd(new String[]{"x"},Constant.PARA_strarr));
								for (float u=Dim[0];u<Dim[1];u+=jd*jd_x) {
									parabuff.put(0,u);
									y+=(-ex.calculate(0)*(jd*jd_x));
									if (!Float.isNaN(y)&&!Float.isInfinite(y)) {
										float ddx1=y-hy;
										if (jishu!=0&&(ddx1*ddx>0||Math.abs(ddx1)<=0.2*(jd/0.01))) {
											path1.lineTo(x,y/jd+960);
										} else {
											path1.moveTo(x,y/jd+960);
										}
										if (jishu!= 0) {
											ddx=y-hy;
										}
										hy=y;
										jishu++;
									} else {
										jishu=0;
									}
									x+=jd_x;
								}
							}else{
								DoubleBuffer parabuff=Express.arr2buff(tool.arrayadd(new double[]{0},parameter.get(i)));
								ex.setexpress(new String[]{hssz[1]},parabuff,tool.arrayadd(new String[]{"x"},Constant.PARA_strarr));
								for (float u=DL;u<DR;u+=jd*jd_x) {
									parabuff.put(0,u);
									float y =-ex.calculate(0);
									if (!Float.isNaN(y)&&!Float.isInfinite(y)) {
									float ddx1=y-hy;
									if (jishu!=0&&(ddx1*ddx>0||Math.abs(ddx1)<=0.2*(jd/0.01))) {
										path1.lineTo(x,y/jd+960);
									} else {
										path1.moveTo(x,y/jd+960);
									}
									if (jishu!= 0) {
										ddx=y-hy;
									}
									hy=y;
									jishu++;
								} else {
									jishu=0;
								}
								x+=jd_x;
								}
							}
						}else if(hssz[0].equals(Constant.FUNCTION_CURVE+"")){ind_ec.add(i);}
					}
					if(hhs){
						if(suanshi.get(i).charAt(0)-'0'==Constant.FUNCTION_CURVE){
							if(!flag_ec){
								Log.v("====","开始绘制");
							flag_ec=true;
							if(ee!=null)for(int o=0;o<ee.length;o++)ee[o].stop=true;
							po_ec.removeAllElements();
							index_ec=0;
							pos.setElementAt(new float[0],index_ec);
							ee=new ecqx[htxc];
							float x_top=(-cd/4-px)*jd,y_top=(-cd_y/4+py)*jd,
								xw=cd/2*jd,yw=(cd_y/2*jd)/htxc;
							for(int o=0;o<ee.length;o++)ee[o]=new ecqx(suanshi.get(i).substring(2).split(";"),x_top,y_top+o*yw,x_top+xw,y_top+(o+1)*yw,parameter.get(i));
							po_ec.add((float)paint.getColor());
							ee[0].drawhs=true;
							for(int o=0;o<ee.length;o++)
							new Thread(ee[o]).start();
							}
						}else{
						canvas.drawPath(path1,paint);
						path.setElementAt(path1,i);
						}
					}else{
						if(suanshi.get(i).charAt(0)-'0'==Constant.FUNCTION_CURVE){
							if(drawnew){
								drawnew=false;
							index_ec++;
							if(index_ec<pos.size()){
								Log.v("====","新绘制");
								//for(int o=0;o<ee.length;o++)ee[o].stop=true;
								po_ec.removeAllElements();
							pos.setElementAt(new float[0],index_ec);
							ee=new ecqx[htxc];
							float x_top=(-cd/4-px)*jd,y_top=(-cd_y/4+py)*jd,
								xw=cd/2*jd,yw=(cd_y/2*jd)/htxc;
								for(int o=0;o<ee.length;o++)ee[o]=new ecqx(suanshi.get(ind_ec.get(index_ec)).substring(2).split(";"),x_top,y_top+o*yw,x_top+xw,y_top+(o+1)*yw,parameter.get(ind_ec.get(index_ec)));
								po_ec.add((float)col.get(ind_ec.get(index_ec)));
							ee[0].drawhs=true;
							for(int o=0;o<ee.length;o++)
								new Thread(ee[o]).start();
							}else{index_ec=0;}
							}
							for(int m=0;m<pos.size();m++){
								float[] poss=pos.get(m);
								if(poss.length>0){
									paint.setColor((int)poss[0]);
									canvas.drawPoints(poss,1,poss.length-1,paint);
								}
							}
						}else
						canvas.drawPath(path.get(i),paint);
					}
				}
				canvas.drawLine(540,-py-960,540,1920+960-py,pai);
				canvas.drawLine(-540-px,960,540+1080-px,960,pai);
				
				if(numon){
					for(int i=60+((960-1900-py)/100)*100;i<60+100*(960+1900-py)/100;i+=100)
					{
						if(fson&&jd<0.01){
							int[] temp=tool.yuefen(-(i-960)/100,Math.round(0.01f/jd));
							if(temp[1]==1)canvas.drawText(""+temp[0],570,i,pai);
							else{
								int off=7,start_up=570,start_down=570,len_x=(int)Math.max(pai.measureText(""+temp[0]),pai.measureText(""+temp[1]));
								if((""+temp[0]).length()>(""+temp[1]).length())
									start_down+=(len_x-pai.measureText(""+temp[1]))/2;
								else
									start_up+=(len_x-pai.measureText(""+temp[0]))/2;
								canvas.drawText(""+temp[0],start_up,i,pai);
								canvas.drawLine(start_up-5,i+off,start_up-5+len_x+10,i+off,pai);
								canvas.drawText(""+temp[1],start_down,i+off+pai.getTextSize(),pai);
							}
						}else
							canvas.drawText(""+-((i-960)*jd),570,i,pai);
						canvas.drawLine(540,i,560,i,pai);
						if(gridopen)canvas.drawLine(40+((540-1000-px)/100)*100,i,40+100*(540+1000-px)/100,i,pa_grid);
					}
					for(int i=40+((540-1000-px)/100)*100;i<40+100*(540+1000-px)/100;i+=100)
					{
						if(fson&&jd<0.01){
							int[] temp=tool.yuefen((i-540)/100,Math.round(0.01f/jd));
							if(temp[1]==1)canvas.drawText(""+temp[0],i-10,1000,pai);
							else{
								int off=7,start_up=i-10,start_down=i-10,len_x=(int)Math.max(pai.measureText(""+temp[0]),pai.measureText(""+temp[1]));
								if((""+temp[0]).length()>(""+temp[1]).length())
									start_down+=(len_x-pai.measureText(""+temp[1]))/2;
								else
									start_up+=(len_x-pai.measureText(""+temp[0]))/2;
								canvas.drawText(""+temp[0],start_up,1000,pai);
								canvas.drawLine(start_up-5,1000+off,start_up-5+len_x+10,1000+off,pai);
								canvas.drawText(""+temp[1],start_down,1000+off+pai.getTextSize(),pai);
							}
						}else
							canvas.drawText(""+((i-540)*jd),i-20,1000,pai);
						canvas.drawLine(i,940,i,960,pai);
						if(gridopen)canvas.drawLine(i,60+((960-1900-py)/100)*100,i,60+100*(960+1900-py)/100,pa_grid);
					}
				}
				//π
				if(paion){
					int count_y=(int)Math.round((2*1920-py)/(Math.PI*100)),
						count_x=(int)Math.round((2*1080-px)/(Math.PI*100));
				
					pai.setColor(Color.RED);
					for(int i=count_y-24;i<count_y;i++)
					{
						if(i==0)continue;
						float temp_y=960+(float)(i*Math.PI*100);
						if(fson&&jd<0.01){
							int[] temp=tool.yuefen(-i,Math.round(0.01f/jd));
							if(temp[1]==1)canvas.drawText(temp[0]+"π",570,temp_y,pai);
							else{
								int off=7,start_up=570,start_down=570,len_x=(int)Math.max(pai.measureText(""+temp[0]),pai.measureText(""+temp[1]));
								if((""+temp[0]).length()>(""+temp[1]).length())
									start_down+=(len_x-pai.measureText(""+temp[1]))/2;
								else
									start_up+=(len_x-pai.measureText(""+temp[0]))/2;
								canvas.drawText(""+temp[0],start_up,temp_y,pai);
								canvas.drawLine(start_up-5,temp_y+off,start_up-5+len_x+10,temp_y+off,pai);
								canvas.drawText(""+temp[1],start_down,temp_y+off+pai.getTextSize(),pai);
								canvas.drawText("π",start_up-5+len_x+10,temp_y+off+7,pai);
							}
						}else
							canvas.drawText(-i*jd*100+"π",570,temp_y,pai);
						canvas.drawLine(540,temp_y,560,temp_y,pai);
					}
					for(int i=count_x-16;i<count_x;i++)
					{
						if(i==0)continue;
						float temp_x=540+(float)(i*Math.PI*100);
						if(fson&&jd<0.01){
							int[] temp=tool.yuefen(i,Math.round(0.01f/jd));
							if(temp[1]==1)canvas.drawText(temp[0]+"π",temp_x-20,1000,pai);
							else{
								int off=7,start_up=(int)temp_x-10,start_down=(int)temp_x-10,len_x=(int)Math.max(pai.measureText(""+temp[0]),pai.measureText(""+temp[1]));
								if((""+temp[0]).length()>(""+temp[1]).length())
									start_down+=(len_x-pai.measureText(""+temp[1]))/2;
								else
									start_up+=(len_x-pai.measureText(""+temp[0]))/2;
								canvas.drawText(""+temp[0],start_up,1000,pai);
								canvas.drawLine(start_up-5,1000+off,start_up-5+len_x+10,1000+off,pai);
								canvas.drawText(""+temp[1],start_down,1000+off+pai.getTextSize(),pai);
								canvas.drawText("π",start_up-5+len_x+10,1000+7+off,pai);
							}
						}else
							canvas.drawText(i*jd*100+"π",temp_x-20,1000,pai);
						canvas.drawLine(temp_x,940,temp_x,960,pai);
					}
					pai.setColor(Color.BLACK);
				}
				/*{
					double pi_jd=Math.PI/jd;
					int pi_min=(int)Math.floor((-540-px)/pi_jd),
						pi_max=(int)Math.ceil((540-px)/pi_jd);
					for(int i=pi_min;i<=pi_max;i++)
					{
						float i_pi=(float)(i*pi_jd);
						canvas.drawText(i+"π",i_pi-20,1000,pai);
						canvas.drawLine(i_pi,940,i_pi,960,pai);
					}
				}*/
				float nx = 0;
				if(cktype)
				{
					nx=-(cd/2*jd)/2+zdx*jd;
					pai.setColor(Color.BLUE);
					canvas.drawLine(zdx,-960-py,zdx,2880-py,pai);
					for(int i=0;i<suanshi.size();i++){
						String jg=suanshi.get(i);
						if(jg.charAt(0)-'0'==Constant.FUNCTION_NORMAL)
						{
							float y;
							float[] Dim={Float.NEGATIVE_INFINITY,Float.POSITIVE_INFINITY};
							String[] sstr=tool.getsubstring(jg,"[","]",0).split(",");
							if(sstr.length==2){
								ex.setexpress(new String[]{sstr[0],sstr[1]},Express.arr2buff(parameter.get(i)),Constant.PARA_strarr);
								Dim[0]=ex.calculate(0);
								Dim[1]=ex.calculate(1);
								jg=tool.cutstring(jg,"[","]");
							}
							if(!(nx>Dim[0]&&nx<Dim[1]))continue;
							DoubleBuffer parabuff=Express.arr2buff(tool.arrayadd(new double[]{nx},parameter.get(i)));
							ex.setexpress(new String[]{jg.substring(2)},parabuff,tool.arrayadd(new String[]{"x"},Constant.PARA_strarr));
							if(suan.get(i).charAt(0)!='\'')y=ex.calculate(0);
							else{
								float temp=ex.calculate(0);
								parabuff.put(0,nx+jd);
								y=(ex.calculate(0)-temp)/jd;
							}
							if(Float.compare(Float.NaN,y)==0)continue;
							pa_tc.setColor((~col.get(i)&0x00ffffff)|0xff000000);
							pa_mb.setColor(col.get(i));
							pai.setColor(col.get(i));
							float ny=-y/jd+960;
							canvas.drawLine(-540-px,ny,1620-px,ny,pai);
							//绘制曲率圆
							if(qly){
								float derivatives1=0,derivatives2=0;
								if(suan.get(i).charAt(0)=='\''){
									float temp1=ex.calculate(0);
									parabuff.put(0,nx+jd);
									float temp2=ex.calculate(0);
									parabuff.put(0,nx-jd);
									float temp3=ex.calculate(0);
									parabuff.put(0,nx+2*jd);
									float temp4=ex.calculate(0);
									
									float temp_d1=(temp4-temp2)/jd,temp_d2=(temp2-temp1)/jd,temp_d3=(temp1-temp3);
									derivatives1=(temp_d3-temp_d2)/jd;
									derivatives2=(((temp_d3-temp_d2)/jd)-((temp_d2-temp_d1)/jd))/jd;
								}
								else{
									float temp=ex.calculate(0);
									parabuff.put(0,nx+jd);
									derivatives1=(ex.calculate(0)-temp)/jd;
									parabuff.put(0,nx-jd);
									float temp_d=(temp-ex.calculate(0))/jd;
									derivatives2=(derivatives1-temp_d)/jd;
								}
								pai.setStyle(Paint.Style.STROKE);
								canvas.drawCircle(zdx-(derivatives1*(1+derivatives1*derivatives1)/derivatives2)/jd,
												  ny-((1+derivatives1*derivatives1)/derivatives2)/jd,(1/(Math.abs(derivatives2)/(float)Math.pow(1+derivatives1*derivatives1,1.5)))/jd,pai);
								pai.setStyle(Paint.Style.FILL);
							}
							if(zdx+px<850){
							canvas.drawText("y="+df.format(y),zdx+10,ny-10,pa_tc);
							canvas.drawText("y="+df.format(y),zdx+10,ny-10,pa_mb);
							}else
							{
								canvas.drawText("y="+df.format(y),zdx-230,ny-10,pa_tc);
								canvas.drawText("y="+df.format(y),zdx-230,ny-10,pa_mb);
							}
						}
					}
					pai.setColor(Color.BLACK);
				}
				canvas.restore();
				if(cktype){
					pa_tc.setColor(Color.BLUE);
					canvas.drawText("x="+df.format(nx),10,50,pa_tc);
				}
				if(para_index[0]!=-1)
				{sk.draw(canvas);lal_para.draw(canvas);}
				canvas.restore();
			}
		}
		catch(Exception e){MainActivity.ma.handler.obtainMessage(0,"错误:"+e).sendToTarget();}
		finally
		{
			if(canvas!=null)
			{
				sfh.unlockCanvasAndPost(canvas);
			}
		}
	}
	int x_last,y_last;
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
	int type=event.getAction();
	if(para_index[0]!=-1){
		if(sk.onseek(event)){
			parameter.get(para_index[0])[para_index[1]]=para_lim[0]+sk.getindex()/100f;
			//parameter.setElementAt(para_lim[0]+sk.getindex()/100f,para_index[1]);
			Draw(true);
			return true;
		}
		if(lal_para.touch(event.getX(0)/(MainActivity.fblx/1080f),
					   event.getY(0)/(MainActivity.fbly/1920f))){Draw(false);return true;}
	}
	//if(type==MotionEvent.ACTION_UP){
		if(lal.touch(event,(tool.scalexy(event.getX()/(MainActivity.fblx/1080f),540,tx)-px),
				  (tool.scalexy(event.getY()/(MainActivity.fbly/1920f),960,tx)-py))){Draw(false);return true;}
	//}
	if(!isck){
	switch (type & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
		{
			touchtime=System.currentTimeMillis();
			x0=(int)tool.scalexy(event.getX()/(MainActivity.fblx/1080f),540,tx);
			y0=(int)tool.scalexy(event.getY()/(MainActivity.fbly/1920f),960,tx);
			mood=1;
		}
		break;
		case MotionEvent.ACTION_POINTER_DOWN:
		{
			mood=2;
			cc=(int)长度(event.getX(0)/(MainActivity.fblx/1080f),event.getY(0)/(MainActivity.fbly/1920f),
					   event.getX(1)/(MainActivity.fblx/1080f),event.getY(1)/(MainActivity.fbly/1920f));
		}
		break;
		case MotionEvent.ACTION_MOVE:
		{
			if(mood==2)
			{int X=(int)(event.getX(0)/(MainActivity.fblx/1080f));
			int Y=(int)(event.getY(0)/(MainActivity.fbly/1920f));
			int X1=(int)(event.getX(1)/(MainActivity.fblx/1080f));
			int Y1=(int)(event.getY(1)/(MainActivity.fbly/1920f));
			int chd=(int)长度(X,Y,X1,Y1);
			tx=((chd-cc)/1080f)+ty1;
			if(tx<0.5)
			{
				ty1=1;
				cc=chd;
				jd*=2;
				px/=2;
				py/=2;
				Draw(true);
			}
			if(tx>2)
			{
				ty1=1;
				cc=chd;
				jd/=2;
				px*=2;
				py*=2;
				Draw(true);
			}
			}
			else if(mood==1)
			{
				int X=(int)tool.scalexy(event.getX()/(MainActivity.fblx/1080f),540,tx);
				int Y=(int)tool.scalexy(event.getY()/(MainActivity.fbly/1920f),960,tx);
				px=px1+X-x0;py=py1+Y-y0;
				
			}
			Draw(false);
			}
			break;
			case MotionEvent.ACTION_UP:
			{
				//int a=(int)(event.getX()/(MainActivity.fblx/1080f));
				//int b=(int)(event.getY()/(MainActivity.fbly/1920f));
				if(mood==1){
					int X=(int)tool.scalexy(event.getX()/(MainActivity.fblx/1080f),540,tx);
					int Y=(int)tool.scalexy(event.getY()/(MainActivity.fbly/1920f),960,tx);
					
					flag_move=true;
					vx=moverate*(X-x0)/(System.currentTimeMillis()-touchtime);
					vy=moverate*(Y-y0)/(System.currentTimeMillis()-touchtime);
				}
				ty1=tx;px1=px;py1=py;
				Draw(true);
			}
			break;
			case MotionEvent.ACTION_POINTER_UP:
			{
				mood=0;
			}
		}
		}
		else{
		switch (type) {
			case MotionEvent.ACTION_DOWN:
				{
					cktype=true;
					zdx=tool.scalexy(event.getX()/(MainActivity.fblx/1080f),540,tx)-px;
					zdy=tool.scalexy(event.getY()/(MainActivity.fbly/1920f),960,tx)-py;
					Draw(false);
				}
				break;
			case MotionEvent.ACTION_MOVE:
				{
					zdx=tool.scalexy(event.getX()/(MainActivity.fblx/1080f),540,tx)-px;
					zdy=tool.scalexy(event.getY()/(MainActivity.fbly/1920f),960,tx)-py;
					Draw(false);
				}
				break;
			case MotionEvent.ACTION_UP:
				{
					cktype=false;
					zdx=tool.scalexy(event.getX()/(MainActivity.fblx/1080f),540,tx)-px;
					zdy=tool.scalexy(event.getY()/(MainActivity.fbly/1920f),960,tx)-py;
					Draw(false);
				}break;
			}
		}
		return true;
	}
	@Override
	public void surfaceChanged(SurfaceHolder p1, int p2, int p3, int p4)
	{
		// TODO: Implement this method
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder p1)
	{
		if(ee!=null)
		for(int i=0;i<ee.length;i++){
			ee[i].stop=true;
		}
	}
	public float[] tofloatsz(String[] a) {
        float[] b= new float[a.length];
        for (int i=0;i<b.length;i++) {
            b[i] = Float.parseFloat(a[i]);
        }
        return b;
    }
	private double 长度(float x,float y,float x1,float y1)
	{
		return Math.sqrt(Math.pow(x-x1,2)+Math.pow(y-y1,2));
	}
	@Override
	public void run()
	{
		while(true)
		{
			if(flag)
			{
				Draw(true);
				flag=false;
				Draw(false);
			}
			if(flag_move&&vx!=0&&vy!=0){
				float xb=(float)Math.sqrt(vx*vx+vy*vy);
				px1=px+=vx;py1=py+=vy;
				float vx_last=vx,vy_last=vy;
				vx-=delta_v*(vx/xb);
				vy-=delta_v*(vy/xb);
				if(vx_last*vx<=0){flag_move=false;vx=0;}
				if(vy_last*vy<=0){flag_move=false;vy=0;}
				Draw(false);
			}
			try
			{
				Thread.sleep(20);
			}
			catch (InterruptedException e)
			{}
		}
		// TODO: Implement this method
	}
	class ecqx implements Runnable
	{
		double[] front_x;
		double front=0;
		float x_top,y_top,x_end,y_end;
		float[] para;
		String[] express;
		boolean drawhs,stop=false;
		Express exp;
		public ecqx(String[] express,float x1,float y1,float x2,float y2,float[] par)
		{
			this.express=express;
			para=par;
			exp=new Express();
			x_top=x1;y_top=y1;
			x_end=x2;y_end=y2;
		}
		@Override
		public void run()
		{
			float zjd=jd*jd_ec;
			int count=0;
			switch(express[2]){
				case "=":{
					front_x=new double[(int)Math.ceil((y_end-y_top)/zjd)+1];
					DoubleBuffer parabuff=Express.arr2buff(tool.arrayadd(new double[]{0,0},para));
					exp.setexpress(new String[]{express[0],express[1]},parabuff,tool.arrayadd(new String[]{"x","y"},Constant.PARA_strarr));
					for (float u=x_top;u<x_end;u+=zjd) {
						int count1=0;
						for (float i=y_top;i<y_end;i+=zjd) {
							parabuff.put(0,u);
							parabuff.put(1,i);
							double z1 =exp.calculate(0);
							double z2 =exp.calculate(1);
							if (Double.compare(z1,Double.NaN) != 0&&Double.compare(z2,Double.NaN) != 0) {
								if(stop)return;
								double now=z1-z2;
								if(i==y_top){
									parabuff.put(0,u);
									parabuff.put(1,i-zjd);
									front=exp.calculate(0)-exp.calculate(1);
								}
								if(u==x_top){
									parabuff.put(0,u-zjd);
									parabuff.put(1,i);
									front_x[count1]=exp.calculate(0)-exp.calculate(1);
								}
								double fhx=now*front,fhy=now*front_x[count1];
								if((fhx<0)||(fhy<0)){
									synchronized(po_ec){
										po_ec.add(540+u/jd);po_ec.add(960-i/jd);
									}
								}
								front_x[count1]=now;
								count1++;
								front=now;
							}
						}
						count++;
						if(drawhs&&count%100==0){pos.setElementAt(tool.tofloatarray(po_ec),index_ec);Draw(false);}
					}}
					break;
				case ">":{
					DoubleBuffer parabuff=Express.arr2buff(tool.arrayadd(new double[]{0,0},para));
					exp.setexpress(new String[]{express[0],express[1]},parabuff,tool.arrayadd(new String[]{"x","y"},Constant.PARA_strarr));
					for (float u=x_top;u<x_end;u+=zjd) {
						for (float i=y_top;i<y_end;i+=zjd) {
							parabuff.put(0,u);
							parabuff.put(1,i);
							double z1 =exp.calculate(0);
							double z2 =exp.calculate(1);
							if (Double.compare(z1,Double.NaN) != 0&&Double.compare(z2,Double.NaN) != 0) {
								if(stop)return;
								if(z1>z2){
									synchronized(po_ec){
										po_ec.add(540+u/jd);po_ec.add(960-i/jd);
									}
								}
							}
						}
						count++;
						if(drawhs&&count%100==0){pos.setElementAt(tool.tofloatarray(po_ec),index_ec);Draw(false);}
					}}
					break;
				case "<":{
					DoubleBuffer parabuff=Express.arr2buff(tool.arrayadd(new double[]{0,0},para));
					exp.setexpress(new String[]{express[0],express[1]},parabuff,tool.arrayadd(new String[]{"x","y"},Constant.PARA_strarr));
					for (float u=x_top;u<x_end;u+=zjd) {
						for (float i=y_top;i<y_end;i+=zjd) {
							parabuff.put(0,u);
							parabuff.put(1,i);
							double z1 =exp.calculate(0);
							double z2 =exp.calculate(1);
							if (Double.compare(z1,Double.NaN) != 0&&Double.compare(z2,Double.NaN) != 0) {
								if(stop)return;
								if(z1<z2){
									synchronized(po_ec){
										po_ec.add(540+u/jd);po_ec.add(960-i/jd);
									}
								}
							}
						}
						count++;
						if(drawhs&&count%100==0){pos.setElementAt(tool.tofloatarray(po_ec),index_ec);Draw(false);}
					}}
					break;
			}
			if(drawhs){
				pos.setElementAt(tool.tofloatarray(po_ec),index_ec);
				Draw(false);
				drawnew = true;
				Draw(false);
			}
			//Log.v("++++",(System.currentTimeMillis()-st)+"");
		}
	}
}
