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

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mActivity = getActivity();
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
  private TextView txtDanbaizhiTest2;
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
    View view = LayoutInflater.from(mActivity).inflate(R.layout.print_layout_v2, null, false);
    txtId = (TextView) mActivity.findViewById(R.id.txt_id);
    txtId.setText("ID\n" + Constant.MainId);
    txtHeight = (TextView) mActivity.findViewById(R.id.txt_height);
    txtHeight.setText("身高\n" + mBodyInfoModel.getHeight() + "cm");
    txtAge = (TextView) mActivity.findViewById(R.id.txt_age);
    txtAge.setText("年龄\n" + mBodyInfoModel.getAge());
    txtMale = (TextView) mActivity.findViewById(R.id.txt_male);
    txtAllBodyWaterTest = (TextView) mActivity.findViewById(R.id.txt_all_body_water_test);
    if (mBodyInfoModel.getSex().equals("1")) {
      txtMale.setText("性别\n男");
      //身体总水分正常范围	女：45%--60% 男	55%--65%

      txtAllBodyWaterTest.setText(
          mBodyInfoModel.getTotal_water_weight() + "\n" + Arith.mul(mBodyInfoModel.getWeight(),
              "0.45"));
    } else {
      txtMale.setText("性别\n女");
    }
    txtTestTime = (TextView) mActivity.findViewById(R.id.txt_test_time);
    txtTestTime.setText("测试时间\n" + mBodyInfoModel.getTime());

    txtAllBodyWater = (TextView) mActivity.findViewById(R.id.txt_all_body_water);
    txtJirouliang = (TextView) mActivity.findViewById(R.id.txt_jirouliang);
    txtQuzhitizhong = (TextView) mActivity.findViewById(R.id.txt_quzhitizhong);
    txtTizhifangtizhong = (TextView) mActivity.findViewById(R.id.txt_tizhifangtizhong);
    txtDanbaizhiTest = (TextView) mActivity.findViewById(R.id.txt_danbaizhi_test);
    txtWujiyanTest = (TextView) mActivity.findViewById(R.id.txt_wujiyan_test);
    txtDanbaizhiTest2 = (TextView) mActivity.findViewById(R.id.txt_danbaizhi_test2);
    lbsTizhong = (CareboLbsView) mActivity.findViewById(R.id.lbs_tizhong);
    lbsGugeji = (CareboLbsView) mActivity.findViewById(R.id.lbs_gugeji);
    lbsTizhifang = (CareboLbsView) mActivity.findViewById(R.id.lbs_tizhifang);
    lbsZhiliangzhishu = (CareboLbsView) mActivity.findViewById(R.id.lbs_zhiliangzhishu);
    lbsTizhiPercent = (CareboLbsView) mActivity.findViewById(R.id.lbs_tizhi_percent);
    dlbsRightTop = (CareboDoubleLbsView) mActivity.findViewById(R.id.dlbs_right_top);
    dlbsLeftTop = (CareboDoubleLbsView) mActivity.findViewById(R.id.dlbs_left_top);
    dlbsBody = (CareboDoubleLbsView) mActivity.findViewById(R.id.dlbs_body);
    dlbsRightBottom = (CareboDoubleLbsView) mActivity.findViewById(R.id.dlbs_right_bottom);
    dlbsLeftBottom = (CareboDoubleLbsView) mActivity.findViewById(R.id.dlbs_left_bottom);
    lbsYaotunBi = (CareboLbsView) mActivity.findViewById(R.id.lbs_yaotun_bi);
    lbsZhifanLevel = (CareboLbsView) mActivity.findViewById(R.id.lbs_zhifan_level);
    chartWeight = (LineChart) mActivity.findViewById(R.id.chart_weight);
    chartGuluoji = (LineChart) mActivity.findViewById(R.id.chart_guluoji);
    chartTizhifan = (LineChart) mActivity.findViewById(R.id.chart_tizhifan);
    txtCgZhifanWei = (TextView) mActivity.findViewById(R.id.txt_cg_zhifan_wei);
    txtCgJirouWei = (TextView) mActivity.findViewById(R.id.txt_cg_jirou_wei);
    txtCgAimWeight = (TextView) mActivity.findViewById(R.id.txt_cg_aim_weight);
    txtCgAimWeightWei = (TextView) mActivity.findViewById(R.id.txt_cg_aim_weight_wei);
    txtCgWeightControl = (TextView) mActivity.findViewById(R.id.txt_cg_weight_control);
    txtCgWeightControlWei = (TextView) mActivity.findViewById(R.id.txt_cg_weight_control_wei);
    txtCgZhifan = (TextView) mActivity.findViewById(R.id.txt_cg_zhifan);
    txtCgJirou = (TextView) mActivity.findViewById(R.id.txt_cg_jirou);
    txtRightTopId = (TextView) mActivity.findViewById(R.id.txt_right_top_id);
    txtLeftTopId = (TextView) mActivity.findViewById(R.id.txt_left_top_id);
    txtBodyId = (TextView) mActivity.findViewById(R.id.txt_body_id);
    txtRightBtId = (TextView) mActivity.findViewById(R.id.txt_right_bt_id);
    txtLeftBtId = (TextView) mActivity.findViewById(R.id.txt_left_bt_id);
    txtRightTopId50 = (TextView) mActivity.findViewById(R.id.txt_right_top_id_50);
    txtLeftTopId50 = (TextView) mActivity.findViewById(R.id.txt_left_top_id_50);
    txtBodyId50 = (TextView) mActivity.findViewById(R.id.txt_body_id_50);
    txtRightBtId50 = (TextView) mActivity.findViewById(R.id.txt_right_bt_id_50);
    txtLeftBtId50 = (TextView) mActivity.findViewById(R.id.txt_left_bt_id_50);
    txtRightTopId250 = (TextView) mActivity.findViewById(R.id.txt_right_top_id_250);
    txtLeftTopId250 = (TextView) mActivity.findViewById(R.id.txt_left_top_id_250);
    txtBodyId250 = (TextView) mActivity.findViewById(R.id.txt_body_id_250);
    txtRightBtId250 = (TextView) mActivity.findViewById(R.id.txt_right_bt_id_250);
    txtLeftBtId250 = (TextView) mActivity.findViewById(R.id.txt_left_bt_id_250);

    layoutView(view, width, height);
    viewSaveToImage(view);
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
