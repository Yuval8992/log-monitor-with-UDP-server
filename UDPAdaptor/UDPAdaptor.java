package il.co.ilrd.CRUD;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

public class UDPAdaptor implements  PropertyChangeListener{

	private InetAddress address;
	private DatagramSocket datagramSocket;
	private DatagramPacket packet;
	private byte[] buffer;
	private int port; 

	
	public UDPAdaptor(int port, String address) throws UnknownHostException, SocketException{
		this.address = InetAddress.getByName(address);	
		this.port = port;
		datagramSocket = new DatagramSocket();
		buffer = new byte[1024];
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		packet = createUDPPacket((String)evt.getNewValue());
		try {
			datagramSocket.send(packet);
		} catch (IOException e) {}
	}
	
	private DatagramPacket createUDPPacket(String data) {
		String data1 = "yuval: " + data;
		buffer = data1.getBytes(Charset.forName("UTF-8"));
		return new DatagramPacket(buffer, buffer.length, address, port);		
	}
}

