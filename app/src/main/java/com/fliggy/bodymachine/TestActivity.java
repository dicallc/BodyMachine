package com.fliggy.bodymachine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.fliggy.bodymachine.widgets.KeyBoardView;

public class TestActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(new KeyBoardView(this));
  }
}
