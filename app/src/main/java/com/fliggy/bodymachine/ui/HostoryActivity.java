package com.fliggy.bodymachine.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.adapter.HistoryAdapter;
import com.fliggy.bodymachine.model.BodyInfoModel;
import com.fliggy.bodymachine.utils.Constant;
import com.fliggy.bodymachine.utils.Utils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;

public class HostoryActivity extends AppCompatActivity {

  @BindView(R.id.txt_load_data) TextView mTxtLoadData;
  @BindView(R.id.txt_title_id) TextView mTxtTitleId;
  @BindView(R.id.txt_title_height) TextView mTxtTitleHeight;
  @BindView(R.id.txt_title_age) TextView mTxtTitleAge;
  @BindView(R.id.txt_title_sex) TextView mTxtTitleSex;
  @BindView(R.id.ly_title_person_msg) LinearLayout mLyTitlePersonMsg;
  @BindView(R.id.chart_weight) LineChart mChartWeight;
  @BindView(R.id.chat_gugeji) LineChart mChatGugeji;
  @BindView(R.id.chat_tizhifang) LineChart mChatTizhifang;
  @BindView(R.id.ly_center) LinearLayout mLyCenter;
  @BindView(R.id.img_back) ImageView mImgBack;
  @BindView(R.id.img_test) ImageView mImgTest;
  @BindView(R.id.fl_container) RelativeLayout mFlContainer;
  @BindView(R.id.rl_contain) RelativeLayout mRlContain;
  @BindView(R.id.rl_time) RecyclerView mRlTime;
  private HistoryAdapter mHistoryAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_hostory);
    ButterKnife.bind(this);
    initView();
    initData();
  }

  private void initView() {
    initChart(mChartWeight);

    initChart(mChatGugeji);

    initChart(mChatTizhifang);

    initTime();

  }

  private void initTime() {
    mHistoryAdapter = new HistoryAdapter(null);
    //创建LinearLayoutManager
    LinearLayoutManager manager = new LinearLayoutManager(this);
    //设置为横向滑动
    manager.setOrientation(LinearLayoutManager.HORIZONTAL);
    //设置
    mRlTime.setLayoutManager(manager);
    mRlTime.setAdapter(mHistoryAdapter);
  }

  private void initChart(LineChart mChart) {
    mChart.getDescription().setEnabled(false);
    mChart.setTouchEnabled(false);
    mChart.setDragDecelerationFrictionCoef(0.9f);
    mChart.setDragEnabled(false);
    mChart.setScaleEnabled(false);
    mChart.setDrawGridBackground(false);
    mChart.setHighlightPerDragEnabled(true);
    mChart.setPinchZoom(true);
    mChart.getLegend().setEnabled(false);
    YAxis y = mChart.getAxisLeft();
    y.setDrawGridLines(false);
    y.setDrawAxisLine(false);
    mChart.getLegend().setEnabled(false);
    mChart.getAxisRight().setEnabled(false);
    mChart.getAxisLeft().setEnabled(false);
    mChart.getXAxis().setEnabled(false);
    mChart.getDescription().setEnabled(false);
  }

  private void toLookidData() {

  }

  private void initData() {
    final String mMainId = Constant.MainId;
    final Realm mRealm = Realm.getDefaultInstance();
    mRealm.executeTransaction(new Realm.Transaction() {
      @Override public void execute(Realm realm) {
        RealmResults<BodyInfoModel> mAll =
            realm.where(BodyInfoModel.class).equalTo("user_id", mMainId).findAll();
        List<BodyInfoModel> mBodyInfoModels = null;
        if (mAll.size() > 7) {
          mBodyInfoModels = mAll.subList(0, 8);
        } else {
          mBodyInfoModels = mAll.subList(0,mAll.size());
        }
        //选最后一条的数据做最后参考
        BodyInfoModel mBodyInfoModel = mAll.get(mAll.size() - 1);
        mTxtTitleId.setText("ID"+mBodyInfoModel.getUser_id());
        mTxtTitleHeight.setText(mBodyInfoModel.getHeight());
        mTxtTitleAge.setText(mBodyInfoModel.getAge());
        if (mBodyInfoModel.getSex().equals("1")) {
          mTxtTitleSex.setText("男");
        } else {
          mTxtTitleSex.setText("女");
        }
        setWeightData(mChartWeight,mBodyInfoModels);
        setGugejiData(mChatGugeji, mBodyInfoModels);
        setTizhifang(mChatTizhifang, mBodyInfoModels);
        List<String> mStrings = new ArrayList<>();
        for (int i = 0; i < mBodyInfoModels.size(); i++) {
          mStrings.add(Utils.getDateToString(Long.parseLong(mBodyInfoModels.get(i).getTime()),"yy-MM-dd HH:mm"));
        }
        mHistoryAdapter.addData(mStrings);
      }
    });
  }

  private void setWeightData(LineChart mChart,List<BodyInfoModel> mBodyInfoModels) {

    ArrayList<Entry> yVals1 = new ArrayList<Entry>();

    for (int i = 0; i < mBodyInfoModels.size(); i++) {
      yVals1.add(new Entry(i,Float.parseFloat(mBodyInfoModels.get(i).getWeight()) ));
    }
    LineDataSet set1;

    if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
      set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
      set1.setValues(yVals1);
      mChart.getData().notifyDataChanged();
      mChart.notifyDataSetChanged();
    } else {
      set1 = new LineDataSet(yVals1, "DataSet 1");
      XAxis xAxisPrice = mChart.getXAxis();
      xAxisPrice.setLabelCount(7, true);
      set1.setAxisDependency(YAxis.AxisDependency.LEFT);
      set1.setColor(Color.parseColor("#0087ff"));
      set1.setCircleColor(Color.WHITE);
      set1.setLineWidth(2f);
      set1.setCircleRadius(3f);
      set1.setFillAlpha(65);
      set1.setFillColor(Color.parseColor("#0087ff"));
      set1.setHighLightColor(Color.rgb(244, 117, 117));
      set1.setDrawCircleHole(false);
      LineData data = new LineData(set1);
      data.setValueTextColor(Color.WHITE);
      data.setDrawValues(true);
      data.setValueTextSize(18f);
      mChart.setData(data);
    }
  }

  private void setGugejiData(LineChart mChart ,List<BodyInfoModel> mBodyInfoModels) {
    ArrayList<Entry> yVals1 = new ArrayList<Entry>();

    for (int i = 0; i < mBodyInfoModels.size(); i++) {
      yVals1.add(new Entry(i,Float.parseFloat(mBodyInfoModels.get(i).getSkeletal_muscle()) ));
    }
    LineDataSet set1;

    if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
      set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
      set1.setValues(yVals1);
      mChart.getData().notifyDataChanged();
      mChart.notifyDataSetChanged();
    } else {
      set1 = new LineDataSet(yVals1, "DataSet 1");
      XAxis xAxisPrice = mChart.getXAxis();
      xAxisPrice.setLabelCount(7, true);
      set1.setAxisDependency(YAxis.AxisDependency.LEFT);
      set1.setColor(Color.parseColor("#e84576"));
      set1.setCircleColor(Color.WHITE);
      set1.setLineWidth(2f);
      set1.setCircleRadius(3f);
      set1.setFillAlpha(65);
      set1.setHighLightColor(Color.rgb(244, 117, 117));
      set1.setDrawCircleHole(false);
      LineData data = new LineData(set1);
      data.setDrawValues(true);
      data.setValueTextColor(Color.WHITE);
      data.setValueTextSize(18f);
      mChart.setData(data);
    }
  }

  private void setTizhifang(LineChart mChart,List<BodyInfoModel> mBodyInfoModels) {

    ArrayList<Entry> yVals1 = new ArrayList<Entry>();

    for (int i = 0; i < mBodyInfoModels.size(); i++) {
      BodyInfoModel mBodyInfoModel = mBodyInfoModels.get(i);
      yVals1.add(new Entry(i, Float.parseFloat(mBodyInfoModel.getFat_weight())));
    }
    LineDataSet set1;

    if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
      set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
      set1.setValues(yVals1);
      mChart.getData().notifyDataChanged();
      mChart.notifyDataSetChanged();
    } else {
      set1 = new LineDataSet(yVals1, "DataSet 1");
      XAxis xAxisPrice = mChart.getXAxis();
      xAxisPrice.setLabelCount(7, true);
      set1.setAxisDependency(YAxis.AxisDependency.LEFT);
      set1.setColor(Color.parseColor("#e8bd21"));
      set1.setCircleColor(Color.WHITE);
      set1.setLineWidth(2f);
      set1.setCircleRadius(3f);
      set1.setFillAlpha(65);
      set1.setFillColor(Color.parseColor("#e8bd21"));
      set1.setHighLightColor(Color.rgb(244, 117, 117));
      set1.setDrawCircleHole(false);
      LineData data = new LineData(set1);
      data.setDrawValues(true);
      data.setValueTextColor(Color.WHITE);
      data.setValueTextSize(18f);
      mChart.setData(data);
    }
  }

  @OnClick({ R.id.img_back, R.id.img_test }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.img_back:
        finish();
        break;
      case R.id.img_test:
        finish();
        break;
    }
  }
}
