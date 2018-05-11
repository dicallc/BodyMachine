package android.serialport.utils;

import android.serialport.SerialPort;
import android.util.Log;
import com.socks.library.KLog;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 串口操作类
 */
public class SimpleSerialPortUtil {
  private String TAG = SimpleSerialPortUtil.class.getSimpleName();
  private SerialPort mSerialPort;
  private OutputStream mOutputStream;
  private InputStream mInputStream;
  private ReadThread mReadThread;
  private String path = "/dev/ttyS4"; //这个是我们要读取的串口路径，这个硬件开发人员会告诉我们的
  private int baudrate = 9600;//这个参数，硬件开发人员也会告诉我们的
  private static SimpleSerialPortUtil portUtil;
  private OnDataReceiveListener onDataReceiveListener = null;
  private boolean isStop = false;

  public interface OnDataReceiveListener {
    public void onDataReceive(byte[] buffer, int size);
  }

  public void setOnDataReceiveListener(OnDataReceiveListener dataReceiveListener) {
    onDataReceiveListener = dataReceiveListener;
  }

  public static SimpleSerialPortUtil getInstance() {
    if (null == portUtil) {
      portUtil = new SimpleSerialPortUtil();
      portUtil.onCreate();
    }
    return portUtil;
  }

  /**
   * 初始化串口信息
   */
  public void onCreate() {
    try {
      mSerialPort = new SerialPort(new File(path), baudrate, 0);
      mOutputStream = mSerialPort.getOutputStream();
      mInputStream = mSerialPort.getInputStream();

      mReadThread = new ReadThread();
      isStop = false;
      mReadThread.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 发送指令到串口
   */
  public boolean sendCmds(String cmd) {
    boolean result = true;
    String str = cmd;
    str = str.replace(" ", "");
    StringBuffer mStringBuffer = new StringBuffer(str);
    String mS = Utils.checkXor(str);
    mStringBuffer.append(mS);
    String msg = mStringBuffer.toString();
    KLog.e("dicallc 发送数据"+msg);
    byte[] mBuffer = SerialDataUtils.HexToByteArr(msg);
    if (!isStop) {
      try {
        if (mOutputStream != null) {
          mOutputStream.write(mBuffer);
        } else {
          result = false;
        }
      } catch (IOException e) {
        e.printStackTrace();
        result = false;
      }
    } else {
      System.out.println("sendCmds serialPort isClose");
      result = false;
    }

    return result;
  }

  /**
   * 读取终端设备数据
   */
  private class ReadThread extends Thread {
    byte[] cData = new byte[512];
    int orgin_index=0;
    @Override public void run() {
      super.run();
      while (!isStop && !isInterrupted()) {
        int size;
        try {
          if (mInputStream == null) {
            Log.e("dicallc ", "mInputStream 是空的");
            return;
          }

          byte[] buffer = new byte[512];
          size = mInputStream.read(buffer);//该方法读不到数据时，会阻塞在这里
          if (size > 0) {
            byte[] buffer2 = new byte[size];
            for (int i = 0; i < size; i++) {
              buffer2[i] = buffer[i];
            }
            //if (orgin_index<10){
            //  System.arraycopy(buffer2,0,cData,orgin_index,size);
            //  orgin_index+=size;
            //}

            if (onDataReceiveListener != null) {
              onDataReceiveListener.onDataReceive(buffer2, size);
            }

          }
          Thread.sleep(50);//延时 50 毫秒
        } catch (Exception e) {
          e.printStackTrace();
          Log.e("dicallc", "ReadThread.run  e.printStackTrace() " + e);
          return;
        }
      }
    }
  }


  protected void onDataReceived(final byte[] buffer, final int index, final int packlen) {
    System.out.println("收到信息");
    byte[] buf = new byte[packlen];
    System.arraycopy(buffer, index, buf, 0, packlen);
    //callback data
    onDataReceiveListener.onDataReceive(buf, 0);
    //        ProtocolAnalyze.getInstance(myHandler).analyze(buf);
  }
  public int parseLen(byte buffer[], int index) {

    //      if (buffer.length - index < headerLength) { return 0; }
    //3和4位就是信息长度
    byte a = buffer[index + 4];//3
    byte b = buffer[index + 5];//4
    int rlt = 0;
    if (((a >> 7) & 0x1) == 0x1) {
      rlt = (((a & 0x7f) << 8) | b);
    } else {
      char[] tmp = new char[2];
      tmp[0] = (char) a;
      tmp[1] = (char) b;
      String s = new String(tmp, 0, 2);
      rlt = Integer.parseInt(s, 16);
    }

    return rlt;
  }

  /**
   * 关闭串口
   */
  public void closeSerialPort() {
    isStop = true;
    if (mReadThread != null) {
      mReadThread.interrupt();
    }
    if (mSerialPort != null) {
      mSerialPort.close();
    }
  }
}