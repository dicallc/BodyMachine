package android.serialport.utils;

/**
 * Created by dicallc on 2018/3/6 0006.
 */

public class NumUtils {
  /*
    * 字符串转16进制字符串
    */
  public static String string2HexString(String s) {
    StringBuffer mStringBuffer = new StringBuffer();
    if (s.length()==1){
      mStringBuffer.append("0");
    }
    String str = Integer.toHexString(Integer.parseInt(s));
    mStringBuffer.append(str);
    return mStringBuffer.toString();
  }
}
