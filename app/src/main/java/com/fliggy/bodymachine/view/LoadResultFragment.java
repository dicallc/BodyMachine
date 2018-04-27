package com.fliggy.bodymachine.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.SwiperFragment;
import com.fliggy.bodymachine.utils.Arith;

public class LoadResultFragment extends SwiperFragment {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  @BindView(R.id.bar_weight) LinearLayout mBarWeight;
  @BindView(R.id.bar_zhifan) LinearLayout mBarZhifan;
  @BindView(R.id.bar_jirou) LinearLayout  mBarJirou;
  @BindView(R.id.bar_feirou) LinearLayout mBarFeirou;
  Unbinder unbinder;

  private String mParam1;
  private String mParam2;

  public LoadResultFragment() {
    // Required empty public constructor
  }

  public static LoadResultFragment newInstance(String param1, String param2) {
    LoadResultFragment fragment = new LoadResultFragment();
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
    View view = inflater.inflate(R.layout.fragment_load_result, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setViewFullScreen(mBarWeight,0.7f);
    setViewFullScreen(mBarZhifan,0.3f);
    setViewFullScreen(mBarJirou,0.2f);
    setViewFullScreen(mBarFeirou,0.9f);
  }
  private void setViewFullScreen(LinearLayout view,float bili) {
    WindowManager manager = getActivity().getWindowManager();
    DisplayMetrics outMetrics = new DisplayMetrics();
    manager.getDefaultDisplay().getMetrics(outMetrics);
    int width = outMetrics.widthPixels;
    double f_width = width * 0.7;
    ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin);
    int mMul = Arith.mul(width * 0.7+"", bili+"");
    layoutParams.width =mMul;
    layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT ;
    view.setLayoutParams(layoutParams);
  }
  public  int dp2px(float dpValue){
    final float scale=getResources().getDisplayMetrics().density;

    return (int)(dpValue*scale+0.5f);
  }
  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}
