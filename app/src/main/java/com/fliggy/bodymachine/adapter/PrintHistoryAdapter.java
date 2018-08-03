package com.fliggy.bodymachine.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fliggy.bodymachine.R;
import java.util.List;

/**
 * Created by dicallc on 2018/3/25 0025.
 */

public class PrintHistoryAdapter extends SFBaseAdapter<String, BaseViewHolder> {
  public PrintHistoryAdapter( @Nullable List data) {
    super(R.layout.item_print_history, data);
  }

  @Override protected void convert(BaseViewHolder helper, String item) {
    String[] mSplit = item.split(" ");
    helper.setText(R.id.item_title,mSplit[0]+"\n"+mSplit[1]);
  }
}
