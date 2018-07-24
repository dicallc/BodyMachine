package com.fliggy.bodymachine.base;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.model.BodyInfoModel;
import com.fliggy.bodymachine.model.DeviderModel;
import com.fliggy.bodymachine.utils.Arith;
import com.fliggy.bodymachine.utils.Constant;
import com.fliggy.bodymachine.utils.DataSource;
import com.fliggy.bodymachine.widgets.CareboDoubleLbsView;
import com.fliggy.bodymachine.widgets.CareboLbsView;
import com.github.mikephil.charting.charts.LineChart;
import com.socks.library.KLog;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

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
  private CareboLbsView lbsTizhong;
  private CareboLbsView lbsGugeji;
  private CareboLbsView lbsTizhifang;
  private CareboLbsView lbsZhiliangzhishu;
  private CareboLbsView lbsTizhiPercent;
  private CareboDoubleLbsView dlbsRightTop;
  private CareboDoubleLbsView dlbsLeftTop;
  private CareboDoubleLbsView dlbsBody;
  private CareboDoubleLbsView dlbsRightBottom;
  private CareboDoubleLbsView dlbsLeftBottom;
  private CareboLbsView lbsYaotunBi;
  private CareboLbsView lbsZhifanLevel;
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
    lbsZhiliangzhishu.setData(
        DataSource.getPhysiqueNumDeviderPercent(mBodyInfoModel.getPhysique_num(), "24", "18",
            R.color.black));
    //体脂百分比
    lbsTizhiPercent = (CareboLbsView) mView.findViewById(R.id.lbs_tizhi_percent);
    String TizhiPercent_low = "";
    String TizhiPercent_high = "";
    if (Integer.parseInt(mBodyInfoModel.getSex()) == 1) {
      //男
      if (Integer.parseInt(mBodyInfoModel.getAge()) <= 39) {
        TizhiPercent_low = "10";
        TizhiPercent_high = "21";
      } else if (Integer.parseInt(mBodyInfoModel.getAge()) <= 59) {
        TizhiPercent_low = "11";
        TizhiPercent_high = "22";
      } else {
        TizhiPercent_low = "13";
        TizhiPercent_high = "24";
      }
      DeviderModel mPhysiqueNumDeviderPercent =
          DataSource.getPhysiqueNumDeviderPercent(mBodyInfoModel.getPhysique_num(),
              TizhiPercent_high, TizhiPercent_low, R.color.s_black);
      mPhysiqueNumDeviderPercentCoordinate = mPhysiqueNumDeviderPercent.coordinate;
      lbsTizhiPercent.setData(mPhysiqueNumDeviderPercent);
    } else {
      //女
      if (Integer.parseInt(mBodyInfoModel.getAge()) <= 39) {
        TizhiPercent_low = "20";
        TizhiPercent_high = "34";
      } else if (Integer.parseInt(mBodyInfoModel.getAge()) <= 59) {
        TizhiPercent_low = "21";
        TizhiPercent_high = "35";
      } else {
        TizhiPercent_low = "22";
        TizhiPercent_high = "36";
      }
      DeviderModel mPhysiqueNumDeviderPercent =
          DataSource.getPhysiqueNumDeviderPercent(mBodyInfoModel.getPhysique_num(),
              TizhiPercent_high, TizhiPercent_low, R.color.s_black);
      mPhysiqueNumDeviderPercentCoordinate = mPhysiqueNumDeviderPercent.coordinate;
      lbsTizhiPercent.setData(mPhysiqueNumDeviderPercent);
    }

    dlbsRightTop = (CareboDoubleLbsView) mView.findViewById(R.id.dlbs_right_top);
    dlbsLeftTop = (CareboDoubleLbsView) mView.findViewById(R.id.dlbs_left_top);
    dlbsBody = (CareboDoubleLbsView) mView.findViewById(R.id.dlbs_body);
    dlbsRightBottom = (CareboDoubleLbsView) mView.findViewById(R.id.dlbs_right_bottom);
    dlbsLeftBottom = (CareboDoubleLbsView) mView.findViewById(R.id.dlbs_left_bottom);
    lbsYaotunBi = (CareboLbsView) mView.findViewById(R.id.lbs_yaotun_bi);
    String yaotunbi = Arith.mulString(mBodyInfoModel.getWaist(), mBodyInfoModel.getHipline());
    String ytb_high = "";
    String ytb_low = "";
    if (Integer.parseInt(mBodyInfoModel.getSex()) == 1) {
      ytb_high = "0.9";
      ytb_low = "0.75";
    } else {
      ytb_high = "0.85";
      ytb_low = "0.7";
    }
    lbsYaotunBi.setData(DataSource.getYaoTunbi(yaotunbi, ytb_high, ytb_low, R.color.black));
    lbsZhifanLevel = (CareboLbsView) mView.findViewById(R.id.lbs_zhifan_level);
    chartWeight = (LineChart) mView.findViewById(R.id.chart_weight);
    chartGuluoji = (LineChart) mView.findViewById(R.id.chart_guluoji);
    chartTizhifan = (LineChart) mView.findViewById(R.id.chart_tizhifan);
    show_core = (TextView) mView.findViewById(R.id.show_core);
    //-----------综合评分---------------------------
    show_core.setText(mBodyInfoModel.getBody_score()+"分");
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
    txt_ce_power.setText(Arith.MyDiv(mBodyInfoModel.getBasal_metabolism(),"0.55")+"");
    double mAdd =
        Arith.add(mBodyInfoModel.getIntracellular_Fluid(), mBodyInfoModel.getExtracellular_fluid());
    txt_ce_xibao.setText(Arith.add(mAdd+"",mBodyInfoModel.getProtein())+"");
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

    layoutView(mView, width, height);
    viewSaveToImage(mView);
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
    txtTestTime.setText("测试时间\n" + mBodyInfoModel.getTime());
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
    if (mFatWeighCoordinate==1&&mPhysiqueNumDeviderPercentCoordinate==1){
      img_authletic.setVisibility(View.VISIBLE);
    }else if(mFatWeighCoordinate==2&&mPhysiqueNumDeviderPercentCoordinate==1){
      img_overweightmusic.setVisibility(View.VISIBLE);
    }else if(mFatWeighCoordinate==3&&mPhysiqueNumDeviderPercentCoordinate==1){
      img_obse.setVisibility(View.VISIBLE);
    }else if(mFatWeighCoordinate==1&&mPhysiqueNumDeviderPercentCoordinate==2){
      img_lowfat_musol.setVisibility(View.VISIBLE);
    }else if(mFatWeighCoordinate==1&&mPhysiqueNumDeviderPercentCoordinate==3){
      img_lowfatlowweight.setVisibility(View.VISIBLE);
    }else if(mFatWeighCoordinate==2&&mPhysiqueNumDeviderPercentCoordinate==2){
      img_stander.setVisibility(View.VISIBLE);
    }else if(mFatWeighCoordinate==2&&mPhysiqueNumDeviderPercentCoordinate==3){
      img_lowweight.setVisibility(View.VISIBLE);
    }else if(mFatWeighCoordinate==3&&mPhysiqueNumDeviderPercentCoordinate==3){
      img_thinfat.setVisibility(View.VISIBLE);
    }else if(mFatWeighCoordinate==3&&mPhysiqueNumDeviderPercentCoordinate==2){
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
      lbsGugeji.setData(
          DataSource.getSkeletalMuscleDataDeviderPercent(mBodyInfoModel.getBone_salt_content(),
              gugeji_high, gugeji_low, R.color.s_black));
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
      lbsGugeji.setData(
          DataSource.getSkeletalMuscleDataDeviderPercent(mBodyInfoModel.getBone_salt_content(),
              gugeji_high, gugeji_low, R.color.s_black));
    }
  }

  /**
   * 赋值 体重和去脂体重，体脂肪
   */
  private void iniTiZhong(BodyInfoModel mBodyInfoModel) {
    txt_tizhong_one = (TextView) mView.findViewById(R.id.txt_tizhong_one);
    txtTizhifangtizhong = (TextView) mView.findViewById(R.id.txt_tizhifangtizhong);
    txtQuzhitizhong = (TextView) mView.findViewById(R.id.txt_quzhitizhong);
    lbsTizhong = (CareboLbsView) mView.findViewById(R.id.lbs_tizhong);
    lbsTizhifang = (CareboLbsView) mView.findViewById(R.id.lbs_tizhifang);
    String tizhifan =
        Arith.mulString(mBodyInfoModel.getWeight(),Arith.mulString(mBodyInfoModel.getBody_fat_percentage(),"0.01"));
    if (mBodyInfoModel.getSex().equals("1")) {
      //男 体重
      double low = Integer.parseInt(mBodyInfoModel.getHeight()) * Integer.parseInt(
          mBodyInfoModel.getHeight()) * 22 * Constant.low_height_pecent;
      String high = Arith.mulString(
          (Integer.parseInt(mBodyInfoModel.getHeight()) * Integer.parseInt(
              mBodyInfoModel.getHeight()) * 22) + "", Constant.high_height_pecent+"");
      txt_tizhong_one.setText(getTextOfThreshold(mBodyInfoModel.getWeight(), low + "", high + ""));
      //lbs 体重
      DeviderModel mWeightDeviderPercent =
          DataSource.getWeightDeviderPercent(mBodyInfoModel.getWeight(), high, low + "",
              R.color.black);
      lbsTizhong.setData(mWeightDeviderPercent);
      //体脂肪
      String tizhifan_low = "";
      String tizhifan_high = "";
      if (mD_age <= 39) {
        tizhifan_low = Arith.mulString(mBodyInfoModel.getWeight(), "0.27");
        tizhifan_high = Arith.mulString(mBodyInfoModel.getWeight(), "0.34");
        txtTizhifangtizhong.setText(getTextOfThreshold(tizhifan, tizhifan_low, tizhifan_high));
      } else if (mD_age <= 59) {
        tizhifan_low = Arith.mulString(mBodyInfoModel.getWeight(), "0.28");
        tizhifan_high = Arith.mulString(mBodyInfoModel.getWeight(), "0.35");
        txtTizhifangtizhong.setText(getTextOfThreshold(tizhifan, tizhifan_low, tizhifan_high));
      } else {
        txtTizhifangtizhong.setText(
            getTextOfThreshold(tizhifan, Arith.mulString(mBodyInfoModel.getWeight(), "0.29"),
                Arith.mulString(mBodyInfoModel.getWeight(), "0.36")));
      }
      //lbs 脂肪重
      DeviderModel mFatWeightDeviderPercent =
          DataSource.getFatWeightDeviderPercent(tizhifan, tizhifan_high, tizhifan_low,
              R.color.s_s_black);
      mFatWeighCoordinate = mFatWeightDeviderPercent.coordinate;
      lbsTizhifang.setData(mFatWeightDeviderPercent);

      //去脂体重
      txtQuzhitizhong.setText(
          getTextOfThreshold(mBodyInfoModel.getFat_free(), Arith.sub(low + "", tizhifan_low),
              Arith.sub(high + "", tizhifan_high)));
    } else {
      //女
      double low = Integer.parseInt(mBodyInfoModel.getHeight()) * Integer.parseInt(
          mBodyInfoModel.getHeight()) * 20 *  Constant.low_height_pecent;
      String high = Arith.mulString(
          (Integer.parseInt(mBodyInfoModel.getHeight()) * Integer.parseInt(
              mBodyInfoModel.getHeight()) * 20) + "", Constant.high_height_pecent+"");
      txt_tizhong_one.setText(getTextOfThreshold(mBodyInfoModel.getWeight(), low + "", high + ""));
      //lbs 体重
      DeviderModel mWeightDeviderPercent =
          DataSource.getWeightDeviderPercent(mBodyInfoModel.getWeight(), high, low + "",
              R.color.black);
      lbsTizhong.setData(mWeightDeviderPercent);
      //体脂肪
      String tizhifan_low = "";
      String tizhifan_high = "";
      if (mD_age <= 39) {
        tizhifan_low = Arith.mulString(mBodyInfoModel.getWeight(), "0.16");
        tizhifan_high = Arith.mulString(mBodyInfoModel.getWeight(), "0.34");
        txtTizhifangtizhong.setText(getTextOfThreshold(tizhifan, tizhifan_low, tizhifan_high));
      } else if (mD_age <= 59) {
        tizhifan_low = Arith.mulString(mBodyInfoModel.getWeight(), "0.17");
        tizhifan_high = Arith.mulString(mBodyInfoModel.getWeight(), "0.22");
        txtTizhifangtizhong.setText(getTextOfThreshold(tizhifan, tizhifan_low, tizhifan_high));
      } else {
        txtTizhifangtizhong.setText(
            getTextOfThreshold(tizhifan, Arith.mulString(mBodyInfoModel.getWeight(), "0.19"),
                Arith.mulString(mBodyInfoModel.getWeight(), "0.24")));
      }
      //lbs 脂肪重
      DeviderModel mFatWeightDeviderPercent =
          DataSource.getFatWeightDeviderPercent(tizhifan, tizhifan_high, tizhifan_low,
              R.color.s_s_black);
      mFatWeighCoordinate = mFatWeightDeviderPercent.coordinate;
      lbsTizhifang.setData(mFatWeightDeviderPercent);
      //去脂体重
      txtQuzhitizhong.setText(
          getTextOfThreshold(mBodyInfoModel.getFat_free(), Arith.sub(low + "", tizhifan_low),
              Arith.sub(high + "", tizhifan_high)));
    }
  }

  private void initDanbaizhi(BodyInfoModel mBodyInfoModel) {
    txtDanbaizhiTest = (TextView) mView.findViewById(R.id.txt_danbaizhi_test);
    String mToPercent = Arith.toPercent(mBodyInfoModel.getProtein());
    txtDanbaizhiTest.setText(getTextOfThreshold(Arith.mulString(mBodyInfoModel.getWeight(), mToPercent),
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
}