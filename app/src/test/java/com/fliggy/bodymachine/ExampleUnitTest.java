package com.fliggy.bodymachine;

import android.serialport.utils.NumUtils;
import android.serialport.utils.Utils;
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
}