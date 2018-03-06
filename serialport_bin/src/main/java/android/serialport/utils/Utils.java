package android.serialport.utils;

import java.io.InputStream;

/**
 * Created by dicallc on 2018/3/5.
 */
public class Utils {
  /**
   * 发送清零命令
   */
  public static String sendClearCmd(boolean isClear) {
    String cmd = "";
    if (isClear) {
      cmd = "01";
    } else {
      cmd = "00";
    }
    String head_str = "CA0406";
    StringBuffer mBuffer = new StringBuffer(head_str);
    mBuffer.append(cmd);
    String tail_str = "0000000000";
    mBuffer.append(tail_str);
    String check_str = checkXor(mBuffer.toString());
    mBuffer.append(check_str);
    return mBuffer.toString();
  }

  /**
   * 发送开始测量指令
   * @param
   * @return
   */
  public static String sendStartCmd() {
    String cmd = "02";
    String head_str = "CA0406";
    StringBuffer mBuffer = new StringBuffer(head_str);
    mBuffer.append(cmd);
    String tail_str = "0000000000";
    mBuffer.append(tail_str);
    String check_str = checkXor(mBuffer.toString());
    mBuffer.append(check_str);
    return mBuffer.toString();
  }
  public static String loadUserInfoCmd(String height,String age,String sex,String runModel) {
    String head_str = "CA0306";
    StringBuffer mBuffer = new StringBuffer(head_str);
    mBuffer.append(NumUtils.string2HexString(height));
    mBuffer.append(NumUtils.string2HexString(age));
    mBuffer.append(NumUtils.string2HexString(sex));
    mBuffer.append(NumUtils.string2HexString(runModel));
    String tail_str = "0000";
    mBuffer.append(tail_str);
    return mBuffer.toString();
  }


  public static String checkXor(String data) {
    int checkData = 0;
    for (int i = 0; i < data.length(); i = i + 2) {
      //将十六进制字符串转成十进制
      int start = Integer.parseInt(data.substring(i, i + 2), 16);
      //进行异或运算
      checkData = start ^ checkData;
    }
    return integerToHexString(checkData);
  }

  /**
   * 将十进制整数转为十六进制数，并补位
   */
  public static String integerToHexString(int s) {
    String ss = Integer.toHexString(s);
    if (ss.length() % 2 != 0) {
      ss = "0" + ss;//0F格式
    }
    return ss.toUpperCase();
  }
  public static String integerToHexString(String ss) {
    if (ss.length() % 2 != 0) {
      ss = "0" + ss;//0F格式
    }
    return ss.toUpperCase();
  }
  public static void  parseData(InputStream mInputStream) {
    // 定义一个包的最大长度
    int maxLength = 2048;
    byte[] buffer = new byte[maxLength];
    // 每次收到实际长度
    int available = 0;
    // 当前已经收到包的总长度
    int currentLength = 0;
    // 协议头长度4个字节（开始符1，类型1，长度1）
    int headerLength = 3;

      try {
        available = mInputStream.available();
        if (available > 0) {
          // 防止超出数组最大长度导致溢出
          if (available > maxLength - currentLength) {
            available = maxLength - currentLength;
          }
          mInputStream.read(buffer, currentLength, available);
          currentLength += available;
        }

      }
      catch (Exception e) {
        e.printStackTrace();
      }

      int cursor = 0;
      // 如果当前收到包大于头的长度，则解析当前包
      while (currentLength >= headerLength) {
        // 取到头部第一个字节=开始符 如果不是开始符 会在这里停止，如果正常一般不会进去这个判断
        char[] tmp = new char[2];
        tmp[0] = (char) buffer[cursor];
        tmp[1] = (char) buffer[cursor+1];
        if (!new String(tmp).equals("CA")) {
          --currentLength;
          ++cursor;
          continue;
        }

        int contentLenght = parseLen(buffer, cursor);
        // 如果内容包的长度大于最大内容长度或者小于等于0，则说明这个包有问题，丢弃
        if (contentLenght <= 0 || contentLenght > maxLength - 5) {
          currentLength = 0;
          break;
        }
        // 如果当前获取到长度小于整个包的长度，则跳出循环等待继续接收数据
        int factPackLen = contentLenght *2;
        if (currentLength < contentLenght + 5) {
          break;
        }
        //获取内容
        byte[] content = loadContent(buffer, factPackLen);
        //校验成功与失败，重新发送
        boolean ischeck = checkReceveMsg(buffer, factPackLen);
      }
  }
  protected static byte[] loadContent(final byte[] buffer, final int packlen) {
    System.out.println("收到信息");
    byte[] buf = new byte[packlen];
    System.arraycopy(buffer, 6, buf, 0, packlen);
    //String str=SerialDataUtils.ByteArrToHex(buf);
    return buf;
  }
  protected static boolean checkReceveMsg(final byte[] buffer, final int packlen) {
    //int head_length=6;
    //数据总长度=头长度+内容长度
    int msg_length=packlen+6;
    byte[] buf = new byte[msg_length];
    System.arraycopy(buffer, 0, buf, 0, msg_length);
    String str = checkXor(new String(buf));
    byte a=buffer[msg_length];
    byte b=buffer[msg_length+1];
    char[] tmp = new char[2];
    tmp[0] = (char) a;
    tmp[1] = (char) b;
    if (str.equals(new String(tmp))){
      return  true;
    }else{
      return  false;
    }
  }
  public static int parseLen(byte buffer[], int index) {
    //      if (buffer.length - index < headerLength) { return 0; }
    //3和4位就是信息长度
    byte a = buffer[index + 4];//3
    byte b = buffer[index + 5];//4
    int rlt = 0;
    if (((a >> 7) & 0x1) == 0x1) {
      rlt = (((a & 0x7f) << 8) | b);
    }
    else {
      char[] tmp = new char[2];
      tmp[0] = (char) a;
      tmp[1] = (char) b;
      String s = new String(tmp, 0, 2);
      rlt = Integer.parseInt(s, 16);
    }
    return rlt;
  }
  public static int parseNumCmd(byte buffer[], int index) {
    //      if (buffer.length - index < headerLength) { return 0; }
    //3和4位就是信息长度
    byte a = buffer[2];//3
    byte b = buffer[3];//4
      char[] tmp = new char[2];
      tmp[0] = (char) a;
      tmp[1] = (char) b;
      String s = new String(tmp, 0, 2);
    return Integer.parseInt(s, 16);
  }
}
