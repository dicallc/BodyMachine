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
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fliggy.bodymachine.model.BodyInfoModel;
import com.fliggy.bodymachine.utils.Constant;
import com.fliggy.bodymachine.view.LoadingDialogFragment;
import com.socks.library.KLog;
import io.realm.Realm;
import io.realm.RealmResults;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

/**
 * Created by dicallc on 2018/3/20.
 */

public class ShowResultActivity extends AppCompatActivity {

  @BindView(R.id.root_view) ConstraintLayout mRootView;
  private boolean isDone = false;
  private String mImagePath;

  @Override public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
    super.onSaveInstanceState(outState, outPersistentState);
    outState.putBoolean("isDone", isDone);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    showDialog();
    setContentView(R.layout.print_layout_v2);
    ButterKnife.bind(this);
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

  private void showDialog() {
    LoadingDialogFragment mdf = new LoadingDialogFragment();
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    mdf.show(ft, "df");
  }

  private void initView(BodyInfoModel mBodyInfoModel) {
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
    } finally {
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
