package com.fliggy.bodymachine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import com.socks.library.KLog;

/**
 * Created by dicallc on 2018/3/20.
 */

public class LbsView extends View {
  private int viewSize;//获取空间的尺寸，也就是我们布局的尺寸大小（不知道理解的是否正确）
  private Paint linePaint;// 线条画笔和点画笔

  private Path mPath;// 路径对象
  private TextPaint mTextPaint;// 文字画笔

  private float width_x_start;
  private float width_y_start;
  private int height;
  private float width_x_end;
  private Paint mRectPaint;
  private Paint mRectGrayPaint;

  public LbsView(Context context, AttributeSet attrs) {
    super(context, attrs);
    //第一步，初始化对象
    linePaint = new Paint();
    linePaint.setColor(Color.BLACK);//线条的颜色
    linePaint.setStrokeWidth(8);//线条的宽度
    linePaint.setAntiAlias(true);//取消锯齿
    linePaint.setStyle(Paint.Style.STROKE);//粗线
    initNormalR();
    initGrayR();
    //初始化Path
    mPath = new Path();

    mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
    mTextPaint.setColor(Color.BLACK);
    mTextPaint.setTextSize(36);
  }

  private void initNormalR() {
    mRectPaint = new Paint();
    mRectPaint.setColor(Color.BLACK);       //设置画笔颜色
    mRectPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
    mRectPaint.setStrokeWidth(10f);         //设置画笔宽度为10px
  }

  private void initGrayR() {
    mRectGrayPaint = new Paint();
    mRectGrayPaint.setColor(Color.GRAY);       //设置画笔颜色
    mRectGrayPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
    mRectGrayPaint.setStrokeWidth(10f);         //设置画笔宽度为10px
  }

  public LbsView(Context context) {
    super(context);
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    viewSize = w;//获取空间的尺寸，
    KLog.e("Text", "viewSize:" + viewSize + "高度: " + h);

    //这个是我们上下左右需要用到的坐标点
    width_x_start = viewSize * (1 / 16f);
    width_y_start = viewSize * (1 / 16f);
    height = h;
    width_x_end = viewSize * (15 / 16f);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    // 锁定画布
    canvas.save();
    //定义一个绘制X,Y轴的方法
    drawXY(canvas);
  }

  private void drawXY(Canvas canvas) {
    mPath.moveTo(width_x_start, height);
    mPath.lineTo(width_x_start, width_y_start);
    mPath.lineTo(width_x_end, width_y_start);
    canvas.drawPath(mPath, linePaint);
    // 释放画布
    canvas.restore();
    //把宽分成11份绘画区间线,同时开始画区块
    float preResult = width_x_start;
    for (int i = 0; i < 11; i++) {
      float devider_w1 = width_x_start + width_x_end * ((1 + (i * 2)) / 24f);
      canvas.drawLine(devider_w1, width_y_start, devider_w1, width_y_start + 30, linePaint);
      canvas.drawText("18" + i, devider_w1 - 30, width_y_start + 65, mTextPaint);
      if (i == 4 || i == 5) {
        canvas.drawRect(preResult + 3, width_y_start + 75, devider_w1 - 3, width_y_start + 100,
            mRectGrayPaint);
      } else {
        canvas.drawRect(preResult + 3, width_y_start + 75, devider_w1 - 3, width_y_start + 100,
            mRectPaint);
      }
      //如果到最后需要画上一个百分号和一个数值
      if (i==10){
        canvas.drawText("%", width_x_end-20, width_y_start + 40, mTextPaint);
      }
      preResult = devider_w1;
    }
  }
}
