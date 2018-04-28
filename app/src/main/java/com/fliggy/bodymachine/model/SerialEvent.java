package com.fliggy.bodymachine.model;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by dicallc on 2018/3/31.
 * EventBus 数据类型  关于串口
 */

public class SerialEvent {
  /**
   * 获取体重
   */
  public static final int WEIGHT = 1;
  /**
   * 身高
   */
  public static final int HEIGHT = 2;
  /**
   * 年龄
   */
  public static final int AGE = 3;
  /**
   * 性别
   */
  public static final int SEX = 4;
  /**
   * 获取用户数据出错
   */
  public static final int LOAD_USER_DATA_ERRO = 5;
  /**
   * 获取用户数据
   */
  public static final int LOAD_USER_DATA = 6;
  /**
   * 体重锁定
   */
  public static final int WEIGHT_LOCK = 7;

  @IntDef({ WEIGHT, HEIGHT, AGE ,SEX,LOAD_USER_DATA_ERRO,LOAD_USER_DATA,WEIGHT_LOCK})
  @Retention(RetentionPolicy.SOURCE)
  public @interface MessageType {
  }

  @MessageType
  int currentMsg = WEIGHT;

  public String message;
  public String content;
  public BodyInfoModel mBodyInfoModel;
  public int type;

  public SerialEvent(@MessageType int  mType,String mContent) {
    content = mContent;
    type = mType;
  }
  public SerialEvent(@MessageType int  mType,BodyInfoModel mContent) {
    mBodyInfoModel = mContent;
    type = mType;
  }

  public SerialEvent() {
  }
}
