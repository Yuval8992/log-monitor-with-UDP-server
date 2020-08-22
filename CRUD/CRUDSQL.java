package il.co.ilrd.CRUD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CRUDSQL implements CRUD<Long, String>{

	private long position = 0L;
	private PreparedStatement statement;
	private Connection connection;
	
	public CRUDSQL() throws IOException, SQLException {
		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());  
        connection = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/CRUDSQL", "root", ""); 
        connection.setAutoCommit(false);
        String sql = "INSERT INTO logs(log) VALUES(?)";
        statement = connection.prepareStatement(sql); 
	}

	@Override
	public void close() throws Exception {
		statement.close();
		connection.close();
	}
	
	@Override
	public Long create(String data) {	
        try {
        	statement.setString(1, data);
			position = statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {}
			
		return position;
	}

	@Override
	public String read(Long key) {
		return null;
	}

	@Override
	public void update(Long key, String data) {
	}

	@Override
	public void delete(Long key) {
	}
}



        
        


