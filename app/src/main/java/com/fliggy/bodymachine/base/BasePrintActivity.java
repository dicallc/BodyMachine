package com.fliggy.bodymachine.base;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.fliggy.bodymachine.LbsView;
import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.adapter.MachineAdapter;
import com.fliggy.bodymachine.model.DeviderModel;
import com.fliggy.bodymachine.model.MessageEvent;
import com.fliggy.bodymachine.utils.DataSource;
import com.socks.library.KLog;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by dicallc on 2018/3/31.
 */

public class BasePrintActivity extends AppCompatActivity {
  private ProgressDialog progressDialog;
  private int mWidthPixels;
  private int mHeightPixels;

  /**
   * 加载框
   */
  protected void buildProgressDialog(String text) {
    if (progressDialog == null) {
      progressDialog = new ProgressDialog(this);
      progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
    progressDialog.setMessage(text);
    progressDialog.setCancelable(true);
    progressDialog.show();
  }

  protected void cancelProgressDialog() {
    if (progressDialog != null) {
      if (progressDialog.isShowing()) {
        progressDialog.dismiss();
      }
    }
  }

  protected String viewSaveToImage(View view) {
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
public void getAppWH(){
  DisplayMetrics dm = new DisplayMetrics();
  getWindowManager().getDefaultDisplay().getMetrics(dm);
  Log.e("WangJ", "屏幕高:" + dm.heightPixels);
  mWidthPixels = dm.widthPixels;
  //应用区域
  Rect outRect1 = new Rect();
  getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
  mHeightPixels = outRect1.height();
  Log.e("WangJ", "应用区顶部" + outRect1.top);
  Log.e("WangJ", "应用区高" + outRect1.height());
}
  protected void getPrintPic() {
    View mView = View.inflate(this, R.layout.print_layout,null);
    //LinearLayout.LayoutParams mLayoutParams = (LinearLayout.LayoutParams) mView.getLayoutParams();
    //mLayoutParams.width=mWidthPixels;
    //mLayoutParams.height=mHeightPixels;
    //mView.setLayoutParams(mLayoutParams);
    //mView.invalidate();
    mView.getWidth();
    mView.getHeight();
    initView(mView);
    //保存前置
    mRootView.setDrawingCacheEnabled(true);
    mRootView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    mRootView.setDrawingCacheBackgroundColor(Color.WHITE);
    new Thread(mRunnable).start();
  }

  Runnable mRunnable = new Runnable() {
    @Override public void run() {
      String mImage = viewSaveToImage(mRootView);
      MessageEvent mMessageEvent = new MessageEvent(MessageEvent.GET_PIC, mImage);
      EventBus.getDefault().post(mMessageEvent);
    }
  };

  private TextView mUserId;
  private TextView mUserHeight;
  private TextView mUserAge;
  private TextView mUserSex;
  private TextView mUserTestTime;
  private LinearLayout mLyBaseinfo;
  private TextView mUserTotalWaterWeight;
  private TextView mUserProtein;
  private TextView user_fatweight;
  private TextView mUserWeight;
  private LbsView mLbsWeightView;
  private LbsView mLbsSkeletalmuscleView;
  private LbsView mLbsFatweightView;
  private LbsView mLbsPhysiqueView;
  private LbsView mLbsBodyfatpercentageView;
  private RecyclerView rl_weight;
  private RecyclerView rl_skeletalmuscle;
  private RecyclerView rl_fatweightpercent;
  private LinearLayout mLyBodyInfo;
  private TextView mTxtBodyScore;
  private TextView mTxtWeightControl;
  private TextView mTxtFatControl;
  private TextView mTxtMuscleControl;
  private TextView mTxtVisceralFat;
  private TextView mTxtFatFree;
  private TextView mTxtBasalMetabolism;
  private TextView txt_fat_degree;
  private LinearLayout mRootView;

  private void initView(View mView) {
    mUserId = (TextView) mView.findViewById(R.id.user_id);
    mUserHeight = (TextView) mView.findViewById(R.id.user_height);
    mUserAge = (TextView) mView.findViewById(R.id.user_age);
    mUserSex = (TextView) mView.findViewById(R.id.user_sex);
    mUserTestTime = (TextView) mView.findViewById(R.id.user_test_time);
    mLyBaseinfo = (LinearLayout) mView.findViewById(R.id.ly_baseinfo);
    mUserTotalWaterWeight = (TextView) mView.findViewById(R.id.user_total_water_weight);
    mUserProtein = (TextView) mView.findViewById(R.id.user_protein);
    user_fatweight = (TextView) mView.findViewById(R.id.user_fatweight);
    mUserWeight = (TextView) mView.findViewById(R.id.user_weight);
    mLbsWeightView = (LbsView) mView.findViewById(R.id.lbs_weight_view);
    mLbsSkeletalmuscleView = (LbsView) mView.findViewById(R.id.lbs_skeletalmuscle_view);
    mLbsFatweightView = (LbsView) mView.findViewById(R.id.lbs_fatweight_view);
    mLbsPhysiqueView = (LbsView) mView.findViewById(R.id.lbs_physique_view);
    mLbsBodyfatpercentageView = (LbsView) mView.findViewById(R.id.lbs_bodyfatpercentage_view);
    rl_weight = (RecyclerView) mView.findViewById(R.id.rl_weight);
    rl_skeletalmuscle = (RecyclerView) mView.findViewById(R.id.rl_skeletalmuscle);
    rl_fatweightpercent = (RecyclerView) mView.findViewById(R.id.rl_fatweightpercent);
    mLyBodyInfo = (LinearLayout) mView.findViewById(R.id.ly_body_info);
    mTxtBodyScore = (TextView) mView.findViewById(R.id.txt_body_score);
    mTxtWeightControl = (TextView) mView.findViewById(R.id.txt_weight_control);
    mTxtFatControl = (TextView) mView.findViewById(R.id.txt_fat_control);
    mTxtMuscleControl = (TextView) mView.findViewById(R.id.txt_muscle_control);
    mTxtVisceralFat = (TextView) mView.findViewById(R.id.txt_visceral_fat);
    mTxtFatFree = (TextView) mView.findViewById(R.id.txt_fat_free);
    mTxtBasalMetabolism = (TextView) mView.findViewById(R.id.txt_basal_metabolism);
    txt_fat_degree = (TextView) mView.findViewById(R.id.txt_fat_degree);
    mRootView = (LinearLayout) mView.findViewById(R.id.root_view);
    initData();
  }

  private void initData() {
    DeviderModel mWeightDevider = DataSource.getDevider(DataSource.getWeightData(), "115");
    mLbsWeightView.setData(mWeightDevider);
    mLbsSkeletalmuscleView.setData(
        DataSource.getDevider(DataSource.getSkeletalMuscleData(), "120"));
    mLbsFatweightView.setData(DataSource.getDevider(DataSource.getFatWeight(), "91.7"));
    mLbsPhysiqueView.setData(DataSource.getDevider(DataSource.getPhysiqueNum(), "53.1"));
    mLbsBodyfatpercentageView.setData(
        DataSource.getDevider(DataSource.getBodyFatPercentage(), "45"));
    initList();
  }

  private void initList() {
    LinearLayoutManager mManager = new LinearLayoutManager(this);
    mManager.setOrientation(LinearLayoutManager.HORIZONTAL);
    LinearLayoutManager mManager1 = new LinearLayoutManager(this);
    mManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
    LinearLayoutManager mManager2 = new LinearLayoutManager(this);
    mManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
    List<String> arr = new ArrayList<>();
    arr.add("159");
    arr.add("168");
    arr.add("178");
    arr.add("178");
    MachineAdapter mMachineAdapter = new MachineAdapter(arr);
    rl_weight.setLayoutManager(mManager);
    rl_weight.setAdapter(mMachineAdapter);
    rl_skeletalmuscle.setLayoutManager(mManager1);
    rl_skeletalmuscle.setAdapter(mMachineAdapter);
    rl_fatweightpercent.setLayoutManager(mManager2);
    rl_fatweightpercent.setAdapter(mMachineAdapter);
  }
}
