package com.fliggy.bodymachine;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import butterknife.ButterKnife;
import com.fliggy.bodymachine.model.DeviderModel;
import com.fliggy.bodymachine.utils.DataSource;

/**
 * Created by dicallc on 2018/3/20.
 */

public class TestActivity extends Activity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.print_layout);
    ButterKnife.bind(this);
    DeviderModel mWeightDevider = DataSource.getDevider(DataSource.getWeightData(), "115");
  }


}
