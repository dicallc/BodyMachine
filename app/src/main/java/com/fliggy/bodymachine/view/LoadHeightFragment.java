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
import com.fliggy.bodymachine.ui.LoadUserActivity;
import com.fliggy.bodymachine.widgets.KeyBoardViewWithLR;
import me.yokeyword.fragmentation.SupportFragment;

public class LoadHeightFragment extends SupportFragment implements View.OnClickListener {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  @BindView(R.id.kb_view) KeyBoardViewWithLR mKbView;
  Unbinder unbinder;

  private String mParam1;
  private String mParam2;

  public LoadHeightFragment() {
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
        mLoadUserActivity.NextPre(false);
        break;
      case R.id.img_next:
        mLoadUserActivity.NextPre(true);
        break;

    }
  }
}
