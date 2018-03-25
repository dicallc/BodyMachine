package com.fliggy.bodymachine;

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

public class LbsView extends View {
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

  public LbsView(Context context, AttributeSet attrs) {
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

    mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
    mTextPaint.setColor(Color.BLACK);
    mTextPaint.setTextSize(8);
    mTextBigPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
    mTextBigPaint.setColor(Color.BLACK);
    mTextBigPaint.setTextSize(10);
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
    //KLog.e("Text", "viewSize:" + viewSize + "高度: " + h);

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
    this.devider_limit_num=mWeightDevider.devider_limit_num;
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
      //画间隔线 间隔线高度是y轴1/6  0.16
      float d_line_end_y = width_y_start + Arith.div(height+"", "6");
      canvas.drawLine(devider_w1, width_y_start, devider_w1, d_line_end_y, linePaint);
      //如果到最后需要画上一个百分号和一个数值
      if (i == 10) {
        canvas.drawText("%", width_x_end - 10, d_line_end_y+5, mTextPaint);
      }
      float d_num_end_y = width_y_start + Arith.div(height+"", "2.57");
      if (arrDeviderText.size() == 11) {
        //间隔标刻数字 x坐标-5是居中，y坐标 数字长度为高度1/4 0.25
        canvas.drawText(arrDeviderText.get(i) + "", devider_w1-5 , d_num_end_y+2,
            mTextPaint);
      }
      //如果遍历循环i小与传入最大值才可以绘制
      //区间矩形 y坐标开始是1.9 0.55
      float d_rect_end_y = width_y_start + Arith.div(height+"", "1.9");
      float d_rect_end_h = width_y_start + Arith.div(height+"", "4.5");
      float d_rect_end_2h = d_rect_end_h*2;
      if (i < devider_limit) {

        if (i == 4 || i == 5) {
          canvas.drawRect(preResult + 3, d_rect_end_y+d_rect_end_h, devider_w1 - 3, d_rect_end_y + d_rect_end_2h,
              mRectGrayPaint);
        } else {
          canvas.drawRect(preResult+2, d_rect_end_y+d_rect_end_h, devider_w1, d_rect_end_y + d_rect_end_2h,
              mRectPaint);
        }
      }else if(i==devider_limit){
        canvas.drawText(devider_limit_num+"", preResult, d_rect_end_y +d_rect_end_2h, mTextBigPaint);
      }else{

      }

      preResult = devider_w1;
    }
  }
}
