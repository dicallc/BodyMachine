package com.fliggy.bodymachine.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.fliggy.bodymachine.R;
import me.yokeyword.fragmentation.SupportFragment;

public class WaitStandFragment extends SupportFragment {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  @BindView(R.id.ly_tips) LinearLayout mLyTips;
  @BindView(R.id.img_pre) ImageView mImgPre;
  @BindView(R.id.devider_top) View mDeviderTop;
  @BindView(R.id.show_text) TextView mShowText;
  @BindView(R.id.devider_bottom) View mDeviderBottom;
  @BindView(R.id.img_next) ImageView mImgNext;
  Unbinder unbinder;

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  public WaitStandFragment() {
    // Required empty public constructor
  }

  public static WaitStandFragment newInstance(String param1, String param2) {
    WaitStandFragment fragment = new WaitStandFragment();
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
    View view = inflater.inflate(R.layout.fragment_wait_stand, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}
