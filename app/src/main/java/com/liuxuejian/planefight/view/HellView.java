package com.liuxuejian.planefight.view;

/**
 * 
 * @author bruce.liu
 * @version 1.0
 * @time 2015/5/13/17.46
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
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class HellView extends SurfaceView implements SurfaceHolder.Callback {
	private Context mcontext;
	private Bitmap background1, background2, background3, player, bullet1,
			enemy1, enemy2, enemy3, enemy4, enemy5, enemy6, duang, life, boss1,
			boss2, boss3, bossbullet1, bosslifebm;
	private Vibrator vibrator;// 震动
	private boolean musiccheck,shockcheck;//音效，震动 默认开启
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
	private int MODE = 3;//模式标识码
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
	private int enysleeptime = 1500;// 刷新敌机速度

	public HellView(Context mcontext, boolean musiccheck, boolean shockcheck) {
		super(mcontext);
		this.musiccheck = musiccheck;
		this.shockcheck = shockcheck;
		vibrator = (Vibrator) getContext().getSystemService(
				Context.VIBRATOR_SERVICE);
		background3 = BitmapFactory.decodeResource(getResources(),
				R.drawable.background3);
		player = BitmapFactory.decodeResource(getResources(),
				R.drawable.redplane);
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
		enemy5 = BitmapFactory
				.decodeResource(getResources(), R.drawable.enemy5);
		enemy6 = BitmapFactory
				.decodeResource(getResources(), R.drawable.enemy6);
		life = BitmapFactory.decodeResource(getResources(), R.drawable.life);
		sh = this.getHolder();
		sh.addCallback(this);
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
		timer.schedule(task, 50, 500);
	}

	private Thread mThread = new Thread(new Runnable() {
		public void run() {
			// 生成不同位置敌人的线程
			new Thread(new Runnable() {
				public void run() {
					Random randow = new Random();
					while (ThreadRun) {
						int i = randow.nextInt(6);
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
						} else if (i == 4) {
							enemy = new Enemy(0, 300, enemy5);
							enemys.add(enemy);
						} else if (i == 5) {
							enemy = new Enemy(width, 100, enemy6);
							enemys.add(enemy);
						} else {
							enemy = new Enemy(width, 600, enemy4);
							enemys.add(enemy);
						}
						try {
							Thread.sleep(enysleeptime);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();

			while (ThreadRun) {
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
		if (ThreadRun == true) {
			// 在canvas上绘制背景
			int enyspeed = 50;// 敌机移动速度
			int bgspeed = 50;// 背景移动速度

			// 调速
			if (time == 15) {
				enysleeptime = 800;
				bgspeed = 30;
			} else if (time == 30) {
				enysleeptime = 600;
				bgspeed = 35;
			} else if (time == 40) {
				enysleeptime = 400;
				bgspeed = 40;
			} else if (time == 60) {
				enysleeptime = 300;
				bgspeed = 50;
			}
			rect1 = new Rect(0, 0, background3.getWidth(),
					background3.getHeight());
			rect2 = new Rect(0, height1, 1200, 1824 + height1);
			rect3 = new Rect(0, height2, 1200, 1824 + height2);
			canvas.drawBitmap(background3, rect1, rect2, null);
			canvas.drawBitmap(background3, rect1, rect3, null);

			height1 += bgspeed;
			height2 += bgspeed;

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
						myscore += 400;
					}
				}
				if (bull.y > 0) {
					bull.draw1(canvas, enyspeed);
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
					vibrator.vibrate(pattern, 1);
					islife();
				}
			}
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
			mThread = null;
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
		}
		return true;
	}

	// SurfaceView创建时调用
	public void surfaceCreated(SurfaceHolder holder) {
		ThreadRun = true;
		mThread.start();
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
