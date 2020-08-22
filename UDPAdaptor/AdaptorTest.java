package il.co.ilrd.CRUD;

import java.io.IOException;

public class AdaptorTest {

	public static void main(String[] args) throws IOException {
		LogMonitor logMonitor = new LogMonitor("/var/log", "syslog");
		UDPAdaptor udpAdaptor = new UDPAdaptor(9000, "localhost");
		logMonitor.addListener(udpAdaptor);
		
	}
}
