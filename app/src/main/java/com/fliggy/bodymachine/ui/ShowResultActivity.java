package com.fliggy.bodymachine.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.base.PrintBase;

/**
 * Created by dicallc on 2018/3/20.
 */

public class ShowResultActivity extends PrintBase {

  @BindView(R.id.root_view) ConstraintLayout mRootView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //showDialog();
    setContentView(R.layout.print_layout_v2);
    ButterKnife.bind(this);
    Test();
    //toPrint(savedInstanceState);
  }
}
