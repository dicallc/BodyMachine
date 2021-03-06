package com.fliggy.bodymachine.view;

import android.os.Bundle;
import android.serialport.utils.SimpleSerialPortUtil;
import android.serialport.utils.Utils;
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
import com.fliggy.bodymachine.model.SerialEvent;
import com.fliggy.bodymachine.ui.LoadUserActivity;
import com.fliggy.bodymachine.utils.Constant;
import com.fliggy.utils_module.utils.SPUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 测量体重
 */
public class LoadWeightFragment extends BaseAudioFragment {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  @BindView(R.id.txt_weight) TextView mTxtWeight;
  @BindView(R.id.img_pre) ImageView mImgPre;
  @BindView(R.id.show_text) TextView mShowText;
  @BindView(R.id.img_next) ImageView mImgNext;
  Unbinder unbinder;

  private String mParam1;
  private String mParam2;
  private boolean isLock=false;

  public LoadWeightFragment() {
  }

  public static LoadWeightFragment newInstance(String param1, String param2) {
    LoadWeightFragment fragment = new LoadWeightFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EventBus.getDefault().register(this);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
    String str = Utils.loadMacheInfoCmd();
    SimpleSerialPortUtil.getInstance().sendCmds(str);
    PlayAudio(R.raw.load_weight);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_load_weight, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    EventBus.getDefault().unregister(this);
    unbinder.unbind();
  }

  @OnClick({ R.id.img_pre, R.id.img_next }) public void onViewClicked(View view) {
   LoadUserActivity mLoadUserActivity= (LoadUserActivity) getActivity();
    switch (view.getId()) {
      case R.id.img_pre:
        mLoadUserActivity.NextPre(false);
        break;
      case R.id.img_next:
        // todo 1
        //isLock=true;
        //if (mTxtWeight.getText().toString().equals("0")||!isLock){
        //  ToastUtils.showLongToast("体重尚未测出，请稍等");
        //  return;
        //}
        int ids_model = SPUtils.getInt(getActivity(), Constant.SETTING_ID, 0);
        if (ids_model==0)
          mLoadUserActivity.NextPre(true);
        else{
          mLoadUserActivity.NextPre(true,false);
        }

        break;
    }
  }

  @Override public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if(hidden){
      isLock=false;
    }else{
      PlayAudio(R.raw.load_weight);
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void Event(SerialEvent messageEvent) {
    switch (messageEvent.type) {
      case SerialEvent.WEIGHT:
        float WEIGHT_OFFEST = SPUtils.getFloat(getActivity(), Constant.WEIGHT_OFFEST, 0f);
        float result = Float.parseFloat(messageEvent.content) - WEIGHT_OFFEST;
        mTxtWeight.setText(result+"");
        break;
      case SerialEvent.WEIGHT_LOCK:
        isLock=true;
        break;
    }
  }
}
