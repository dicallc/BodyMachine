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
import com.fliggy.bodymachine.utils.Constant;
import com.fliggy.bodymachine.utils.ToastUtils;
import com.fliggy.bodymachine.widgets.KeyBoardViewWithLR;
import com.fliggy.utils_module.utils.SPUtils;
import org.greenrobot.eventbus.EventBus;

/**
 * 测量身高
 */
public class LoadHeightFragment extends SwiperFragment implements View.OnClickListener {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  @BindView(R.id.kb_view) KeyBoardViewWithLR mKbView;
  Unbinder unbinder;

  private String mParam1;
  private String mParam2;

  public LoadHeightFragment() {
  }

  @Override public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (!hidden){
      PlayAudio(R.raw.load_height);
    }
  }

  public static LoadHeightFragment newInstance(String param1, String param2) {
    LoadHeightFragment fragment = new LoadHeightFragment();
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
    View view = inflater.inflate(R.layout.fragment_load_height, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mKbView.getImg_next().setOnClickListener(this);
    mKbView.getImg_pre().setOnClickListener(this);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override public void onClick(View mView) {
    LoadUserActivity mLoadUserActivity= (LoadUserActivity) getActivity();
    switch (mView.getId()){
      case R.id.img_pre:
        int ids_model = SPUtils.getInt(getActivity(), Constant.SETTING_ID, 0);
        if (ids_model==0)
        mLoadUserActivity.NextPre(false);
        else{
          mLoadUserActivity.NextPre(false,false);
        }
        break;
      case R.id.img_next:
        String mValue = mKbView.getValue();
        if(90>Integer.parseInt(mValue)&&Integer.parseInt(mValue)>220){
          ToastUtils.showShortToast("输入的身高不在范围内");
          return;
        }
        EventBus.getDefault().post(new SerialEvent(SerialEvent.HEIGHT,mValue));
        mLoadUserActivity.NextPre(true);
        break;

    }
  }
}
