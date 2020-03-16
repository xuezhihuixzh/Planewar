package com.liuxuejian.planefight.entity;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
//爆炸实体类
public class Duang {
	private Bitmap duang;
	float x;
	float y;
	int n;
	public int m = 1;
	private ArrayList<Bitmap> frame = new ArrayList<Bitmap>();
	public Duang(float x,float y,Bitmap duangs){
		this.x = x;
		this.y = y;
		for(int i =0;i<8;i++){
			duang = Bitmap.createBitmap(duangs, i+35, 0, 35, 28);
			frame.add(duang);			
		}
		
	}
	//每刷次图则换张爆炸图片
	public void draw(Canvas canvas){
		if(m%4!=0){
			canvas.drawBitmap(frame.get(n), x, y,null);
		}else{
			canvas.drawBitmap(frame.get(n++), x, y,null);
		}
		m++;
	}
}
