package com.fliggy.bodymachine;

import android.serialport.utils.NumUtils;
import android.serialport.utils.Utils;
import com.fliggy.bodymachine.utils.Arith;
import com.fliggy.bodymachine.utils.BigDecimalUtils;
import com.socks.library.KLog;
import java.io.File;
import java.io.FileInputStream;
import java.security.NoSuchAlgorithmException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
  @Test public void addition_isCorrect() throws Exception {
    assertEquals(4, 2 + 2);
  }

  @Test public void sendClear() throws Exception {
    String str = Utils.sendClearCmd(true);
  }

  @Test public void sendUser() throws Exception {
    //正常CA0306AA140300000070
    //测试CA0306B0180101000067
    //String str = Utils.loadUserInfoCmd();
  }

  @Test public void recevelUser() throws Exception {
    String pathname = "F:\\user.txt";
    File filename = new File(pathname); // 要读取以上路径的input。txt文件
    Utils.parseData(new FileInputStream(filename));
  }

  @Test public void testFunction() throws Exception {
    String mS = Utils.checkXor("CA0206010000000000");
    String mS1 = Integer.toHexString(175);
  }

  @Test public void testFunction1() throws Exception {
    String mS = NumUtils.string2HexString("175");
    String mS1 = NumUtils.string2HexString("1");
    String mS2 = NumUtils.string2HexString("10");
    String mS3 = NumUtils.string2HexString("9");
  }

  @Test public void testFunction2() throws Exception {
    String str = "CA02 06 01 00 00 00 00 00 CF";
    String mReplace = str.replace(" ", "");
    System.out.print(mReplace);
  }

  @Test public void testFunction3() throws Exception {
    String str = "CA0206010000000000CF";
    StringBuffer mStringBuffer = new StringBuffer(str);
    String mSubstring = mStringBuffer.substring(4, 6);
    String code = mStringBuffer.substring(2, 4);
    int mInt = Integer.parseInt(mSubstring, 16);
    int mInt2 = Integer.parseInt(code, 16);
    int full_lenght = mInt * 2 + 8;
    assertEquals(str.length(), full_lenght);
  }

  @Test public void testFunction4() throws Exception {
    String mS = Utils.sendStartCmd();
    KLog.e(mS);
  }

  @Test public void testFunction6() throws Exception {
    String mS = Utils.getTime();
    KLog.e(mS);
  }

  /**
   * 第五版
   * @throws Exception
   */
  @Test public void testFunction5() throws Exception {
    String mS =
        "5A0360036C00000D340D1107CE07EB00E6000001ED000000000000000000000022013600690000038A018D01BA0288005A343400000000009680E380B20000019F017F019E0182018000DB001600160058005A0000000000000000000800000000000025";
    //体重
    String weight = Utils.toResultHasPoint(mS, 6, 10);
    //预留
    String all_impedance = Utils.toResultHasPoint(mS, 10, 14);
    //左手阻抗
    String left_h_impedance = Utils.toResultHasPoint(mS, 14, 18);
    //右手阻抗
    String right_h_impedance = Utils.toResultHasPoint(mS, 18, 22);
    //左脚阻抗
    String left_f_impedance = Utils.toResultHasPoint(mS, 22, 26);
    //右脚阻抗
    String right_f_impedance = Utils.toResultHasPoint(mS, 26, 30);
    //躯干阻抗
    String body_impedance = Utils.toResultHasPoint(mS, 30, 34);
    //预留
    String fat_weight = Utils.toResultHasPoint(mS, 34, 38);
    //肌肉重
    String muscle_weight = Utils.toResultHasPoint(mS, 38, 42);
    //预留
    String total_water_weight = Utils.toResultHasPoint(mS, 42, 46);
    //预留
    String extracellular_fluid = Utils.toResultHasPoint(mS, 46, 50);
    //预留
    String intracellular_Fluid = Utils.toResultHasPoint(mS, 50, 54);
    //预留
    String fat_free = Utils.toResultHasPoint(mS, 54, 58);
    //预留
    String Standard_muscle = Utils.toResultHasPoint(mS, 58, 62);
    //骨盐量
    String Bone_salt_content = Utils.toResultHasPoint(mS, 62, 66);
    //骨骼肌重
    String skeletal_muscle = Utils.toResultHasPoint(mS, 66, 70);
    //蛋白质
    String protein = Utils.toResultHasPoint(mS, 70, 74);
    //体质指数
    String physique_num = Utils.toResultHasPoint(mS, 74, 78);
    //基础代谢1
    String basal_metabolism = Utils.toResultHasPoint(mS, 78, 82);
    //体脂百分比
    String Body_fat_percentage = Utils.toResultHasPoint(mS, 82, 86);
    //体水分率
    String Percentage_of_water = Utils.toResultHasPoint(mS, 86, 90);
    //标准体重
    String stander_weight = Utils.toResultHasPoint(mS, 90, 94);
    //内脏脂肪等级
    String visceral_fat = Utils.toResultHasPoint(mS, 94, 98);
    //身体年龄
    String physical_age = Utils.toResultHasPoint(mS, 98, 100);
    //身体评分
    String Body_score = Utils.toResultHasPoint(mS, 100, 102);
    //预留
    String Edematous_degree = Utils.toResultHasPoint(mS, 102, 106);
    //预留
    String Fat_degree = Utils.toResultHasPoint(mS, 106, 110);
    //肌肉控制
    String Muscle_control = Utils.toResultHasPoint(mS, 110, 114);
    //体重控制
    String Weight_control = Utils.toResultHasPoint(mS, 114, 118);
    //脂肪控制
    String Fat_control = Utils.toResultHasPoint(mS, 118, 122);
    //预留
    String no_one = Utils.toResultHasPoint(mS, 122, 126);
    //躯干脂肪率
    String Trunk_fat_rate = Utils.toResultHasPoint(mS, 126, 130);
    //右手脂肪率
    String Right_hand_fat_rate = Utils.toResult(mS, 130, 134);
    //左手脂肪率
    String Left_hand_fat_ratio = Utils.toResultHasPoint(mS, 134, 138);
    //右脚脂肪率
    String Right_foot_fat_ratio = Utils.toResultHasPoint(mS, 138, 142);
    //左脚脂肪率
    String LEFT_foot_fat_ratio = Utils.toResultHasPoint(mS, 142, 146);
    //躯干肌肉量
    String Trunk_muscle_volume = Utils.toResultHasPoint(mS, 146, 150);
    //右手肌肉量2.2
    String Right_hand_muscle_volume = Utils.toResultHasPoint(mS, 150, 154);
    //左手肌肉2.2
    String LEFT_hand_muscle_volume = Utils.toResultHasPoint(mS, 154, 158);
    //右脚肌肉8.8
    String Right_root_muscle_volume = Utils.toResultHasPoint(mS, 158, 162);
    //左脚肌肉9.0
    String LEFT_root_muscle_volume = Utils.toResultHasPoint(mS, 162, 166);
    //颈围1
    String Neck_circumference = Utils.toResultHasPoint(mS, 166, 170);
    //腰围1
    String waist = Utils.toResultHasPoint(mS, 170, 174);
    //臀围1
    String Hipline = Utils.toResultHasPoint(mS, 174, 178);
    //腰臀比
    String Bust = Utils.toResultHasPoint(mS, 178, 182);
    //右上臂围
    String r_t_Hipline = Utils.toResultHasPoint(mS, 182, 186);
    //左上臂围1
    String l_t_Hipline = Utils.toResultHasPoint(mS, 186, 190);
    //右大腿围1
    String r_b_Hipline = Utils.toResultHasPoint(mS, 190, 194);
    //左大腿围1
    String l_b_Hipline = Utils.toResultHasPoint(mS, 194, 198);

    KLog.e(weight);
  }

  @Test public void testFunction7() {
    String authKey = "zp0adsxvtp1oeiw1k7isd3tz9pft";
    String str =
        "AtUserID=1521043729&BMI=1521043729&BasalMeta=1521043729&BoneSalt=1521043729&FatFreeBodyWt=1521043729&FatWt=1521043729&LhandFatRate=1521043729&LhandMsclVal=1521043729&LlegFatRate=1521043729&LlegMsclVal=1521043729&MuscleWt=1521043729&ObesityDegree=1521043729&PBF=1521043729&PBW=1521043729&PhysicalAge=1521043729&PhysicalScore=1521043729&Protein=1521043729&RecID=1521043729&RecTime=1521043729&RhandFatRate=1521043729&RhandMsclVal=1521043729&RlegFatRate=1521043729&RlegMsclVal=1521043729&ToatalWatWt=1521043729&TrunkFatRate=1521043729&TrunkMsclVal=1521043729&Wt=1521043729&time=1521080922";
    String finalStr = authKey + str + authKey;
    String mS = null;
    try {
      mS = com.fliggy.bodymachine.utils.Utils.sha1(finalStr);
    } catch (NoSuchAlgorithmException mE) {
      mE.printStackTrace();
    }
    KLog.e(mS);
  }

  @Test public void testFunction8() {
    String authKey = "5A020601F400000000AB";
    Utils.toResult(authKey, 6, 10);
  }

  @Test public void testFunction9() {
    String authKey = "5A040601F400000000AB";
    Utils.toResult(authKey, 6, 8);
  }

  @Test public void testFunction10() {
    double mDiv = Arith.div("95", "1.9");
    KLog.e(mDiv);
  }

  @Test public void testFunction11() {
    float authKey = 154;
    float mI = authKey / 10;
    KLog.e(mI);
  }

  @Test public void testFunction12() {
    int n = 12 - 1;
    n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    KLog.e(n);
  }

  @Test public void testFunction13() {
    String num = "000";
    int mI = Integer.parseInt(num);
    KLog.e(mI);
  }

  @Test public void testFunction14() {
    float mV = Arith.MyDiv("65", 130);
    System.out.print(mV);
  }

  @Test public void testFunction15() {
    //String mTestSign = com.fliggy.bodymachine.utils.Utils.getTestSign(mMache_id);
    //String url="http://193.112.106.43:8080/v1/build?";
    //String mS = url + mTestSign;
    //System.out.print(mS);
  }

  @Test public void testFunction16() {
    double low = 175 * 175 * 22 * 0.85;
    double low1 = low * 0.0001;
    //DeviderModel mDevider = DataSource.getDeviderPercent(DataSource.getWeightData(), 130 + "");
    //float mDevider_percent = mDevider.devider_percent;
    //System.out.print(mDevider_percent);
  }

  @Test public void testFunction17() {
    String str = "10.5";
    String mMul = Arith.mulString(str, "0.01");
    //DeviderModel mDevider = DataSource.getDeviderPercent(DataSource.getWeightData(), 130 + "");
    //float mDevider_percent = mDevider.devider_percent;
    //System.out.print(mDevider_percent);
  }

  @Test public void testFunction18() {
    if (Float.parseFloat("18.1") - Float.parseFloat("18.2") < 0) {
      System.out.print("成功");
    }
  }

  @Test public void testFunction19() {
    String str = "34.7772";
    String mRound = BigDecimalUtils.round(str, 2);
    float mSub = BigDecimalUtils.sub(mRound, "40");
    System.out.print(mSub);
  }

  @Test public void testFunction20() {
    //Weight(kg),Height(cm),BMI = Weight/((Height/100)^2)。
    //String height = "175";
    //String Weight = "80";
    //String sex = "0";
    //String Age = "25";
    ////BMI
    //String BMI= Float.parseFloat(Weight)/((Integer.parseInt(height)/100)^2)+"";
    ////9.0326 * 1/(Height/100)
    //String a1 = BigDecimalUtils.mul("9.0326", BigDecimalUtils.divString(height, "100", 4));
    //
    ////0.37064 * 1/Age
    //String a2 = BigDecimalUtils.mul("0.37064", Age);
    ////106.4763 * 1/Weight
    //String a3 = BigDecimalUtils.mul("106.4763", Weight);
    ////0.1656* BMI
    //String a4 = BigDecimalUtils.divString("0.1656", BMI, 4);
    ////0.00040719 * Age
    //String a5 = BigDecimalUtils.divString("0.00040719", Age);
    ////0.051186 * Weight
    //String a6 = BigDecimalUtils.divString("0.051186", Weight);
    //String a7 = BigDecimalUtils.addString("-0.98666", a1);
    //String a8 = BigDecimalUtils.subString(a7, a2);
    //String a9 = BigDecimalUtils.subString(a8, a3);
    //String a10= BigDecimalUtils.subString(a9, a4);
    //String a11= BigDecimalUtils.subString(a10, a5);
    //String a12= BigDecimalUtils.subString(a11, a6);
    //System.out.print("a12"+a12);
    double height = 1.7;
    double Weight = 60;
    //double sex = "0";
    double Age = 28;
    double BMI=Weight/(height*height);
    double A1 = -0.98666 + 9.0326 * 1 / height
        - 0.37064 / Age
        - 106.4763 / Weight
        - 0.1656 * BMI
        - 0.00040719 * Age + 0.051186 * Weight;
    System.out.print(A1);
    //2.160488371052892
    //-0.98666 + 9.0326 * 1/(Height/100) - 0.37064 * 1/Age - 106.4763 * 1/Weight –0.1656* BMI - 0.00040719 * Age + 0.051186 * Weight
  }


  @Test public void testFunction21() {
    long mCurTimeLong = com.fliggy.bodymachine.utils.Utils.getCurTimeLong();
    String mDateToString =
        com.fliggy.bodymachine.utils.Utils.getDateToString(mCurTimeLong, "yy-MM-dd HH:mm");
    System.out.print(mDateToString);
  }
}