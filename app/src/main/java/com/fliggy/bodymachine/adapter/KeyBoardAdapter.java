package com.fliggy.bodymachine.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fliggy.bodymachine.R;
import java.util.List;

/**
 * Created by dicallc on 2018/4/13.
 */

public class KeyBoardAdapter extends SFBaseAdapter<String, BaseViewHolder> {

  public KeyBoardAdapter( @Nullable List data) {
    super(R.layout.item_keyboard_view, data);
  }

  @Override protected void convert(BaseViewHolder helper, String item) {
    helper.setText(R.id.item_title,item);
  }
}
