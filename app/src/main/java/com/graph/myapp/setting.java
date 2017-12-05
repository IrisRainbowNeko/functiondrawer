package com.graph.myapp;

import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;
import android.widget.SeekBar.*;
import android.view.*;
import com.graph.myapp.widget.*;

public class setting extends Activity
{
	boolean flag;
	LinearLayout la;
	CheckBox ckb;
	Switch sw,sw2,sw_num,sw_pai,sw_fson,sw_qly,sw_grid;
	EditText t1,t2;
	SeekBar sk_gx;
	TextView tx_gx;
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		t1=(EditText)findViewById(R.id.settingEditText1);
		t2=(EditText)findViewById(R.id.settingEditText2);
		t1.setText(""+gra.para_lim[0]);
		t2.setText(""+gra.para_lim[1]);
		sw=(Switch)findViewById(R.id.settingSwitch1);
		sw.setChecked(MainActivity.fs.getBoolean("AntiAlias",false));
		sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {  
				@Override  
				public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {  
					if(!isChecked||MainActivity.fs.getBoolean("sz",false)||flag){
						MainActivity.ma.gr.paint.setAntiAlias(isChecked);
						MainActivity.fs.edit().putBoolean("AntiAlias",isChecked).commit();
						flag=false;
					}else
					{
						sw.setChecked(false);
						la=(LinearLayout)getLayoutInflater().inflate(R.layout.kjc,null);
						ckb=(CheckBox)la.findViewById(R.id.kjcCheckBox1);
						new AlertDialog.Builder(setting.this).setTitle("平移").
							setIcon(R.drawable.ic_launcher).setView(la)
							.setPositiveButton("确定",new DialogInterface.OnClickListener()
							{
								@Override
								public void onClick(DialogInterface dialog, int which)
								{
									if(ckb.isChecked())
									MainActivity.fs.edit().putBoolean("sz",true).commit();
									flag=true;
									sw.setChecked(true);
								} 
							})
							.setNegativeButton("取消", null).show();
					}
				}  
			});  
		final SeekBar sk=(SeekBar)findViewById(R.id.settingSeekBar1);
		final TextView tx=(TextView)findViewById(R.id.settingTextView1);
		sk.setProgress(MainActivity.fs.getInt("jd",3));
		tx.setText(""+(sk.getProgress()+1)/100f);
		sk.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				@Override
				public void onStopTrackingTouch(SeekBar arg0) {//停止点击seekbar触发的事件
					MainActivity.fs.edit().putInt("jd",sk.getProgress()).commit();
					MainActivity.ma.gr.jd_x=sk.getProgress()+1;
				}
				@Override
				public void onStartTrackingTouch(SeekBar arg0) {//开始点击时触发的事件

				}
				@Override
				public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
					tx.setText(""+(arg1+1)/100f);
				}
			});
		final SeekBar sk1=(SeekBar)findViewById(R.id.settingSeekBar2);
		final TextView tx1=(TextView)findViewById(R.id.settingTextView2);
		sk1.setProgress(MainActivity.fs.getInt("htxc",0));
		tx1.setText(""+(sk1.getProgress()+1));
		sk1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				@Override
				public void onStopTrackingTouch(SeekBar arg0) {//停止点击seekbar触发的事件
					MainActivity.fs.edit().putInt("htxc",sk1.getProgress()).commit();
					gra.htxc=sk1.getProgress()+1;
				}
				@Override
				public void onStartTrackingTouch(SeekBar arg0) {//开始点击时触发的事件

				}
				@Override
				public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
					tx1.setText(""+(arg1+1));
				}
			});
		final SeekBar sk2=(SeekBar)findViewById(R.id.settingSeekBar3);
		final TextView tx2=(TextView)findViewById(R.id.settingTextView3);
		sk2.setProgress(MainActivity.fs.getInt("jd_ec",3));
		tx2.setText(""+(sk2.getProgress()+1)/100f);
		sk2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				@Override
				public void onStopTrackingTouch(SeekBar arg0) {//停止点击seekbar触发的事件
					MainActivity.fs.edit().putInt("jd_ec",sk2.getProgress()).commit();
					MainActivity.ma.gr.jd_ec=sk2.getProgress()+1;
				}
				@Override
				public void onStartTrackingTouch(SeekBar arg0) {//开始点击时触发的事件

				}
				@Override
				public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
					tx2.setText(""+(arg1+1)/100f);
				}
			});
		final View colview=findViewById(R.id.settingColorView);
		colview.setBackgroundColor(MainActivity.color_default);
		colview.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View p1)
			{
				final ColorPicker cp=new ColorPicker(setting.this);
				cp.setcolor(MainActivity.color_default);
				new AlertDialog.Builder(setting.this).setTitle("选择颜色").
					setIcon(R.drawable.ic_launcher).setView(cp)
					.setPositiveButton("确定",new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							colview.setBackgroundColor(cp.getcolor());
							MainActivity.color_default=cp.getcolor();
							MainActivity.fs.edit().putInt("defualt_color",MainActivity.color_default).commit();
						} 
					})
					.setNegativeButton("取消", null).show();
			}
		});
		sw2=(Switch)findViewById(R.id.settingSwitch2);
		sw2.setChecked(MainActivity.deleteAI);
		sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
				{
					MainActivity.fs.edit().putBoolean("deleteAI",isChecked).commit();
					MainActivity.deleteAI=isChecked;
				}
			});
		sw_num=(Switch)findViewById(R.id.settingSwitch_num);
		sw_num.setChecked(gra.numon);
		sw_num.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
				{
					gra.numon=isChecked;
				}
			});
		sw_pai=(Switch)findViewById(R.id.settingSwitch_pai);
		sw_pai.setChecked(gra.paion);
		sw_pai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
				{
					gra.paion=isChecked;
				}
			});
		sw_fson=(Switch)findViewById(R.id.settingSwitch_fson);
		sw_fson.setChecked(gra.fson);
		sw_fson.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
				{
					gra.fson=isChecked;
				}
			});
		sw_qly=(Switch)findViewById(R.id.settingSwitch_qly);
		sw_qly.setChecked(MainActivity.fs.getBoolean("CurvatureCircle",false));
		sw_qly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {  
				@Override  
				public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {  
					gra.qly=isChecked;
					MainActivity.fs.edit().putBoolean("CurvatureCircle",isChecked).commit();
				}  
			});
		sw_grid=(Switch)findViewById(R.id.settingSwitch_grid);
		sw_grid.setChecked(gra.gridopen);
		sw_grid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
				{
					gra.gridopen=isChecked;
					MainActivity.fs.edit().putBoolean("gridopen",isChecked).commit();
				}
			});
		sk_gx=(SeekBar)findViewById(R.id.settingSeekBar_gx);
		tx_gx=(TextView)findViewById(R.id.settingTextView_gx);
		sk_gx.setProgress(MainActivity.fs.getInt("gx",20));
		tx_gx.setText(""+sk_gx.getProgress());
		sk_gx.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				@Override
				public void onStopTrackingTouch(SeekBar arg0) {//停止点击seekbar触发的事件
					MainActivity.fs.edit().putInt("gx",sk_gx.getProgress()).commit();
					gra.moverate=sk_gx.getProgress();
				}
				@Override
				public void onStartTrackingTouch(SeekBar arg0) {//开始点击时触发的事件

				}
				@Override
				public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
					tx_gx.setText(""+arg1);
				}
			});
	}
	public void oncsfw(View v){
		
		try{
			float a=Float.parseFloat(t1.getText().toString());
			float b=Float.parseFloat(t2.getText().toString());
			if(a>=b){Toast.makeText(this,"最小值不能大于最大值",2000).show();}
			else if(b-a>1000){Toast.makeText(this,"参数范围过大",2000).show();}
			else {
				gra.para_lim[0]=a;
				gra.para_lim[1]=b;
				MainActivity.ma.gr.sk.setmax((int)(100*(b-a)));
				MainActivity.fs.edit().putFloat("lim_min",a).commit();
				MainActivity.fs.edit().putFloat("lim_max",b).commit();
				Toast.makeText(this,"设置成功",2000).show();
			}
		}catch(Exception e){Toast.makeText(this,"错误!",2000).show();}
	}
}
