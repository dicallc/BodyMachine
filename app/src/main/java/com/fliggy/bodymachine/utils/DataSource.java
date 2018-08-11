package com.fliggy.bodymachine.utils;

import android.support.annotation.NonNull;
import com.fliggy.bodymachine.model.DeviderModel;
import java.util.ArrayList;

/**
 * Created by dicallc on 2018/3/21.
 */

public class DataSource {
  @NonNull public  static DeviderModel getDevider(ArrayList<String> mWeightData, String num) {
    DeviderModel mDeviderModel = new DeviderModel();
    for (int i = 0; i < mWeightData.size(); i++) {
      if (Float.parseFloat(mWeightData.get(i)) > Float.parseFloat(num)) {
        mDeviderModel.devider_limit = i;
        break;
      }
    }
    mDeviderModel.devider_limit_num = Float.parseFloat(num);
    mDeviderModel.devider_text = mWeightData;
    return mDeviderModel;
  }

  @NonNull public  static DeviderModel getDeviderPercent(ArrayList<String> mWeightData, String num,int color) {
    DeviderModel mDeviderModel = new DeviderModel();
    String max_num = mWeightData.get(mWeightData.size() - 1);
    float percent = Arith.MyDiv(num, max_num);
    mDeviderModel.devider_limit_num = Float.parseFloat(num);
    mDeviderModel.devider_text = mWeightData;
    mDeviderModel.paint_color = color;
    mDeviderModel.devider_percent = percent-0.1f;
    return mDeviderModel;
  }

