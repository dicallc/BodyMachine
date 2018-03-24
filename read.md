### 模拟串口文件下载
http://blog.csdn.net/simple__happyness/article/details/50636318?locationNum=11

### android模拟器上模拟串口通信

#### 第一步：
emulator.exe -list-avds
emulator.exe @Nexus_4_API_21 -qemu -serial COM4

chmod 777 ttyS2
### 参考文档
http://blog.csdn.net/da_caoyuan/article/details/69566299
http://blog.csdn.net/junfeng120125/article/details/9328947


### keys:
password:kaisa008
alias:bodymachine
#### Android与串口设备通信的方案：

* 直接用SDK的BluetoothSocket类来进行蓝牙通信，外部设备再用蓝牙转串口进行控制。这种方式有较高延时，蓝牙模块需要供电，低带宽。
* 使用USB转RS232方式（使用内核驱动和使用Android驱动两种方式），这种方式不需要硬件改动，不需要另外的供电，延时很小且有较高带宽。但是Android设备需要硬件上支持USB Host接口（一般Android平板支持，Android手机一般是没有的），另外可能需要root以改变/dev/ttyUSB0文件权限来加载一个内核模块。开发需要使用android_serialport_api。
* 直接使用串口进行连接，但是这种方式兼容性不好，只有少数设备支持，而且串口不支持流控制(由Android提供的USB Host API决定的)。使用时也用android_serialport_api。
* 将Android作为USB从机，外部设备作为USB主机与之通信，这种方式几乎与所有Android设备兼容（一般都有USB从口），无需root，低延迟，高带宽。

参考：https://www.jianshu.com/p/86866a98125d


协议规则：
开始符:一位
信息类型：一位
信息长度:两位`
校验：一位 //是自信息类型起至对象号止所有码的异或
结束符：一位


https://www.jianshu.com/p/b5d3462407fc

[Android打印机--没有设备驱动sdk，自己 实现USB打印功能](http://blog.csdn.net/johnWcheung/article/details/7157683)

[android连接打印机打印](http://blog.csdn.net/dengpeng_/article/details/60869509)

[Android通过系统打印功能实现PDF预览打印](http://blog.csdn.net/tangxl2008008/article/details/78424867)


### 数据不完成参考

https://www.jianshu.com/p/e5004d75bd9c
96*2=192
0A141E283200000000000000000000000000000000000000000000000000000000000000000000000000000000000000
000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
数据库框架使用
http://blog.csdn.net/jack_king007/article/details/70227721#comments