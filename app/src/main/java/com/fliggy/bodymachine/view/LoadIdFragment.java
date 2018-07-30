package com.fliggy.bodymachine.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.base.SwiperFragment;
import com.fliggy.bodymachine.ui.LoadUserActivity;
import com.fliggy.bodymachine.utils.Constant;
import com.fliggy.bodymachine.utils.ToastUtils;

public class LoadIdFragment extends SwiperFragment {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  @BindView(R.id.et_id) EditText mEtId;
  Unbinder unbinder;
  @BindView(R.id.img_pre) ImageView mImgPre;
  @BindView(R.id.img_next) ImageView mImgNext;
  @BindView(R.id.show_text) TextView mShowText;

  private String mParam1;
  private String mParam2;

  public LoadIdFragment() {
  }

  public static LoadIdFragment newInstance(String param1, String param2) {
    LoadIdFragment fragment = new LoadIdFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (!hidden){
      mEtId.setText("");
      mEtId.setSelection(mEtId.getText().length());

    }
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
    View view = inflater.inflate(R.layout.fragment_load_id, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mShowText.setText("请输入自己的姓名或者手机号");

    mEtId.setOnEditorActionListener(mOnEditorActionListener);
  }
  TextView.OnEditorActionListener mOnEditorActionListener=new TextView.OnEditorActionListener() {

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
      //当actionId == XX_SEND 或者 XX_DONE时都触发
      //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
      //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
      if (actionId == EditorInfo.IME_ACTION_SEND
          || actionId == EditorInfo.IME_ACTION_DONE
          || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
        //处理事件
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEtId,InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(mEtId.getWindowToken(), 0);
      }
      return false;
    }
  };

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({ R.id.img_pre, R.id.img_next }) public void onViewClicked(View view) {
    String id = mEtId.getText().toString();
    if (TextUtils.isEmpty(id)) {
      ToastUtils.showLongToast("当前未输入id");
      return;
    }
    Constant.MainId = id;
    LoadUserActivity mLoadUserActivity = (LoadUserActivity) getActivity();
    switch (view.getId()) {
      case R.id.img_pre:
        mLoadUserActivity.NextPre(false);
        break;
      case R.id.img_next:
        mLoadUserActivity.NextPre(true);
        break;
    }
  }
}