  /**
   *  根据输入的体重 在区间里面找X轴 在一个节段 算百分比 加上到X轴 就得到百分比
   * @param mWeightData
   * @param num
   * @param color
   * @return
   */
  @NonNull public  static DeviderModel getWeightDeviderPercent(ArrayList<String> mWeightData, String num,int color) {
    int low=0;
    for (int i = 0; i <mWeightData.size() ; i++) {
      if (Float.parseFloat(mWeightData.get(i))>Float.parseFloat(num)){
        if (i==0)low=0;
        else{
          low=i-1;
        }
        break;
      }
    }
    String low_data = mWeightData.get(low);
    String mid_data = mWeightData.get(low+1);
    float a1 = BigDecimalUtils.sub(mid_data, low_data);
    float a2 = BigDecimalUtils.sub(num, low_data);
    float a3 = a2 / a1;
    float mRound = BigDecimalUtils.roundF(a3, 2);
    float a4 = low + mRound;
    float a5 = BigDecimalUtils.mul(a4, 0.1f);
    DeviderModel mDeviderModel = new DeviderModel();
    mDeviderModel.devider_limit_num = Float.parseFloat(num);
    mDeviderModel.devider_text = mWeightData;
    mDeviderModel.paint_color = color;
    mDeviderModel.devider_percent = a5;
    return mDeviderModel;
  }
  @NonNull public  static DeviderModel getTiZhiDeviderPercent(ArrayList<String> mWeightData, String num,int color) {
    int low=0;
    for (int i = 0; i <mWeightData.size() ; i++) {
      if (Float.parseFloat(mWeightData.get(i))>Float.parseFloat(num)){
        if (i==0)low=0;
        else{
          low=i-1;
        }
        break;
      }
    }
    String low_data = mWeightData.get(low);
    String mid_data = mWeightData.get(low+1);
    float a1 = BigDecimalUtils.sub(mid_data, low_data);
    float a2 = BigDecimalUtils.sub(num, low_data);
    float a3 = a2 / a1;
    float mRound = BigDecimalUtils.roundF(a3, 2);
    float a4=0f;
    switch (low){
      case 0:
        a4=2+ mRound;
        break;
      case 1:
        a4=4+ mRound;
        break;
      case 2:
        a4=5+ mRound;
        break;
      case 3:
        a4=8+ mRound;
        break;
      case 4:
        a4=9+ mRound;
        break;
        default:
          a4 = low + mRound;
          break;
    }

    float a5 = BigDecimalUtils.mul(a4, 0.1f);
    DeviderModel mDeviderModel = new DeviderModel();
    mDeviderModel.devider_limit_num = Float.parseFloat(num);
    mDeviderModel.devider_text = mWeightData;
    mDeviderModel.paint_color = color;
    mDeviderModel.devider_percent = a5;
    return mDeviderModel;
  }
  @NonNull public  static DeviderModel getMuscleDevider(ArrayList<String> mWeightData, String num,float pecent,int color) {

    int low=0;
    for (int i = 0; i <mWeightData.size() ; i++) {
      if (Float.parseFloat(mWeightData.get(i))>pecent){
        if (i==0)low=0;
        else{
          low=i-1;
        }
        break;
      }
    }
    String low_data = mWeightData.get(low);
    String mid_data = mWeightData.get(low+1);
    float a2 = BigDecimalUtils.sub(pecent+"", low_data);
    float a5=0;
    if (a2<0){
      //如果A2小于0说明值比第一个还要小
      a5=0.02f;
    }else{
      float a1 = BigDecimalUtils.sub(mid_data, low_data);

      float a3 = a2 / a1;
      float mRound = BigDecimalUtils.roundF(a3, 2);
      float a4 = low + mRound;
      a5 = BigDecimalUtils.mul(a4, 0.1f);
    }

    DeviderModel mDeviderModel = new DeviderModel();
    mDeviderModel.devider_limit_num = Float.parseFloat(num);
    mDeviderModel.devider_text = mWeightData;
    mDeviderModel.paint_color = color;
    mDeviderModel.devider_percent = a5;
    return mDeviderModel;
  }
  @NonNull public  static DeviderModel getCommonDeviderPercent_l(ArrayList<String> mWeightData, String num,float pecent,int color) {

    int low=0;
    for (int i = 0; i <mWeightData.size() ; i++) {
      if (Float.parseFloat(mWeightData.get(i))>pecent){
        if (i==0)low=0;
        else{
          low=i-1;
        }
        break;
      }
    }
    String low_data = mWeightData.get(low);
    String mid_data = mWeightData.get(low+1);
    float a2 = BigDecimalUtils.sub(pecent+"", low_data);
    float a5=0;
    if (a2<0){
      //如果A2小于0说明值比第一个还要小
      a5=0.02f;
    }else{
      float a1 = BigDecimalUtils.sub(mid_data, low_data);

      float a3 = a2 / a1;
      float mRound = BigDecimalUtils.roundF(a3, 2);
      float a4 = low + mRound;
       a5 = BigDecimalUtils.mul(a4, 0.1f);
    }

    DeviderModel mDeviderModel = new DeviderModel();
    mDeviderModel.devider_limit_num = Float.parseFloat(num);
    mDeviderModel.devider_text = mWeightData;
    mDeviderModel.paint_color = color;
    mDeviderModel.devider_percent = a5;
    return mDeviderModel;
  }
  @NonNull public  static DeviderModel getCommonDeviderPercent(ArrayList<String> mWeightData, String num,int color) {

    int low=0;
    for (int i = 0; i <mWeightData.size() ; i++) {
      if (Float.parseFloat(mWeightData.get(i))>Float.parseFloat(num)){
        if (i==0)low=0;
        else{
          low=i-1;
        }
        break;
      }
    }
    String low_data = mWeightData.get(low);
    String mid_data = mWeightData.get(low+1);
    float a2 = BigDecimalUtils.sub(num, low_data);
    float a5=0;
    if (a2<0){
      //如果A2小于0说明值比第一个还要小
      a5=0.02f;
    }else{
      float a1 = BigDecimalUtils.sub(mid_data, low_data);

      float a3 = a2 / a1;
      float mRound = BigDecimalUtils.roundF(a3, 2);
      float a4 = low + mRound;
       a5 = BigDecimalUtils.mul(a4, 0.1f);
    }

    DeviderModel mDeviderModel = new DeviderModel();
    mDeviderModel.devider_limit_num = Float.parseFloat(num);
    mDeviderModel.devider_text = mWeightData;
    mDeviderModel.paint_color = color;
    mDeviderModel.devider_percent = a5;
    return mDeviderModel;
  }

