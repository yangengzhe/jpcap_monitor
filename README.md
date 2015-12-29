# jpcap_monitor
##安装
###Windows

1、下载并安装WinPcap

2、下载Jpcap相关库，安装方法如下两张：

方法一：

运行附带的安装器（JpcapSetup-0.6.exe）进行安装，注意选择JRE和SDK。

方法二：

* 拷贝 “lib\Jpcap.dll” 到 “[JRE directory]\bin” 或者 “[JRE directory]\lib\ext\x86”
* 拷贝 “lib\jpcap.jar” 到 “[JRE directory]\lib\ext”
* 如果安装SDK的话，还需要拷贝 “lib\jpcap.jar” 到 “[JDK directory]\jre\lib\ext”.

###Unix

1、下载并安装libpcap

2、下载Jpcap相关库，进入到 “src/c” 目录，并且配置好Makefile

3、在终端运行make命令

*如果出现错误 “structure has no member named `sa_len'”，则注释掉 “#define HAVE_SA_LEN” 在文件Jpcap_sub.h中。*

4、把生成的 libjpcap.so 拷贝到 [Java directory]/jre/lib/<arch>。（ <arch> is either “i386” or “sparc”）

5、把 “lib/jpcap.jar” 拷贝到 [Java directory]/jre/lib/ext.

##主要步骤
###1、实现PacketReceiver（接口）
该接口是用来定义一个分析被捕获数据包的方法。需要实现当前接口，当processPacket() 或loopPacket()捕捉到数据包之后被动调用接口中PacketReceiver方法。
###2、获得网卡信息
可能含有多个网卡，故用数组形式存储，一个对象代表一个网卡信息

	NetworkInterface[] devices = JpcapCaptor.getDeviceList();

一个网卡可能含有多个地址，同时注意会换地址的筛选
###3、对相关网卡进行监听
	JpcapCaptor.openDevice(NetworkInterface intrface, int snaplen, boolean promisc, int to_ms);

intrface参数是监听的网卡；snaplen参数是一次捕获的最大字节数量（本例：65535）；promisc参数，如果为true，接口被设置为混杂模式（本例：true）；to_ms参数，设定processPacket()中的Timeout（本例：20）；当指定接口不能被打开抛出IO异常。

###4、捕获数据包

此时可以调用processPacket() 或loopPacket()开始监听了。这两种方式都带有两个参数：捕获的最大包数可以是-1（说明没有限制）；执行PacketReceiver的一个类的实例。

	int processPacket(int count,JpcapHandler handler);
		
	int loopPacket(int count,JpcapHandler handler);

作用：连续地捕获数据包，返回捕获数据包的数量。参数count是要捕获数据包的数量，可以将其设置为-1，这样就可以持续抓包直到EOF或发生错误为止。

区别：loopPacket忽视超时。

###5、分析数据包
该部分主要在PacketReceiver接口的实现类中进行体现。

##代码实现
参考src下java文件



*来源：www.i3geek.com*