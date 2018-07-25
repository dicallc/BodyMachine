package com.fliggy.bodymachine.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fliggy.bodymachine.R;

public class ShowResultActivity extends TestPrintBase {

  @BindView(R.id.root_view) ConstraintLayout mRootView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //showDialog();
    setContentView(R.layout.print_layout_v2);
    ButterKnife.bind(this);
    Test();
  }
}