  /**
   * 注意传进来就是百分比
   * @param mWeightData
   * @param num
   * @param num_two
   * @param color
   * @return
   */
  @NonNull public  static DeviderModel getQuKuaiDeviderPercent(ArrayList<String> mWeightData, String num,String num_two,int color) {

    float a5 = getPecent(mWeightData, num);
    float a6 = getPecent(mWeightData, num_two);

    DeviderModel mDeviderModel = new DeviderModel();
    mDeviderModel.devider_limit_num = Float.parseFloat(num);
    mDeviderModel.devider_Two_limit_num = Float.parseFloat(num_two);
    mDeviderModel.devider_text = mWeightData;
    mDeviderModel.paint_color = color;
    mDeviderModel.devider_percent = a5;
    mDeviderModel.devider_Two_percent = a6;
    return mDeviderModel;
  }

  private static float getPecent(ArrayList<String> mWeightData, String num) {
    int low=0;
    for (int i = 0; i <mWeightData.size() ; i++) {
      if (Float.parseFloat(mWeightData.get(i))>Float.parseFloat(num)){
        if (i==0)low=0;
        else{
          low=i-1;
        }
        break;
      }
    }
    String low_data = mWeightData.get(low);
    String mid_data = mWeightData.get(low+1);
    float a2 = BigDecimalUtils.sub(num, low_data);
    float a5=0;
    if (a2<0){
      //如果A2小于0说明值比第一个还要小
      a5=0.05f;
    }else{
      float a1 = BigDecimalUtils.sub(mid_data, low_data);

      float a3 = a2 / a1;
      float mRound = BigDecimalUtils.roundF(a3, 2);
      float a4 = low + mRound;
       a5 = BigDecimalUtils.mul(a4, 0.1f);
      a5+=0.05f;
    }
    return a5;
  }

  @NonNull public  static DeviderModel getSkeletalMuscleDataDeviderPercent(String num,String high,String low,int color) {

    DeviderModel mDeviderModel = new DeviderModel();
    ArrayList<String> mWeightData = getSkeletalMuscleData();
    //计算出中间值
    double half_mid = BigDecimalUtils.sub(high, low,2)/2;
    double mid = BigDecimalUtils.add(low, half_mid + "");
    float percent=BigDecimalUtils.addFloat(num,mid+"");
    mDeviderModel.devider_limit_num = Float.parseFloat(num);
    mDeviderModel.devider_text = mWeightData;
    mDeviderModel.paint_color = color;
    mDeviderModel.devider_percent = percent;
    return mDeviderModel;
  }
  @NonNull public  static DeviderModel getFatWeightDeviderPercent(String num,String high,String low,int color) {

    DeviderModel mDeviderModel = new DeviderModel();
    ArrayList<String> mWeightData = getFatWeight();
    if ((Float.parseFloat(low)-Float.parseFloat(num))<0){
      mDeviderModel.coordinate=1;
    }else if((Float.parseFloat(high)-Float.parseFloat(num))<0){
      mDeviderModel.coordinate=3;
    }else{
      mDeviderModel.coordinate=2;
    }
    //计算出中间值
    double half_mid = BigDecimalUtils.sub(high, low,2)/2;
    double mid = BigDecimalUtils.add(low, half_mid + "");
    float percent=BigDecimalUtils.addFloat(num,mid+"");
    mDeviderModel.devider_limit_num = Float.parseFloat(num);
    mDeviderModel.devider_text = mWeightData;
    mDeviderModel.paint_color = color;
    mDeviderModel.devider_percent = percent;
    return mDeviderModel;
  }
  @NonNull public  static int getFatWeightCoordinate(String num,String high,String low) {

    if ((Float.parseFloat(low)-Float.parseFloat(num))<0){
      return 1;
    }else if((Float.parseFloat(high)-Float.parseFloat(num))<0){
      return 3;
    }else{
      return 2;
    }
  }
  @NonNull public  static DeviderModel getPhysiqueNumDeviderPercent(ArrayList<String> mWeightData, String num,int color) {
    DeviderModel mDeviderModel = new DeviderModel();
    if ((Float.parseFloat("18")-Float.parseFloat(num))<0){
      mDeviderModel.coordinate=1;
    }else if((Float.parseFloat("24.5")-Float.parseFloat(num))<0){
      mDeviderModel.coordinate=3;
    }else{
      mDeviderModel.coordinate=2;
    }
    int low=0;
    for (int i = 0; i <mWeightData.size() ; i++) {
      if (Float.parseFloat(mWeightData.get(i))>Float.parseFloat(num)){
        if (i==0)low=0;
        else{
          low=i-1;
        }
        break;
      }
    }
    String low_data = mWeightData.get(low);
    String mid_data = mWeightData.get(low+1);
    float a2 = BigDecimalUtils.sub(num, low_data);
    float a5=0;
    if (a2<0){
      //如果A2小于0说明值比第一个还要小
      a5=0.02f;
    }else{
      float a1 = BigDecimalUtils.sub(mid_data, low_data);
      //得到大于区间值
      float a3 = a2 / a1;
      float mRound = BigDecimalUtils.roundF(a3, 2);
      //这里的round是要乘区段的
      float a4 = low + mRound+1;
      a5 = BigDecimalUtils.mul(a4, 0.1f);
    }
    mDeviderModel.devider_limit_num = Float.parseFloat(num);
    mDeviderModel.devider_text = mWeightData;
    mDeviderModel.paint_color = color;
    mDeviderModel.devider_percent = a5;
    //计算出中间值
    return mDeviderModel;
  }

