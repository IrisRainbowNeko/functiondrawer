package com.graph.myapp;

import android.app.*;
import android.content.*;
import android.graphics.drawable.*;
import android.os.*;
import android.text.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.AdapterView.*;
import com.graph.myapp.widget.*;
import java.lang.reflect.*;
import java.util.*;

import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import com.bdsjx.tool.*;

public class jsqjzb extends Activity implements OnClickListener,OnLongClickListener
{
	EditText t1,t2,t3,et;
	Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,tg,xs;
	ListView lv;
	GridView gd,gd1;
	int which;
	//ArrayList<String> ar=new ArrayList<String>();
	SimpleAdapter mSA1,mSA2,mSA3;
	ArrayList<HashMap<String, Object>> listItem2,listItem1,listItem3;
	View vii;
	String prefix="";
	TextView fx;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.jsqjzb);
		which=getIntent().getExtras().get("which");
		listItem1 = new ArrayList<HashMap<String,Object>>();
		listItem2 = new ArrayList<HashMap<String,Object>>();
		listItem3 = new ArrayList<HashMap<String,Object>>();
		this.lv = (ListView) findViewById(R.id.jsqjzbListView1);
        this.gd = (GridView) findViewById(R.id.jsqjzbGridView1);
        this.gd1 = (GridView) findViewById(R.id.jsqjzbGridView2);
        this.b0 = (Button) findViewById(R.id.jsqjzbButton00);
        this.b1 = (Button) findViewById(R.id.jsqjzbButton01);
        this.b2 = (Button) findViewById(R.id.jsqjzbButton02);
        this.b3 = (Button) findViewById(R.id.jsqjzbButton03);
        this.b4 = (Button) findViewById(R.id.jsqjzbButton04);
        this.b5 = (Button) findViewById(R.id.jsqjzbButton05);
        this.b6 = (Button) findViewById(R.id.jsqjzbButton06);
        this.b7 = (Button) findViewById(R.id.jsqjzbButton07);
        this.b8 = (Button) findViewById(R.id.jsqjzbButton08);
        this.b9 = (Button) findViewById(R.id.jsqjzbButton09);
        this.tg = (Button) findViewById(R.id.jsqjzbButtontg);
        this.xs = (Button) findViewById(R.id.jzbxianshi);
        this.vii = findViewById(R.id.titleView1);
        this.b0.setOnClickListener(this);
        this.b1.setOnClickListener(this);
        this.b2.setOnClickListener(this);
        this.b3.setOnClickListener(this);
        this.b4.setOnClickListener(this);
        this.b5.setOnClickListener(this);
        this.b6.setOnClickListener(this);
        this.b7.setOnClickListener(this);
        this.b8.setOnClickListener(this);
        this.b9.setOnClickListener(this);
        this.tg.setOnClickListener(this);
        this.tg.setOnLongClickListener(this);
        this.xs.setOnClickListener(this);
		gd1.setVisibility(View.GONE);
		final String[] bs="θ + - * / ^ √ π e . , ( ) a b c m n".split(" ");
		for(int i=0;i<bs.length;i++)
		{HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("shuzi",bs[i]);
			listItem1.add(map);}
		final String[] as=Constant.FUNCTION_NAME;
		for(int i=0;i<as.length;i++)
		{HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("hs",as[i]);
			listItem2.add(map);}
		final String[] zimu="q w e r t y u i o p a s d f g h j k l z x c v b n m 关".split(" ");
		for(String i:zimu)
		{HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("zimu",i);
			listItem3.add(map);}
		mSA1=new SimpleAdapter(this,listItem1,R.layout.lvi,
							   new String[] {"shuzi"},new int[]{R.id.lviTextView1});
		mSA2=new SimpleAdapter(this,listItem2,R.layout.lvv,
							   new String[] {"hs"},new int[]{R.id.lvvTextView1});
		mSA3=new SimpleAdapter(this,listItem3,R.layout.jp,
							   new String[] {"zimu"},new int[]{R.id.jpTextView1});
		lv.setAdapter(mSA1);
		gd.setAdapter(mSA2);
		gd1.setAdapter(mSA3);
		lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
					getEditText();
					insertText(bs[arg2],et.getSelectionStart());
				}
			});
		gd.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
					getEditText();
					insertText(as[arg2]+"()",et.getSelectionStart());
					Selection.setSelection(et.getText(),et.getSelectionStart()-1);
				}
			});
		gd1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
					getEditText();
					if(arg2==26)
					{gd1.setVisibility(View.GONE);xs.setVisibility(View.VISIBLE);}
					else
						insertText(zimu[arg2],et.getSelectionStart());
				}
			});
		TextView tx=(TextView)findViewById(R.id.titleTextView1);
		tx.setText("极坐标方程");
		String ss=gra.suan.get(which);
		fx=(TextView)findViewById(R.id.titleTextView2);
		resettype();
		if(ss.length()!=0){
			prefix=""+ss.charAt(0);
			if(resettype())ss=ss.substring(1);
			else prefix="";
		}
		fx.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					prefix=Constant.PREFIXES[(1+tool.indexof_arr(Constant.PREFIXES,prefix))%Constant.PREFIXES.length];
					resettype();
				}
			});
		vii.setBackgroundColor(gra.col.get(which));
		t1 = (EditText) findViewById(R.id.jsqjzbEditText1);
        t2 = (EditText) findViewById(R.id.jsqjzbEditText2);
        t3 = (EditText) findViewById(R.id.jsqjzbEditText3);
        String[] split=gra.suan.get(which).split(";");
		if(split.length==3){
        t1.setText(split[0].substring(2+prefix.length()));
        t2.setText(split[1]);
        t3.setText(split[2]);}
		disableShowSoftInput();
		et=t1;
    }
	public boolean resettype(){
		switch(prefix){
			case "'":
				fx.setText("f'(x)");
				break;
			case "∫":
				fx.setText("∫f(x)");
				break;
			default:
				fx.setText("f(x)");
				return false;
		}
		return true;
	}
	public void disableShowSoftInput()
	{
		if (android.os.Build.VERSION.SDK_INT <= 10) 
		{
			t1.setInputType(InputType.TYPE_NULL);
			t2.setInputType(InputType.TYPE_NULL);
			t3.setInputType(InputType.TYPE_NULL);
        } 
		else {  
			Class<EditText> cls = EditText.class;  
			Method method;
			try { 
				method = cls.getMethod("setShowSoftInputOnFocus",boolean.class);  
				method.setAccessible(true);  
				method.invoke(t2, false);  
				method.invoke(t3, false);  
				method.invoke(t1, false);
			}catch (Exception e){}
			try { 
				method = cls.getMethod("setSoftInputShownOnFocus",boolean.class);  
				method.setAccessible(true);    
				method.invoke(t2, false);  
				method.invoke(t3, false);  
				method.invoke(t1, false);
			}catch (Exception e) {}
        } 
	}
	@Override
	public void onClick(View v)
	{
		getEditText();
		int wz=et.getSelectionStart();
		if(v==b0)
		{insertText("0",wz);}
		else if(v==b1)
		{insertText("1",wz);}
		else if(v==b2)
		{insertText("2",wz);}
		else if(v==b3)
		{insertText("3",wz);}
		else if(v==b4)
		{insertText("4",wz);}
		else if(v==b5)
		{insertText("5",wz);}
		else if(v==b6)
		{insertText("6",wz);}
		else if(v==b7)
		{insertText("7",wz);}
		else if(v==b8)
		{insertText("8",wz);}
		else if(v==b9)
		{insertText("9",wz);}
		else if(v==tg)
		{deleteText(wz);}
		else if(v==xs)
		{gd1.setVisibility(View.VISIBLE);xs.setVisibility(View.GONE);}
		// TODO: Implement this method
	}
	public void onys(View view) {
        final ColorPicker cp=new ColorPicker(this);
		cp.setcolor(gra.col.get(which));
		new AlertDialog.Builder(this).setTitle("选择颜色").
			setIcon(R.drawable.ic_launcher).setView(cp)
			.setPositiveButton("确定",new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					vii.setBackgroundColor(cp.getcolor());
				} 
			})
			.setNegativeButton("取消", null).show();
    }
	@Override
	public boolean onLongClick(View v)
	{
		getEditText();
		if(v==tg)
		{et.setText("");}
		return true;
	}
	private void getEditText()
	{
		if(t1.isFocused()){et=t1;}
		else if (t2.isFocused()){et=t2;}
		else if (t3.isFocused()){et=t3;}
	}
	private void insertText(String mText,int wz){
		et.getText().insert(wz,mText); 
	}
	private void deleteText(int wz){
		if(wz>0){
			String text=et.getText().toString();
			if(!MainActivity.deleteAI||text.charAt(wz-1)!=')')
				et.getText().delete(wz-1,wz);
			else{
				int ind=0;
				for(int i=wz-1;i>=0;i--){
					if(ind!=0||i==wz-1){
						if(text.charAt(i)==')')ind++;
						else if(text.charAt(i)=='(')ind--;
					}else{
						char a=text.charAt(i);
						if((a>='0'&&a<='9')||"+-*/^√.,()".indexOf(""+a)!=-1){
							et.getText().delete(i+1,wz);
							break;
						}
						if(i==0)et.getText().delete(i,wz);
					}
				}
				if(ind>0)et.getText().delete(wz-1,wz);
			}
		}
	}
	public void onjzbyes(View view)
	{
		String s1=t1.getText().toString();
		String s2=t2.getText().toString();
		String s3=t3.getText().toString();
		gra.suan.setElementAt(prefix+"ρ="+s1+";"+s2+";"+s3,which);
        try {
			s1=s1.replaceAll("θ","t");
			String sx="("+s1+")*cos(t)",sy="("+s1+")*sin(t)";
            sx=gra.ex.doTrans(sx);
			sy=gra.ex.doTrans(sy);
			gra.ex.setexpress(new String[]{sx,sy},Express.arr2buff(new double[1+Constant.PARA_strarr.length]),tool.arrayadd(new String[]{"t"},Constant.PARA_strarr));
			gra.ex.calculate0check(0);
			gra.ex.calculate0check(1);
			String[] st3=s3.split(",");
			st3[0]=gra.ex.doTrans(st3[0]);
			st3[1]=gra.ex.doTrans(st3[1]);
			gra.ex.setexpress(st3,Express.arr2buff(new double[Constant.PARA_strarr.length]),Constant.PARA_strarr);
			gra.ex.calculate0check(0);
			gra.ex.calculate0check(1);
			s3=st3[0]+","+st3[1];
            gra.suanshi.setElementAt("3;"+sx+";"+sy+";"+s2+";"+s3,which);
			gra.lal.SetText(gra.suan.get(which),which);
			gra.flag = true;
			int color=((ColorDrawable)vii.getBackground()).getColor();
			gra.col.setElementAt(color,which);
			gra.lal.SetColor(color,which);
			finish();
        }
		catch(StackOverflowError soe){Toast.makeText(this,"表达式解析错误!",1500).show();}
		catch (Exception e) {
			Toast.makeText(this,"表达式解析错误!",1500).show();
        }
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			if(gra.suanshi.get(which).split(";").length!=5)
			{
				gra.suan.removeElementAt(which);
				gra.suanshi.removeElementAt(which);
				gra.col.removeElementAt(which);
				gra.path.removeElementAt(which);
				gra.lal.remove(which);
				gra.parameter.removeElementAt(which);
			}
			finish();
			return true;
		}
		return false;
	}
}
