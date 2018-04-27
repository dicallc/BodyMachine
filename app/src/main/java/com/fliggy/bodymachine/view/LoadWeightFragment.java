package com.fliggy.bodymachine.view;

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
import com.fliggy.bodymachine.SwiperFragment;
import com.fliggy.bodymachine.ui.LoadUserActivity;

public class LoadWeightFragment extends SwiperFragment {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  @BindView(R.id.txt_weight) TextView mTxtWeight;
  @BindView(R.id.img_pre) ImageView mImgPre;
  @BindView(R.id.devider_top) View mDeviderTop;
  @BindView(R.id.show_text) TextView mShowText;
  @BindView(R.id.devider_bottom) View mDeviderBottom;
  @BindView(R.id.img_next) ImageView mImgNext;
  Unbinder unbinder;

  private String mParam1;
  private String mParam2;

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
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_load_weight, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({ R.id.img_pre, R.id.img_next }) public void onViewClicked(View view) {
   LoadUserActivity mLoadUserActivity= (LoadUserActivity) getActivity();
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
