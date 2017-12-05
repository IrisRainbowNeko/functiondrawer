package com.graph.myapp.widget;
import android.content.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.text.*;
import android.graphics.*;
import com.graph.myapp.*;

public class ColorPicker extends LinearLayout
{
	ColorPickerView cpv;
	EditText coloredit;
	boolean onchange;
	View vi;
	public ColorPicker(Context c){
		super(c);
		setOrientation(LinearLayout.VERTICAL);
		setGravity(Gravity.CENTER);
		coloredit=new EditText(c);
		cpv=new ColorPickerView(c);
		LayoutParams layoutParams = new LayoutParams((int)(740*(MainActivity.fblx/1080f)),(int)(1020*(MainActivity.fbly/1920f)));
		int mdp=dp2px(5);
		layoutParams.setMargins(mdp,mdp,mdp,mdp);
		cpv.setLayoutParams(layoutParams);
		cpv.setColorChangedListener(new ColorPickerView.ColorChangedListener(){

				@Override
				public void startchange()
				{
					onchange=true;
				}

				@Override
				public void stopchange()
				{
					onchange=false;
				}

				@Override
				public void oncolorchange(int color)
				{
					setcolor(color);
					vi.setBackgroundColor(color);
				}
			});
		addView(cpv);
		addcoloredit(c);
	}
	public void addcoloredit(Context c){
		LinearLayout ln=new LinearLayout(c);
		ln.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		ln.setGravity(Gravity.CENTER);
		vi=new View(c);
		LayoutParams layoutParams = new LayoutParams(dp2px(30),dp2px(30));
		layoutParams.setMargins(0,0,dp2px(10),0);
		vi.setLayoutParams(layoutParams);
		ln.addView(vi);
		coloredit.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		coloredit.setEms(9);
		coloredit.addTextChangedListener(new TextWatcher() {           
				@Override  
				public void onTextChanged(CharSequence s, int start, int before, int count) {  
					try{
						if(!onchange)
						{
							int col=Color.parseColor(s.toString());
							cpv.setcolor(col);
							vi.setBackgroundColor(col);
						}
					}catch(Exception e){}
				}  

				@Override  
				public void beforeTextChanged(CharSequence s, int start, int count,  
											  int after) {                  
				}  

				@Override  
				public void afterTextChanged(Editable s) {                                
				}  
			});
		ln.addView(coloredit);
		addView(ln);
	}
	public void setcolor(int color){
		coloredit.setText("#"+bqstr(Integer.toHexString(color),8));
	}
	protected int dp2px(int dp){
        return  (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
    }
	public String bqstr(String a,int b){
		int len=a.length();
		for(int i=0;i<b-len;i++)
		a="0"+a;
		return a;
	}
	public int getcolor(){
		return cpv.getcolor();
	}
}
