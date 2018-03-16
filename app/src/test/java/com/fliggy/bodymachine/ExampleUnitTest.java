package com.fliggy.bodymachine;

import android.serialport.utils.NumUtils;
import android.serialport.utils.Utils;
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
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void sendClear() throws Exception {
        String str = Utils.sendClearCmd(true);
    }
    @Test
    public void sendUser() throws Exception {
        //正常CA0306AA140300000070
        //测试CA0306B0180101000067
        //String str = Utils.loadUserInfoCmd();
    }
    @Test
    public void recevelUser() throws Exception {
        String pathname = "F:\\user.txt";
        File filename = new File(pathname); // 要读取以上路径的input。txt文件
        Utils.parseData( new FileInputStream(filename));
    }
    @Test
    public void testFunction() throws Exception {
        String mS = Utils.checkXor("CA0206010000000000");
        String mS1 = Integer.toHexString(175);
    }
    @Test
    public void testFunction1() throws Exception {
        String mS = NumUtils.string2HexString("175");
        String mS1 = NumUtils.string2HexString("1");
        String mS2 = NumUtils.string2HexString("10");
        String mS3 = NumUtils.string2HexString("9");
    }
    @Test
    public void testFunction2() throws Exception {
       String str="CA02 06 01 00 00 00 00 00 CF";
        String mReplace = str.replace(" ", "");
        System.out.print(mReplace);
    }
    @Test
    public void testFunction3() throws Exception {
       String str="CA0206010000000000CF";
        StringBuffer mStringBuffer = new StringBuffer(str);
        String mSubstring = mStringBuffer.substring(4, 6);
        String code = mStringBuffer.substring(2, 4);
        int mInt = Integer.parseInt(mSubstring, 16);
        int mInt2 = Integer.parseInt(code, 16);
        int full_lenght= mInt*2+8;
        assertEquals(str.length(), full_lenght);
    }
    @Test
    public void testFunction4() throws Exception {
      String mS = Utils.sendStartCmd();
      KLog.e(mS);
    }
    @Test
    public void testFunction6() throws Exception {
      String mS = Utils.getTime();
      KLog.e(mS);
    }
    @Test
    public void testFunction5() throws Exception {
      String mS = "CA03610A141E283200000000000000000000000000000000000000000000000000000000000000000000000000000000000000\n"
          + "                  000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000B9";
      //体重
      String  weight= Utils.toResult(mS,6,10);
      //总阻抗
      String  all_impedance= Utils.toResult(mS,10,14);
      String  left_h_impedance= Utils.toResult(mS,14,18);
      String  right_h_impedance= Utils.toResult(mS,18,22);
      String  left_f_impedance= Utils.toResult(mS,22,26);
      String  right_f_impedance= Utils.toResult(mS,26,30);
      String  body_impedance= Utils.toResult(mS,30,34);
      //脂肪重
      String  fat_weight= Utils.toResult(mS,34,38);
      //肌肉重
      String  muscle_weight= Utils.toResult(mS,38,42);
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
      String    physical_age   = Utils.toResult(mS,98,102);
      //身体评分
      String    Body_score    = Utils.toResult(mS,102,106);
      //水肿度
      String    Edematous_degree    = Utils.toResult(mS,106,110);
      //肥胖度
      String    Fat_degree     = Utils.toResult(mS,110,114);
      //肌肉控制
      String    Muscle_control     = Utils.toResult(mS,114,118);
      //体重控制
      String    Weight_control     = Utils.toResult(mS,118,122);
      //脂肪控制
      String    Fat_control     = Utils.toResult(mS,122,126);
      //no
      String    no_one     = Utils.toResult(mS,126,130);
      //躯干脂肪率
      String    Trunk_fat_rate      = Utils.toResult(mS,130,134);
      //右手脂肪率
      String    Right_hand_fat_rate       = Utils.toResult(mS,134,138);
      //左手脂肪率
      String    Left_hand_fat_ratio       = Utils.toResult(mS,138,142);
      //右脚脂肪率
      String    Right_foot_fat_ratio        = Utils.toResult(mS,142,146);
      //左脚脂肪率
      String    LEFT_foot_fat_ratio        = Utils.toResult(mS,146,150);
      //躯干肌肉量
      String    Trunk_muscle_volume        = Utils.toResult(mS,150,154);
      //右手肌肉量
      String    Right_hand_muscle_volume        = Utils.toResult(mS,154,158);
      //左手肌肉
      String    LEFT_hand_muscle_volume        = Utils.toResult(mS,158,162);
      //右脚肌肉
      String    Right_root_muscle_volume        = Utils.toResult(mS,162,166);
      //左脚肌肉
      String    LEFT_root_muscle_volume        = Utils.toResult(mS,166,170);
      //颈围
      String    Neck_circumference        = Utils.toResult(mS,170,174);
      //腰围
      String    waist        = Utils.toResult(mS,174,178);
      //臀围
      String    Hipline        = Utils.toResult(mS,178,182);
      //胸围
      String    Bust        = Utils.toResult(mS,182,186);
      //右上臂围
      String    r_t_Hipline        = Utils.toResult(mS,186,190);
      //左上臂围
      String    l_t_Hipline        = Utils.toResult(mS,190,194);
      //右大腿围
      String    r_b_Hipline        = Utils.toResult(mS,194,198);
      //左大腿围
      String    l_b_Hipline        = Utils.toResult(mS,198,202);

      KLog.e(weight);
    }
  @Test
  public void testFunction7() {
   String authKey="zp0adsxvtp1oeiw1k7isd3tz9pft";
   String str="AtUserID=1521043729&BMI=1521043729&BasalMeta=1521043729&BoneSalt=1521043729&FatFreeBodyWt=1521043729&FatWt=1521043729&LhandFatRate=1521043729&LhandMsclVal=1521043729&LlegFatRate=1521043729&LlegMsclVal=1521043729&MuscleWt=1521043729&ObesityDegree=1521043729&PBF=1521043729&PBW=1521043729&PhysicalAge=1521043729&PhysicalScore=1521043729&Protein=1521043729&RecID=1521043729&RecTime=1521043729&RhandFatRate=1521043729&RhandMsclVal=1521043729&RlegFatRate=1521043729&RlegMsclVal=1521043729&ToatalWatWt=1521043729&TrunkFatRate=1521043729&TrunkMsclVal=1521043729&Wt=1521043729&time=1521080922";
   String finalStr=authKey+str+authKey;
    String mS = null;
    try {
      mS = com.fliggy.bodymachine.utils.Utils.sha1(finalStr);
    } catch (NoSuchAlgorithmException mE) {
      mE.printStackTrace();
    }
    KLog.e(mS);
  }
  @Test
  public void testFunction8() {
    String authKey = "5A020601F400000000AB";
    Utils.toResult(authKey,6,10);
  }
  @Test
  public void testFunction9() {
    String authKey = "5A040601F400000000AB";
    Utils.toResult(authKey,6,8);
  }
}