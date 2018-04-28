package com.fliggy.bodymachine.view;

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
import com.fliggy.bodymachine.base.SwiperFragment;
import com.fliggy.bodymachine.model.SerialEvent;
import com.fliggy.bodymachine.ui.LoadUserActivity;
import com.fliggy.bodymachine.utils.ToastUtils;
import org.greenrobot.eventbus.EventBus;

/**
 * 输入性别
 */
public class LoadMaleFragment extends SwiperFragment implements RadioGroup.OnCheckedChangeListener {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  @BindView(R.id.img_pre) ImageView mImgPre;
  @BindView(R.id.devider_top) View mDeviderTop;
  @BindView(R.id.show_text) TextView mShowText;
  @BindView(R.id.devider_bottom) View mDeviderBottom;
  @BindView(R.id.img_next) ImageView mImgNext;
  Unbinder unbinder;
  @BindView(R.id.txt_sub) TextView mTxtSub;
  @BindView(R.id.txt_main) TextView mTxtMain;
  @BindView(R.id.rb_gender) RadioButton mRbGender;
  @BindView(R.id.rb_femail) RadioButton mRbFemail;
  @BindView(R.id.rg_sex) RadioGroup mRgSex;

  private String mParam1;
  private String mParam2;

  public LoadMaleFragment() {
  }

  public static LoadMaleFragment newInstance(String param1, String param2) {
    LoadMaleFragment fragment = new LoadMaleFragment();
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
    View view = inflater.inflate(R.layout.fragment_load_male, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mShowText.setText("请选择性别");
    mRgSex.setOnCheckedChangeListener(this);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({ R.id.img_pre, R.id.img_next }) public void onViewClicked(View view) {
    LoadUserActivity mLoadUserActivity = (LoadUserActivity) getActivity();
    switch (view.getId()) {
      case R.id.img_pre:
        mLoadUserActivity.NextPre(false);
        break;
      case R.id.img_next:
        int mId = mRgSex.getCheckedRadioButtonId();
        String femail = "";
        if(mId==R.id.rb_gender){
          femail = "1";
        }else if(mId==R.id.rb_femail){
          femail = "0";
        }else{
          ToastUtils.showShortToast("请选择性别");
          return;
        }
        EventBus.getDefault().post(new SerialEvent(SerialEvent.SEX,femail));
        mLoadUserActivity.NextPre(true);
        break;
    }
  }

  @Override public void onCheckedChanged(RadioGroup mRadioGroup, int position) {
    if (position == R.id.rb_gender) {
      mTxtMain.setText("GENDER");
      mTxtSub.setText("FEMALE");
    } else {
      mTxtMain.setText("FEMALE");
      mTxtSub.setText("GENDER");
    }
  }
}
