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
    list_text.add("922");
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
    list_text.add("0");
    list_text.add("5.0");
    list_text.add("10.0");
    list_text.add("15.0");
    list_text.add("20.0");
    list_text.add("25.0");
    list_text.add("30.0");
    list_text.add("35.0");
    list_text.add("40.0");
    list_text.add("45.0");
    list_text.add("57.3");
    return list_text;
  }
}
