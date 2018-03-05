package com.fliggy.bodymachine;

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
}