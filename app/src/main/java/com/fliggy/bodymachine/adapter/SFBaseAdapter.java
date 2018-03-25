package com.fliggy.bodymachine.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class SFBaseAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {

  public SFBaseAdapter(int layoutResId, @Nullable List data) {
    super(layoutResId, data);
  }

  @Override public void addData(@NonNull Collection<? extends T> data) {
    if (null == data) {
      data = new ArrayList<>();
    }

    super.addData(data);
  }
}