  /**
   * 计算体脂比
   * @param num
   * @param color
   * @return
   */
  @NonNull public  static DeviderModel getBodyFatPercentagePercent(ArrayList<String> mWeightData, String num,int color) {
    DeviderModel mDeviderModel = new DeviderModel();
    int low=0;
    for (int i = 0; i <mWeightData.size() ; i++) {
      if (Float.parseFloat(mWeightData.get(i))>Float.parseFloat(num)){
        if (i==0)low=0;
        else{
          low=i-1;
        }
        break;
      }
    }
    String low_data = mWeightData.get(low);
    String mid_data = mWeightData.get(low+1);
    float a2 = BigDecimalUtils.sub(num, low_data);
    float a5=0;
    if (a2<0){
      //如果A2小于0说明值比第一个还要小
      a5=0.02f;
    }else{
      float a1 = BigDecimalUtils.sub(mid_data, low_data);
      //得到大于区间值
      float a3 = a2 / a1;
      float mRound = BigDecimalUtils.roundF(a3, 2);
      //这里的round是要乘区段的
      float a4 = low + mRound+1;
      a5 = BigDecimalUtils.mul(a4, 0.1f);
    }
    mDeviderModel.devider_limit_num = Float.parseFloat(num);
    mDeviderModel.devider_text = mWeightData;
    mDeviderModel.paint_color = color;
    mDeviderModel.devider_percent = a5;
    return mDeviderModel;
  }
  @NonNull public  static DeviderModel getYaoTunbi(String num,String high,String low,int color) {

    DeviderModel mDeviderModel = new DeviderModel();
    ArrayList<String> mWeightData = getYaoTunbi();
    //计算出中间值
    double half_mid = BigDecimalUtils.sub(high, low,2)/2;
    double mid = BigDecimalUtils.add(low, half_mid + "");
    float percent=BigDecimalUtils.addFloat(num,mid+"");
    mDeviderModel.devider_limit_num = Float.parseFloat(num);
    mDeviderModel.devider_text = mWeightData;
    mDeviderModel.paint_color = color;
    mDeviderModel.devider_percent = percent;
    return mDeviderModel;
  }
  /**
   * 获取体重区间
   * @return
   */
  public  static ArrayList<String> getWeightData(){
    ArrayList<String> list_text=new ArrayList<>();
    list_text.add("55");
    list_text.add("70");
    list_text.add("85");
    list_text.add("100");
    list_text.add("115");
    list_text.add("130");
    list_text.add("145");
    list_text.add("160");
    list_text.add("175");
    list_text.add("190");
    return list_text;
  }

