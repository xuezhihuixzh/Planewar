package com.liuxuejian.planefight.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
//敌机实体类
public class Enemy {
	public float x;
	public float y;
	private Bitmap enemy;
	public Enemy(float x,float y,Bitmap enemy){
		this.x = x;
		this.y = y;
		this.enemy = enemy;
	}
	public void draw(final Canvas canvas,float playerx){
		Matrix matrix = new Matrix();
		if(x>playerx){
			x = x-5;
			y = y+10;
			matrix.setTranslate(x-23, y);
		}else if(x<playerx){
			x = x+5;
			y = y+10;
			matrix.setTranslate(x-23, y);
		}else{
			y = y+10;
			matrix.setTranslate(x-23, y);
		}
		canvas.drawBitmap(enemy, matrix, null);
	}
}
