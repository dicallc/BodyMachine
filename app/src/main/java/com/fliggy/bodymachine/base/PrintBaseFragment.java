package com.fliggy.bodymachine.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.adapter.PrintHistoryAdapter;
import com.fliggy.bodymachine.model.BodyInfoModel;
import com.fliggy.bodymachine.model.DeviderModel;
import com.fliggy.bodymachine.utils.Arith;
import com.fliggy.bodymachine.utils.BigDecimalUtils;
import com.fliggy.bodymachine.utils.Constant;
import com.fliggy.bodymachine.utils.DataSource;
import com.fliggy.bodymachine.utils.TiZhiData;
import com.fliggy.bodymachine.utils.Utils;
import com.fliggy.bodymachine.widgets.CareboDoubleLbsView;
import com.fliggy.bodymachine.widgets.CareboLbsBaseView;
import com.fliggy.bodymachine.widgets.CareboLbsFatView;
import com.fliggy.bodymachine.widgets.CareboLbsView;
import com.fliggy.bodymachine.widgets.CareboLbsWaistToHipView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.socks.library.KLog;
import io.realm.Realm;
import io.realm.RealmResults;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by dicallc on 2018/7/21 0021.
 */
public class PrintBaseFragment extends SwiperFragment {

  private Activity mActivity;
  private View mView;
  private int mD_age;
  /**
   * 体脂肪率 坐标
   */
  private int mFatWeighCoordinate;
  private int mPhysiqueNumDeviderPercentCoordinate;
  private TextView show_core;
  private RecyclerView rl_time;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mActivity = getActivity();
  }

  protected String getTextOfThreshold(String str, String low, String high) {
    return str + "\n" + "(" + low + "-" + high + ")";
  }

  protected String getTextOfThreshold(String str, String threshold) {
    return str + "\n" + "(" + threshold + ")";
  }

  private TextView txtId;
  private TextView txtHeight;
  private TextView txtAge;
  private TextView txtMale;
  private TextView txtTestTime;
  private ConstraintLayout constraintLayout;
  private TextView txtAllBodyWaterTest;
  private TextView txtAllBodyWater;
  private TextView txtJirouliang;
  private TextView txtQuzhitizhong;
  private TextView txtTizhifangtizhong;
  private TextView txtDanbaizhiTest;
  private TextView txtWujiyanTest;
  private TextView txt_tizhong_one;
  private CareboLbsBaseView lbsTizhong;
  private CareboLbsView lbsGugeji;
  private CareboLbsView lbsTizhifang;
  private CareboLbsView lbsZhiliangzhishu;
  private CareboLbsView lbsTizhiPercent;
  private CareboDoubleLbsView dlbsRightTop;
  private CareboDoubleLbsView dlbsLeftTop;
  private CareboDoubleLbsView dlbsBody;
  private CareboDoubleLbsView dlbsRightBottom;
  private CareboDoubleLbsView dlbsLeftBottom;
  private CareboLbsWaistToHipView lbsYaotunBi;
  private CareboLbsFatView lbsZhifanLevel;
  private LineChart chartWeight;
  private LineChart chartGuluoji;
  private LineChart chartTizhifan;
  private TextView txtCgAimWeight;
  private TextView txtCgAimWeightWei;
  private TextView txtCgWeightControl;
  private TextView txtCgWeightControlWei;
  private TextView txtCgZhifan;
  private TextView txtCgZhifanWei;
  private TextView txtCgJirou;
  private TextView txtCgJirouWei;
  private TextView txtRightTopId;
  private TextView txtLeftTopId;
  private TextView txtBodyId;
  private TextView txtRightBtId;
  private TextView txtLeftBtId;
  private TextView txtRightTopId50;
  private TextView txtLeftTopId50;
  private TextView txtBodyId50;
  private TextView txtRightBtId50;
  private TextView txtLeftBtId50;
  private TextView txtRightTopId250;
  private TextView txtLeftTopId250;
  private TextView txtBodyId250;
  private TextView txtRightBtId250;
  private TextView txtLeftBtId250;

  protected void toPrint(BodyInfoModel mBodyInfoModel) {
    DisplayMetrics metric = new DisplayMetrics();
    mActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);
    int width = metric.heightPixels;// 屏幕宽度（像素）
    int height = metric.widthPixels;// 屏幕高度（像素）
    mView = LayoutInflater.from(mActivity).inflate(R.layout.print_layout_v2, null, false);
    initBase(mBodyInfoModel);
    initBodyCompostionAnalysis(mBodyInfoModel);
    //身体质量指数
    lbsZhiliangzhishu = (CareboLbsView) mView.findViewById(R.id.lbs_zhiliangzhishu);
    lbsZhiliangzhishu.setData(DataSource.getPhysiqueNumDeviderPercent(DataSource.getPhysiqueNum(),
        mBodyInfoModel.getPhysique_num(), getResources().getColor(R.color.black)));
    //体脂百分比
    initTiZhiPecent(mBodyInfoModel);

    dlbsRightTop = (CareboDoubleLbsView) mView.findViewById(R.id.dlbs_right_top);
    dlbsLeftTop = (CareboDoubleLbsView) mView.findViewById(R.id.dlbs_left_top);
    dlbsBody = (CareboDoubleLbsView) mView.findViewById(R.id.dlbs_body);
    dlbsRightBottom = (CareboDoubleLbsView) mView.findViewById(R.id.dlbs_right_bottom);
    dlbsLeftBottom = (CareboDoubleLbsView) mView.findViewById(R.id.dlbs_left_bottom);
    lbsYaotunBi = (CareboLbsWaistToHipView) mView.findViewById(R.id.lbs_yaotun_bi);
    //区段分析
    initqukuaifenxi(mBodyInfoModel);
    //dlbsRightTop.setData();

    String yaotunbi = Arith.mulString(mBodyInfoModel.getWaist(), mBodyInfoModel.getHipline());
    lbsZhifanLevel = (CareboLbsFatView) mView.findViewById(R.id.lbs_zhifan_level);
    //腰臀比
    lbsYaotunBi.setData(
        DataSource.getDeviderPercent(DataSource.getWaistToHip(), mBodyInfoModel.getYaotunbi() + "", getResources().getColor(R.color.black)));
    lbsZhifanLevel.setData(DataSource.getTiZhiDeviderPercent(DataSource.getVisceralFat(),
        mBodyInfoModel.getVisceral_fat() + "", getResources().getColor(R.color.s_black)));

    chartWeight = (LineChart) mView.findViewById(R.id.chart_weight);
    chartGuluoji = (LineChart) mView.findViewById(R.id.chart_guluoji);
    chartTizhifan = (LineChart) mView.findViewById(R.id.chart_tizhifan);
    rl_time = (RecyclerView) mView.findViewById(R.id.rl_time);
    show_core = (TextView) mView.findViewById(R.id.show_core);
    //-----------综合评分---------------------------
    show_core.setText(mBodyInfoModel.getBody_score() + "分");
    //-----------综合评分---------------------------

    //-----------体型判断---------------------------
    InitBodyType();
    //-----------体型判断---------------------------

    //-----------控制目标---------------------------
    //目标体重
    txtCgAimWeight = (TextView) mView.findViewById(R.id.txt_cg_aim_weight);
    txtCgAimWeight.setText(mBodyInfoModel.getStander_weight());
    //体重控制
    txtCgWeightControl = (TextView) mView.findViewById(R.id.txt_cg_weight_control);
    txtCgWeightControl.setText(mBodyInfoModel.getWeight_control());
    //脂肪控制
    txtCgZhifan = (TextView) mView.findViewById(R.id.txt_cg_zhifan);
    txtCgZhifan.setText(mBodyInfoModel.getFat_control());
    //肌肉控制
    txtCgJirou = (TextView) mView.findViewById(R.id.txt_cg_jirou);
    txtCgJirou.setText(mBodyInfoModel.getMuscle_control());
    //txtCgZhifanWei = (TextView) mView.findViewById(R.id.txt_cg_zhifan_wei);
    //txtCgJirouWei = (TextView) mView.findViewById(R.id.txt_cg_jirou_wei);
    //txtCgAimWeightWei = (TextView) mView.findViewById(R.id.txt_cg_aim_weight_wei);
    //txtCgWeightControlWei = (TextView) mView.findViewById(R.id.txt_cg_weight_control_wei);

    //-----------控制目标---------------------------

    //-----------综合评估---------------------------
    TextView txt_ce_age = (TextView) mView.findViewById(R.id.txt_ce_age);
    TextView txt_ce_jichudaixie = (TextView) mView.findViewById(R.id.txt_ce_jichudaixie);
    TextView txt_ce_power = (TextView) mView.findViewById(R.id.txt_ce_power);
    TextView txt_ce_xibao = (TextView) mView.findViewById(R.id.txt_ce_xibao);
    txt_ce_age.setText(mBodyInfoModel.getPhysical_age());
    txt_ce_jichudaixie.setText(mBodyInfoModel.getBasal_metabolism());
    txt_ce_power.setText(Arith.MyDiv(mBodyInfoModel.getBasal_metabolism(), "0.55") + "");
    double mAdd =
        Arith.add(mBodyInfoModel.getIntracellular_Fluid(), mBodyInfoModel.getExtracellular_fluid());
    txt_ce_xibao.setText(Arith.add(mAdd + "", mBodyInfoModel.getProtein()) + "");
    //-----------综合评估---------------------------

    //-----------生物电阻抗---------------------------
    txtRightTopId = (TextView) mView.findViewById(R.id.txt_right_top_id);
    txtRightTopId.setText(mBodyInfoModel.getRight_h_impedance());
    txtLeftTopId = (TextView) mView.findViewById(R.id.txt_left_top_id);
    txtLeftTopId.setText(mBodyInfoModel.getLeft_h_impedance());
    txtBodyId = (TextView) mView.findViewById(R.id.txt_body_id);
    txtBodyId.setText(mBodyInfoModel.getBody_impedance());
    txtRightBtId = (TextView) mView.findViewById(R.id.txt_right_bt_id);
    txtRightBtId.setText(mBodyInfoModel.getRight_f_impedance());
    txtLeftBtId = (TextView) mView.findViewById(R.id.txt_left_bt_id);
    txtLeftBtId.setText(mBodyInfoModel.getLeft_f_impedance());
    //-----------生物电阻抗---------------------------
    //txtRightTopId50 = (TextView) mView.findViewById(R.id.txt_right_top_id_50);
    //txtLeftTopId50 = (TextView) mView.findViewById(R.id.txt_left_top_id_50);
    //txtBodyId50 = (TextView) mView.findViewById(R.id.txt_body_id_50);
    //txtRightBtId50 = (TextView) mView.findViewById(R.id.txt_right_bt_id_50);
    //txtLeftBtId50 = (TextView) mView.findViewById(R.id.txt_left_bt_id_50);
    //txtRightTopId250 = (TextView) mView.findViewById(R.id.txt_right_top_id_250);
    //txtLeftTopId250 = (TextView) mView.findViewById(R.id.txt_left_top_id_250);
    //txtBodyId250 = (TextView) mView.findViewById(R.id.txt_body_id_250);
    //txtRightBtId250 = (TextView) mView.findViewById(R.id.txt_right_bt_id_250);
    //txtLeftBtId250 = (TextView) mView.findViewById(R.id.txt_left_bt_id_250);
    //------------------历史----------------------------
    initChart(chartWeight);
    initChart(chartGuluoji);
    initChart(chartTizhifan);
    initHostoryData(width, height);


  }

  private void initqukuaifenxi(BodyInfoModel mBodyInfoModel) {
    if (Integer.parseInt(mBodyInfoModel.getSex()) == 1) {
      //男

    }else{
      double height = Double.parseDouble(mBodyInfoModel.getHeight());
      double Weight = Double.parseDouble(mBodyInfoModel.getWeight());
      //double sex = "0";
      double Age = Double.parseDouble(mBodyInfoModel.getAge());
      double BMI=Weight/(height*height);
      double A1 = -0.98666 + 9.0326 * 1 / height
          - 0.37064 / Age
          - 106.4763 / Weight
          - 0.1656 * BMI
          - 0.00040719 * Age + 0.051186 * Weight;
      String mMul = BigDecimalUtils.mul(mBodyInfoModel.getRight_hand_muscle_volume(), A1 + "");
      dlbsRightTop.setData(DataSource.getQuKuaiDeviderPercent(DataSource.getqukuai(),mBodyInfoModel.getRight_hand_fat_rate(),mMul,
          getResources().getColor(R.color.black)));
    }
  }

  private void toShare() {
    String mImagePath = viewSaveToImage(mView);
    Uri imageUri = Uri.fromFile(new File(mImagePath));
    Intent shareIntent = new Intent();
    shareIntent.setAction(Intent.ACTION_SEND);
    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
    shareIntent.setType("image/*");
    startActivity(Intent.createChooser(shareIntent, "分享到"));
  }

  private void initHostoryData(final int mWidth, final int mHeight) {
    final String mMainId = Constant.MainId;
    final Realm mRealm = Realm.getDefaultInstance();
    mRealm.executeTransaction(new Realm.Transaction() {
      @Override public void execute(Realm realm) {
        RealmResults<BodyInfoModel> mAll =
            realm.where(BodyInfoModel.class).equalTo("user_id", mMainId).findAll();
        List<BodyInfoModel> mBodyInfoModels = null;
        if (mAll.size() > 10) {
          mBodyInfoModels = mAll.subList(0, 10);
        } else {
          mBodyInfoModels = mAll.subList(0,mAll.size());
        }
        //选最后一条的数据做最后参考
        setWeightData(chartWeight,mAll);
        setGugejiData(chartGuluoji, mAll);
        setTizhifang(chartTizhifan, mAll);
        initTime(mBodyInfoModels);
        getActivity().runOnUiThread(new Runnable() {
          @Override public void run() {
            layoutView(mView, mWidth,mHeight);
            toShare();
          }
        });
      }
    });
  }

  private void initTime(List<BodyInfoModel> mBodyInfoModels) {
    List<String> mStrings = new ArrayList<>();
    for (int i = 0; i < mBodyInfoModels.size(); i++) {
      mStrings.add(
          Utils.getDateToString(Long.parseLong(mBodyInfoModels.get(i).getTime()),"yy-MM-dd HH:mm"));
    }
    PrintHistoryAdapter mHistoryAdapter = new PrintHistoryAdapter(mStrings);
    //创建LinearLayoutManager
    LinearLayoutManager manager = new LinearLayoutManager(getActivity());
    //设置为横向滑动
    manager.setOrientation(LinearLayoutManager.HORIZONTAL);
    //设置
    rl_time.setLayoutManager(manager);
    rl_time.setAdapter(mHistoryAdapter);
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

      set1.setAxisDependency(YAxis.AxisDependency.LEFT);
      set1.setColor(getResources().getColor(R.color.black));
      set1.setCircleColor(Color.WHITE);
      set1.setLineWidth(2f);
      set1.setDrawValues(false);
      set1.setCircleRadius(3f);
      set1.setFillAlpha(65);
      set1.setCircleColor(getResources().getColor(R.color.black));
      set1.setFillColor(Color.parseColor("#0087ff"));
      set1.setHighLightColor(Color.rgb(244, 117, 117));
      set1.setDrawCircleHole(false);
      LineData data = new LineData(set1);
      data.setValueTextColor(Color.WHITE);
      data.setValueTextSize(9f);
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

      set1.setAxisDependency(YAxis.AxisDependency.LEFT);
      set1.setColor(getResources().getColor(R.color.black));
      set1.setCircleColor(Color.WHITE);
      set1.setLineWidth(2f);
      set1.setCircleColor(getResources().getColor(R.color.black));
      set1.setDrawValues(false);
      set1.setCircleRadius(3f);
      set1.setFillAlpha(65);
      set1.setHighLightColor(Color.rgb(244, 117, 117));
      set1.setDrawCircleHole(false);
      LineData data = new LineData(set1);
      data.setValueTextColor(Color.WHITE);
      data.setValueTextSize(9f);
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

      set1.setAxisDependency(YAxis.AxisDependency.LEFT);
      set1.setColor(getResources().getColor(R.color.black));
      set1.setCircleColor(Color.WHITE);
      set1.setLineWidth(2f);
      set1.setDrawValues(false);
      set1.setCircleRadius(3f);
      set1.setCircleColor(getResources().getColor(R.color.black));
      set1.setFillAlpha(65);
      set1.setFillColor(Color.parseColor("#e8bd21"));
      set1.setHighLightColor(Color.rgb(244, 117, 117));
      set1.setDrawCircleHole(false);
      LineData data = new LineData(set1);
      data.setValueTextColor(Color.WHITE);
      data.setValueTextSize(9f);
      mChart.setData(data);
    }
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

  private void initTiZhiPecent(BodyInfoModel mBodyInfoModel) {
    lbsTizhiPercent = (CareboLbsView) mView.findViewById(R.id.lbs_tizhi_percent);
    if (Integer.parseInt(mBodyInfoModel.getSex()) == 1) {
      //男
      DeviderModel mPhysiqueNumDeviderPercent =
          DataSource.getBodyFatPercentagePercent(TiZhiData.getTiZhiForMan(),
              mBodyInfoModel.getBody_fat_percentage(), getResources().getColor(R.color.s_black));

      if (Float.parseFloat(mBodyInfoModel.getBody_fat_percentage()) < 10) {
        mPhysiqueNumDeviderPercent.coordinate = 1;
      } else if (Float.parseFloat(mBodyInfoModel.getBody_fat_percentage()) < 20) {
        mPhysiqueNumDeviderPercent.coordinate = 2;
      } else {
        mPhysiqueNumDeviderPercent.coordinate = 3;
      }
      mPhysiqueNumDeviderPercentCoordinate = mPhysiqueNumDeviderPercent.coordinate;
      lbsTizhiPercent.setData(mPhysiqueNumDeviderPercent);
      //体脂百分比
    } else {
      //女
      DeviderModel mPhysiqueNumDeviderPercent =
          DataSource.getBodyFatPercentagePercent(TiZhiData.getTiZhiForMan(),
              mBodyInfoModel.getBody_fat_percentage(), getResources().getColor(R.color.s_black));
      if (Float.parseFloat(mBodyInfoModel.getBody_fat_percentage()) < 20) {
        mPhysiqueNumDeviderPercent.coordinate = 1;
      } else if (Float.parseFloat(mBodyInfoModel.getBody_fat_percentage()) < 30) {
        mPhysiqueNumDeviderPercent.coordinate = 2;
      } else {
        mPhysiqueNumDeviderPercent.coordinate = 3;
      }
      mPhysiqueNumDeviderPercentCoordinate = mPhysiqueNumDeviderPercent.coordinate;
      lbsTizhiPercent.setData(mPhysiqueNumDeviderPercent);
    }
  }

  private void initBase(BodyInfoModel mBodyInfoModel) {
    txtId = (TextView) mView.findViewById(R.id.txt_id);
    txtId.setText("ID\n" + Constant.MainId);
    txtHeight = (TextView) mView.findViewById(R.id.txt_height);
    txtHeight.setText("身高\n" + mBodyInfoModel.getHeight() + "cm");
    txtAge = (TextView) mView.findViewById(R.id.txt_age);
    txtAge.setText("年龄\n" + mBodyInfoModel.getAge());
    txtMale = (TextView) mView.findViewById(R.id.txt_male);
    txtTestTime = (TextView) mView.findViewById(R.id.txt_test_time);
    txtTestTime.setText("测试时间\n" + Utils.getStrTime(mBodyInfoModel.getTime()));
  }

  private void InitBodyType() {
    ImageView img_thinfat = (ImageView) mView.findViewById(R.id.img_thinfat);
    ImageView img_overfat = (ImageView) mView.findViewById(R.id.img_overfat);
    ImageView img_obse = (ImageView) mView.findViewById(R.id.img_obse);
    ImageView img_lowweight = (ImageView) mView.findViewById(R.id.img_lowweight);
    ImageView img_stander = (ImageView) mView.findViewById(R.id.img_stander);
    ImageView img_overweightmusic = (ImageView) mView.findViewById(R.id.img_overweightmusic);
    ImageView img_lowfatlowweight = (ImageView) mView.findViewById(R.id.img_lowfatlowweight);
    ImageView img_lowfat_musol = (ImageView) mView.findViewById(R.id.img_lowfat_musol);
    ImageView img_authletic = (ImageView) mView.findViewById(R.id.img_authletic);
    if (mFatWeighCoordinate == 1 && mPhysiqueNumDeviderPercentCoordinate == 1) {
      img_authletic.setVisibility(View.VISIBLE);
    } else if (mFatWeighCoordinate == 2 && mPhysiqueNumDeviderPercentCoordinate == 1) {
      img_overweightmusic.setVisibility(View.VISIBLE);
    } else if (mFatWeighCoordinate == 3 && mPhysiqueNumDeviderPercentCoordinate == 1) {
      img_obse.setVisibility(View.VISIBLE);
    } else if (mFatWeighCoordinate == 1 && mPhysiqueNumDeviderPercentCoordinate == 2) {
      img_lowfat_musol.setVisibility(View.VISIBLE);
    } else if (mFatWeighCoordinate == 1 && mPhysiqueNumDeviderPercentCoordinate == 3) {
      img_lowfatlowweight.setVisibility(View.VISIBLE);
    } else if (mFatWeighCoordinate == 2 && mPhysiqueNumDeviderPercentCoordinate == 2) {
      img_stander.setVisibility(View.VISIBLE);
    } else if (mFatWeighCoordinate == 2 && mPhysiqueNumDeviderPercentCoordinate == 3) {
      img_lowweight.setVisibility(View.VISIBLE);
    } else if (mFatWeighCoordinate == 3 && mPhysiqueNumDeviderPercentCoordinate == 3) {
      img_thinfat.setVisibility(View.VISIBLE);
    } else if (mFatWeighCoordinate == 3 && mPhysiqueNumDeviderPercentCoordinate == 2) {
      img_overfat.setVisibility(View.VISIBLE);
    }
  }

  private void initBodyCompostionAnalysis(BodyInfoModel mBodyInfoModel) {
    txtAllBodyWaterTest = (TextView) mView.findViewById(R.id.txt_all_body_water_test);
    txtAllBodyWater = (TextView) mView.findViewById(R.id.txt_all_body_water);
    txtAllBodyWater.setText(mBodyInfoModel.getTotal_water_weight());
    txtJirouliang = (TextView) mView.findViewById(R.id.txt_jirouliang);
    lbsGugeji = (CareboLbsView) mView.findViewById(R.id.lbs_gugeji);
    initDanbaizhi(mBodyInfoModel);
    txtWujiyanTest = (TextView) mView.findViewById(R.id.txt_wujiyan_test);
    mD_age = Integer.parseInt(mBodyInfoModel.getAge());
    //TODO 赋值 体重和去脂体重，体脂肪
    iniTiZhong(mBodyInfoModel);
    if (mBodyInfoModel.getSex().equals("1")) {
      txtMale.setText("性别\n男");
      //身体总水分正常范围	女：45%--60% 男	55%--65%
      txtAllBodyWaterTest.setText(
          mBodyInfoModel.getTotal_water_weight() + "\n" + Arith.mul(mBodyInfoModel.getWeight(),
              "0.45") + "-" + Arith.mul(mBodyInfoModel.getWeight(), "0.6"));
      //肌肉量
      if (Integer.parseInt(mBodyInfoModel.getHeight()) < 160) {
        txtJirouliang.setText(mBodyInfoModel.getMuscle_weight() + "\n" + "(38.5-46.5)");
      } else if (Integer.parseInt(mBodyInfoModel.getHeight()) < 170) {
        txtJirouliang.setText(mBodyInfoModel.getMuscle_weight() + "\n" + "(44-52.4)");
      } else {
        txtJirouliang.setText(mBodyInfoModel.getMuscle_weight() + "\n" + "(49.4-59.4)");
      }
      //无机盐
      String gugeji_low = "2.5";
      String gugeji_high = "3.2";
      txtWujiyanTest.setText(
          getTextOfThreshold(mBodyInfoModel.getBone_salt_content(), gugeji_low, gugeji_high));
      //算出骨骼肌正常值
      String mMul = BigDecimalUtils.mul(mBodyInfoModel.getMuscle_weight(), "0.6");
      float gugeji_pecent = BigDecimalUtils.numPecent(mBodyInfoModel.getSkeletal_muscle(), mMul);
      lbsGugeji.setData(DataSource.getCommonDeviderPercent_l(DataSource.getSkeletalMuscleData(),
          mBodyInfoModel.getSkeletal_muscle(), gugeji_pecent,
          getResources().getColor(R.color.s_black)));
    } else {
      txtMale.setText("性别\n女");
      //身体总水分正常范围	女：45%--60% 男	55%--65%
      txtAllBodyWaterTest.setText(
          mBodyInfoModel.getTotal_water_weight() + "\n" + Arith.mul(mBodyInfoModel.getWeight(),
              "0.55") + "-" + Arith.mul(mBodyInfoModel.getWeight(), "0.65"));
      //肌肉量
      if (Integer.parseInt(mBodyInfoModel.getHeight()) < 160) {
        txtJirouliang.setText(mBodyInfoModel.getMuscle_weight() + "\n" + "(21.9-34.7)");
      } else if (Integer.parseInt(mBodyInfoModel.getHeight()) < 170) {
        txtJirouliang.setText(mBodyInfoModel.getMuscle_weight() + "\n" + "(32.9-37.5)");
      } else {
        txtJirouliang.setText(mBodyInfoModel.getMuscle_weight() + "\n" + "(36.5-42.5)");
      }
      //无机盐
      String gugeji_low = "1.8";
      String gugeji_high = "2.5";
      txtWujiyanTest.setText(
          getTextOfThreshold(mBodyInfoModel.getBone_salt_content(), gugeji_low, gugeji_high));
      //骨骼肌
      //算出骨骼肌正常值
      String mMul = BigDecimalUtils.mul(mBodyInfoModel.getMuscle_weight(), "0.6");
      float gugeji_pecent = BigDecimalUtils.numPecent(mBodyInfoModel.getSkeletal_muscle(), mMul);
      lbsGugeji.setData(DataSource.getCommonDeviderPercent_l(DataSource.getSkeletalMuscleData(),
          mBodyInfoModel.getSkeletal_muscle(), gugeji_pecent,
          getResources().getColor(R.color.s_black)));
    }
  }

  /**
   * 赋值 体重和去脂体重，体脂肪
   */
  private void iniTiZhong(BodyInfoModel mBodyInfoModel) {
    txt_tizhong_one = (TextView) mView.findViewById(R.id.txt_tizhong_one);
    txtTizhifangtizhong = (TextView) mView.findViewById(R.id.txt_tizhifangtizhong);
    txtQuzhitizhong = (TextView) mView.findViewById(R.id.txt_quzhitizhong);
    lbsTizhong = (CareboLbsBaseView) mView.findViewById(R.id.lbs_tizhong);
    lbsTizhifang = (CareboLbsView) mView.findViewById(R.id.lbs_tizhifang);
    String tizhifan = Arith.mulString(mBodyInfoModel.getWeight(),
        Arith.mulString(mBodyInfoModel.getBody_fat_percentage(), "0.01"));
    if (mBodyInfoModel.getSex().equals("1")) {
      //男 lbs 体重----------
      InitLbsWeight initLbsWeight = new InitLbsWeight(mBodyInfoModel, 22f).invoke();
      float low = initLbsWeight.getLow();
      String high = initLbsWeight.getHigh();
      //体脂肪
      String tizhifan_low = "";
      String tizhifan_high = "";
      String tizhifan_mid = "";
      if (mD_age <= 39) {
        tizhifan_low = Arith.mulString(mBodyInfoModel.getWeight(), "0.27");
        tizhifan_high = Arith.mulString(mBodyInfoModel.getWeight(), "0.34");
        tizhifan_mid = Arith.mulString(mBodyInfoModel.getWeight(), "0.305");
        txtTizhifangtizhong.setText(getTextOfThreshold(tizhifan, tizhifan_low, tizhifan_high));
      } else if (mD_age <= 59) {
        tizhifan_low = Arith.mulString(mBodyInfoModel.getWeight(), "0.28");
        tizhifan_high = Arith.mulString(mBodyInfoModel.getWeight(), "0.35");
        tizhifan_mid = Arith.mulString(mBodyInfoModel.getWeight(), "0.315");
        txtTizhifangtizhong.setText(getTextOfThreshold(tizhifan, tizhifan_low, tizhifan_high));
      } else {
        tizhifan_low = Arith.mulString(mBodyInfoModel.getWeight(), "0.29");
        tizhifan_high = Arith.mulString(mBodyInfoModel.getWeight(), "0.36");
        tizhifan_mid = Arith.mulString(mBodyInfoModel.getWeight(), "0.325");
        txtTizhifangtizhong.setText(getTextOfThreshold(tizhifan, tizhifan_low, tizhifan_high));
      }
      //lbs 脂肪重
      float zhifanzhong_pecent = BigDecimalUtils.numPecent(tizhifan, tizhifan_mid);
      lbsTizhifang.setData(DataSource.getCommonDeviderPercent_l(DataSource.getFatWeight(), tizhifan,
          zhifanzhong_pecent, getResources().getColor(R.color.s_black)));
      mFatWeighCoordinate =
          DataSource.getFatWeightCoordinate(tizhifan, tizhifan_high, tizhifan_low);

      //去脂体重
      txtQuzhitizhong.setText(
          getTextOfThreshold(mBodyInfoModel.getFat_free(), Arith.sub(low + "", tizhifan_low),
              Arith.sub(high + "", tizhifan_high)));
    } else {
      //女 lbs 体重----------
      InitLbsWeight initLbsWeight = new InitLbsWeight(mBodyInfoModel, 20f).invoke();
      float low = initLbsWeight.getLow();
      String high = initLbsWeight.getHigh();
      //体脂肪
      String tizhifan_low = "";
      String tizhifan_high = "";
      String tizhifan_mid = "";
      if (mD_age <= 39) {
        tizhifan_low = Arith.mulString(mBodyInfoModel.getWeight(), "0.16");
        tizhifan_high = Arith.mulString(mBodyInfoModel.getWeight(), "0.34");
        tizhifan_mid = Arith.mulString(mBodyInfoModel.getWeight(), "0.30");
        txtTizhifangtizhong.setText(getTextOfThreshold(tizhifan, tizhifan_low, tizhifan_high));
      } else if (mD_age <= 59) {
        tizhifan_low = Arith.mulString(mBodyInfoModel.getWeight(), "0.17");
        tizhifan_high = Arith.mulString(mBodyInfoModel.getWeight(), "0.22");
        tizhifan_mid = Arith.mulString(mBodyInfoModel.getWeight(), "0.195");
        txtTizhifangtizhong.setText(getTextOfThreshold(tizhifan, tizhifan_low, tizhifan_high));
      } else {
        tizhifan_low = Arith.mulString(mBodyInfoModel.getWeight(), "0.19");
        tizhifan_high = Arith.mulString(mBodyInfoModel.getWeight(), "0.24");
        tizhifan_mid = Arith.mulString(mBodyInfoModel.getWeight(), "0.215");
        txtTizhifangtizhong.setText(
            getTextOfThreshold(tizhifan, Arith.mulString(mBodyInfoModel.getWeight(), "0.19"),
                Arith.mulString(mBodyInfoModel.getWeight(), "0.24")));
      }
      //lbs 脂肪重
      float zhifanzhong_pecent = BigDecimalUtils.numPecent(tizhifan, tizhifan_mid);
      lbsTizhifang.setData(DataSource.getCommonDeviderPercent_l(DataSource.getFatWeight(), tizhifan,
          zhifanzhong_pecent, getResources().getColor(R.color.s_black)));
      mFatWeighCoordinate =
          DataSource.getFatWeightCoordinate(tizhifan, tizhifan_high, tizhifan_low);
      //去脂体重
      txtQuzhitizhong.setText(
          getTextOfThreshold(mBodyInfoModel.getFat_free(), Arith.sub(low + "", tizhifan_low),
              Arith.sub(high + "", tizhifan_high)));
    }
  }

  private void initDanbaizhi(BodyInfoModel mBodyInfoModel) {
    txtDanbaizhiTest = (TextView) mView.findViewById(R.id.txt_danbaizhi_test);
    String mToPercent = Arith.toPercent(mBodyInfoModel.getProtein());
    txtDanbaizhiTest.setText(
        getTextOfThreshold(Arith.mulString(mBodyInfoModel.getWeight(), mToPercent),
            Arith.mulString(mBodyInfoModel.getWeight(), "0.16"),
            Arith.mulString(mBodyInfoModel.getWeight(), "0.20")));
  }

  protected void layoutView(View v, int width, int height) {
    // 指定整个View的大小 参数是左上角 和右下角的坐标

    v.layout(0, 0, width, height);

    int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);

    int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);

    /** 当然，measure完后，并不会实际改变View的尺寸，需要调用View.layout方法去进行布局。

     * 按示例调用layout函数后，View的大小将会变成你想要设置成的大小。

     */

    v.measure(measuredWidth, measuredHeight);

    v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
    int w = v.getWidth();
    int h = v.getHeight();
    KLog.e("w" + w + "h" + h);
  }

  protected String viewSaveToImage(View view) {
    view.setDrawingCacheEnabled(true);
    view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    view.setDrawingCacheBackgroundColor(Color.WHITE);

    // 把一个View转换成图片
    Bitmap cachebmp = loadBitmapFromView(view);

    FileOutputStream fos;
    String imagePath = "";
    try {
      // 判断手机设备是否有SD卡
      boolean isHasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
      if (isHasSDCard) {
        // SD卡根目录
        File sdRoot = Environment.getExternalStorageDirectory();
        File file = new File(sdRoot, Calendar.getInstance().getTimeInMillis() + ".png");
        fos = new FileOutputStream(file);
        imagePath = file.getAbsolutePath();
      } else {
        throw new Exception("创建文件失败!");
      }

      cachebmp.compress(Bitmap.CompressFormat.PNG, 90, fos);

      fos.flush();
      fos.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      cachebmp.recycle();   // 回收bitmap的内存
      cachebmp = null;
    }
    KLog.e("imagePath=" + imagePath);

    view.destroyDrawingCache();
    return imagePath;
  }

  protected Bitmap loadBitmapFromView(View v) {
    int w = v.getWidth();
    int h = v.getHeight();

    Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    Canvas c = new Canvas(bmp);

    c.drawColor(Color.WHITE);
    /** 如果不设置canvas画布为白色，则生成透明 */

    v.layout(0, 0, w, h);
    v.draw(c);

    return bmp;
  }

  private class InitLbsWeight {
    private BodyInfoModel mBodyInfoModel;
    private float mLow;
    private float scale;
    private String mHigh;

    public InitLbsWeight(BodyInfoModel mBodyInfoModel, float scale) {
      this.mBodyInfoModel = mBodyInfoModel;
      this.scale = scale;
    }

    public float getLow() {
      return mLow;
    }

    public String getHigh() {
      return mHigh;
    }

    public InitLbsWeight invoke() {
      float mV = Integer.parseInt(mBodyInfoModel.getHeight()) * Integer.parseInt(
          mBodyInfoModel.getHeight()) * scale;
      float mid = BigDecimalUtils.div(mV + "", "10000", 2);
      mLow = mid * Constant.low_height_pecent;
      mHigh = Arith.mulString(mid + "", Constant.high_height_pecent + "");
      txt_tizhong_one.setText(
          getTextOfThreshold(mBodyInfoModel.getWeight(), BigDecimalUtils.round(mLow + "", 2),
              BigDecimalUtils.round(mHigh + "", 2)));
      float mPecent = BigDecimalUtils.numPecent(mBodyInfoModel.getWeight(), mid + "");
      DeviderModel mWeightDeviderPercent =
          DataSource.getCommonDeviderPercent_l(DataSource.getWeightData(),
              mBodyInfoModel.getWeight(), mPecent, getResources().getColor(R.color.black));
      lbsTizhong.setData(mWeightDeviderPercent);
      return this;
    }
  }
}
