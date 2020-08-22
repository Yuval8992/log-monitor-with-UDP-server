package il.co.ilrd.CRUD;

import java.io.IOException;
import java.sql.SQLException;

public class SQLServerTest {

	public static void main(String[] args) throws IOException, SQLException {
		UDPServer sqlServer = new UDPServer(new CRUDSQL(), 9000);
		sqlServer.start();
	}
}
