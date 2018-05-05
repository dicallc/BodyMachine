package com.fliggy.bodymachine;

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
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fliggy.bodymachine.adapter.MachineAdapter;
import com.fliggy.bodymachine.model.DeviderModel;
import com.fliggy.bodymachine.utils.DataSource;
import com.fliggy.bodymachine.view.LoadingDialogFragment;
import com.fliggy.bodymachine.widgets.LbsView;
import com.socks.library.KLog;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by dicallc on 2018/3/20.
 */

public class ShowResultActivity extends AppCompatActivity {

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
  @BindView(R.id.user_fatweight) TextView mUserFatweight;
  @BindView(R.id.rl_weight) RecyclerView mRlWeight;
  @BindView(R.id.rl_skeletalmuscle) RecyclerView mRlSkeletalmuscle;
  @BindView(R.id.rl_fatweightpercent) RecyclerView mRlFatweightpercent;
  private boolean isDone=false;
  private String mImagePath;

  @Override public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
    super.onSaveInstanceState(outState, outPersistentState);
    outState.putBoolean("isDone",isDone);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LoadingDialogFragment mdf = new LoadingDialogFragment();
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    mdf.show(ft, "df");
    setContentView(R.layout.print_layout);
    ButterKnife.bind(this);
    if(savedInstanceState != null){
      isDone = savedInstanceState.getBoolean("isDone",true);
    }
    initView();
    if (isDone)return;
    new Handler().postDelayed(new Runnable() {



      @Override public void run() {
        //do something
        mImagePath = viewSaveToImage(mRootView);
        KLog.e("测试顺序");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        isDone=true;
        Uri imageUri = Uri.fromFile(new File(mImagePath));
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "分享到"));
      }
    }, 3000);    //延时1s执行
  }

  private void initView() {
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
    List<String> arr=new ArrayList<>();
    arr.add("159");
    arr.add("168");
    arr.add("178");
    arr.add("178");
    MachineAdapter mMachineAdapter = new MachineAdapter(arr);
    mRlWeight.setLayoutManager(mManager);
    mRlWeight.setAdapter(mMachineAdapter);
    mRlSkeletalmuscle.setLayoutManager(mManager1);
    mRlSkeletalmuscle.setAdapter(mMachineAdapter);
    mRlFatweightpercent.setLayoutManager(mManager2);
    mRlFatweightpercent.setAdapter(mMachineAdapter);
  }
  private String viewSaveToImage(View view) {
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
    finally
    {
      cachebmp.recycle();   // 回收bitmap的内存
      cachebmp = null;
    }
    KLog.e("imagePath=" + imagePath);

    view.destroyDrawingCache();
    return imagePath;
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
