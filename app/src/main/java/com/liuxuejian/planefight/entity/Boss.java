package com.liuxuejian.planefight.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Boss {
	public float x;
	public float y;
	private Bitmap boss;

	public Boss(float x, float y, Bitmap boss) {
		this.x = x;
		this.y = y;
		this.boss = boss;
	}

	public void draw(final Canvas canvas, float playerx) {
		Matrix matrix = new Matrix();
		if (x > playerx) {
			x = x - 5;
			matrix.setTranslate(x - 23, y);
		} else if (x < playerx) {
			x = x + 5;
			matrix.setTranslate(x - 23, y);
		} else {
			matrix.setTranslate(x - 23, y);
		}
		canvas.drawBitmap(boss, matrix, null);
	}
}
