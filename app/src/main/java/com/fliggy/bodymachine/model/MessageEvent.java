package com.fliggy.bodymachine.model;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by dicallc on 2018/3/31.
 */

public class MessageEvent {
  public static final int GET_PIC = 1;
  public static final int PRINT_Y = 2;
  public static final int PRINT_N = 3;

  @IntDef({ GET_PIC, PRINT_Y, PRINT_N })
  @Retention(RetentionPolicy.SOURCE)
  public @interface MessageType {
  }

  @MessageType
  int currentMsg = GET_PIC;

  public String message;
  public String content;
  public int type;

  public MessageEvent(@MessageType int  mType,String mContent) {
    content = mContent;
    type = mType;
  }

  public MessageEvent() {
  }
}
