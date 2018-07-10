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

public class SettingIdFragment extends SupportFragment
    implements RadioGroup.OnCheckedChangeListener {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  @BindView(R.id.rb_on) RadioButton mRbOn;
  @BindView(R.id.rb_off) RadioButton mRbOff;
  @BindView(R.id.rg_id) RadioGroup mRgId;
  @BindView(R.id.img_pre) ImageView mImgPre;
  @BindView(R.id.img_next) ImageView mImgNext;
  @BindView(R.id.show_text) TextView mShowText;
  Unbinder unbinder;

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  public SettingIdFragment() {
    // Required empty public constructor
  }

  public static SettingIdFragment newInstance(String param1, String param2) {
    SettingIdFragment fragment = new SettingIdFragment();
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
    View view = inflater.inflate(R.layout.fragment_setting_id, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mRgId.setOnCheckedChangeListener(this);
    mShowText.setVisibility(View.GONE);
    int ids_model = SPUtils.getInt(getActivity(), Constant.SETTING_ID, 0);
    switch (ids_model) {
      case 0:
        mRbOn.setChecked(true);
        break;
      case 1:
        mRbOff.setChecked(true);
        break;
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override public void onCheckedChanged(RadioGroup mRadioGroup, int position) {
    if (isHidden()) {
      return;
    }
    if (position == R.id.rb_on) {
      SPUtils.putInt(getActivity(), Constant.SETTING_ID, 0);
    } else {
      SPUtils.putInt(getActivity(), Constant.SETTING_ID, 1);
    }
  }

  @OnClick({ R.id.img_next, R.id.img_pre }) public void onViewClicked(View view) {
    SettingActivity mSettingActivity = (SettingActivity) getActivity();
    switch (view.getId()) {
      case R.id.img_pre:
      case R.id.img_next:
        mSettingActivity.showOrgin();
        break;
    }
  }
}
