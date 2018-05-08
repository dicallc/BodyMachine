package com.fliggy.bodymachine.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.ShowResultActivity;
import com.fliggy.bodymachine.base.SwiperFragment;
import com.fliggy.bodymachine.model.BodyInfoModel;
import com.fliggy.bodymachine.model.SerialEvent;
import com.fliggy.bodymachine.ui.LoadUserActivity;
import com.fliggy.bodymachine.utils.Arith;
import com.fliggy.bodymachine.utils.Constant;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LoadResultFragment extends SwiperFragment {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  @BindView(R.id.bar_weight) LinearLayout mBarWeight;
  @BindView(R.id.bar_zhifan) LinearLayout mBarZhifan;
  @BindView(R.id.bar_jirou) LinearLayout mBarJirou;
  @BindView(R.id.bar_feirou) LinearLayout mBarFeirou;
  Unbinder unbinder;
  @BindView(R.id.txt_bt_one) TextView mTxtBtOne;
  @BindView(R.id.txt_bt_two) TextView mTxtBtTwo;
  @BindView(R.id.txt_bt_three) TextView mTxtBtThree;
  @BindView(R.id.txt_bt_four) TextView mTxtBtFour;
  @BindView(R.id.txt_weight) TextView mTxtWeight;
  @BindView(R.id.txt_zhifan) TextView mTxtZhifan;
  @BindView(R.id.txt_jirou) TextView mTxtJirou;
  @BindView(R.id.txt_feirou) TextView mTxtFeirou;
  @BindView(R.id.print) TextView mPrint;
  @BindView(R.id.txt_back) TextView mTxtBack;

  private String mParam1;
  private String mParam2;
  private String mSex;
  private String mAge;
  private String mHeight;
  private String mId;

  public LoadResultFragment() {
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
    EventBus.getDefault().register(this);
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
  }

  private void setViewFullScreen(LinearLayout view, float bili) {
    WindowManager manager = getActivity().getWindowManager();
    DisplayMetrics outMetrics = new DisplayMetrics();
    manager.getDefaultDisplay().getMetrics(outMetrics);
    int width = outMetrics.widthPixels;
    double f_width = width * 0.7;
    ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin);
    int mMul = Arith.mul(width * 0.7 + "", bili + "");
    layoutParams.width = mMul;
    layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT;
    view.setLayoutParams(layoutParams);
  }

  public int dp2px(float dpValue) {
    final float scale = getResources().getDisplayMetrics().density;

    return (int) (dpValue * scale + 0.5f);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    EventBus.getDefault().unregister(this);
    unbinder.unbind();
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void Event(SerialEvent messageEvent) {
    LoadUserActivity mLoadUserActivity = (LoadUserActivity) getActivity();
    switch (messageEvent.type) {
      case SerialEvent.LOAD_USER_DATA:
        //todo 还得上传数据
        BodyInfoModel mBodyInfoModel = com.fliggy.bodymachine.utils.Utils.toShowFinalResultModel(mHeight,mAge,mSex,messageEvent.content);
        Constant.CurentId = mBodyInfoModel.getId();
        mTxtWeight.setText(mBodyInfoModel.getWeight()+"kg");
        setViewFullScreen(mBarWeight, Arith.MyDiv(mBodyInfoModel.getWeight(), 180));
        mTxtZhifan.setText(mBodyInfoModel.getFat_weight()+"kg");
        setViewFullScreen(mBarZhifan, Arith.MyDiv(mBodyInfoModel.getFat_weight(), 130));
        mTxtJirou.setText(mBodyInfoModel.getMuscle_weight()+"kg");
        setViewFullScreen(mBarJirou, Arith.MyDiv(mBodyInfoModel.getMuscle_weight(), 142.5));
        mTxtFeirou.setText(mBodyInfoModel.getFat_degree());
        setViewFullScreen(mBarFeirou, Arith.MyDiv(mBodyInfoModel.getFat_degree(), 40));
        setViewFullScreen(mBarFeirou, 0.9f);
        //标准体重
        mTxtBtOne.setText("标准体重    " + mBodyInfoModel.getStander_weight()+"kg");
        //体脂百分比
        mTxtBtTwo.setText("体脂肪率    " + mBodyInfoModel.getBody_fat_percentage()+"%");
        // 身体评分
        mTxtBtThree.setText("健康指数    " + mBodyInfoModel.getBody_score());
        // 身体质量指数
        mTxtBtFour.setText("身体质量指数    " + mBodyInfoModel.getPhysique_num());

        break;
      case SerialEvent.HEIGHT:
        mHeight = messageEvent.content;
        mLoadUserActivity.getTxtTitleHeight().setText(messageEvent.content + "");
        break;
      case SerialEvent.AGE:
        mAge = messageEvent.content;
        mLoadUserActivity.getTxtTitleAge().setText(messageEvent.content + "");
        break;
      case SerialEvent.SEX:
        mSex = messageEvent.content + "";
        if (mSex.equals("1")) {
          mLoadUserActivity.getTxtTitleSex().setText("男");
        } else {
          mLoadUserActivity.getTxtTitleSex().setText("女");
        }
        break;
    }
  }

  @OnClick({ R.id.txt_back, R.id.print }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.txt_back:
        break;
      case R.id.print:
        Intent mIntent = new Intent(getActivity(), ShowResultActivity.class);
        startActivity(mIntent);
        break;
    }
  }
}
