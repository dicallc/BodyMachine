package com.fliggy.bodymachine.ui.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.fliggy.bodymachine.utils.BigDecimalUtils;
import com.fliggy.bodymachine.utils.Constant;
import com.fliggy.utils_module.utils.SPUtils;
import me.yokeyword.fragmentation.SupportFragment;

public class SettingPrintOffestFragment extends SupportFragment {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  @BindView(R.id.btn_height_add) ImageView mBtnHeightAdd;
  @BindView(R.id.txt_height) TextView mTxtHeight;
  @BindView(R.id.btn_height_reduce) ImageView mBtnHeightReduce;
  @BindView(R.id.btn_width_add) ImageView mBtnWidthAdd;
  @BindView(R.id.txt_width) TextView mTxtWidth;
  @BindView(R.id.btn_width_reduce) ImageView mBtnWidthReduce;
  @BindView(R.id.img_pre) ImageView mImgPre;
  @BindView(R.id.img_next) ImageView mImgNext;
  @BindView(R.id.show_text) TextView mShowText;
  Unbinder unbinder;

  private String mParam1;
  private String mParam2;
  private float mWidthFloat;
  private float mHeightFloat;

  public SettingPrintOffestFragment() {
  }

  public static SettingPrintOffestFragment newInstance(String param1, String param2) {
    SettingPrintOffestFragment fragment = new SettingPrintOffestFragment();
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
    View view = inflater.inflate(R.layout.fragment_setting_print_offest, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mWidthFloat = SPUtils.getFloat(getActivity(), Constant.PRINT_OFFSET_WIDTH, 0f);
    mHeightFloat = SPUtils.getFloat(getActivity(), Constant.PRINT_OFFSET_HEIGHT, 0f);
    mTxtWidth.setText(mWidthFloat +"");
    mTxtHeight.setText(mHeightFloat +"");
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({
      R.id.btn_height_add, R.id.btn_height_reduce, R.id.btn_width_add, R.id.btn_width_reduce,
      R.id.img_pre, R.id.img_next
  }) public void onViewClicked(View view) {
    SettingActivity mSettingActivity= (SettingActivity) getActivity();
    switch (view.getId()) {
      case R.id.btn_height_add:
        mHeightFloat= BigDecimalUtils.addFloat(mHeightFloat,0.1f,1);
        mTxtHeight.setText(mHeightFloat +"");
        break;
      case R.id.btn_height_reduce:
        mHeightFloat= (float) BigDecimalUtils.sub(mHeightFloat,0.1f,1);
        mTxtHeight.setText(mHeightFloat +"");
        break;
      case R.id.btn_width_add:
        mWidthFloat= BigDecimalUtils.addFloat(mWidthFloat,0.1f,1);
        mTxtWidth.setText(mWidthFloat +"");
        break;
      case R.id.btn_width_reduce:
        mWidthFloat= (float) BigDecimalUtils.sub(mWidthFloat,0.1f,1);
        mTxtWidth.setText(mWidthFloat +"");
        break;
      case R.id.img_pre:
        mSettingActivity.gonePrintOffet();
        break;
      case R.id.img_next:
        SPUtils.putFloat(getActivity(), Constant.PRINT_OFFSET_WIDTH, mWidthFloat);
        SPUtils.putFloat(getActivity(), Constant.PRINT_OFFSET_HEIGHT, mWidthFloat);
        mSettingActivity.gonePrintOffet();
        break;
    }
  }
}
