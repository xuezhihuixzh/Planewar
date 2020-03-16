package com.liuxuejian.planefight.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Bullet {
	public float x ;
	public float y ;
	private Bitmap bullet;
	private int time;
	public Bullet(float x,float y,Bitmap bullet){
		this.x = x;
		this.y = y;
		this.bullet = bullet;
		
	}
	//玩家子弹
	public void draw1(Canvas canvas,int speed){
		Matrix matrix = new Matrix();
		y = y-speed;
		matrix.setTranslate(x-22, y-30);
		canvas.drawBitmap(bullet, matrix, null);
	}
	//敌人子弹
	public void draw2(Canvas canvas){
		Matrix matrix = new Matrix();
		y=y+20;
		matrix.setTranslate(x, y);
		canvas.drawBitmap(bullet, matrix, null);
	}
	public void draw4(Canvas canvas){
		y+=60;
		canvas.drawBitmap(bullet,x,y,null);
	}
}
