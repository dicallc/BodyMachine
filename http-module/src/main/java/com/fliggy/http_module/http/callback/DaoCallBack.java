package com.fliggy.http_module.http.callback;

public interface DaoCallBack<T> {
  public void onSuccess(int code, T result);

  public void onFail(int code, String result);
}