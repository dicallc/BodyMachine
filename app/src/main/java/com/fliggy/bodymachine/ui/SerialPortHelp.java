package com.fliggy.bodymachine.ui;

import android.serialport.utils.SerialDataUtils;
import android.serialport.utils.SimpleSerialPortUtil;
import android.serialport.utils.Utils;
import com.fliggy.bodymachine.model.BodyInfoModel;
import com.fliggy.bodymachine.model.SerialEvent;
import com.socks.library.KLog;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by dicallc on 2018/4/28.
 */

public class SerialPortHelp {
  private BodyInfoModel mBodyInfoModel;
  private static SerialPortHelp sSerialPortHelp;
  private SimpleSerialPortUtil mSerialPort;
  private StringBuffer result = new StringBuffer();

  public static SerialPortHelp getInstance() {
    if (null == sSerialPortHelp) {
      sSerialPortHelp = new SerialPortHelp();
    }
    return sSerialPortHelp;
  }

  public SerialPortHelp() {
    initData();
  }

  private void initData() {
    mSerialPort = SimpleSerialPortUtil.getInstance();
    mSerialPort.setOnDataReceiveListener(new SimpleSerialPortUtil.OnDataReceiveListener() {
      @Override public void onDataReceive(byte[] buffer, int size) {
        try {
          String receiveString = SerialDataUtils.ByteArrToHex(buffer).replace(" ", "");
          //如果数据是2位，等待下一次接受，
          result.append(receiveString);
          if (result.length() < 6) {
            return;
          } else {
            //大于6的话，就可以知道字段长度了
            String mSubstring = result.substring(4, 6);
            int mInt = Integer.parseInt(mSubstring, 16);
            int full_lenght = mInt * 2 + 8;
            if (result.length() < full_lenght) {
              return;
            }
          }
          final String str = result.toString().trim();
          KLog.e("接受：dicallc: " + str);
          final String code = str.substring(2, 4);
          {
            switch (Integer.parseInt(code, 16)) {
              case 1:
                //模块回应初始化是否成功
                break;
              case 2:
                //上行体重数据
                String wd = Utils.toResultHasPoint(str, 6, 10);
                EventBus.getDefault().post(new SerialEvent(SerialEvent.WEIGHT,wd));
                break;
              case 3:
                //上行体脂数据
                //String s = Utils.toShowFinalResult(str);
                KLog.e("上行体脂数据");
                EventBus.getDefault().post(new SerialEvent(SerialEvent.LOAD_USER_DATA,str));
                break;
              case 4:
                // 上行测脂报错
                String rt = Utils.toResult(str, 6, 8);
                switch (rt) {
                  case "1":
                    EventBus.getDefault().post(new SerialEvent(SerialEvent.LOAD_USER_DATA_ERRO,"错误参数非法"));
                    break;
                  case "2":
                    EventBus.getDefault().post(new SerialEvent(SerialEvent.LOAD_USER_DATA_ERRO,"站姿错误"));
                    break;
                  case "3":
                    EventBus.getDefault().post(new SerialEvent(SerialEvent.LOAD_USER_DATA_ERRO,"数据溢出"));
                    break;
                }

                break;
              case 5:
                //上行锁定体重
                EventBus.getDefault().post(new SerialEvent(SerialEvent.WEIGHT_LOCK,"已经稳定了，可以进行下一步"));
                break;
              case 6:
                //查询是否准备好了 5a060612xz00000000
                String version = Utils.toResult(str, 6, 8);
                String id = Utils.toResult(str, 10, 18);
                EventBus.getDefault().post(new SerialEvent(SerialEvent.MACHE_INFO,id,version));
                //mTxtCalVersion.setText("当前版本是: "+version);
                break;
              case 7:
                //用户数据回应是否收到
                //mTextView.setText("用户数据，模块已经收到");
                break;
              case 9:
                //清理回复
                String clear_code = Utils.toResult(str, 6, 8);
                switch (clear_code) {
                  case "1":
                    //mTextView.setText("零点读取完成");
                    break;
                  case "2":
                    //mTextView.setText("标定 50kg 完成");
                    break;
                  case "3":
                    //mTextView.setText("标定 100kg 完成");
                    break;
                  case "4":
                    //mTextView.setText("标定成功");
                    break;
                  case "5":
                    //mTextView.setText("标定失败");
                    break;
                }
                break;
              //                case 8:
              //                  //测体脂回应
              //
              //                  break;
            }
            result.delete(0, result.length());
          }
          // TODO: 2018/3/9 0009 检验失败怎么办

          //去除识别码，知道这条语句应该干什么

        } catch (Exception mE) {
          KLog.e(mE.getMessage());
        }
      }
    });
  }
}
