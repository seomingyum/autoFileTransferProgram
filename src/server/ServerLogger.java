package server;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class ServerLogger {
	
	public static Logger logger = Logger.getLogger("ServerLogger");
	public static Level consoleLevel;
	public static boolean fileLogMode;
	public static boolean consoleLogMode;
	public static String logFilePath;

	public ServerLogger() {
		// 기본로그 삭제
		Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
        	rootLogger.removeHandler(handlers[0]);
        }
		
		if(fileLogMode) {
			try {
				FileHandler fh = new FileHandler(logFilePath, true);
				fh.setFormatter(new SimpleFormatter());
				// consoleLevel부터 출력
				fh.setLevel(consoleLevel);
				logger.addHandler(fh);
				
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		if(consoleLogMode) {
			try {
				ConsoleHandler ch = new ConsoleHandler();
				ch.setLevel(consoleLevel);
				logger.addHandler(ch);
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void log(String msg) {
		logger.severe(msg);
	}
	
	public void info(String msg) {
		logger.info(msg);;
	}
	
	public void all(String msg) {
		logger.finest(msg);
	}
	
}
