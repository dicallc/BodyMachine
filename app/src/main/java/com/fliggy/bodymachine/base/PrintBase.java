package com.fliggy.bodymachine.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.model.BodyInfoModel;
import com.fliggy.bodymachine.utils.Constant;
import com.fliggy.bodymachine.view.LoadingDialogFragment;
import com.fliggy.bodymachine.widgets.CareboDoubleLbsView;
import com.fliggy.bodymachine.widgets.CareboLbsView;
import com.github.mikephil.charting.charts.LineChart;
import com.socks.library.KLog;
import io.realm.Realm;
import io.realm.RealmResults;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

/**
 * Created by dicallc on 2018/6/30.
 */
public class PrintBase extends AppCompatActivity {
  protected boolean isDone = false;
  protected String mImagePath;

  @Override public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
    super.onSaveInstanceState(outState, outPersistentState);
    outState.putBoolean("isDone", isDone);
  }

  protected void toPrint(@Nullable Bundle savedInstanceState, final View mRootView) {
    if (savedInstanceState != null) {
      isDone = savedInstanceState.getBoolean("isDone", true);
    }
    RealmResults<BodyInfoModel> mBodyInfoModels = Realm.getDefaultInstance()
        .where(BodyInfoModel.class)
        .equalTo("id", Constant.CurentId)
        .findAll();
    if (mBodyInfoModels.size() == 0) {
      KLog.e("获取数据库数据出错");
      return;
    }
    BodyInfoModel mBodyInfoModel = mBodyInfoModels.get(0);
    initView(mBodyInfoModel);
    if (isDone) return;
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        //do something
        mImagePath = viewSaveToImage(mRootView);
        KLog.e("测试顺序");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        isDone = true;
        Uri imageUri = Uri.fromFile(new File(mImagePath));
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "分享到"));
      }
    }, 3000);    //延时1s执行
  }
  private ConstraintLayout rootView;
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

  protected void Test() {
    DisplayMetrics metric = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(metric);
    int width = metric.heightPixels;// 屏幕宽度（像素）
    int height = metric.widthPixels;// 屏幕高度（像素）
    View view = LayoutInflater.from(this).inflate(R.layout.print_layout_v2, null, false);
    rootView = (ConstraintLayout) findViewById(R.id.root_view);
    txtId = (TextView) findViewById(R.id.txt_id);
    txtHeight = (TextView) findViewById(R.id.txt_height);
    txtAge = (TextView) findViewById(R.id.txt_age);
    txtMale = (TextView) findViewById(R.id.txt_male);
    txtTestTime = (TextView) findViewById(R.id.txt_test_time);
    constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
    txtAllBodyWaterTest = (TextView) findViewById(R.id.txt_all_body_water_test);
    txtAllBodyWater = (TextView) findViewById(R.id.txt_all_body_water);
    txtJirouliang = (TextView) findViewById(R.id.txt_jirouliang);
    txtQuzhitizhong = (TextView) findViewById(R.id.txt_quzhitizhong);
    txtTizhifangtizhong = (TextView) findViewById(R.id.txt_tizhifangtizhong);
    txtDanbaizhiTest = (TextView) findViewById(R.id.txt_danbaizhi_test);
    txtWujiyanTest = (TextView) findViewById(R.id.txt_wujiyan_test);
    txtDanbaizhiTest2 = (TextView) findViewById(R.id.txt_danbaizhi_test2);
    lbsTizhong = (CareboLbsView) findViewById(R.id.lbs_tizhong);
    lbsGugeji = (CareboLbsView) findViewById(R.id.lbs_gugeji);
    lbsTizhifang = (CareboLbsView) findViewById(R.id.lbs_tizhifang);
    lbsZhiliangzhishu = (CareboLbsView) findViewById(R.id.lbs_zhiliangzhishu);
    lbsTizhiPercent = (CareboLbsView) findViewById(R.id.lbs_tizhi_percent);
    dlbsRightTop = (CareboDoubleLbsView) findViewById(R.id.dlbs_right_top);
    dlbsLeftTop = (CareboDoubleLbsView) findViewById(R.id.dlbs_left_top);
    dlbsBody = (CareboDoubleLbsView) findViewById(R.id.dlbs_body);
    dlbsRightBottom = (CareboDoubleLbsView) findViewById(R.id.dlbs_right_bottom);
    dlbsLeftBottom = (CareboDoubleLbsView) findViewById(R.id.dlbs_left_bottom);
    lbsYaotunBi = (CareboLbsView) findViewById(R.id.lbs_yaotun_bi);
    lbsZhifanLevel = (CareboLbsView) findViewById(R.id.lbs_zhifan_level);
    chartWeight = (LineChart) findViewById(R.id.chart_weight);
    chartGuluoji = (LineChart) findViewById(R.id.chart_guluoji);
    chartTizhifan = (LineChart) findViewById(R.id.chart_tizhifan);
    txtCgZhifanWei = (TextView) findViewById(R.id.txt_cg_zhifan_wei);
    txtCgJirouWei = (TextView) findViewById(R.id.txt_cg_jirou_wei);
    txtCgAimWeight = (TextView) findViewById(R.id.txt_cg_aim_weight);
    txtCgAimWeightWei = (TextView) findViewById(R.id.txt_cg_aim_weight_wei);
    txtCgWeightControl = (TextView) findViewById(R.id.txt_cg_weight_control);
    txtCgWeightControlWei = (TextView) findViewById(R.id.txt_cg_weight_control_wei);
    txtCgZhifan = (TextView) findViewById(R.id.txt_cg_zhifan);
    txtCgJirou = (TextView) findViewById(R.id.txt_cg_jirou);
    txtRightTopId = (TextView) findViewById(R.id.txt_right_top_id);
    txtLeftTopId = (TextView) findViewById(R.id.txt_left_top_id);
    txtBodyId = (TextView) findViewById(R.id.txt_body_id);
    txtRightBtId = (TextView) findViewById(R.id.txt_right_bt_id);
    txtLeftBtId = (TextView) findViewById(R.id.txt_left_bt_id);
    txtRightTopId50 = (TextView) findViewById(R.id.txt_right_top_id_50);
    txtLeftTopId50 = (TextView) findViewById(R.id.txt_left_top_id_50);
    txtBodyId50 = (TextView) findViewById(R.id.txt_body_id_50);
    txtRightBtId50 = (TextView) findViewById(R.id.txt_right_bt_id_50);
    txtLeftBtId50 = (TextView) findViewById(R.id.txt_left_bt_id_50);
    txtRightTopId250 = (TextView) findViewById(R.id.txt_right_top_id_250);
    txtLeftTopId250 = (TextView) findViewById(R.id.txt_left_top_id_250);
    txtBodyId250 = (TextView) findViewById(R.id.txt_body_id_250);
    txtRightBtId250 = (TextView) findViewById(R.id.txt_right_bt_id_250);
    txtLeftBtId250 = (TextView) findViewById(R.id.txt_left_bt_id_250);

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

  protected void showDialog() {
    LoadingDialogFragment mdf = new LoadingDialogFragment();
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    mdf.show(ft, "df");
  }

  protected void initView(BodyInfoModel mBodyInfoModel) {
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
