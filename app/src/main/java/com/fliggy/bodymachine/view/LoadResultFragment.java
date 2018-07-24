package com.fliggy.bodymachine.view;

import android.os.Bundle;
import android.serialport.utils.SimpleSerialPortUtil;
import android.serialport.utils.Utils;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.fliggy.bodymachine.base.PrintBaseFragment;
import com.fliggy.bodymachine.dao.Dao;
import com.fliggy.bodymachine.model.BodyInfoModel;
import com.fliggy.bodymachine.model.MachineModel;
import com.fliggy.bodymachine.model.MsgModel;
import com.fliggy.bodymachine.model.SerialEvent;
import com.fliggy.bodymachine.ui.LoadUserActivity;
import com.fliggy.bodymachine.utils.Arith;
import com.fliggy.bodymachine.utils.Constant;
import com.fliggy.bodymachine.utils.ToastUtils;
import com.fliggy.http_module.http.callback.DaoCallBack;
import com.socks.library.KLog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LoadResultFragment extends PrintBaseFragment {
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
  private String mMache_id;
  private BodyInfoModel mBodyInfoModel;

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
    TestFunction();
  }

  private void TestFunction() {
    mBodyInfoModel = com.fliggy.bodymachine.utils.Utils.toShowFinalResultModel("176", "19", "1",
        "5A0360036C00000D340D1107CE07EB00E6000001ED000000000000000000000022013600690000038A018D01BA0288005A343400000000009680E380B20000019F017F019E0182018000DB001600160058005A0000000000000000000800000000000025");
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

  @Override public void onDestroyView() {
    super.onDestroyView();
    EventBus.getDefault().unregister(this);
    unbinder.unbind();
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void Event(SerialEvent messageEvent) {
    LoadUserActivity mLoadUserActivity = (LoadUserActivity) getActivity();
    switch (messageEvent.type) {
      case SerialEvent.LOAD_USER_DATA:
        Dao.postData(messageEvent.content, new DaoCallBack<MachineModel>() {
          @Override public void onSuccess(int code, MachineModel result) {
            KLog.e("成功了");
          }

          @Override public void onFail(int code, String result) {
            KLog.e("失败了");
          }
        });
        mBodyInfoModel =
            com.fliggy.bodymachine.utils.Utils.toShowFinalResultModel(mHeight, mAge, mSex,
                messageEvent.content);

        Constant.CurentId = mBodyInfoModel.getId();
        if (TextUtils.isEmpty(mBodyInfoModel.getId())) {
          ToastUtils.showShortToast("数据库初始化失败");
          return;
        }
        if (TextUtils.isEmpty(mBodyInfoModel.getWeight())) {
          ToastUtils.showShortToast("模块获取数据为空");
          return;
        }
        if ("0".equals(mBodyInfoModel.getFat_weight())) {
          ToastUtils.showShortToast("模块获取数据为0");
          return;
        }
        mTxtWeight.setText(mBodyInfoModel.getWeight() + "kg");
        setViewFullScreen(mBarWeight, Arith.MyDiv(mBodyInfoModel.getWeight(), 180));
        mTxtZhifan.setText(mBodyInfoModel.getFat_weight() + "kg");
        setViewFullScreen(mBarZhifan, Arith.MyDiv(mBodyInfoModel.getFat_weight(), 130));
        mTxtJirou.setText(mBodyInfoModel.getMuscle_weight() + "kg");
        setViewFullScreen(mBarJirou, Arith.MyDiv(mBodyInfoModel.getMuscle_weight(), 142.5));
        mTxtFeirou.setText(mBodyInfoModel.getFat_degree());
        setViewFullScreen(mBarFeirou, Arith.MyDiv(mBodyInfoModel.getFat_degree(), 40));
        setViewFullScreen(mBarFeirou, 0.9f);
        //标准体重
        mTxtBtOne.setText("标准体重    " + mBodyInfoModel.getStander_weight() + "kg");
        //体脂百分比
        mTxtBtTwo.setText("体脂肪率    " + mBodyInfoModel.getBody_fat_percentage() + "%");
        // 身体评分
        mTxtBtThree.setText("健康指数    " + mBodyInfoModel.getBody_score());
        // 身体质量指数
        mTxtBtFour.setText("身体质量指数    " + mBodyInfoModel.getPhysique_num());
        if (TextUtils.isEmpty(mMache_id)) {
          ToastUtils.showShortToast("机器id为空");
          return;
        }
        Dao.postCelect(mBodyInfoModel, mMache_id, new DaoCallBack<MsgModel>() {
          @Override public void onSuccess(int code, MsgModel result) {
            ToastUtils.showShortToast("上传数据成功");
          }

          @Override public void onFail(int code, String result) {
            ToastUtils.showShortToast("上传数据失败");
          }
        });
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
        KLog.e("dicallc 已经发生了用户信息");
        mSex = messageEvent.content + "";
        if (mSex.equals("1")) {
          mLoadUserActivity.getTxtTitleSex().setText("男");
        } else {
          mLoadUserActivity.getTxtTitleSex().setText("女");
        }
        String str = Utils.loadUserInfoCmd(mHeight, mAge, mSex, "00");
        SimpleSerialPortUtil.getInstance().sendCmds(str);

        break;
      case SerialEvent.MACHE_INFO:
        mMache_id = messageEvent.mache_id;
        break;
    }
  }

  @OnClick({ R.id.txt_back, R.id.print }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.txt_back:
        LoadUserActivity mLoadUserActivity = (LoadUserActivity) getActivity();
        mLoadUserActivity.toOrgin();
        break;
      case R.id.print:
        toPrint(mBodyInfoModel);
        break;
    }
  }
}
