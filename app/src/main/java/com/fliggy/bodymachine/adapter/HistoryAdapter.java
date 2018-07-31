package com.fliggy.bodymachine.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fliggy.bodymachine.R;
import java.util.List;

/**
 * Created by dicallc on 2018/3/25 0025.
 */

public class HistoryAdapter extends SFBaseAdapter<String, BaseViewHolder> {
  public HistoryAdapter( @Nullable List data) {
    super(R.layout.item_history, data);
  }

  @Override protected void convert(BaseViewHolder helper, String item) {
    helper.setText(R.id.item_title,item);
  }
}
