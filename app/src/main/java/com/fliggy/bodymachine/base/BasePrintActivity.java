package com.fliggy.bodymachine.base;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.socks.library.KLog;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

/**
 * Created by dicallc on 2018/3/31.
 */

public class BasePrintActivity extends AppCompatActivity {
  private ProgressDialog progressDialog;

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
}
