package il.co.ilrd.CRUD;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class LogMonitor {

	private String readDirPath = null;
	private String readFilePath = null;
	private PropertyChangeSupport support = null;
	private String log = null;
	
	public LogMonitor(String readDirPath, String readFileName) throws IOException {
		this.readDirPath = readDirPath;
		readFilePath = readDirPath + "/" + readFileName;
		
		support = new PropertyChangeSupport(this);
		new Thread(new MonitorThread()).start();
	}
		
	 public void addListener(PropertyChangeListener pcl) {
	        support.addPropertyChangeListener(pcl);
	 }
	 
	 public void removeListener(PropertyChangeListener pcl) {
	        support.removePropertyChangeListener(pcl);
	 }
	 
	 public void setNewLog(String newLog) {
		 String oldLog = log;
		 log = newLog;
		 support.firePropertyChange("log", oldLog, newLog);
	 }

	class MonitorThread implements Runnable {
		
		private Path dirPath = null;
		private Path filePath = null;
		private WatchService watchService = null;
		private SeekableByteChannel readerChannel = null;
		private String newLine = null;
		private ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		public MonitorThread() throws IOException {
			dirPath = Paths.get(readDirPath);
			filePath = Paths.get(readFilePath);
			watchService = dirPath.getFileSystem().newWatchService();
			dirPath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
			readerChannel = Files.newByteChannel(filePath, StandardOpenOption.READ);
			readerChannel.position(readerChannel.size());
		}
	
		@Override	
		public void run() {
			try {
				WatchKey key = null;
				while ((key = watchService.take()) != null) {
					for (WatchEvent<?> event : key.pollEvents()) {
						if((event.context()).toString() != null) {
							while(readerChannel.read(buffer) > 0) {
								buffer.flip();
								newLine += Charset.forName("UTF-8").decode(buffer).toString();
								buffer.clear();
							}
							setNewLog(newLine);	
							newLine = "";
						}
					}
					key.reset();
				}				
				
			} catch (InterruptedException e) {} 
			  catch (IOException e) {}
		}
	}
}

	