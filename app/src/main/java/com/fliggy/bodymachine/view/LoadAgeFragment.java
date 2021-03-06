package com.fliggy.bodymachine.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.base.SwiperFragment;
import com.fliggy.bodymachine.model.SerialEvent;
import com.fliggy.bodymachine.ui.LoadUserActivity;
import com.fliggy.bodymachine.utils.ToastUtils;
import com.fliggy.bodymachine.widgets.KeyBoardViewWithLR;
import org.greenrobot.eventbus.EventBus;

/**
 * 输入年龄
 */
public class LoadAgeFragment extends SwiperFragment implements View.OnClickListener {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  @BindView(R.id.age_keyboad) KeyBoardViewWithLR mAgeKeyboad;
  Unbinder unbinder;

  private String mParam1;
  private String mParam2;

  public LoadAgeFragment() {
  }

  public static LoadAgeFragment newInstance(String param1, String param2) {
    LoadAgeFragment fragment = new LoadAgeFragment();
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
    View view = inflater.inflate(R.layout.fragment_load_age, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mAgeKeyboad.setTips("请输入年龄");
    mAgeKeyboad.getImg_pre().setOnClickListener(this);
    mAgeKeyboad.getImg_next().setOnClickListener(this);
    mAgeKeyboad.getRl_bg().setBackgroundResource(R.mipmap.bg_age_c);
    mAgeKeyboad.setWei();
    mAgeKeyboad.getTxt_wei().setText("");
  }
  @Override public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (!hidden){
      PlayAudio(R.raw.load_age);
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override public void onClick(View mView) {
    LoadUserActivity mLoadUserActivity= (LoadUserActivity) getActivity();
    switch (mView.getId()){
      case R.id.img_pre:
        mLoadUserActivity.NextPre(false);
        break;
      case R.id.img_next:
        String mValue = mAgeKeyboad.getValue();
        if(Integer.parseInt(mValue)<3&&Integer.parseInt(mValue)>99){
          ToastUtils.showShortToast("输入的年龄不在范围内");
          return;
        }
        EventBus.getDefault().post(new SerialEvent(SerialEvent.AGE,mValue));
        mLoadUserActivity.NextPre(true);
        break;

    }
  }
}
