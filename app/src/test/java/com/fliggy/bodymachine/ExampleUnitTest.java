package com.fliggy.bodymachine;

import android.serialport.utils.NumUtils;
import android.serialport.utils.Utils;
import com.socks.library.KLog;
import java.io.File;
import java.io.FileInputStream;
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
      //脂肪重
      String  fat_weight= Utils.toResult(mS,10,14);
      //去脂体重
      String  fat_free= Utils.toResult(mS,14,18);
      //肌肉重
      String  muscle_weight= Utils.toResult(mS,18,22);
      //总水重
      String  total_water_weight= Utils.toResult(mS,22,26);
      //骨盐量
      String  Bone_salt_content = Utils.toResult(mS,26,30);
      //骨骼肌重
      String   skeletal_muscle = Utils.toResult(mS,30,34);
      //蛋白质
      String    protein = Utils.toResult(mS,34,38);
      //体质指数
      String    physique_num = Utils.toResult(mS,38,42);
      //基础代谢
      String    basal_metabolism = Utils.toResult(mS,42,46);
      //体脂百分比
      String    Body_fat_percentage = Utils.toResult(mS,46,50);
      //含水百分比
      String    Percentage_of_water  = Utils.toResult(mS,50,54);
      //身体年龄
      String    physical_age   = Utils.toResult(mS,54,58);
      //身体评分
      String    Body_score    = Utils.toResult(mS,58,62);
      //肥胖度
      String    Fat_degree     = Utils.toResult(mS,62,66);
      //躯干脂肪率
      String    Trunk_fat_rate      = Utils.toResult(mS,66,70);
      //右手脂肪率
      String    Right_hand_fat_rate       = Utils.toResult(mS,70,74);
      //左手脂肪率
      String    Left_hand_fat_ratio       = Utils.toResult(mS,74,78);
      //右脚脂肪率
      String    Right_foot_fat_ratio        = Utils.toResult(mS,78,82);
      //左脚脂肪率
      String    LEFT_foot_fat_ratio        = Utils.toResult(mS,82,86);
      //躯干肌肉量
      String    Trunk_muscle_volume        = Utils.toResult(mS,86,90);
      //右手肌肉量
      String    Right_hand_muscle_volume        = Utils.toResult(mS,90,94);
      //左手肌肉
      String    LEFT_hand_muscle_volume        = Utils.toResult(mS,94,98);
      //右脚肌肉
      String    Right_root_muscle_volume        = Utils.toResult(mS,98,102);
      //左脚肌肉
      String    LEFT_root_muscle_volume        = Utils.toResult(mS,102,106);
      KLog.e(weight);
    }
}