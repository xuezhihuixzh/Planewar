package com.liuxuejian.planefight.view;

/**
 * 
 * @author bruce.liu
 * @version 1.0
 * @time 2015/4/27/16.50
 * 
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.liuxuejian.planefight.R;
import com.liuxuejian.planefight.activity.GameOverActivity;
import com.liuxuejian.planefight.entity.Boss;
import com.liuxuejian.planefight.entity.Bullet;
import com.liuxuejian.planefight.entity.Duang;
import com.liuxuejian.planefight.entity.Enemy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Abvanceview extends SurfaceView implements SurfaceHolder.Callback {
	private Context mcontext;
	private boolean musiccheck, shockcheck;// 音效，震动 默认开启
	private Bitmap background1, background2, background3, player, bullet1,
			enemy1, enemy2, enemy3, enemy4, duang, life, boss1, boss2, boss3,
			bossbullet1, bosslifebm;
	private Vibrator vibrator;// 震动
	private Canvas canvas;
	private SurfaceHolder sh;
	private boolean ThreadRun;
	private int height1;
	private int height2 = -1824;
	private Rect rect1, rect2, rect3;// 背景图片矩阵
	private float x, y;// 手指触摸变量
	private int shoot;// 子弹射击速度变量
	private int width, height;// 屏幕宽度，高度
	private Paint paint;// 绘图画笔
	private int myscore;// 得分
	private int lifecount = 3;// 生命值
	private int time;// 游戏时间
	private Timer timer;
	private int bossx = 600, bossy = 50;// Boss坐标
	private boolean warning = false;// 警告Boss将出现
	private boolean flagboss = true;// Boss出现
	private int bosslife = 20;
	private int MODE = 1;// 模式标识码
	// 子弹
	private Bullet bullet;
	// 玩家子弹集合
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	// 敌机
	private Enemy enemy;
	// 敌机集合
	private ArrayList<Enemy> enemys = new ArrayList<Enemy>();
	// 敌机子弹集合
	private ArrayList<Bullet> ebullets = new ArrayList<Bullet>();
	// 爆炸集合
	private ArrayList<Duang> duangs = new ArrayList<Duang>();
	// Boss子弹集合
	private ArrayList<Bullet> bossbullets = new ArrayList<Bullet>();
	private ArrayList<Bullet> bossbullets1 = new ArrayList<Bullet>();
	private ArrayList<Bullet> bossbullets2 = new ArrayList<Bullet>();
	private long[] pattern = { 100, 400 }; // 震动 停止 开启
	private Boss b1;
	private Handler handler1;
	private int intboss = 0;

	public Abvanceview(Context mcontext, boolean musiccheck, boolean shockcheck) {
		super(mcontext);
		this.musiccheck = musiccheck;
		this.shockcheck = shockcheck;
		vibrator = (Vibrator) getContext().getSystemService(
				Context.VIBRATOR_SERVICE);
		background1 = BitmapFactory.decodeResource(getResources(),
				R.drawable.background1);
		background2 = BitmapFactory.decodeResource(getResources(),
				R.drawable.background2);
		background3 = BitmapFactory.decodeResource(getResources(),
				R.drawable.background3);
		player = BitmapFactory.decodeResource(getResources(),
				R.drawable.yellowplane);
		bullet1 = BitmapFactory.decodeResource(getResources(),
				R.drawable.bullet1);
		enemy1 = BitmapFactory
				.decodeResource(getResources(), R.drawable.enemy1);
		enemy2 = BitmapFactory
				.decodeResource(getResources(), R.drawable.enemy2);
		enemy3 = BitmapFactory
				.decodeResource(getResources(), R.drawable.enemy3);
		enemy4 = BitmapFactory
				.decodeResource(getResources(), R.drawable.enemy4);
		boss1 = BitmapFactory.decodeResource(getResources(), R.drawable.boss1);
		boss2 = BitmapFactory.decodeResource(getResources(), R.drawable.boss2);
		boss3 = BitmapFactory.decodeResource(getResources(), R.drawable.boss3);
		bossbullet1 = BitmapFactory.decodeResource(getResources(),
				R.drawable.bossbullet1);
		duang = BitmapFactory.decodeResource(getResources(),
				R.drawable.bossbullet);
		life = BitmapFactory.decodeResource(getResources(), R.drawable.life);
		bosslifebm = BitmapFactory.decodeResource(getResources(),
				R.drawable.bosslife);
		sh = this.getHolder();
		sh.addCallback(this);
		handler1 = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case 11111:
						ThreadRun = false;
						break;
				}
			};
		};
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					time++;
					break;
				}
				super.handleMessage(msg);
			}
		};
		TimerTask task = new TimerTask() {
			public void run() {
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}
		};
		timer = new Timer(true);
		timer.schedule(task, 200, 1000);
	}

	private Thread mThread = new Thread(new Runnable() {
		public void run() {
			// 生成不同位置敌人的线程
			Random randow = new Random();
			while (true) {
				if (!ThreadRun) {
					break;
				}
				if (intboss == 0) {
					b1 = new Boss(bossx, bossy, boss3);
				}
				int i = randow.nextInt(4);
				if (i == 0) {
					enemy = new Enemy(0, 0, enemy1);
					enemys.add(enemy);
				} else if (i == 1) {
					enemy = new Enemy(300, 0, enemy2);
					enemys.add(enemy);
				} else if (i == 2) {
					enemy = new Enemy(700, 0, enemy3);
					enemys.add(enemy);
				} else if (i == 3) {
					enemy = new Enemy(700, 0, enemy4);
					enemys.add(enemy);
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				intboss = 1;
			}
		}
	});
	private Thread bThread = new Thread(new Runnable() {
		public void run() {
			while (true) {
				if (!ThreadRun) {
					break;
				}
				// 子弹射击速度
				if (shoot % 170 == 0) {
					bullet = new Bullet(x + 9, y + 9, bullet1);
					bullets.add(bullet);
				}
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				ondraw();
				shoot += 30;
			}
		}
	});

	private void ondraw() {
		// 锁定画布，得到Canvas对象
		canvas = sh.lockCanvas();
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(40);
//		Looper.prepare();
//		handler1 = new Handler() {
//			public void handleMessage(Message msg) {
//				switch (msg.what) {
//				case 11111:
//					ThreadRun = false;
//					break;
//				}
//			};
//		};
//		Looper.loop();
		if (ThreadRun == true) {
			// 在canvas上绘制背景
			rect1 = new Rect(0, 0, background1.getWidth(),
					background1.getHeight());
			rect2 = new Rect(0, height1, 1200, 1824 + height1);
			rect3 = new Rect(0, height2, 1200, 1824 + height2);
			canvas.drawBitmap(background1, rect1, rect2, null);
			canvas.drawBitmap(background1, rect1, rect3, null);
			height1 += 10;
			height2 += 10;
			if (height1 >= 1824) {
				height1 = -1824;
			}
			if (height2 >= 1824) {
				height2 = -1824;
			}
			// 在canvas上绘制分数
			canvas.drawText("得分: " + myscore, 100, 100, paint);
			// 在canvas上绘制玩家飞机
			canvas.drawBitmap(player, x - 25, y - 20, null);
			// 在canvas上绘制生命值
			for (int i = 0; i < lifecount; i++) {
				canvas.drawBitmap(life, 100 + 50 * i, height - 100, null);
			}
			// 在canvas上绘制玩家子弹以及判断玩家子弹与敌机(Boss)相撞
			for (int i = 0; i < bullets.size(); i++) {
				Bullet bull = bullets.get(i);
				float bullx = bull.x + 10;
				float bully = bull.y;
				for (int k = 0; k < enemys.size(); k++) {
					Enemy eneb = enemys.get(k);
					float enemyx = eneb.x;
					float enemyy = eneb.y;
					if (bullx < (enemyx + 40) & bullx > (enemyx - 40)
							& bully < (enemyy + 40) & bully > (enemyy - 40)) {
						enemys.remove(eneb);
						bullets.remove(bull);
						// Duang duan = new Duang(enemyx, enemyy, duang);
						// duangs.add(duan);
						myscore += 400;
					}
				}
				if (time > 15) {
					if (bullx < (b1.x + 40) & bullx > (b1.x - 40)
							& bully < (b1.y + 40) & bully > (b1.y - 40)) {
						bosslife--;
						bullets.remove(bull);
						// Duang duan = new Duang(enemyx, enemyy, duang);
						// duangs.add(duan);
						myscore += 4000;
						if (bosslife == 0) {
							// 关闭相应线程
							ThreadRun = false;
							mThread = null;
							timer.cancel();
							Intent intent = new Intent(getContext(),
									GameOverActivity.class);
							intent.putExtra("score", myscore);
							getContext().startActivity(intent);
						}
					}
				}
				if (bull.y > 0) {
					bull.draw1(canvas, 20);
				}
			}
			// 在canvas上绘制敌机以及敌机与玩家相撞
			for (int j = 0; j < enemys.size(); j++) {
				Enemy ene = enemys.get(j);
				int i = new Random().nextInt(25);
				if (i == 0) {
					Bitmap ebullet1 = BitmapFactory.decodeResource(
							this.getResources(), R.drawable.ebullet1);
					Bullet ebull = new Bullet(ene.x + 9, ene.y + 31, ebullet1);
					ebullets.add(ebull);
				}
				if (ene.y < height) {
					ene.draw(canvas, x);
				} else {
					enemys.remove(ene);
				}
				if (ene.x < (x + 40) & ene.x > (x - 40) & ene.y < (y + 40)
						& ene.y > (y - 40)) {
					enemys.remove(ene);
					lifecount--;
					islife();
				}
			}
			// 警告boss将出现
			if (time > 10 && time < 15) {
				if (time % 2 == 0) {
					paint.setColor(Color.RED);
				} else {
					paint.setColor(Color.WHITE);
				}
				canvas.drawText("警告！Boss接近中.....", width / 2 - 140, height / 2,
						paint);
			}
			// 绘制boss出现以及boss与玩家相撞
			if (time > 15) {
				// 绘制Boss血量
				for (int i = 0; i < bosslife; i++) {
					canvas.drawBitmap(bosslifebm, 100 + 50 * i, 20, null);
				}
				b1.draw(canvas, x);
				int j = new Random().nextInt(25);
				if (j == 0) {
					Bullet ebull = new Bullet(b1.x - 25, b1.y + 30, bossbullet1);
					bossbullets.add(ebull);
				}
				if (b1.x < (x + 40) & b1.x > (x - 40) & b1.y < (y + 40)
						& b1.y > (y - 40)) {
					lifecount--;
					if (shockcheck) {
						vibrator.vibrate(pattern, 1);
					}
					islife();
				}
			}

			// 在canvas上绘制Boss子弹以及判断Boss子弹与玩家相撞
			for (int i = 0; i < bossbullets.size(); i++) {
				Bullet bull = bossbullets.get(i);
				float ebullx = bull.x;
				float ebully = bull.y;
				if (bull.y < height) {
					bull.draw2(canvas);
				} else {
					bossbullets.remove(bull);
				}
				if (ebullx < (x + 40) & ebullx > (x - 40) & ebully < (y + 40)
						& ebully > (y - 40)) {
					bossbullets.remove(bull);
					lifecount--;
					if (shockcheck) {
						vibrator.vibrate(pattern, 1);
					}
					islife();
				}
			}
			// 在canvas上绘制敌机子弹以及判断敌人子弹与玩家相撞
			for (int i = 0; i < ebullets.size(); i++) {
				Bullet bull = ebullets.get(i);
				float ebullx = bull.x;
				float ebully = bull.y;
				if (bull.y < height) {
					bull.draw2(canvas);
				} else {
					ebullets.remove(bull);
				}
				if (ebullx < (x + 40) & ebullx > (x - 40) & ebully < (y + 40)
						& ebully > (y - 40)) {
					ebullets.remove(bull);
					lifecount--;
					if (shockcheck) {
						vibrator.vibrate(pattern, 1);
					}
					islife();
				}
			}

			// 绘制爆炸
			// for (int m = 0; m < duangs.size(); m++) {
			// Duang duan = duangs.get(m);
			// if (duan.m < 32) {
			// duan.draw(canvas);
			// } else {
			// duangs.remove(duan);
			// }
			// }
		}
		if (canvas != null) {
			// 解除锁定，并提交修改内容，更新屏幕
			sh.unlockCanvasAndPost(canvas);
		}

	}

	// 判断是否生命值为0，跳转
	private void islife() {
		if (lifecount == 0) {
			// 关闭相应线程
			ThreadRun = false;
			timer.cancel();
			Intent intent = new Intent(getContext(), GameOverActivity.class);
			intent.putExtra("score", myscore);
			intent.putExtra("mode", MODE);
			getContext().startActivity(intent);
		}
	}

	// 触摸屏幕事件监听
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			x = (int) event.getX();
			y = (int) event.getY();
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		return true;
	}

	// SurfaceView创建时调用
	public void surfaceCreated(SurfaceHolder holder) {
		ThreadRun = true;
		mThread.start();
		bThread.start();
		width = this.getWidth();
		height = this.getHeight();
		// 设置玩家初始位置
		x = width / 2;
		y = height - 250;
	}

	// SurfaceView改变时调用
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	// SurfaceView结束时调用
	public void surfaceDestroyed(SurfaceHolder holder) {
		ThreadRun = false;
	}

}
