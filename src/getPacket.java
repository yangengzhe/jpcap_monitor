import java.util.ArrayList;
import jpcap.JpcapCaptor;
import jpcap.PacketReceiver;
import jpcap.packet.ARPPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

public class getPacket implements PacketReceiver {
	String ipAddr;// 本机ip地址数组

	private int upLoadPacketSize = 0;// 上传数据包总字节数
	private int downLoadPacketSize = 0;// 下载数据包总字节数

	private int upLoadIPPacketSize = 0;// 上传IP数据包总字节数
	private int downLoadIPPacketSize = 0;// 下载IP数据包总字节数

	private int upLoadTCPPacketSize = 0; // 上传TCP数据包总字节数
	private int downLoadTCPPacketSize = 0; // 下载TCP数据包总字节数

	private int upLoadUDPPacketSize = 0; // 上传UDP数据包总字节数
	private int downLoadUDPPacketSize = 0; // 下载UDP数据包总字节数

	private static Packet currentPacket = new Packet();// 表示最新抓取的包

	public int getupLoadPacketSize() {
		return upLoadPacketSize;
	}

	public int getdownLoadPacketSize() {
		return downLoadPacketSize;
	}

	public int getupLoadIPPacketSize() {
		return upLoadIPPacketSize;
	}

	public int getdownLoadIPPacketSize() {
		return downLoadIPPacketSize;
	}

	public int getupLoadTCPPacketSize() {
		return upLoadTCPPacketSize;
	}

	public int getdownLoadTCPPacketSize() {
		return downLoadTCPPacketSize;
	}

	public int getupLoadUDPPacketSize() {
		return upLoadUDPPacketSize;
	}

	public int getdownLoadUDPPacketSize() {
		return downLoadUDPPacketSize;
	}

	public getPacket() {
		// 空实现
	}

//	public getPacket(String[] ipAddr, int ipcount) {
//		this.ipAddr = ipAddr;
//		this.ipcount = ipcount;
//	}
	
	public getPacket(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	// 判断当前抓的包是否为空，若为空则表示断网，否则没断网
	public static boolean isPacketEmpty() {
		if (currentPacket == null) {
			return true;
		} else {
			return false;
		}
	}

	// 获得截取的数据包
	public void receivePacket(Packet p) {
		// currentPacket=null;
		if (p instanceof TCPPacket) {// 如果截取的是TCP包
			TCPPacket tcp = (TCPPacket) p;
			if (tcp.src_ip.getHostAddress().equals(ipAddr.substring(1))) {// 如果TCP包的源IP地址等于本机ip则此包为上传包
				upLoadTCPPacketSize += tcp.len;
				upLoadPacketSize += tcp.len;
			} else if (tcp.dst_ip.getHostAddress().equals(
					ipAddr.substring(1))) {// 如果TCP包的目的IP地址等于本机ip则此包为下载包
				downLoadTCPPacketSize += tcp.len;
				downLoadPacketSize += tcp.len;
			}
		}
		if (p instanceof UDPPacket) {// 如果截取的是UDP包
			UDPPacket udp = (UDPPacket) p;
			if (udp.src_ip.getHostAddress().equals(ipAddr.substring(1))) {// 如果UDP包的源IP地址等于本机ip则此包为上传包
				upLoadUDPPacketSize += udp.len;
				upLoadPacketSize += udp.len;
			} else if (udp.dst_ip.getHostAddress().equals(
					ipAddr.substring(1))) {// 如果UDP包的目的IP地址等于本机ip则此包为下载包
				downLoadUDPPacketSize += udp.len;
				downLoadPacketSize += udp.len;
			}
		}
		if (p instanceof IPPacket) {// 如果截取的是IP包
			IPPacket ippacket = (IPPacket) p;
			if (ippacket.src_ip.getHostAddress().equals(ipAddr.substring(1))) {// 如果IP包的源IP地址等于本机ip则此包为上传包
				upLoadIPPacketSize += ippacket.len;
				upLoadPacketSize += ippacket.len;
			} else if (ippacket.dst_ip.getHostAddress().equals(
					ipAddr.substring(1))) {// 如果IP包的目的IP地址等于本机ip则此包为下载包
				downLoadIPPacketSize += ippacket.len;
				downLoadPacketSize += ippacket.len;
				currentPacket = p;
			}
		}

//		trafficData.setupLoadTCPPacketSize(upLoadTCPPacketSize);
//		trafficData.setdownLoadTCPPacketSize(downLoadTCPPacketSize);
//		trafficData.setupLoadUDPPacketSize(upLoadUDPPacketSize);
//		trafficData.setdownLoadUDPPacketSize(downLoadUDPPacketSize);
//		trafficData.setupLoadIPPacketSize(upLoadIPPacketSize);
//		trafficData.setdownLoadIPPacketSize(downLoadIPPacketSize);
//		trafficData.setUpLoadPacketSize(upLoadPacketSize);
//		trafficData.setdownLoadPacketSize(downLoadPacketSize);

		System.out.println("上传数据包：" + upLoadPacketSize + "字节");
		System.out.println("下载数据包：" + downLoadPacketSize + "字节");
		System.out.println("IP上传数据包：" + upLoadIPPacketSize + "字节");
		System.out.println("IP下载传数据包：" + downLoadIPPacketSize + "字节");
		System.out.println("TCP上传数据包：" + upLoadTCPPacketSize + "字节");
		System.out.println("TCP下载传数据包：" + downLoadTCPPacketSize + "字节");
		System.out.println("UDP上传数据包：" + upLoadUDPPacketSize + "字节");
		System.out.println("UDP下载传数据包：" + downLoadUDPPacketSize + "字节");
//		System.out.println(System.currentTimeMillis());
	}
}