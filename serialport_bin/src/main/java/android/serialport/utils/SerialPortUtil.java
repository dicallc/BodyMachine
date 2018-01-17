package android.serialport.utils;

import android.serialport.SerialPort;
import android.util.Log;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 串口操作类
 */
public class SerialPortUtil {
    private String TAG = SerialPortUtil.class.getSimpleName();
    private SerialPort mSerialPort;
    private OutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;
    private String path = "/dev/ttyS4"; //这个是我们要读取的串口路径，这个硬件开发人员会告诉我们的
    private int baudrate = 9600;//这个参数，硬件开发人员也会告诉我们的
    private static SerialPortUtil portUtil;
    private OnDataReceiveListener onDataReceiveListener = null;
    private boolean isStop = false;

    public interface OnDataReceiveListener {
        public void onDataReceive(byte[] buffer, int size);
    }

    public void setOnDataReceiveListener(OnDataReceiveListener dataReceiveListener) {
        onDataReceiveListener = dataReceiveListener;
    }

    public static SerialPortUtil getInstance() {
        if (null == portUtil) {
            portUtil = new SerialPortUtil();
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
     *
     * @param cmd
     * @return
     */
    public boolean sendCmds(String cmd) {
        boolean result = true;
        String str = cmd;
        str = str.replace(" ", "");
        byte[] mBuffer = SerialDataUtils.HexToByteArr(str);
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

        @Override
        public void run() {
            super.run();
            while (!isStop && !isInterrupted()) {
                int size;
                try {
                    if (mInputStream == null){
                        Log.e("dicallc ","mInputStream 是空的");
                        return;
                    }

                    byte[] buffer = new byte[512];
                    size = mInputStream.read(buffer);//该方法读不到数据时，会阻塞在这里
                    if (size > 0) {
                        byte[] buffer2 = new byte[size];
                        for (int i = 0; i < size; i++) {
                            buffer2[i] = buffer[i];
                        }
                        if (onDataReceiveListener != null) {
                            onDataReceiveListener.onDataReceive(buffer2, size);
                        }
                    }
                    Thread.sleep(50);//延时 50 毫秒
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("dicallc","ReadThread.run  e.printStackTrace() " + e);
                    return;
                }
            }
        }
    }


    private class ReadThread1 extends Thread {

        @Override
        public void run() {
            super.run();

            // 定义一个包的最大长度
            int maxLength = 2048;
            byte[] buffer = new byte[maxLength];
            // 每次收到实际长度
            int available = 0;
            // 当前已经收到包的总长度
            int currentLength = 0;
            // 协议头长度4个字节（开始符1，类型1，长度2）
            int headerLength = 4;

            while (!isInterrupted()) {
                try {
                    available = mInputStream.available();
                    if (available > 0) {
                        // 防止超出数组最大长度导致溢出
                        if (available > maxLength - currentLength) {
                            available = maxLength - currentLength;
                        }
                        mInputStream.read(buffer, currentLength, available);
                        currentLength += available;
                    }

                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                int cursor = 0;
                // 如果当前收到包大于头的长度，则解析当前包
                while (currentLength >= headerLength) {
                    // 取到头部第一个字节=开始符 如果不是开始符 会在这里停止，如果正常一般不会进去这个判断
                    if (buffer[cursor] != 0x0F) {
                        --currentLength;
                        ++cursor;
                        continue;
                    }

                    int contentLenght = parseLen(buffer, cursor, headerLength);
                    // 如果内容包的长度大于最大内容长度或者小于等于0，则说明这个包有问题，丢弃
                    if (contentLenght <= 0 || contentLenght > maxLength - 5) {
                        currentLength = 0;
                        break;
                    }
                    // 如果当前获取到长度小于整个包的长度，则跳出循环等待继续接收数据
                    int factPackLen = contentLenght + 5;
                    if (currentLength < contentLenght + 5) {
                        break;
                    }

                    // 一个完整包即产生
                    // proceOnePacket(buffer,i,factPackLen);
                    onDataReceived(buffer, cursor, factPackLen);
                    currentLength -= factPackLen;
                    cursor += factPackLen;
                }
                // 残留字节移到缓冲区首
                if (currentLength > 0 && cursor > 0) {
                    System.arraycopy(buffer, cursor, buffer, 0, currentLength);
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
    public int parseLen(byte buffer[], int index, int headerLength) {

//      if (buffer.length - index < headerLength) { return 0; }
        //3和4位就是信息长度
        byte a = buffer[index + 2];//3
        byte b = buffer[index + 3];//4
        int rlt = 0;
        if (((a >> 7) & 0x1) == 0x1) {
            rlt = (((a & 0x7f) << 8) | b);
        }
        else {
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