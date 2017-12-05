package com.graph.myapp;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.view.ViewGroup.*;
import android.widget.*;
import android.widget.AdapterView.*;
import android.widget.SimpleAdapter.*;
import com.graph.myapp.widget.*;
import java.io.*;
import java.util.*;

import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.support.v7.widget.*;

public class MainActivity extends Activity
{
	public static float fblx,fbly;
	public static SharedPreferences fs;
	public static MainActivity ma;
	public static AssetManager am;
	public static int color_default=0xFF00FF00;
	public static boolean deleteAI;
	gra gr;
    String wjm = "";
	AlertDialog al;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		ma=this;
		am=getAssets();
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		WindowManager windowManager = getWindowManager();  
        Display display = windowManager.getDefaultDisplay();  
        fblx = display.getWidth();  
      	fbly = display.getHeight();
		fs=this.getSharedPreferences("diyi",Context.MODE_PRIVATE);
		if(!fs.getBoolean("first_run",false))
		{
			sm.first=true;
			sm();
			tool.copydata("function");
			fs.edit().putBoolean("first_run",true).commit();
		}
		setContentView(R.layout.main);
		if(!fs.getBoolean("qqtx",false))showQQdialog();
		try{
			tool.copyBigDataToSD(Constant.FILE_SD + "/skm.png", "skm.png");
		}catch (IOException e){}
		File f=new File(Constant.FILE_SD_THIS);
		if(!f.exists())f.mkdir();
		gr = (gra) findViewById(R.id.maingra1);
		gr.jd_x=1+fs.getInt("jd",2);
		gr.paint.setAntiAlias(fs.getBoolean("AntiAlias",false));
		gra.htxc=1+fs.getInt("htxc",1);
		gr.jd_ec=1+fs.getInt("jd_ec",2);
		gra.para_lim[0]=fs.getFloat("lim_min",-50);
		gra.para_lim[1]=fs.getFloat("lim_max",50);
		gr.sk.setmax((int)(100*(gra.para_lim[1]-gra.para_lim[0])));
		gra.qly=fs.getBoolean("CurvatureCircle",false);
		gra.moverate=MainActivity.fs.getInt("gx",20);
		gra.gridopen=MainActivity.fs.getBoolean("gridopen",false);
		color_default=fs.getInt("defualt_color",0xff00ff00);
		deleteAI=fs.getBoolean("deleteAI",false);
		init();
    }
	public Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			Toast.makeText(MainActivity.this,msg.obj.toString(),Toast.LENGTH_LONG).show();
		}
	};
	public void init(){
		CardView button = (CardView)findViewById(R.id.mainCardView1);
        button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					xz();
				}
			});
	}
	public void ongl(View v)
	{
		gr.px1=gr.px=0;
		gr.py1=gr.py=0;
		gr.tx=1;
		gr.jd=0.01f;
		gr.flag=true;
	}
	public void on1(View view)
	{
		ImageButton bu=(ImageButton)this.findViewById(R.id.mainImageButton1);
		showpop(bu);
	}
	public void showpop(View v)
	{
		View contentView = LayoutInflater.from(this).inflate(R.layout.pop, null);
		
        final PopupWindow popupWindow = new PopupWindow(contentView,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					return false;
					// 这里如果返回true的话，touch事件将被拦截
					// 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
				}
			});

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		 // 设置按钮的点击事件
		ImageButton button1 = (ImageButton) contentView.findViewById(R.id.pop_sm);
        button1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					sm();
					popupWindow.dismiss();
				}
			});
		ImageButton button2 = (ImageButton) contentView.findViewById(R.id.pop_sz);
        button2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(MainActivity.this,setting.class));
					popupWindow.dismiss();
				}
			});
		ImageButton button3 = (ImageButton) contentView.findViewById(R.id.pop_exit);
        button3.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					System.exit(0);
					popupWindow.dismiss();
				}
			});
		ImageButton button4 = (ImageButton) contentView.findViewById(R.id.pop_other);
        button4.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					shezhi();
					popupWindow.dismiss();
				}
			});
		ImageButton button5 = (ImageButton) contentView.findViewById(R.id.pop_help);
        button5.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showPaydialog();
					popupWindow.dismiss();
				}
			});
		// 设置好参数之后再show
        popupWindow.showAsDropDown(v);
		
	}
	public void showPaydialog(){
		LinearLayout ln=new LinearLayout(this);
		ln.setOrientation(LinearLayout.VERTICAL);
		TextView tv=new TextView(this);
		tv.setText("如果觉得软件不错的话，就来资助作者一块钱吧，扫码可以微信支付");
		tv.setTextSize(sp2px(7));
		ImageView im=new ImageView(this);
		try
		{
			im.setImageBitmap(BitmapFactory.decodeStream(am.open("skm.png")));
		}
		catch (IOException e)
		{}
		ln.addView(tv);
		ln.addView(im);
		new AlertDialog.Builder(this).setTitle("资助作者").
			setIcon(R.drawable.ic_launcher).
			setView(ln).setPositiveButton("微信资助",new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					if(isWeixinAvilible(MainActivity.this))
						toWeChatScanDirect(MainActivity.this);
				}
			})
			.setNeutralButton("支付宝资助", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					if(iszfbAvilible(MainActivity.this)){
						String donateUri="alipayqr://platformapi/startapp?saId=10000007&qrcode=HTTPS://QR.ALIPAY.COM/FKX025978LGOXC16WOYI1A";
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(donateUri)));
					}else{
						Toast.makeText(MainActivity.this,"支付宝没找到诶",2000).show();
					}
				}
			})
			.setNegativeButton("取消", null).show();
	}
	public void showQQdialog(){
		new AlertDialog.Builder(this).setTitle("加入讨论群").
			setIcon(R.drawable.ic_launcher).
			setMessage("欢迎大家加入讨论群，群号码:623046839").setPositiveButton("去加群",new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					if(joinQQGroup("Icz-vKHoWauKFigc_7AK4Z4vIgofDnO6"))
						fs.edit().putBoolean("qqtx",true).commit();
					else
						Toast.makeText(MainActivity.this,"QQ没找到诶",2000).show();
				}
			})
			.setNeutralButton("取消",null)
			.setNegativeButton("不再提醒",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					fs.edit().putBoolean("qqtx",true).commit();
				}
			}).show();
	}
	public boolean joinQQGroup(String key) {
		Intent intent = new Intent();
		intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
		// 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		try {
			startActivity(intent);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public static boolean isWeixinAvilible(Context context) {    
		final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息    
		if (pinfo != null) {        
			for (int i = 0; i < pinfo.size(); i++) {            
				String pn = pinfo.get(i).packageName;           
				if (pn.equals("com.tencent.mm")) {                
					return true;            
				}        
			}    
		}    
		return false;
	}
	public static boolean iszfbAvilible(Context context) {    
		final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息    
		if (pinfo != null) {        
			for (int i = 0; i < pinfo.size(); i++) {            
				String pn = pinfo.get(i).packageName;           
				if (pn.equals("com.eg.android.AlipayGphone")) {                
					return true;            
				}        
			}    
		}    
		return false;
	}
	public void startweixin(){
		Intent intent = new Intent();
		ComponentName cmp=new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setComponent(cmp);
		startActivity(intent);
	}
	public static void toWeChatScanDirect(Context context) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI"));
            intent.putExtra("LauncherUI.From.Scaner.Shortcut", true);
            intent.setFlags(335544320);
            intent.setAction("android.intent.action.VIEW");
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }
	public void shezhi()
	{
		String a="";
		if(gra.isck)a="移动模式";
		else a="查看模式";
		new AlertDialog.Builder(this).setTitle("其他选项").
			setIcon(R.drawable.ic_launcher).
			setItems(new String[]{"平移到指定位置","保存函数","读取函数","复制函数",a,"加入讨论群"}, new DialogInterface.OnClickListener() { 
				@Override 
				public void onClick(DialogInterface dialog, int which) {
					switch(which){
						case 0:setpos();break;
						case 1:savehs();break;
						case 2:readhs_di();break;
						case 3:copyhs();break;
						case 4:gra.isck=!gra.isck;break;
						case 5:if(!joinQQGroup("Icz-vKHoWauKFigc_7AK4Z4vIgofDnO6"))
								Toast.makeText(MainActivity.this,"QQ没找到诶",2000).show();
								break;
					}
				} 
			})
			.setNegativeButton("取消", null).show();
	}
	public void setpos()
	{
		final EditText edi=new EditText(this);
		edi.setHint("x,y");
		new AlertDialog.Builder(this).setTitle("平移").
			setIcon(R.drawable.ic_launcher).setView(edi)
			.setPositiveButton("确定",new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					try{
					String[] a=edi.getText().toString().split(",|，");
					gr.px1=gr.px=-(int)(Float.parseFloat(a[0])/gr.jd);
					gr.py1=gr.py=(int)(Float.parseFloat(a[1])/gr.jd);
					gr.flag=true;
					}catch(Exception e){
						Toast.makeText(MainActivity.this,"输入错误",2500).show();
					}
				} 
			})
			.setNegativeButton("取消", null).show();
	}
	public void sm()
	{
		startActivity(new Intent(MainActivity.this,sm.class));
	}
	public void xz()
	{
		ListView lv=new ListView(this);
		ArrayList<HashMap> li=new ArrayList<HashMap>();
		for (int i=0;i<gra.col.size();i++) {
            HashMap<String,Object> hashMap=new HashMap<String,Object>();
            hashMap.put("suan",gra.suan.get(i));
            hashMap.put("col",gra.col.get(i));
            li.add(hashMap);
        }
		SimpleAdapter sma=new SimpleAdapter(this,li,R.layout.xz,new String[]{"suan","col"},new int[]{R.id.xzTextView1,R.id.xzView1});
		sma.setViewBinder(vb);
		lv.setAdapter(sma);
		lv.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
					Intent intent=null;
					//Toast.makeText(MainActivity.this,(gra.suanshi.get(i).charAt(0)-'0')+"",2000).show();
					if ((gra.suanshi.get(i).charAt(0)-'0')==Constant.FUNCTION_NORMAL) {
						intent=new Intent(MainActivity.this, jsq.class);
					}else
					if ((gra.suanshi.get(i).charAt(0)-'0')==Constant.FUNCTION_PARAMETER) {
						intent=new Intent(MainActivity.this, jsqcs.class);
					}else
					if ((gra.suanshi.get(i).charAt(0)-'0')==Constant.FUNCTION_CURVE) {
						intent=new Intent(MainActivity.this, jsqec.class);
					}else
					if ((gra.suanshi.get(i).charAt(0)-'0')==Constant.FUNCTION_POLAR) {
						intent=new Intent(MainActivity.this, jsqjzb.class);
					}
					intent.putExtra("which",i);
					startActivity(intent);
					al.dismiss();
				}
			});
		lv.setOnItemLongClickListener(new OnItemLongClickListener(){

				@Override
				public boolean onItemLongClick(AdapterView adapterView, View view, int i, long j) {
					sc(i);
					al.dismiss();
					return true;
				}	
		});
		al=new AlertDialog.Builder(this).setTitle("选择函数").
			setIcon(R.drawable.ic_launcher).setView(lv)
			.setPositiveButton("一般函数",new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					gra.suan.add("");
					gra.suanshi.add("0");
					gra.path.add(new Path());
					gra.col.add(color_default);
					gr.lal.add(new Lable());
					gra.parameter.add(tool.getparafloat());
					Intent intent = new Intent(MainActivity.this,jsq.class);
					intent.putExtra("which", gra.suan.size() - 1);
					startActivity(intent);
				}})
			.setNegativeButton("其他函数",new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					otherhs();
				}}).show();
	}
	public void otherhs(){
		new AlertDialog.Builder(this).setTitle("添加其他函数").
			setIcon(R.drawable.ic_launcher).
			setItems(new String[]{"参数方程","二次曲线","极坐标方程"},new DialogInterface.OnClickListener() { 
				@Override 
				public void onClick(DialogInterface dialog, int which) {
					switch(which){
						case 0:{
							gra.suan.add("");
							gra.suanshi.add("1");
							gra.path.add(new Path());
								gra.col.add(color_default);
							gr.lal.add(new Lable());
							gra.parameter.add(tool.getparafloat());
							Intent intent = new Intent(MainActivity.this,jsqcs.class);
							intent.putExtra("which", gra.suan.size() - 1);
							startActivity(intent);}
							break;
						case 1:{
							gra.suan.add("");
							gra.suanshi.add("2");
								gra.col.add(color_default);
							gra.pos.add(new float[0]);
							gra.path.add(new Path());
							gr.lal.add(new Lable());
							gra.parameter.add(tool.getparafloat());
							Intent intent = new Intent(MainActivity.this,jsqec.class);
							intent.putExtra("which", gra.suan.size() - 1);
							startActivity(intent);}
							break;
						case 2:{
							gra.suan.add("");
							gra.suanshi.add("3");
							gra.path.add(new Path());
							gra.col.add(color_default);
							gr.lal.add(new Lable());
							gra.parameter.add(tool.getparafloat());
							Intent intent = new Intent(MainActivity.this,jsqjzb.class);
							intent.putExtra("which", gra.suan.size() - 1);
							startActivity(intent);}
						break;
					}
				}
			})
			.setNegativeButton("取消", null).show();
	}
	public void sc(final int a)
	{
		new AlertDialog.Builder(this).setTitle("删除函数").
			setIcon(R.drawable.ic_launcher).setMessage("确定要删除吗？")
			.setPositiveButton("确定",new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					if(gra.suanshi.get(a).charAt(0)-'0'==Constant.FUNCTION_CURVE)
					{
						int cou=0;
						for(int i=0;i<a;i++)
						{if(gra.suanshi.get(i).charAt(0)-'0'==Constant.FUNCTION_CURVE)cou++;}
						for(int i=0;i<gr.ee.length;i++)gr.ee[i].stop=true;
						gra.pos.remove(cou);
						gra.ind_ec.remove(cou);
					}
					if(gr.para_index[0]==a){
						gr.para_index[0]=-1;
						gr.lal.rest();
					}
					gra.suan.remove(a);
					gra.suanshi.remove(a);
					gra.path.remove(a);
					gra.col.remove(a);
					gr.lal.remove(a);
					gra.parameter.remove(a);
					gr.Draw(false);
				}
			})
			.setNegativeButton("取消", null).show();
			
	}
	public void copyhs(){
		if(gra.suan.size()==0){
			Toast.makeText(this,"没有可复制的函数",2500).show();
			return;
		}
		final String[] a=gra.suan.toArray(new String[1]);
		final boolean[] b=new boolean[a.length];
		new AlertDialog.Builder(this).setTitle("复制函数")
			.setMultiChoiceItems(a, null, new DialogInterface.OnMultiChoiceClickListener(){
				@Override
				public void onClick(DialogInterface p1, int which, boolean p3)
				{
					b[which]=p3;
				}
			})
			.setPositiveButton("确定",new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					for(int i=0;i<a.length;i++)
					{
						if(b[i]){
							char ch=gra.suanshi.get(i).charAt(0);
							switch(ch-'0'){
								case 0:case 1:case 3:{
									gra.suan.add(gra.suan.get(i));
									gra.suanshi.add(gra.suanshi.get(i));
									gra.path.add(gra.path.get(i));
									gra.col.add(gra.col.get(i));
									gr.lal.add(new Lable(gr.lal.ve.get(i)));
									gra.parameter.add(gra.parameter.get(i));
									gra.flag=true;
									break;
								}
								case 2:{
									gra.suan.add(gra.suan.get(i));
									gra.suanshi.add(gra.suanshi.get(i));
									gra.path.add(gra.path.get(i));
									gra.col.add(gra.col.get(i));
									gr.lal.add(new Lable(gr.lal.ve.get(i)));
									gra.pos.add(gra.pos.get(i));
									gra.parameter.add(gra.parameter.get(i));
									gra.flag=true;
									break;
								}
							}
						}
					}
				} 
			})
			.setNegativeButton("取消", null).show();
	}
	public void savehs() {
        final EditText editText = new EditText(this);
        editText.setHint("输入文件名");
        editText.setText(this.wjm);
        new AlertDialog.Builder(this).setTitle("保存函数").
			setIcon(R.drawable.ic_launcher).setView(editText)
			.setPositiveButton("确定",new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					if(editText.length()>0){
						wjm=editText.getText().toString();
						String a="";
						a+=(gr.lal.px+";"+gr.lal.py+""+(char)13);
						for (int i=0;i<gra.suan.size();i++) {
							a+=(gra.suan.get(i)+" "+gra.col.get(i)+" "+gra.suanshi.get(i)+(char)13);
						}
						if(!wjm.endsWith(".fuc"))
						{
							wjm+=".fuc";
						}
						tool.shuchu(a.substring(0, a.length() - 1),wjm);
					}
				} 
			}).setNegativeButton("取消", null).show();
		}
	public void readhs_di()
	{
		File fi=new File(Constant.FILE_SD_THIS);
		final String[] a=fi.list();
		new AlertDialog.Builder(this).setTitle("读取函数").
			setIcon(R.drawable.ic_launcher).
			setItems(a,new DialogInterface.OnClickListener() { 
				@Override 
				public void onClick(DialogInterface dialog, int which) {
					readhs(Constant.FILE_SD_THIS+a[which]);
					wjm=a[which];
					gra.flag=true;
				}
			})
			.setPositiveButton("删除",new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					deletehs(a);
				} 
			})
			.setNegativeButton("取消", null).show();
	}
	public void readhs(String file)
	{
		if(gr.ee!=null)
		for(int i=0;i<gr.ee.length;i++){
			gr.ee[i].stop=true;
		}
		gra.suan.removeAllElements();
		gra.suanshi.removeAllElements();
		gra.col.removeAllElements();
		gra.path.removeAllElements();
		gra.pos.removeAllElements();
		gra.lal.ve.removeAllElements();
		gra.parameter.removeAllElements();
		gr.para_index[0]=-1;
		String[] b=tool.readFileSdcard(file).split(""+(char)13);
		float[] fb=tool.str2float_arr(b[0].split(";"));
		gr.lal.px=fb[0];
		gr.lal.py=fb[1];
		for(int i=1;i<b.length;i++)
		{
			String[] b1=b[i].split(" ");
			gra.suan.add(b1[0]);
			gra.col.add(Integer.parseInt(b1[1]));
			gra.suanshi.add(b1[2]);
			gra.path.add(new Path());
			if(b1[2].charAt(0)-'0'==Constant.FUNCTION_CURVE)
				gra.pos.add(new float[0]);
			gra.lal.add(new Lable());
			gra.lal.SetText(b1[0],i-1);
			gra.lal.SetColor(Integer.parseInt(b1[1]),i-1);
			gra.parameter.add(tool.getparafloat());
		}
	}
	public void deletehs(final String[] a)
	{
		final boolean[] b=new boolean[a.length];
		new AlertDialog.Builder(this).setTitle("删除函数")
			.setMultiChoiceItems(a, null, new DialogInterface.OnMultiChoiceClickListener(){
				@Override
				public void onClick(DialogInterface p1, int which, boolean p3)
				{
					b[which]=p3;
				}
			})
			.setPositiveButton("确定",new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					int u=0;
					for(int i=0;i<a.length;i++)
					{
						if(b[i]){
							new File(Constant.FILE_SD_THIS+a[i]).delete();
							u++;
						}
					}
					handler.obtainMessage(2,u+"个函数已删除").sendToTarget();
				} 
			})
			.setNegativeButton("取消", null).show();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO: Implement this method
		if(keyCode==KeyEvent.KEYCODE_MENU){shezhi();return true;}
		else if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			new AlertDialog.Builder(this).setTitle("退出").
				setIcon(R.drawable.ic_launcher).
				setMessage("确定要退出吗")
				.setPositiveButton("确定",new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						System.exit(0);
					} 
				})
				.setNegativeButton("取消", null).show();
			fs.edit().putBoolean("yi",true).commit();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	ViewBinder vb=new ViewBinder(){
		@Override
		public boolean setViewValue(View view,Object obj,String str) {
			if (!(obj instanceof Integer)) {
				return false;
			}
			view.setBackgroundColor((int)obj);
			return true;
		}
	};
	public int sp2px(float spValue) {
		final float fontScale = getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}
}
