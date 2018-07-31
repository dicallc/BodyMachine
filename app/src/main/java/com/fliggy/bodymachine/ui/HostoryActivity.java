package com.fliggy.bodymachine.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.model.BodyInfoModel;
import com.fliggy.bodymachine.utils.Constant;
import com.github.mikephil.charting.charts.LineChart;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.List;

public class HostoryActivity extends AppCompatActivity {

  @BindView(R.id.txt_load_data) TextView mTxtLoadData;
  @BindView(R.id.txt_title_id) TextView mTxtTitleId;
  @BindView(R.id.txt_title_height) TextView mTxtTitleHeight;
  @BindView(R.id.txt_title_age) TextView mTxtTitleAge;
  @BindView(R.id.txt_title_sex) TextView mTxtTitleSex;
  @BindView(R.id.ly_title_person_msg) LinearLayout mLyTitlePersonMsg;
  @BindView(R.id.logo) ImageView mLogo;
  @BindView(R.id.chart_weight) LineChart mChartWeight;
  @BindView(R.id.chat_gugeji) LineChart mChatGugeji;
  @BindView(R.id.chat_tizhifang) LineChart mChatTizhifang;
  @BindView(R.id.ly_center) LinearLayout mLyCenter;
  @BindView(R.id.img_back) ImageView mImgBack;
  @BindView(R.id.img_test) ImageView mImgTest;
  @BindView(R.id.fl_container) RelativeLayout mFlContainer;
  @BindView(R.id.rl_contain) RelativeLayout mRlContain;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_hostory);
    ButterKnife.bind(this);
    initData();
  }
  private void toLookidData() {

  }
  private void initData() {
    final String mMainId = Constant.MainId;
    final Realm mRealm = Realm.getDefaultInstance();
    mRealm.executeTransaction(new Realm.Transaction() {



      @Override
      public void execute(Realm realm) {
        RealmResults<BodyInfoModel> mAll = realm.where(BodyInfoModel.class)
            .equalTo("id", mMainId).findAll();
         List<BodyInfoModel> mBodyInfoModels = null;
        if(mAll.size()>10){
          mBodyInfoModels = mAll.subList(0, 10);
        }else{
          mBodyInfoModels=mAll;
        }

      }
    });
  }
}
