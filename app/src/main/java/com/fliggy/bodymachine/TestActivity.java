package com.fliggy.bodymachine;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by dicallc on 2018/3/20.
 */

public class TestActivity  extends Activity{
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.custom_lbs);
  }
}