  /**
   * 获取骨骼肌区间
   * @return
   */
  public  static ArrayList<String> getSkeletalMuscleData(){
    ArrayList<String> list_text=new ArrayList<>();
    list_text.add("70");
    list_text.add("80");
    list_text.add("90");
    list_text.add("100");
    list_text.add("110");
    list_text.add("120");
    list_text.add("130");
    list_text.add("140");
    list_text.add("150");
    list_text.add("160");
    list_text.add("170");
    return list_text;
  }

  /**
   * 获取脂肪重区间
   * @return
   */
  public  static ArrayList<String> getFatWeight(){
    ArrayList<String> list_text=new ArrayList<>();
    list_text.add("40");
    list_text.add("60");
    list_text.add("80");
    list_text.add("100");
    list_text.add("160");
    list_text.add("220");
    list_text.add("280");
    list_text.add("340");
    list_text.add("400");
    list_text.add("460");
    list_text.add("520");
    return list_text;
  }

  /**
   * 获取体质指数区间
   * @return
   */
  public  static ArrayList<String> getPhysiqueNum(){
    ArrayList<String> list_text=new ArrayList<>();
    list_text.add("10");
    list_text.add("15");
    list_text.add("18.5");
    list_text.add("22.0");
    list_text.add("23.0");
    list_text.add("30.0");
    list_text.add("35.0");
    list_text.add("40.0");
    list_text.add("45.0");
    list_text.add("50.0");
    list_text.add("55.0");
    return list_text;
  }

  /**
   * 体脂百分比
   * @return
   */
  public  static ArrayList<String> getBodyFatPercentage(){
    ArrayList<String> list_text=new ArrayList<>();
    list_text.add("12.5");
    list_text.add("15");
    list_text.add("17.5");
    list_text.add("20.0");
    list_text.add("22.5");
    list_text.add("25");
    list_text.add("27.5");
    list_text.add("30");
    list_text.add("32.5");
    list_text.add("35");
    list_text.add("37.5");
    return list_text;
  }
  /**
   * 腰臀比
   * @return
   */
  public  static ArrayList<String> getYaoTunbi(){
    ArrayList<String> list_text=new ArrayList<>();
    list_text.add("0.55");
    list_text.add("0.65");
    list_text.add("0.75");
    list_text.add("0.85");
    list_text.add("0.95");
    list_text.add("1.05");
    list_text.add("1.15");
    list_text.add("1.25");
    list_text.add("1.35");
    list_text.add("1.45");
    list_text.add("1.55");
    return list_text;
  }

  /**
   * 获取内脏脂肪等级数据源
   * @return
   */
  public  static ArrayList<String> getVisceralFat(){
    ArrayList<String> list_text=new ArrayList<>();
    list_text.add("4");
    list_text.add("8");
    list_text.add("10");
    list_text.add("15");
    return list_text;
  }
  /**
   * 获取腰臀比
   * @return
   */
  public  static ArrayList<String> getWaistToHip(){
    ArrayList<String> list_text=new ArrayList<>();
    list_text.add("0.75");
    list_text.add("0.95");
    list_text.add("1.65");
    return list_text;
  }

  /**
   * 获取区段数据
   * @return
   */
  public  static ArrayList<String> getqukuai(){
    ArrayList<String> list_text=new ArrayList<>();
    list_text.add("40");
    list_text.add("60");
    list_text.add("80");
    list_text.add("100");
    list_text.add("120");
    list_text.add("140");
    list_text.add("160");
    list_text.add("180");
    list_text.add("200");
    list_text.add("220");
    return list_text;
  }
}
