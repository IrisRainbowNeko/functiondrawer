package com.graph.myapp;

import android.app.*;
import android.content.res.*;
import android.graphics.*;
import android.os.*;
import android.support.v4.view.*;
import android.support.v4.view.ViewPager.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import android.util.*;

public class sm extends Activity implements OnPageChangeListener
{
	//定义ViewPager对象
	private ViewPager viewPager;
	//定义ViewPager适配器
	private ViewPagerAdapter vpAdapter;
	//定义一个ArrayList来存放View
	private ArrayList<View> views=new ArrayList<View>();
    //底部小点的图片
    private ImageView[] points;
    //记录当前选中位置
    private int currentIndex;
	public static boolean first;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.sm);
		final TextView tx=(TextView)findViewById(R.id.smTextView1);
		tx.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					tx.setVisibility(View.GONE);
				}
			});
		viewPager = (ViewPager) findViewById(R.id.smViewPager1);
		//定义一个布局并设置参数
		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                														  LinearLayout.LayoutParams.MATCH_PARENT);
       	Bitmap[] bit=tool.loadbitmaps("sm");
		//views.add(tx);
        for(int i=0; i<bit.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
			iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageBitmap(bit[i]);
            views.add(iv);
        }
		LinearLayout la=(LinearLayout) getLayoutInflater().inflate(R.layout.di,null);
		ImageButton imb=(ImageButton)la.findViewById(R.id.diImageButton1);
		imb.setImageBitmap(tool.loadbitmap("ks.png"));
		imb.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					if(first){
						first=false;
						MainActivity.ma.showPaydialog();
					}
					sm.this.finish();
				}
			});
		views.add(la);
		vpAdapter = new ViewPagerAdapter(views);
        //设置数据
        viewPager.setAdapter(vpAdapter);
        //设置监听
        viewPager.setOnPageChangeListener(this);
    }
	/**
	 * 当滑动状态改变时调用
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}
	/**
	 * 当当前页面被滑动时调用
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}
	/**
	 * 当新的页面被选中时调用
	 */
	@Override
	public void onPageSelected(int position) {
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO: Implement this method
		if(keyCode==event.KEYCODE_BACK)return true;
		return super.onKeyDown(keyCode, event);
	}
	
}
