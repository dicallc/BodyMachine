package com.fliggy.bodymachine.dao;

import com.fliggy.bodymachine.model.MsgModel;
import com.fliggy.http_module.http.callback.DaoCallBack;
import com.fliggy.http_module.http.callback.JCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

/**
 * Created by dicallc on 2018/3/16.
 */

public class Dao {

  public static void postCelect(String id,final DaoCallBack<MsgModel> mLinstener) {
    OkGo.<MsgModel>post("")
        .params("commodity_id", id)
        .execute(new JCallBack<MsgModel>(MsgModel.class) {

          @Override public void onSuccess(Response<MsgModel> response) {
            mLinstener.onSuccess(200, null);
          }

          @Override public void onError(Response<MsgModel> response) {
            super.onError(response);
          }
        });
  }
}
