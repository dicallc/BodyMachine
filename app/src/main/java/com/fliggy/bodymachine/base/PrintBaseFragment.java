package com.fliggy.bodymachine.base;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.model.BodyInfoModel;
import com.fliggy.bodymachine.utils.Arith;
import com.fliggy.bodymachine.utils.Constant;
import com.fliggy.bodymachine.widgets.CareboDoubleLbsView;
import com.fliggy.bodymachine.widgets.CareboLbsView;
import com.github.mikephil.charting.charts.LineChart;
import com.socks.library.KLog;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

/**
 * Created by dicallc on 2018/7/21 0021.
 */
public class PrintBaseFragment extends SwiperFragment {

  private Activity mActivity;
  private View mView;
  private int mD_age;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mActivity = getActivity();
  }

  protected String getTextOfThreshold(String str, String low, String high) {
    return str + "\n" + "(" + low + "-" + high + ")";
  }

  protected String getTextOfThreshold(String str, String threshold) {
    return str + "\n" + "(" + threshold + ")";
  }

  private TextView txtId;
  private TextView txtHeight;
  private TextView txtAge;
  private TextView txtMale;
  private TextView txtTestTime;
  private ConstraintLayout constraintLayout;
  private TextView txtAllBodyWaterTest;
  private TextView txtAllBodyWater;
  private TextView txtJirouliang;
  private TextView txtQuzhitizhong;
  private TextView txtTizhifangtizhong;
  private TextView txtDanbaizhiTest;
  private TextView txtWujiyanTest;
  private TextView txt_tizhong_one;
  private CareboLbsView lbsTizhong;
  private CareboLbsView lbsGugeji;
  private CareboLbsView lbsTizhifang;
  private CareboLbsView lbsZhiliangzhishu;
  private CareboLbsView lbsTizhiPercent;
  private CareboDoubleLbsView dlbsRightTop;
  private CareboDoubleLbsView dlbsLeftTop;
  private CareboDoubleLbsView dlbsBody;
  private CareboDoubleLbsView dlbsRightBottom;
  private CareboDoubleLbsView dlbsLeftBottom;
  private CareboLbsView lbsYaotunBi;
  private CareboLbsView lbsZhifanLevel;
  private LineChart chartWeight;
  private LineChart chartGuluoji;
  private LineChart chartTizhifan;
  private TextView txtCgAimWeight;
  private TextView txtCgAimWeightWei;
  private TextView txtCgWeightControl;
  private TextView txtCgWeightControlWei;
  private TextView txtCgZhifan;
  private TextView txtCgZhifanWei;
  private TextView txtCgJirou;
  private TextView txtCgJirouWei;
  private TextView txtRightTopId;
  private TextView txtLeftTopId;
  private TextView txtBodyId;
  private TextView txtRightBtId;
  private TextView txtLeftBtId;
  private TextView txtRightTopId50;
  private TextView txtLeftTopId50;
  private TextView txtBodyId50;
  private TextView txtRightBtId50;
  private TextView txtLeftBtId50;
  private TextView txtRightTopId250;
  private TextView txtLeftTopId250;
  private TextView txtBodyId250;
  private TextView txtRightBtId250;
  private TextView txtLeftBtId250;

  protected void toPrint(BodyInfoModel mBodyInfoModel) {
    DisplayMetrics metric = new DisplayMetrics();
    mActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);
    int width = metric.heightPixels;// 屏幕宽度（像素）
    int height = metric.widthPixels;// 屏幕高度（像素）
    mView = LayoutInflater.from(mActivity).inflate(R.layout.print_layout_v2, null, false);
    txtId = (TextView) mView.findViewById(R.id.txt_id);
    txtId.setText("ID\n" + Constant.MainId);
    txtHeight = (TextView) mView.findViewById(R.id.txt_height);
    txtHeight.setText("身高\n" + mBodyInfoModel.getHeight() + "cm");
    txtAge = (TextView) mView.findViewById(R.id.txt_age);
    txtAge.setText("年龄\n" + mBodyInfoModel.getAge());
    txtMale = (TextView) mView.findViewById(R.id.txt_male);
    txtAllBodyWaterTest = (TextView) mView.findViewById(R.id.txt_all_body_water_test);
    txtAllBodyWater = (TextView) mView.findViewById(R.id.txt_all_body_water);
    txtAllBodyWater.setText(mBodyInfoModel.getTotal_water_weight());
    txtJirouliang = (TextView) mView.findViewById(R.id.txt_jirouliang);

    initDanbaizhi(mBodyInfoModel);
    txtWujiyanTest = (TextView) mView.findViewById(R.id.txt_wujiyan_test);
    mD_age = Integer.parseInt(mBodyInfoModel.getAge());
    iniTiZhong(mBodyInfoModel);
    if (mBodyInfoModel.getSex().equals("1")) {
      txtMale.setText("性别\n男");
      //身体总水分正常范围	女：45%--60% 男	55%--65%
      txtAllBodyWaterTest.setText(
          mBodyInfoModel.getTotal_water_weight() + "\n" + Arith.mul(mBodyInfoModel.getWeight(),
              "0.45") + "-" + Arith.mul(mBodyInfoModel.getWeight(), "0.6"));
      //肌肉量
      if (Integer.parseInt(mBodyInfoModel.getHeight()) < 160) {
        txtJirouliang.setText(mBodyInfoModel.getMuscle_weight() + "\n" + "(38.5-46.5)");
      } else if (Integer.parseInt(mBodyInfoModel.getHeight()) < 170) {
        txtJirouliang.setText(mBodyInfoModel.getMuscle_weight() + "\n" + "(44-52.4)");
      } else {
        txtJirouliang.setText(mBodyInfoModel.getMuscle_weight() + "\n" + "(49.4-59.4)");
      }
      //无机盐
      txtWujiyanTest.setText(
          getTextOfThreshold(mBodyInfoModel.getBone_salt_content(), "2.5", "3.2"));
    } else {
      txtMale.setText("性别\n女");
      //身体总水分正常范围	女：45%--60% 男	55%--65%
      txtAllBodyWaterTest.setText(
          mBodyInfoModel.getTotal_water_weight() + "\n" + Arith.mul(mBodyInfoModel.getWeight(),
              "0.55") + "-" + Arith.mul(mBodyInfoModel.getWeight(), "0.65"));
      //肌肉量
      if (Integer.parseInt(mBodyInfoModel.getHeight()) < 160) {
        txtJirouliang.setText(mBodyInfoModel.getMuscle_weight() + "\n" + "(21.9-34.7)");
      } else if (Integer.parseInt(mBodyInfoModel.getHeight()) < 170) {
        txtJirouliang.setText(mBodyInfoModel.getMuscle_weight() + "\n" + "(32.9-37.5)");
      } else {
        txtJirouliang.setText(mBodyInfoModel.getMuscle_weight() + "\n" + "(36.5-42.5)");
      }
      //无机盐
      txtWujiyanTest.setText(
          getTextOfThreshold(mBodyInfoModel.getBone_salt_content(), "1.8", "2.5"));
    }
    txtTestTime = (TextView) mView.findViewById(R.id.txt_test_time);
    txtTestTime.setText("测试时间\n" + mBodyInfoModel.getTime());

    lbsTizhong = (CareboLbsView) mView.findViewById(R.id.lbs_tizhong);
    lbsGugeji = (CareboLbsView) mView.findViewById(R.id.lbs_gugeji);
    lbsTizhifang = (CareboLbsView) mView.findViewById(R.id.lbs_tizhifang);
    lbsZhiliangzhishu = (CareboLbsView) mView.findViewById(R.id.lbs_zhiliangzhishu);
    lbsTizhiPercent = (CareboLbsView) mView.findViewById(R.id.lbs_tizhi_percent);
    dlbsRightTop = (CareboDoubleLbsView) mView.findViewById(R.id.dlbs_right_top);
    dlbsLeftTop = (CareboDoubleLbsView) mView.findViewById(R.id.dlbs_left_top);
    dlbsBody = (CareboDoubleLbsView) mView.findViewById(R.id.dlbs_body);
    dlbsRightBottom = (CareboDoubleLbsView) mView.findViewById(R.id.dlbs_right_bottom);
    dlbsLeftBottom = (CareboDoubleLbsView) mView.findViewById(R.id.dlbs_left_bottom);
    lbsYaotunBi = (CareboLbsView) mView.findViewById(R.id.lbs_yaotun_bi);
    lbsZhifanLevel = (CareboLbsView) mView.findViewById(R.id.lbs_zhifan_level);
    chartWeight = (LineChart) mView.findViewById(R.id.chart_weight);
    chartGuluoji = (LineChart) mView.findViewById(R.id.chart_guluoji);
    chartTizhifan = (LineChart) mView.findViewById(R.id.chart_tizhifan);
    txtCgZhifanWei = (TextView) mView.findViewById(R.id.txt_cg_zhifan_wei);
    txtCgJirouWei = (TextView) mView.findViewById(R.id.txt_cg_jirou_wei);
    txtCgAimWeight = (TextView) mView.findViewById(R.id.txt_cg_aim_weight);
    txtCgAimWeightWei = (TextView) mView.findViewById(R.id.txt_cg_aim_weight_wei);
    txtCgWeightControl = (TextView) mView.findViewById(R.id.txt_cg_weight_control);
    txtCgWeightControlWei = (TextView) mView.findViewById(R.id.txt_cg_weight_control_wei);
    txtCgZhifan = (TextView) mView.findViewById(R.id.txt_cg_zhifan);
    txtCgJirou = (TextView) mView.findViewById(R.id.txt_cg_jirou);
    txtRightTopId = (TextView) mView.findViewById(R.id.txt_right_top_id);
    txtLeftTopId = (TextView) mView.findViewById(R.id.txt_left_top_id);
    txtBodyId = (TextView) mView.findViewById(R.id.txt_body_id);
    txtRightBtId = (TextView) mView.findViewById(R.id.txt_right_bt_id);
    txtLeftBtId = (TextView) mView.findViewById(R.id.txt_left_bt_id);
    txtRightTopId50 = (TextView) mView.findViewById(R.id.txt_right_top_id_50);
    txtLeftTopId50 = (TextView) mView.findViewById(R.id.txt_left_top_id_50);
    txtBodyId50 = (TextView) mView.findViewById(R.id.txt_body_id_50);
    txtRightBtId50 = (TextView) mView.findViewById(R.id.txt_right_bt_id_50);
    txtLeftBtId50 = (TextView) mView.findViewById(R.id.txt_left_bt_id_50);
    txtRightTopId250 = (TextView) mView.findViewById(R.id.txt_right_top_id_250);
    txtLeftTopId250 = (TextView) mView.findViewById(R.id.txt_left_top_id_250);
    txtBodyId250 = (TextView) mView.findViewById(R.id.txt_body_id_250);
    txtRightBtId250 = (TextView) mView.findViewById(R.id.txt_right_bt_id_250);
    txtLeftBtId250 = (TextView) mView.findViewById(R.id.txt_left_bt_id_250);

    layoutView(mView, width, height);
    viewSaveToImage(mView);
  }

  /**
   * 赋值 体重和去脂体重，体脂肪
   * @param mBodyInfoModel
   */
  private void iniTiZhong(BodyInfoModel mBodyInfoModel) {
    txt_tizhong_one = (TextView) mView.findViewById(R.id.txt_tizhong_one);
    txtTizhifangtizhong = (TextView) mView.findViewById(R.id.txt_tizhifangtizhong);
    txtQuzhitizhong = (TextView) mView.findViewById(R.id.txt_quzhitizhong);
    String tizhifan =
        Arith.mulString(mBodyInfoModel.getWeight(), mBodyInfoModel.getBody_fat_percentage());
    if (mBodyInfoModel.getSex().equals("1")) {
      //男
      double low = Integer.parseInt(mBodyInfoModel.getHeight()) * Integer.parseInt(
          mBodyInfoModel.getHeight()) * 22 * 0.85;
      String high = Arith.mulString(
          (Integer.parseInt(mBodyInfoModel.getHeight()) * Integer.parseInt(
              mBodyInfoModel.getHeight()) * 22) + "", " 1.15");
      txt_tizhong_one.setText(getTextOfThreshold(mBodyInfoModel.getWeight(), low + "", high + ""));
      //体脂肪
      String tizhifan_low="";
      String tizhifan_high="";
      if (mD_age <= 39) {
         tizhifan_low = Arith.mulString(mBodyInfoModel.getWeight(), "0.27");
         tizhifan_high = Arith.mulString(mBodyInfoModel.getWeight(), "0.34");
        txtTizhifangtizhong.setText(getTextOfThreshold(tizhifan, tizhifan_low, tizhifan_high));
      } else if (mD_age <= 59) {
         tizhifan_low = Arith.mulString(mBodyInfoModel.getWeight(), "0.28");
         tizhifan_high = Arith.mulString(mBodyInfoModel.getWeight(), "0.35");
        txtTizhifangtizhong.setText(getTextOfThreshold(tizhifan, tizhifan_low, tizhifan_high));
      } else {
        txtTizhifangtizhong.setText(
            getTextOfThreshold(tizhifan, Arith.mulString(mBodyInfoModel.getWeight(), "0.29"),
                Arith.mulString(mBodyInfoModel.getWeight(), "0.36")));
      }
      //去脂体重
      txtQuzhitizhong.setText(
          getTextOfThreshold(mBodyInfoModel.getFat_free(), Arith.sub(low + "", tizhifan_low),
              Arith.sub(high + "", tizhifan_high)));
    } else {
      //女
      double low = Integer.parseInt(mBodyInfoModel.getHeight()) * Integer.parseInt(
          mBodyInfoModel.getHeight()) * 20 * 0.85;
      String high = Arith.mulString(
          (Integer.parseInt(mBodyInfoModel.getHeight()) * Integer.parseInt(
              mBodyInfoModel.getHeight()) * 20) + "", " 1.15");
      txt_tizhong_one.setText(getTextOfThreshold(mBodyInfoModel.getWeight(), low + "", high + ""));
      //体脂肪
      String tizhifan_low="";
      String tizhifan_high="";
      if (mD_age <= 39) {
        tizhifan_low = Arith.mulString(mBodyInfoModel.getWeight(), "0.16");
        tizhifan_high = Arith.mulString(mBodyInfoModel.getWeight(), "0.34");
        txtTizhifangtizhong.setText(getTextOfThreshold(tizhifan, tizhifan_low, tizhifan_high));
      } else if (mD_age <= 59) {
        tizhifan_low = Arith.mulString(mBodyInfoModel.getWeight(), "0.17");
        tizhifan_high = Arith.mulString(mBodyInfoModel.getWeight(), "0.22");
        txtTizhifangtizhong.setText(getTextOfThreshold(tizhifan, tizhifan_low, tizhifan_high));
      } else {
        txtTizhifangtizhong.setText(
            getTextOfThreshold(tizhifan, Arith.mulString(mBodyInfoModel.getWeight(), "0.19"),
                Arith.mulString(mBodyInfoModel.getWeight(), "0.24")));
      }
      //去脂体重
      txtQuzhitizhong.setText(
          getTextOfThreshold(mBodyInfoModel.getFat_free(), Arith.sub(low + "", tizhifan_low),
              Arith.sub(high + "", tizhifan_high)));
    }
  }

  private void initDanbaizhi(BodyInfoModel mBodyInfoModel) {
    txtDanbaizhiTest = (TextView) mView.findViewById(R.id.txt_danbaizhi_test);
    txtDanbaizhiTest.setText(getTextOfThreshold(mBodyInfoModel.getProtein(),
        Arith.mulString(mBodyInfoModel.getWeight(), "0.16"),
        Arith.mulString(mBodyInfoModel.getWeight(), "0.20")));
  }

  protected void layoutView(View v, int width, int height) {
    // 指定整个View的大小 参数是左上角 和右下角的坐标

    v.layout(0, 0, width, height);

    int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);

    int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);

    /** 当然，measure完后，并不会实际改变View的尺寸，需要调用View.layout方法去进行布局。

     * 按示例调用layout函数后，View的大小将会变成你想要设置成的大小。

     */

    v.measure(measuredWidth, measuredHeight);

    v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
    int w = v.getWidth();
    int h = v.getHeight();
    KLog.e("w" + w + "h" + h);
  }

  protected String viewSaveToImage(View view) {
    view.setDrawingCacheEnabled(true);
    view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    view.setDrawingCacheBackgroundColor(Color.WHITE);

    // 把一个View转换成图片
    Bitmap cachebmp = loadBitmapFromView(view);

    FileOutputStream fos;
    String imagePath = "";
    try {
      // 判断手机设备是否有SD卡
      boolean isHasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
      if (isHasSDCard) {
        // SD卡根目录
        File sdRoot = Environment.getExternalStorageDirectory();
        File file = new File(sdRoot, Calendar.getInstance().getTimeInMillis() + ".png");
        fos = new FileOutputStream(file);
        imagePath = file.getAbsolutePath();
      } else {
        throw new Exception("创建文件失败!");
      }

      cachebmp.compress(Bitmap.CompressFormat.PNG, 90, fos);

      fos.flush();
      fos.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      cachebmp.recycle();   // 回收bitmap的内存
      cachebmp = null;
    }
    KLog.e("imagePath=" + imagePath);

    view.destroyDrawingCache();
    return imagePath;
  }

  protected Bitmap loadBitmapFromView(View v) {
    int w = v.getWidth();
    int h = v.getHeight();

    Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    Canvas c = new Canvas(bmp);

    c.drawColor(Color.WHITE);
    /** 如果不设置canvas画布为白色，则生成透明 */

    v.layout(0, 0, w, h);
    v.draw(c);

    return bmp;
  }
}
