package com.fliggy.bodymachine;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import me.yokeyword.fragmentation.Fragmentation;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by dicallc on 2018/3/5.
 */
public class App extends Application{
  static App context;

  @Override
  public void onCreate() {
    super.onCreate();
    Beta.canNotifyUserRestart = true;
    Bugly.init(getApplicationContext(), "9311e75ffa", true);
    Realm.init(this);
    RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm").build();
    Realm.setDefaultConfiguration(config);
    context = this;
    Fragmentation.builder()
        // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
        .stackViewMode(Fragmentation.BUBBLE)
        .debug(BuildConfig.DEBUG)
             .install();
    CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
        .setDefaultFontPath("fonts/ArialBoldItalic.ttf")
        .setFontAttrId(R.attr.fontPath)
        .build() );
  }
  public static Context getAppContext() {
    return context;
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(base);
    // 安装tinker
    Beta.installTinker();
  }
}
