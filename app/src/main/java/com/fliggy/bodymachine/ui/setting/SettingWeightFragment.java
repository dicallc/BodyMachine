package com.fliggy.bodymachine.ui.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.utils.Constant;
import com.fliggy.utils_module.utils.SPUtils;
import me.yokeyword.fragmentation.SupportFragment;

public class SettingWeightFragment extends SupportFragment {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  @BindView(R.id.btn_weight_add) ImageView mBtnWeightAdd;
  @BindView(R.id.txt_weight) TextView mTxtWeight;
  @BindView(R.id.btn_weight_reduce) ImageView mBtnWeightReduce;
  @BindView(R.id.img_pre) ImageView mImgPre;
  @BindView(R.id.img_next) ImageView mImgNext;
  @BindView(R.id.show_text) TextView mShowText;
  Unbinder unbinder;

  private String mParam1;
  private String mParam2;
  private float mFloat;

  public SettingWeightFragment() {
  }

  public static SettingWeightFragment newInstance(String param1, String param2) {
    SettingWeightFragment fragment = new SettingWeightFragment();
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
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_setting_weight, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);

    if (!hidden) {
      mFloat = SPUtils.getFloat(getActivity(), Constant.WEIGHT_OFFEST, 0f);
      mTxtWeight.setText(mFloat +"");
      mShowText.setVisibility(View.GONE);
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({ R.id.btn_weight_add, R.id.btn_weight_reduce, R.id.img_pre, R.id.img_next })
  public void onViewClicked(View view) {
    SettingActivity mSettingActivity= (SettingActivity) getActivity();
    switch (view.getId()) {
      case R.id.btn_weight_add:
        mFloat+=1;
        mTxtWeight.setText(mFloat+"");
        SPUtils.putFloat(getActivity(), Constant.WEIGHT_OFFEST,mFloat);
        break;
      case R.id.btn_weight_reduce:
        mFloat-=1;
        mTxtWeight.setText(mFloat+"");
        SPUtils.putFloat(getActivity(), Constant.WEIGHT_OFFEST,mFloat);
        break;
      case R.id.img_pre:
        mSettingActivity.showOrgin();
        break;
      case R.id.img_next:
        mSettingActivity.showOrgin();
        break;
    }
  }
}
