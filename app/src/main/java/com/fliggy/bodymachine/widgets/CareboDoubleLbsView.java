package com.fliggy.bodymachine.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import com.fliggy.bodymachine.model.DeviderModel;
import com.fliggy.bodymachine.utils.Arith;
import java.util.ArrayList;

/**
 * Created by dicallc on 2018/3/20.
 */

public class CareboDoubleLbsView extends View {
  private  TextPaint mTextBigPaint;
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
  private ArrayList<String> arrDeviderText;
  private int devider_limit;
  private float devider_limit_num;
  private float devider_percent;
  private int paint_color;
  private Paint mRectPaint2;
  private float devider_Two_limit_num;
  private float devider_Two_percent;

  public CareboDoubleLbsView(Context context, AttributeSet attrs) {
    super(context, attrs);
    //第一步，初始化对象
    linePaint = new Paint();
    linePaint.setColor(Color.BLACK);//线条的颜色
    linePaint.setStrokeWidth(1);//线条的宽度
    linePaint.setAntiAlias(true);//取消锯齿
    linePaint.setStyle(Paint.Style.STROKE);//粗线
    initNormalR();
    initGrayR();
    //初始化Path
    mPath = new Path();
    paint_color=Color.BLACK;
    mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
    mTextPaint.setColor(Color.BLACK);
    mTextPaint.setTextSize(8);
    mTextBigPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
    mTextBigPaint.setColor(Color.BLACK);
    mTextBigPaint.setTextSize(10);
  }

  private void initNormalR() {
    mRectPaint = new Paint();
    mRectPaint.setColor(paint_color);       //设置画笔颜色
    mRectPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
    mRectPaint.setStrokeWidth(10f);         //设置画笔宽度为10px

    mRectPaint2 = new Paint();
    mRectPaint2.setColor(Color.GRAY);       //设置画笔颜色
    mRectPaint2.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
    mRectPaint2.setStrokeWidth(10f);         //设置画笔宽度为10px
  }

  private void initGrayR() {
    mRectGrayPaint = new Paint();
    mRectGrayPaint.setColor(Color.GRAY);       //设置画笔颜色
    mRectGrayPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
    mRectGrayPaint.setStrokeWidth(10f);         //设置画笔宽度为10px
  }

  public CareboDoubleLbsView(Context context) {
    super(context);
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    viewSize = w;//获取空间的尺寸，

    //这个是我们上下左右需要用到的坐标点
    width_x_start = 0;
    width_y_start = 0;
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

  public void setData(DeviderModel mWeightDevider) {
   /*
         * 数据为空直接GG
         */
    if (mWeightDevider.devider_text.size() == 0) {
      throw new IllegalArgumentException("No data to display !");
    }
    this.devider_limit = mWeightDevider.devider_limit;
    // 设置区间文字
    this.arrDeviderText = mWeightDevider.devider_text;
    this.devider_limit_num = mWeightDevider.devider_limit_num;
    this.devider_Two_limit_num = mWeightDevider.devider_Two_limit_num;
    this.devider_percent=mWeightDevider.devider_percent;
    this.devider_Two_percent=mWeightDevider.devider_Two_percent;
    this.paint_color=mWeightDevider.paint_color;
    invalidate();
  }

  public synchronized void setData(ArrayList<String> devider_w_arr) {
        /*
         * 数据为空直接GG
         */
    if (devider_w_arr.size() == 0) throw new IllegalArgumentException("No data to display !");

    // 设置区间文字
    this.arrDeviderText = devider_w_arr;
    invalidate();
  }

  private void drawXY(Canvas canvas) {
    mRectPaint.setColor(paint_color);
    //画坐标
    doDrawXy(canvas);
    //把宽分成11份绘画区间线,同时开始画区块
    //float preResult = width_x_start;
    for (int i = 0; i < 10; i++) {
      float devider_w1 = width_x_start + width_x_end * ((1 + (i * 2)) / 19f);
      //画间隔线 间隔线高度是y轴1/6  0.16
      float d_line_end_y = height-(width_y_start + Arith.div(height+"", "6"));
      canvas.drawLine(devider_w1, height, devider_w1, d_line_end_y, linePaint);


      float d_num_end_y = d_line_end_y;
      if (null!=arrDeviderText) {
        //间隔标刻数字 x坐标-5是居中，y坐标 数字长度为高度1/4 0.25
        canvas.drawText(arrDeviderText.get(i) + "", devider_w1-5 , d_num_end_y-5,
            mTextPaint);
      }
      ////如果到最后需要画上一个百分号和一个数值
      if (i == 9) {
        canvas.drawText("%", width_x_end - 10, d_line_end_y+5, mTextPaint);
      }
      //如果遍历循环i小与传入最大值才可以绘制
      //区间矩形 y坐标开始是1.9 0.55
      float d_rect_start_h = width_y_start + Arith.mul(height+"", "0.15");
      float d_rect_end_h = d_rect_start_h+Arith.mul(height+"","0.15");
      float d_rect_start_h_two =d_rect_start_h+Arith.mul(height+"", "0.2");
      float d_rect_end_h_two = d_rect_start_h_two+Arith.mul(height+"","0.15");
      if (i == 9) {
        //肌肉
          canvas.drawRect(width_x_start, d_rect_start_h, width_x_end*devider_percent, d_rect_end_h,
              mRectPaint);
        canvas.drawText(devider_limit_num+"", width_x_end*devider_percent+10, d_rect_end_h, mTextBigPaint);
        //第二根
          canvas.drawRect(width_x_start, d_rect_start_h_two, width_x_end*devider_Two_percent, d_rect_end_h_two,
              mRectPaint2);
        canvas.drawText(devider_Two_limit_num+"", width_x_end*devider_Two_percent+10, d_rect_end_h_two, mTextBigPaint);
      }

    }
  }

  private void doDrawXy(Canvas canvas) {
    mPath.moveTo(width_x_start, width_y_start);
    mPath.lineTo(width_x_start, height);
    mPath.lineTo(width_x_end, height);
    canvas.drawPath(mPath, linePaint);
    // 释放画布
    canvas.restore();
  }
}
