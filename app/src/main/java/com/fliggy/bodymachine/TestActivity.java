package com.fliggy.bodymachine;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fliggy.bodymachine.utils.DataSource;
import com.fliggy.bodymachine.widgets.CareboDoubleLbsView;

public class TestActivity extends Activity {

  @BindView(R.id.lbs_view) CareboDoubleLbsView mLbsView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.custom_lbs);
    ButterKnife.bind(this);
    mLbsView.setData(
        DataSource.getDeviderPercent(DataSource.getWeightData(), 130+"", Color.RED));
  }
}
