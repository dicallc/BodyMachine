package com.fliggy.bodymachine;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.socks.library.KLog;
import java.util.HashMap;
import java.util.Iterator;

public class TestPrintActivity extends AppCompatActivity {

  @BindView(R.id.textView) TextView mTextView;
  @BindView(R.id.button) Button mButton;
  private UsbManager usbManager;
  /**
   * 满足的设备
   */
  private UsbDevice myUsbDevice;
  /**
   * usb接口
   */
  private UsbInterface usbInterface;

  /**
   * 块输出端点
   */
  private UsbEndpoint epBulkOut;
  private UsbEndpoint epBulkIn;
  /**
   * 控制端点
   */
  private UsbEndpoint epControl;
  /**
   * 中断端点
   */
  private UsbEndpoint epIntEndpointOut;
  private UsbEndpoint epIntEndpointIn;

  /**
   * 连接
   */
  private UsbDeviceConnection myDeviceConnection;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_print);
    ButterKnife.bind(this);

    //Intent i = new Intent(this, PrinterService.class);
    //startService(i);

    //1)创建usbManager
    usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

    //2)获取到所有设备 选择出满足的设备
    enumeraterDevices();
    //3)查找设备接口
    getDeviceInterface();
    //4)获取设备endpoint
    assignEndpoint();
    //5)打开conn连接通道
    openDevice();

  }
  @OnClick(R.id.button) public void onViewClicked() {
    print("Test print");
  }
  public void print(String msg) {
    final String printData = msg;
    if (myUsbDevice != null) {
      UsbInterface usbInterface = myUsbDevice.getInterface(0);
      for (int i = 0; i < usbInterface.getEndpointCount(); i++) {
        final UsbEndpoint ep = usbInterface.getEndpoint(i);
        if (ep.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
          if (ep.getDirection() == UsbConstants.USB_DIR_OUT) {
            final UsbDeviceConnection mUsbDeviceConnection = usbManager.openDevice(myUsbDevice);
            if (mUsbDeviceConnection != null) {
              Toast.makeText(this, "Device connected", Toast.LENGTH_SHORT).show();
              mUsbDeviceConnection.claimInterface(usbInterface, true);
              new Thread(new Runnable() {
                @Override public void run() {
                  byte[] bytes = printData.getBytes();
                  byte[] init = {0x00,0x00,0x00,0x00,0x05,0x00,0x00,0x00,0x00,0x00,0x00,0x00};

                  int b = mUsbDeviceConnection.bulkTransfer(ep, init, init.length, 100000);
                  Log.i("Return Status", "b-->" + b);
                }
              }).start();

              mUsbDeviceConnection.releaseInterface(usbInterface);
              break;
            }
          }
        }
      }
    } else {
      Toast.makeText(this, "No available USB print device", Toast.LENGTH_SHORT).show();
    }
  }

  /**
   * 发送数据
   */
  public void sendMessageToPoint(byte[] buffer) {
    if (myDeviceConnection.bulkTransfer(epBulkOut, buffer, buffer.length, 0) >= 0) {
      //0 或者正数表示成功
      Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(this, "发送失败的", Toast.LENGTH_SHORT).show();
    }
  }

  private static final String ACTION_USB_PERMISSION = "com.usb.printer.USB_PERMISSION";
  UsbDeviceConnection conn = null;

  /**
   * 连接设备
   */
  public void openDevice() {
    if (usbInterface != null) {//接口是否为null
      // 在open前判断是否有连接权限；对于连接权限可以静态分配，也可以动态分配权限
      UsbDeviceConnection conn = null;
      PendingIntent mPermissionIntent =
          PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
      usbManager.requestPermission(myUsbDevice, mPermissionIntent);
      if (usbManager.hasPermission(myUsbDevice)) {
        //有权限，那么打开
        conn = usbManager.openDevice(myUsbDevice);
      }
      if (null == conn) {
        Toast.makeText(this, "不能连接到设备", Toast.LENGTH_SHORT).show();
        return;
      }
      //打开设备
      if (conn.claimInterface(usbInterface, true)) {
        myDeviceConnection = conn;
        if (myDeviceConnection != null)// 到此你的android设备已经连上zigbee设备
        {
          KLog.e("open设备成功！");
        }
        final String mySerial = myDeviceConnection.getSerial();
        KLog.e("设备serial number：" + mySerial);
      } else {
        KLog.e("无法打开连接通道。");
        Toast.makeText(this, "无法打开连接通道。", Toast.LENGTH_SHORT).show();
        conn.close();
      }
    }
  }

  /**
   * 分配端点，IN | OUT，即输入输出；可以通过判断
   */
  private void assignEndpoint() {
    if (usbInterface != null) {
      for (int i = 0; i < usbInterface.getEndpointCount(); i++) {
        UsbEndpoint ep = usbInterface.getEndpoint(i);
        switch (ep.getType()) {
          case UsbConstants.USB_ENDPOINT_XFER_BULK://块
            if (UsbConstants.USB_DIR_OUT == ep.getDirection()) {//输出
              epBulkOut = ep;
              KLog.e("Find the BulkEndpointOut,"
                  + "index:"
                  + i
                  + ","
                  + "使用端点号："
                  + epBulkOut.getEndpointNumber());
            } else {
              epBulkIn = ep;
              System.out.println("Find the BulkEndpointIn:"
                  + "index:"
                  + i
                  + ","
                  + "使用端点号："
                  + epBulkIn.getEndpointNumber());
            }
            break;
          case UsbConstants.USB_ENDPOINT_XFER_CONTROL://控制
            epControl = ep;
            KLog.e(
                "find the ControlEndPoint:" + "index:" + i + "," + epControl.getEndpointNumber());
            break;
          case UsbConstants.USB_ENDPOINT_XFER_INT://中断
            if (ep.getDirection() == UsbConstants.USB_DIR_OUT) {//输出
              epIntEndpointOut = ep;
              KLog.e("find the InterruptEndpointOut:"
                  + "index:"
                  + i
                  + ","
                  + epIntEndpointOut.getEndpointNumber());
            }
            if (ep.getDirection() == UsbConstants.USB_DIR_IN) {
              epIntEndpointIn = ep;
              KLog.e("find the InterruptEndpointIn:"
                  + "index:"
                  + i
                  + ","
                  + epIntEndpointIn.getEndpointNumber());
            }
            break;
          default:
            break;
        }
      }
    }
  }

  /**
   * 枚举设备
   */
  public void enumeraterDevices() {
    HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
    Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
    StringBuilder sb = new StringBuilder();
    while (deviceIterator.hasNext()) {
      UsbDevice device = deviceIterator.next();
      sb.append(devicesString(device));
      //                    sb.append(device.toString());
      //                    sb.append("\n\n");
      if (device.getVendorId() == 1273 && device.getProductId() == 108) {
        myUsbDevice = device; // 获取USBDevice
      }
    }
    mTextView.setText(sb.toString());
  }

  /**
   * usb设备的信息
   */
  public String devicesString(UsbDevice device) {
    StringBuilder builder = new StringBuilder("UsbDevice\nName="
        + device.getDeviceName()
        + "\nVendorId="
        + device.getVendorId()
        + "\nProductId="
        + device.getProductId()
        + "\nmClass="
        + device.getClass()
        + "\nmSubclass="
        + device.getDeviceSubclass()
        + "\nmProtocol="
        + device.getDeviceProtocol()
        + "\nmManufacturerName="
        + "\nmSerialNumber="
        + "\n\n");
    return builder.toString();
  }

  /**
   * 获取设备的接口
   */
  private void getDeviceInterface() {
    if (myUsbDevice != null) {
      KLog.e("interfaceCounts : " + myUsbDevice.getInterfaceCount());
      usbInterface = myUsbDevice.getInterface(0);
      KLog.e("成功获得设备接口:" + usbInterface.getId());
    }
  }


}
