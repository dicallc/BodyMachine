package com.fliggy.bodymachine;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by dicallc on 2018/3/5.
 */
public class App extends Application{
  static App context;

  @Override
  public void onCreate() {
    super.onCreate();
    Bugly.init(getApplicationContext(), "9311e75ffa", true);
    Realm.init(this);
    RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm").build();
    Realm.setDefaultConfiguration(config);
    context = this;
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
