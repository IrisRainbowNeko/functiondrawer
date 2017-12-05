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

public class jsqec extends Activity implements OnClickListener,OnLongClickListener
{
	EditText t1;
	Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,tg,xs;
	ListView lv;
	GridView gd,gd1;
	int which;
	//ArrayList<String> ar=new ArrayList<String>();
	SimpleAdapter mSA1,mSA2,mSA3;
	ArrayList<HashMap<String, Object>> listItem2,listItem1,listItem3;
	View vii;
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.jsqec);
		which=getIntent().getExtras().get("which");
		listItem1 = new ArrayList<HashMap<String,Object>>();
		listItem2 = new ArrayList<HashMap<String,Object>>();
		listItem3 = new ArrayList<HashMap<String,Object>>();
		lv=(ListView)findViewById(R.id.jsqecListView1);
		gd=(GridView)findViewById(R.id.jsqecGridView1);
		gd1=(GridView)findViewById(R.id.jsqecGridView2);
		b0=(Button)findViewById(R.id.jsqecButton00);
		b1=(Button)findViewById(R.id.jsqecButton01);
		b2=(Button)findViewById(R.id.jsqecButton02);
		b3=(Button)findViewById(R.id.jsqecButton03);
		b4=(Button)findViewById(R.id.jsqecButton04);
		b5=(Button)findViewById(R.id.jsqecButton05);
		b6=(Button)findViewById(R.id.jsqecButton06);
		b7=(Button)findViewById(R.id.jsqecButton07);
		b8=(Button)findViewById(R.id.jsqecButton08);
		b9=(Button)findViewById(R.id.jsqecButton09);
		tg=(Button)findViewById(R.id.jsqecButtontg);
		xs=(Button)findViewById(R.id.jecxianshi);
		b0.setOnClickListener(this);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		b4.setOnClickListener(this);
		b5.setOnClickListener(this);
		b6.setOnClickListener(this);
		b7.setOnClickListener(this);
		b8.setOnClickListener(this);
		b9.setOnClickListener(this);
		tg.setOnClickListener(this);
		tg.setOnLongClickListener(this);
		xs.setOnClickListener(this);
		gd1.setVisibility(View.GONE);
		final String[] bs="x y = > < + - * / ^ √ π e . , ( ) a b c m n".split(" ");
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
					insertText(bs[arg2],t1.getSelectionStart());
				}
			});
		gd.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
					insertText(as[arg2]+"()",t1.getSelectionStart());
					Selection.setSelection(t1.getText(),t1.getSelectionStart()-1);
				}
			});
		gd1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
					if(arg2==26)
					{gd1.setVisibility(View.GONE);xs.setVisibility(View.VISIBLE);}
					else
						insertText(zimu[arg2],t1.getSelectionStart());
				}
			});
		TextView tx=(TextView)findViewById(R.id.titleTextView1);
		tx.setText("二次曲线");
		String ss=gra.suan.get(which);
		TextView fx=(TextView)findViewById(R.id.titleTextView2);
		fx.setVisibility(View.GONE);
		t1=(EditText)findViewById(R.id.jsqecEditText1);
		t1.setText(ss);
		vii=findViewById(R.id.titleView1);
		vii.setBackgroundColor(gra.col.get(which));
		disableShowSoftInput();
    }
	public void disableShowSoftInput()
	{
		if (android.os.Build.VERSION.SDK_INT <= 10) 
		{
			t1.setInputType(InputType.TYPE_NULL);  
        } 
		else {  
			Class<EditText> cls = EditText.class;  
			Method method;
			try { 
				method = cls.getMethod("setShowSoftInputOnFocus",boolean.class);  
				method.setAccessible(true);  
				method.invoke(t1, false);  
			}catch (Exception e){}
			try { 
				method = cls.getMethod("setSoftInputShownOnFocus",boolean.class);  
				method.setAccessible(true);  
				method.invoke(t1, false);  
			}catch (Exception e) {}
        } 
	}
	@Override
	public void onClick(View v)
	{
		int wz=t1.getSelectionStart();
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
		if(v==tg)
		{t1.setText("");}
		return true;
	}
	private void insertText(String mText,int wz){
		t1.getText().insert(wz,mText); 
	}
	private void deleteText(int wz){
		if(wz>0){
			String text=t1.getText().toString();
			if(!MainActivity.deleteAI||text.charAt(wz-1)!=')')
				t1.getText().delete(wz-1,wz);
			else{
				int ind=0;
				for(int i=wz-1;i>=0;i--){
					if(ind!=0||i==wz-1){
						if(text.charAt(i)==')')ind++;
						else if(text.charAt(i)=='(')ind--;
					}else{
						char a=text.charAt(i);
						if((a>='0'&&a<='9')||"=+-*/^√.,()".indexOf(""+a)!=-1){
							t1.getText().delete(i+1,wz);
							break;
						}
						if(i==0)t1.getText().delete(i,wz);
					}
				}
				if(ind>0)t1.getText().delete(wz-1,wz);
			}
		}
	}
	public void onjsqecyes(View view)
	{
		String ss=t1.getText().toString();
		int o=0;
		String[] ssarr=ss.split("=|<|>");
		if(ssarr.length==1){Toast.makeText(this,"错误!没有比较运算符",2500).show();return;}
        if(ssarr.length>2){Toast.makeText(this,"错误!比较运算符不能有多个",2500).show();return;}
		
		try {
			String bds=gra.ex.doTrans(ssarr[0]);
			String bdss=gra.ex.doTrans(ssarr[1]);
			gra.ex.setexpress(new String[]{bds,bdss},Express.arr2buff(new double[2+Constant.PARA_strarr.length]),tool.arrayadd(new String[]{"x","y"},Constant.PARA_strarr));
			gra.ex.calculate0check(0);
			gra.ex.calculate0check(1);
			gra.flag = true;
			gra.suan.setElementAt(ss,which);
			gra.suanshi.setElementAt("2;"+bds+";"+bdss+";"+ss.charAt(ssarr[0].length()),which);
			int color=((ColorDrawable)vii.getBackground()).getColor();
			gra.col.setElementAt(color,which);
			gra.lal.SetText(ss,which);
			gra.lal.SetColor(color,which);finish();
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
			if(gra.suan.get(which).equals(""))
			{
				gra.suan.removeElementAt(which);
				gra.suanshi.removeElementAt(which);
				gra.col.removeElementAt(which);
				gra.pos.removeElementAt(gra.pos.size()-1);
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
