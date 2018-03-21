package com.fliggy.bodymachine;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fliggy.bodymachine.model.DeviderModel;
import java.util.ArrayList;

/**
 * Created by dicallc on 2018/3/20.
 */

public class TestActivity extends Activity {
  @BindView(R.id.lbs_view) LbsView mLbsView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.custom_lbs);
    ButterKnife.bind(this);
    DeviderModel mWeightDevider = getWeightDevider("115");
    mLbsView.setData(mWeightDevider);
  }

  @NonNull private DeviderModel getWeightDevider(String num) {
    DeviderModel mDeviderModel=new DeviderModel();
    ArrayList<String> list_text=new ArrayList<>();
    list_text.add("55");
    list_text.add("70");
    list_text.add("85");
    list_text.add("100");
    list_text.add("115");
    list_text.add("130");
    list_text.add("145");
    list_text.add("160");
    list_text.add("175");
    list_text.add("190");
    list_text.add("241");
    for (int i = 0; i <list_text.size() ; i++) {
      if(Integer.parseInt(list_text.get(i))>Integer.parseInt(num)){
        mDeviderModel.devider_limit=i;
        break;
      }
    }
    mDeviderModel.devider_limit_num=Integer.parseInt(num);
    mDeviderModel.devider_text=list_text;
    return mDeviderModel;
  }
}
