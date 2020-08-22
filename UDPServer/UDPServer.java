package il.co.ilrd.CRUD;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.sql.SQLException;


public class UDPServer {
	
	private CRUD<Long, String> database;
	private DatagramSocket socket;
	private Thread thread;
	private byte[] buffer;
	private boolean running = true;
	
	public UDPServer(CRUD<Long, String> database, int port) throws IOException, SQLException {
		this.database = database;
		socket = new DatagramSocket(port);
		buffer = new byte[1024];
		thread = new Thread(new UDPServerThread());
	}
	
	public void start() {
		thread.start();
	}
	
	public void stop() {
		running = false;
	}
	
	class UDPServerThread implements Runnable {

		@Override
		public void run() {
			
			while(running) {
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			    try {
					socket.receive(packet);
				} catch (IOException e) {}
			    
	            String received = new String(packet.getData(), 0, packet.getLength());
	            database.create(received);
	        }
		}		
	}
}