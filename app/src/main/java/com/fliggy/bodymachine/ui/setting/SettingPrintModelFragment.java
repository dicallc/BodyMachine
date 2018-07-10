package com.fliggy.bodymachine.ui.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.utils.Constant;
import com.fliggy.utils_module.utils.SPUtils;
import me.yokeyword.fragmentation.SupportFragment;

public class SettingPrintModelFragment extends SupportFragment
    implements RadioGroup.OnCheckedChangeListener {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  @BindView(R.id.rb_auto) RadioButton mRbAuto;
  @BindView(R.id.rb_off) RadioButton mRbOff;
  @BindView(R.id.rb_position) RadioButton mRbPosition;
  @BindView(R.id.rg_sex) RadioGroup mRgSex;
  @BindView(R.id.img_pre) ImageView mImgPre;
  @BindView(R.id.img_next) ImageView mImgNext;
  @BindView(R.id.show_text) TextView mShowText;
  Unbinder unbinder;

  private String mParam1;
  private String mParam2;

  public SettingPrintModelFragment() {
  }

  public static SettingPrintModelFragment newInstance(String param1, String param2) {
    SettingPrintModelFragment fragment = new SettingPrintModelFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_setting_print_model, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mShowText.setText("");
    mRgSex.setOnCheckedChangeListener(this);
    int mPrint_model = SPUtils.getInt(getActivity(), Constant.PRINT_MODEL, 0);
    switch (mPrint_model) {
      case 0:
        mRbAuto.setChecked(true);
        break;
      case 1:
        mRbOff.setChecked(true);
        break;
      case 2:
        mRbPosition.setChecked(true);
        break;
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override public void onCheckedChanged(RadioGroup mRadioGroup, int position) {
    if (isHidden()){
      return;
    }
    SettingActivity mSettingActivity = (SettingActivity) getActivity();
    if (position == R.id.rb_auto) {
      SPUtils.putInt(getActivity(), Constant.PRINT_MODEL, 0);
    } else if (position == R.id.rb_off) {
      SPUtils.putInt(getActivity(), Constant.PRINT_MODEL, 1);
    } else {
      mSettingActivity.showPrintOffet();
      SPUtils.putInt(getActivity(), Constant.PRINT_MODEL, 2);
    }
  }

  @OnClick({ R.id.img_pre, R.id.img_next }) public void onViewClicked(View view) {
    SettingActivity mSettingActivity = (SettingActivity) getActivity();
    switch (view.getId()) {
      case R.id.img_pre:
      case R.id.img_next:
        mSettingActivity.showOrgin();
        break;
    }
  }
}
