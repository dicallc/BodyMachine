package com.fliggy.bodymachine.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.ui.LoadUserActivity;
import java.util.Timer;
import java.util.TimerTask;
import me.yokeyword.fragmentation.SupportFragment;

public class WaitStandFragment extends SupportFragment {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  Unbinder unbinder;

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
    View view = inflater.inflate(R.layout.fragment_wait_stand, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if(!hidden){
      Timer timer = new Timer();
      timer.schedule(task, 3000);
    }
  }

  TimerTask task = new TimerTask(){
    public void run(){
      LoadUserActivity mLoadUserActivity= (LoadUserActivity) getActivity();
      mLoadUserActivity.ShowMeasureUI();
    }
  };
  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}
