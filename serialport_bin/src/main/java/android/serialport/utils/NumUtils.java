package com.fliggy.bodymachine.utils;

/**
 * Created by dicallc on 2018/3/6 0006.
 */

public class NumUtils {
  /*
    * 字符串转16进制字符串
    */
  public static String string2HexString(String s) throws Exception{
    StringBuffer mStringBuffer = new StringBuffer();
    if (s.length()==1){

    }
    String r = bytes2HexString(string2Bytes(s));
    return r;
  }
}
