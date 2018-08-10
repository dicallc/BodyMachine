package com.fliggy.bodymachine.utils;

import java.math.BigDecimal;

/**
 * Created by dicallc on 2018/7/24 0024.
 */
public class BigDecimalUtils {

  public static double add(String v1, String v2) {
    BigDecimal b1 = new BigDecimal(v1);
    BigDecimal b2 = new BigDecimal(v2);
    return b1.add(b2).doubleValue();
  }
  public static String addString(String v1, String v2) {
    BigDecimal b1 = new BigDecimal(v1);
    BigDecimal b2 = new BigDecimal(v2);
    return b1.add(b2).toString();
  }

  public static float addFloat(String v1, String v2) {
    BigDecimal b1 = new BigDecimal(v1);
    BigDecimal b2 = new BigDecimal(v2);
    return b1.add(b2).floatValue();
  }
  public static float addFloat(float v1, float v2) {
    BigDecimal b1 = new BigDecimal(v1);
    BigDecimal b2 = new BigDecimal(v2);
    return b1.add(b2).floatValue();
  }
  public static float addFloat(float v1, float v2,int wei) {
    BigDecimal b1 = new BigDecimal(v1);
    BigDecimal b2 = new BigDecimal(v2);
    float mRound = round(b1.add(b2).floatValue(), wei);
    return mRound;
  }
  /**
   * 提供精确的减法运算。
   * @param v1 被减数
   * @param v2 减数
   * @return 两个参数的差
   */
  public static float sub(String v1, String v2) {
    BigDecimal b1 = new BigDecimal(v1);
    BigDecimal b2 = new BigDecimal(v2);
    return b1.subtract(b2).floatValue();
  }
  public static String subInt(String v1, String v2) {
    BigDecimal b1 = new BigDecimal(v1);
    BigDecimal b2 = new BigDecimal(v2);
    return b1.subtract(b2).intValue()+"";
  }
  public static String subString(String v1, String v2) {
    BigDecimal b1 = new BigDecimal(v1);
    BigDecimal b2 = new BigDecimal(v2);
    return b1.subtract(b2).toString();
  }

  public static double sub(String v1, String v2, int wei) {
    BigDecimal b1 = new BigDecimal(v1);
    BigDecimal b2 = new BigDecimal(v2);
    double mValue = b1.subtract(b2).doubleValue();
    return round(mValue, wei);
  }

  public static double sub(double v1, double v2, int wei) {
    BigDecimal b1 = new BigDecimal(v1);
    BigDecimal b2 = new BigDecimal(v2);
    double mValue = b1.subtract(b2).doubleValue();
    return round(mValue, wei);
  }
  public static double sub(float v1, float v2, int wei) {
    BigDecimal b1 = new BigDecimal(v1);
    BigDecimal b2 = new BigDecimal(v2);
    double mValue = b1.subtract(b2).doubleValue();
    return round(mValue, wei);
  }

  public static double round(double v, int scale) {

    if (scale < 0) {
      throw new IllegalArgumentException("The scale must be a positive integer or zero");
    }
    BigDecimal b = new BigDecimal(Double.toString(v));
    BigDecimal one = new BigDecimal("1");
    return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
  }
  public static String roundString(String v, int scale) {

    if (scale < 0) {
      throw new IllegalArgumentException("The scale must be a positive integer or zero");
    }
    BigDecimal b = new BigDecimal(v);
    BigDecimal one = new BigDecimal("1");
    return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString();
  }
  public static float round(float v, int scale) {

    if (scale < 0) {
      throw new IllegalArgumentException("The scale must be a positive integer or zero");
    }
    BigDecimal b = new BigDecimal(Double.toString(v));
    BigDecimal one = new BigDecimal("1");
    return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).floatValue();
  }
  public static float roundF(double v, int scale) {

    if (scale < 0) {
      throw new IllegalArgumentException("The scale must be a positive integer or zero");
    }
    BigDecimal b = new BigDecimal(Double.toString(v));
    BigDecimal one = new BigDecimal("1");
    return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).floatValue();
  }
  public static String round(String v, int scale) {

    if (scale < 0) {
      throw new IllegalArgumentException("The scale must be a positive integer or zero");
    }
    BigDecimal b = new BigDecimal(v);
    BigDecimal one = new BigDecimal("1");
    return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString();
  }

  //public static String subString(String v1, String v2) {
  //  BigDecimal b1 = new BigDecimal(v1);
  //  BigDecimal b2 = new BigDecimal(v2);
  //  return b1.subtract(b2).toString();
  //}

  //默认除法运算精度
  private static final int DEF_DIV_SCALE = 2;

  public static float div(String v1, String v2) {
    return div(Double.parseDouble(v1), Double.parseDouble(v2), DEF_DIV_SCALE);
  }
  public static float numPecent(String v1, String v2) {
    float mV = div(Double.parseDouble(v1), Double.parseDouble(v2), DEF_DIV_SCALE);
    float mMul = mul(mV, 100f);
    return round(mMul,2);
  }

  public static float div(double v1, double v2, int scale) {
    if (scale < 0) {
      throw new IllegalArgumentException("The scale must be a positive integer or zero");
    }
    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));
    return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
  }
  public static float  div(String v1, String v2, int scale) {
    if (scale < 0) {
      throw new IllegalArgumentException("The scale must be a positive integer or zero");
    }
    BigDecimal b1 = new BigDecimal(v1);
    BigDecimal b2 = new BigDecimal(v2);
    return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
  }
  public static String  divString(String v1, String v2, int scale) {
    if (scale < 0) {
      throw new IllegalArgumentException("The scale must be a positive integer or zero");
    }
    BigDecimal b1 = new BigDecimal(v1);
    BigDecimal b2 = new BigDecimal(v2);
    return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString();
  }
  public static String  divString(String v1, String v2) {
    BigDecimal b1 = new BigDecimal(v1);
    BigDecimal b2 = new BigDecimal(v2);
    return b1.divide(b2).toString();
  }
  /**
   * 乘法
   * @param v1
   * @param v2
   * @return
   */
  public static String mul(String v1,String v2){
    BigDecimal b1 = new BigDecimal(v1);
    BigDecimal b2 = new BigDecimal(v2);
    return b1.multiply(b2).toString();
  }
  public static String chu(String v1,String v2){
    BigDecimal b1 = new BigDecimal(v1);
    BigDecimal b2 = new BigDecimal(v2);
    return b1.multiply(b2).toString();
  }
  public static float mul(float v1,float v2){
    BigDecimal b1 = new BigDecimal(v1);
    BigDecimal b2 = new BigDecimal(v2);
    return b1.multiply(b2).floatValue();
  }
}
