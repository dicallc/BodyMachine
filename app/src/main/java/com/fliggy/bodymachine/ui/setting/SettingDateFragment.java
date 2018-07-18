package com.fliggy.bodymachine.ui.setting;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.fliggy.bodymachine.R;
import com.socks.library.KLog;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import me.yokeyword.fragmentation.SupportFragment;

/**
 *
 */
public class SettingDateFragment extends SupportFragment {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  @BindView(R.id.btn_year_add) ImageView mBtnYearAdd;
  @BindView(R.id.txt_year) TextView mTxtYear;
  @BindView(R.id.btn_year_reduce) ImageView mBtnYearReduce;
  @BindView(R.id.btn_month_add) ImageView mBtnMonthAdd;
  @BindView(R.id.txt_month) TextView mTxtMonth;
  @BindView(R.id.btn_month_reduce) ImageView mBtnMonthReduce;
  @BindView(R.id.btn_day_add) ImageView mBtnDayAdd;
  @BindView(R.id.txt_day) TextView mTxtDay;
  @BindView(R.id.btn_day_ruduce) ImageView mBtnDayRuduce;
  @BindView(R.id.btn_hour_add) ImageView mBtnHourAdd;
  @BindView(R.id.txt_hour) TextView mTxtHour;
  @BindView(R.id.btn_hour_reduce) ImageView mBtnHourReduce;
  @BindView(R.id.btn_minute_add) ImageView mBtnMinuteAdd;
  @BindView(R.id.txt_minute) TextView mTxtMinute;
  @BindView(R.id.btn_minute_reduce) LinearLayout mBtnMinuteReduce;
  @BindView(R.id.img_pre) ImageView mImgPre;
  @BindView(R.id.img_next) ImageView mImgNext;
  @BindView(R.id.show_text) TextView mShowText;
  Unbinder unbinder;

  private String mParam1;
  private String mParam2;
  private int mYear;
  private int mMonth;
  private int mDay;
  private int mHour;
  private int mMinute;
  private Calendar mCalendar;

  public SettingDateFragment() {
    // Required empty public constructor
  }

  public static SettingDateFragment newInstance(String param1, String param2) {
    SettingDateFragment fragment = new SettingDateFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_setting_date, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (!hidden){
      mShowText.setText("");
      KLog.e("是否开启root权限"+getRootAhth());
      mCalendar = Calendar.getInstance();
      //获取系统的日期
      //年
      mYear = mCalendar.get(Calendar.YEAR);
      mTxtYear.setText(mYear +"");
      //月
      mMonth = mCalendar.get(Calendar.MONTH)+1;
      mTxtMonth.setText(mMonth +"");
      //日
      mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
      mTxtDay.setText(mDay +"");
      //获取系统时间
      //小时
      mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
      mTxtHour.setText(mHour +"");
      //分钟
      mMinute = mCalendar.get(Calendar.MINUTE);
      mTxtMinute.setText(mMinute +"");
    }
  }


  public void setAutoDateTime(int checked) {
    Settings.Global.putInt(getActivity().getContentResolver(), Settings.Global.AUTO_TIME, checked);
  }
  public synchronized boolean getRootAhth()
  {
    Process process = null;
    DataOutputStream os = null;
    try
    {
      process = Runtime.getRuntime().exec("su");
      os = new DataOutputStream(process.getOutputStream());
      os.writeBytes("exit\n");
      os.flush();
      int exitValue = process.waitFor();
      if (exitValue == 0)
      {
        return true;
      } else
      {
        return false;
      }
    } catch (Exception e)
    {
      Log.d("*** DEBUG ***", "Unexpected error - Here is what I know: "
          + e.getMessage());
      return false;
    } finally
    {
      try
      {
        if (os != null)
        {
          os.close();
        }
        process.destroy();
      } catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }

  public void setSysDate(int year, int month, int day) {
    Calendar c = Calendar.getInstance();
    c.set(Calendar.YEAR, year);
    c.set(Calendar.MONTH, month);
    c.set(Calendar.DAY_OF_MONTH, day);
    long when = c.getTimeInMillis();
    if (when / 1000 < Integer.MAX_VALUE) {
      ((AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE)).setTime(when);
    }
  }

  public void setSysTime(int hour, int minute) {
    Calendar c = Calendar.getInstance();
    c.set(Calendar.HOUR_OF_DAY, hour);
    c.set(Calendar.MINUTE, minute);
    c.set(Calendar.SECOND, 0);
    c.set(Calendar.MILLISECOND, 0);
    long when = c.getTimeInMillis();
    if (when / 1000 < Integer.MAX_VALUE) {
      ((AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE)).setTime(when);
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({
      R.id.btn_year_add, R.id.btn_year_reduce, R.id.btn_month_add, R.id.btn_month_reduce,
      R.id.btn_day_add, R.id.btn_day_ruduce, R.id.btn_hour_add, R.id.btn_hour_reduce,
      R.id.btn_minute_add, R.id.btn_minute_reduce, R.id.img_pre, R.id.img_next
  }) public void onViewClicked(View view) {
    SettingActivity mSettingActivity= (SettingActivity) getActivity();
    switch (view.getId()) {
      case R.id.btn_year_add:
        mYear+=1;
        mTxtYear.setText(mYear +"");
        testDate(mYear,Calendar.YEAR);
        break;
      case R.id.btn_year_reduce:
        mYear-=1;
        mTxtYear.setText(mYear +"");
        testDate(mYear,Calendar.YEAR);
        break;
      case R.id.btn_month_add:
        mMonth+=1;
        mTxtMonth.setText(mMonth+"");
        testDate(mMonth,Calendar.MONTH);
        break;
      case R.id.btn_month_reduce:
        mMonth-=1;
        mTxtMonth.setText(mMonth+"");
        testDate(mMonth,Calendar.MONTH);
        break;
      case R.id.btn_day_add:
        mDay+=1;
        mTxtDay.setText(mDay+"");
        testDate(mDay,Calendar.DAY_OF_MONTH);
        break;
      case R.id.btn_day_ruduce:
        mDay-=1;
        mTxtDay.setText(mDay+"");
        testDate(mDay,Calendar.DAY_OF_MONTH);
        break;
      case R.id.btn_hour_add:
        mHour-=1;
        mTxtHour.setText(mHour+"");
        testDate(mHour,Calendar.HOUR);
        break;
      case R.id.btn_hour_reduce:
        mHour-=1;
        mTxtHour.setText(mHour+"");
        testDate(mHour,Calendar.HOUR);
        break;
      case R.id.btn_minute_add:
        mMinute+=1;
        mTxtMinute.setText(mMinute+"");
        testDate(mMinute,Calendar.MINUTE);
        break;
      case R.id.btn_minute_reduce:
        mMinute-=1;
        mTxtMinute.setText(mMinute+"");
        testDate(mMinute,Calendar.MINUTE);
        break;
      case R.id.img_pre:
        mSettingActivity.showOrgin();
      case R.id.img_next:
        mSettingActivity.showOrgin();
        break;
    }
  }
  public void testDate(int y,int field){
    mCalendar.set(field,y);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd.HHmmss");
    String time = sdf.format(mCalendar.getTime());
    try {
      Process process = Runtime.getRuntime().exec("su");
      String datetime = time; //测试的设置的时间【时间格式 yyyyMMdd.HHmmss】
      DataOutputStream os = new DataOutputStream(process.getOutputStream());
      os.writeBytes("setprop persist.sys.timezone GMT\n");
      os.writeBytes("/system/bin/date -s "+datetime+"\n");
      os.writeBytes("clock -w\n");
      os.writeBytes("exit\n");
      os.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
