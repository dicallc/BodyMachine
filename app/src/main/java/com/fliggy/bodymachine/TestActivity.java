package com.fliggy.bodymachine;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fliggy.bodymachine.model.DeviderModel;
import com.fliggy.bodymachine.utils.DataSource;
import com.socks.library.KLog;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

/**
 * Created by dicallc on 2018/3/20.
 */

public class TestActivity extends Activity {

  @BindView(R.id.user_id) TextView mUserId;
  @BindView(R.id.user_height) TextView mUserHeight;
  @BindView(R.id.user_age) TextView mUserAge;
  @BindView(R.id.user_sex) TextView mUserSex;
  @BindView(R.id.user_test_time) TextView mUserTestTime;
  @BindView(R.id.user_total_water_weight) TextView mUserTotalWaterWeight;
  @BindView(R.id.user_protein) TextView mUserProtein;
  @BindView(R.id.user_weight) TextView mUserWeight;
  @BindView(R.id.ly_body_info) LinearLayout mLyBodyInfo;
  @BindView(R.id.txt_body_score) TextView mTxtBodyScore;
  @BindView(R.id.txt_weight_control) TextView mTxtWeightControl;
  @BindView(R.id.txt_fat_control) TextView mTxtFatControl;
  @BindView(R.id.txt_muscle_control) TextView mTxtMuscleControl;
  @BindView(R.id.txt_visceral_fat) TextView mTxtVisceralFat;
  @BindView(R.id.txt_fat_free) TextView mTxtFatFree;
  @BindView(R.id.txt_basal_metabolism) TextView mTxtBasalMetabolism;
  @BindView(R.id.txt_fat_degree) TextView mTxtFatDegree;
  @BindView(R.id.root_view) LinearLayout mRootView;
  @BindView(R.id.lbs_weight_view) LbsView mLbsWeightView;
  @BindView(R.id.lbs_skeletalmuscle_view) LbsView mLbsSkeletalmuscleView;
  @BindView(R.id.lbs_fatweight_view) LbsView mLbsFatweightView;
  @BindView(R.id.ly_baseinfo) LinearLayout mLyBaseinfo;
  @BindView(R.id.lbs_physique_view) LbsView mLbsPhysiqueView;
  @BindView(R.id.lbs_bodyfatpercentage_view) LbsView mLbsBodyfatpercentageView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.print_layout);
    ButterKnife.bind(this);
    DeviderModel mWeightDevider = DataSource.getDevider(DataSource.getWeightData(), "115");
    mLbsWeightView.setData(mWeightDevider);
    mLbsSkeletalmuscleView.setData(
        DataSource.getDevider(DataSource.getSkeletalMuscleData(), "120"));
    mLbsFatweightView.setData(DataSource.getDevider(DataSource.getFatWeight(), "91.7"));
    mLbsPhysiqueView.setData(DataSource.getDevider(DataSource.getPhysiqueNum(), "53.1"));
    mLbsBodyfatpercentageView.setData(DataSource.getDevider(DataSource.getBodyFatPercentage(), "45"));
    new Handler().postDelayed(new Runnable() {

      @Override public void run() {
        //do something
        //viewSaveToImage(mRootView);
      }
    }, 5000);    //延时1s执行
  }

  private void viewSaveToImage(View view) {
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
    }
    KLog.e("imagePath=" + imagePath);

    view.destroyDrawingCache();
  }

  private Bitmap loadBitmapFromView(View v) {
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
