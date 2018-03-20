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
      cmd = "00";
    } else {
      cmd = "00";
    }
    String head_str = "CA0406";
    StringBuffer mBuffer = new StringBuffer(head_str);
    mBuffer.append(cmd);
    String tail_str = "0000000000";
    mBuffer.append(tail_str);
    return mBuffer.toString();
  }
  public static String toResult(String str,int start,int end){
    String  weight = str.substring(start, end);
    return Integer.parseInt(weight,16)+"";
  }

  public static String toShowFinalResult(String mS){
    StringBuffer stringBuffer=new StringBuffer();
    String  weight= Utils.toResult(mS,6,10);
    //总阻抗
    String  all_impedance= Utils.toResult(mS,10,14);
    String  left_h_impedance= Utils.toResult(mS,14,18);
    String  right_h_impedance= Utils.toResult(mS,18,22);
    String  left_f_impedance= Utils.toResult(mS,22,26);
    String  right_f_impedance= Utils.toResult(mS,26,30);
    String  body_impedance= Utils.toResult(mS,30,34);
    stringBuffer.append("体重："+weight+"\n");
    stringBuffer.append("总阻抗："+all_impedance+"\n");
    stringBuffer.append("左手："+left_h_impedance+"\n");
    stringBuffer.append("左手："+right_h_impedance+"\n");
    stringBuffer.append("左脚："+left_f_impedance+"\n");
    stringBuffer.append("右脚："+right_f_impedance+"\n");
    stringBuffer.append("躯干："+body_impedance+"\n");
    //脂肪重
    String  fat_weight= Utils.toResult(mS,34,38);
    stringBuffer.append("脂肪重："+fat_weight+"\n");
    //肌肉重
    String  muscle_weight= Utils.toResult(mS,38,42);
    stringBuffer.append("肌肉重："+muscle_weight+"\n");
    //总水重
    String  total_water_weight= Utils.toResult(mS,42,46);
    //细胞外液
    String  extracellular_fluid= Utils.toResult(mS,46,50);
    //细胞内液
    String  intracellular_Fluid= Utils.toResult(mS,50,54);
    //去脂体重
    String  fat_free= Utils.toResult(mS,54,58);
    //标准肌肉
    String  Standard_muscle= Utils.toResult(mS,58,62);
    //骨盐量
    String  Bone_salt_content = Utils.toResult(mS,62,66);
    //骨骼肌重
    String   skeletal_muscle = Utils.toResult(mS,66,70);
    stringBuffer.append("总水重："+total_water_weight+"\n");
    stringBuffer.append("细胞外液："+extracellular_fluid+"\n");
    stringBuffer.append("细胞内液："+intracellular_Fluid+"\n");
    stringBuffer.append("去脂体重："+fat_free+"\n");
    stringBuffer.append("标准肌肉："+Standard_muscle+"\n");
    stringBuffer.append("骨盐量："+Bone_salt_content+"\n");
    stringBuffer.append("骨骼肌重："+skeletal_muscle+"\n");
    //蛋白质
    String    protein = Utils.toResult(mS,70,74);
    //体质指数
    String    physique_num = Utils.toResult(mS,74,78);
    //基础代谢
    String    basal_metabolism = Utils.toResult(mS,78,82);
    //体脂百分比
    String    Body_fat_percentage = Utils.toResult(mS,82,86);
    //含水百分比
    String    Percentage_of_water  = Utils.toResult(mS,86,90);
    //标准体重
    String    stander_weight  = Utils.toResult(mS,90,94);
    //内脏脂肪等级
    String    visceral_fat  = Utils.toResult(mS,94,98);
    //身体年龄
    String    physical_age   = Utils.toResult(mS,98,100);
    //身体评分
    String    Body_score    = Utils.toResult(mS,100,102);
    //水肿度
    String    Edematous_degree    = Utils.toResult(mS,102,106);
    //肥胖度
    String    Fat_degree     = Utils.toResult(mS,106,110);
    //肌肉控制
    String    Muscle_control     = Utils.toResult(mS,110,114);
    //体重控制
    String    Weight_control     = Utils.toResult(mS,114,118);
    //脂肪控制
    String    Fat_control     = Utils.toResult(mS,118,122);
    stringBuffer.append("蛋白质："+protein+"\n");
    stringBuffer.append("体质指数："+physique_num+"\n");
    stringBuffer.append("基础代谢："+basal_metabolism+"\n");
    stringBuffer.append("体脂百分比："+Body_fat_percentage+"\n");
    stringBuffer.append("含水百分比："+Percentage_of_water+"\n");
    stringBuffer.append("标准体重："+stander_weight+"\n");
    stringBuffer.append("内脏脂肪等级："+visceral_fat+"\n");
    stringBuffer.append("身体年龄："+physical_age+"\n");
    stringBuffer.append("身体评分："+Body_score+"\n");
    stringBuffer.append("水肿度："+Edematous_degree+"\n");
    stringBuffer.append("肥胖度："+Fat_degree+"\n");
    stringBuffer.append("肌肉控制："+Muscle_control+"\n");
    stringBuffer.append("体重控制："+Weight_control+"\n");
    stringBuffer.append("脂肪控制："+Fat_control+"\n");
    //no
    String    no_one     = Utils.toResult(mS,122,126);
    //躯干脂肪率
    String    Trunk_fat_rate      = Utils.toResult(mS,126,130);
    //右手脂肪率
    String    Right_hand_fat_rate       = Utils.toResult(mS,130,134);
    //左手脂肪率
    String    Left_hand_fat_ratio       = Utils.toResult(mS,134,138);
    //右脚脂肪率
    String    Right_foot_fat_ratio        = Utils.toResult(mS,138,142);
    //左脚脂肪率
    String    LEFT_foot_fat_ratio        = Utils.toResult(mS,142,146);
    stringBuffer.append("躯干脂肪率："+Trunk_fat_rate+"\n");
    stringBuffer.append("右手脂肪率："+Right_hand_fat_rate+"\n");
    stringBuffer.append("左手脂肪率："+Left_hand_fat_ratio+"\n");
    stringBuffer.append("右脚脂肪率："+Right_foot_fat_ratio+"\n");
    stringBuffer.append("左脚脂肪率："+LEFT_foot_fat_ratio+"\n");

    //躯干肌肉量
    String    Trunk_muscle_volume        = Utils.toResult(mS,146,150);
    //右手肌肉量
    String    Right_hand_muscle_volume        = Utils.toResult(mS,150,154);
    //左手肌肉
    String    LEFT_hand_muscle_volume        = Utils.toResult(mS,154,158);
    //右脚肌肉
    String    Right_root_muscle_volume        = Utils.toResult(mS,158,162);
    //左脚肌肉
    String    LEFT_root_muscle_volume        = Utils.toResult(mS,162,166);
    stringBuffer.append("躯干肌肉量："+Trunk_muscle_volume+"\n");
    stringBuffer.append("右手肌肉量："+Right_hand_muscle_volume+"\n");
    stringBuffer.append("左手肌肉："+LEFT_hand_muscle_volume+"\n");
    stringBuffer.append("右脚肌肉："+Right_root_muscle_volume+"\n");
    stringBuffer.append("左脚肌肉："+LEFT_root_muscle_volume+"\n");
    //颈围
    String    Neck_circumference        = Utils.toResult(mS,166,170);
    //腰围
    String    waist        = Utils.toResult(mS,170,174);
    //臀围
    String    Hipline        = Utils.toResult(mS,174,178);
    //胸围
    String    Bust        = Utils.toResult(mS,178,182);
    //右上臂围
    String    r_t_Hipline        = Utils.toResult(mS,182,186);
    //左上臂围
    String    l_t_Hipline        = Utils.toResult(mS,186,190);
    //右大腿围
    String    r_b_Hipline        = Utils.toResult(mS,190,194);
    //左大腿围
    String    l_b_Hipline        = Utils.toResult(mS,194,198);
    stringBuffer.append("颈围："+Neck_circumference+"\n");
    stringBuffer.append("腰围："+waist+"\n");
    stringBuffer.append("臀围："+Hipline+"\n");
    stringBuffer.append("胸围："+Bust+"\n");
    stringBuffer.append("右上臂围："+r_t_Hipline+"\n");
    stringBuffer.append("左上臂围："+l_t_Hipline+"\n");
    stringBuffer.append("右大腿围："+r_b_Hipline+"\n");
    stringBuffer.append("左大腿围："+l_b_Hipline+"\n");
    return stringBuffer.toString();
  }
  public static BodyInfoModel toShowFinalResultModel(String mS){
    BodyInfoModel mModel = new BodyInfoModel();
    mModel.  weight= Utils.toResult(mS,6,10);
    //总阻抗
    mModel.  all_impedance= Utils.toResult(mS,10,14);
    mModel.  left_h_impedance= Utils.toResult(mS,14,18);
    mModel.  right_h_impedance= Utils.toResult(mS,18,22);
    mModel.  left_f_impedance= Utils.toResult(mS,22,26);
    mModel.  right_f_impedance= Utils.toResult(mS,26,30);
    mModel.  body_impedance= Utils.toResult(mS,30,34);
    //脂肪重
    mModel.  fat_weight= Utils.toResult(mS,34,38);
    //肌肉重
    mModel.  muscle_weight= Utils.toResult(mS,38,42);
    //总水重
    mModel.  total_water_weight= Utils.toResult(mS,42,46);
    //细胞外液
    mModel.  extracellular_fluid= Utils.toResult(mS,46,50);
    //细胞内液
    mModel.  intracellular_Fluid= Utils.toResult(mS,50,54);
    //去脂体重
    mModel.  fat_free= Utils.toResult(mS,54,58);
    //标准肌肉
    mModel.  Standard_muscle= Utils.toResult(mS,58,62);
    //骨盐量
    mModel.  Bone_salt_content = Utils.toResult(mS,62,66);
    //骨骼肌重
    mModel.   skeletal_muscle = Utils.toResult(mS,66,70);
    //蛋白质
    mModel.    protein = Utils.toResult(mS,70,74);
    //体质指数
    mModel.    physique_num = Utils.toResult(mS,74,78);
    //基础代谢
    mModel.    basal_metabolism = Utils.toResult(mS,78,82);
    //体脂百分比
    mModel.    Body_fat_percentage = Utils.toResult(mS,82,86);
    //含水百分比
    mModel.    Percentage_of_water  = Utils.toResult(mS,86,90);
    //标准体重
    mModel.    stander_weight  = Utils.toResult(mS,90,94);
    //内脏脂肪等级
    mModel.    visceral_fat  = Utils.toResult(mS,94,98);
    //身体年龄
    mModel.    physical_age   = Utils.toResult(mS,98,100);
    //身体评分
    mModel.    Body_score    = Utils.toResult(mS,100,102);
    //水肿度
    mModel.    Edematous_degree    = Utils.toResult(mS,102,106);
    //肥胖度
    mModel.    Fat_degree     = Utils.toResult(mS,106,110);
    //肌肉控制
    mModel.    Muscle_control     = Utils.toResult(mS,110,114);
    //体重控制
    mModel.    Weight_control     = Utils.toResult(mS,114,118);
    //脂肪控制
    mModel.    Fat_control     = Utils.toResult(mS,118,122);
    //no
    mModel.    no_one     = Utils.toResult(mS,122,126);
    //躯干脂肪率
    mModel.    Trunk_fat_rate      = Utils.toResult(mS,126,130);
    //右手脂肪率
    mModel.    Right_hand_fat_rate       = Utils.toResult(mS,130,134);
    //左手脂肪率
    mModel.    Left_hand_fat_ratio       = Utils.toResult(mS,134,138);
    //右脚脂肪率
    mModel.    Right_foot_fat_ratio        = Utils.toResult(mS,138,142);
    //左脚脂肪率
    mModel.    LEFT_foot_fat_ratio        = Utils.toResult(mS,142,146);
    //躯干肌肉量
    mModel.    Trunk_muscle_volume        = Utils.toResult(mS,146,150);
    //右手肌肉量
    mModel.    Right_hand_muscle_volume        = Utils.toResult(mS,150,154);
    //左手肌肉
    mModel.    LEFT_hand_muscle_volume        = Utils.toResult(mS,154,158);
    //右脚肌肉
    mModel.    Right_root_muscle_volume        = Utils.toResult(mS,158,162);
    //左脚肌肉
    mModel.    LEFT_root_muscle_volume        = Utils.toResult(mS,162,166);
    //颈围
    mModel.    Neck_circumference        = Utils.toResult(mS,166,170);
    //腰围
    mModel.    waist        = Utils.toResult(mS,170,174);
    //臀围
    mModel.    Hipline        = Utils.toResult(mS,174,178);
    //胸围
    mModel.    Bust        = Utils.toResult(mS,178,182);
    //右上臂围
    mModel.    r_t_Hipline        = Utils.toResult(mS,182,186);
    //左上臂围
    mModel.    l_t_Hipline        = Utils.toResult(mS,186,190);
    //右大腿围
    mModel.    r_b_Hipline        = Utils.toResult(mS,190,194);
    //左大腿围
    mModel.    l_b_Hipline        = Utils.toResult(mS,194,198);
    return mModel;
  }

  /**
   * 发送开始测量指令
   * @param
   * @return
   */
  public static String sendStartCmd() {
    String head_str = "CA0806";
    StringBuffer mBuffer = new StringBuffer(head_str);
    String tail_str = "000000000000";
    mBuffer.append(tail_str);
    return mBuffer.toString();
  }

  /**
   * 发送用户数据
   * @param height
   * @param age
   * @param sex
   * @param runModel
   * @return
   */
  public static String loadUserInfoCmd(String height,String age,String sex,String runModel) {
    String head_str = "CA0706";
    StringBuffer mBuffer = new StringBuffer(head_str);
    mBuffer.append(NumUtils.string2HexString(height));
    mBuffer.append(NumUtils.string2HexString(age));
    mBuffer.append(NumUtils.string2HexString(sex));
    mBuffer.append(runModel);
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
  public static String getTime(){
    long timeStampSec = System.currentTimeMillis()/1000;
    String timestamp = String.format("%010d", timeStampSec);

    return timestamp;

  }
}
