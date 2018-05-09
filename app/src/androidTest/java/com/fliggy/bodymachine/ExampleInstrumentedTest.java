package com.fliggy.bodymachine;

import android.support.test.runner.AndroidJUnit4;
import com.fliggy.bodymachine.dao.Dao;
import com.fliggy.bodymachine.model.MsgModel;
import com.fliggy.http_module.http.callback.DaoCallBack;
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
        Dao.postCelect("", new DaoCallBack<MsgModel>() {
            @Override public void onSuccess(int code, MsgModel result) {
                System.out.print(result);
            }

            @Override public void onFail(int code, String result) {
                System.out.print(result);
            }
        });
    }
}
