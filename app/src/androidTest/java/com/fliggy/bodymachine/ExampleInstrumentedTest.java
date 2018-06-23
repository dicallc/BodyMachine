package com.fliggy.bodymachine;

import android.support.test.runner.AndroidJUnit4;
import com.fliggy.bodymachine.dao.Dao;
import com.fliggy.bodymachine.model.MachineModel;
import com.fliggy.http_module.http.callback.DaoCallBack;
import com.socks.library.KLog;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        Dao.postData("android测试提交数据", new DaoCallBack<MachineModel>() {
            @Override public void onSuccess(int code, MachineModel result) {
                KLog.e("成功了");
            }

            @Override public void onFail(int code, String result) {
                KLog.e("失败了");
            }
        });
    }
}
