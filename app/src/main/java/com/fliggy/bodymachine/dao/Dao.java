package com.fliggy.bodymachine.dao;

import com.fliggy.bodymachine.model.BodyInfoModel;
import com.fliggy.bodymachine.model.MsgModel;
import com.fliggy.http_module.http.callback.DaoCallBack;
import com.fliggy.http_module.http.callback.JCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

/**
 * Created by dicallc on 2018/3/16.
 */

public class Dao {

  public static void postCelect(BodyInfoModel mBodyInfoModel, final DaoCallBack<MsgModel> mLinstener) {
    String mTestSign = com.fliggy.bodymachine.utils.Utils.getTestSign();
    String url="http://193.112.106.43:8080/v1/build?";
    String r_url = url + mTestSign;
    OkGo.<MsgModel>post(r_url)
        .execute(new JCallBack<MsgModel>(MsgModel.class) {

          @Override public void onSuccess(Response<MsgModel> response) {
            mLinstener.onSuccess(200, null);
          }

          @Override public void onError(Response<MsgModel> response) {
            super.onError(response);
          }
        });
  }
  public static void postCelect(BodyInfoModel mBodyInfoModel, String mMache_id,final DaoCallBack<MsgModel> mLinstener) {
    String mTestSign = com.fliggy.bodymachine.utils.Utils.getSign(mBodyInfoModel,mMache_id);
    String url="http://193.112.106.43:8080/v1/build?";
    String r_url = url + mTestSign;
    OkGo.<MsgModel>post(r_url)
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
