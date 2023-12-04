package server;

import java.io.BufferedOutputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import org.json.JSONObject;

public class ServerApp {
	// serverPort 설정
	public static int serverPort;
	// 여러 클라이언트로 부터 파일 전송 받을 때 저장할 최대 파일 갯수 설정
	public static int maxFile;
	// 전송받은 파일을 저장할 디렉토리 설정
	public static String serverDir;
	public static ExecutorService threadPool;
	public static ServerLogger serverLogger;
	
	public static void main(String[] args) {
		
		loadProperties();
		serverLogger = new ServerLogger();
		serverLogger.info("서버 설정정보 로딩 완료");
		threadPool = Executors.newFixedThreadPool(maxFile);
		
		startServer();
	}
	
	//서버 시작 함수
	public static void startServer() {
		try {
			ServerSocket serverSoket = new ServerSocket(ServerApp.serverPort);
			serverLogger.info("서버 시작");
			
			while(true) {
				Socket socket = serverSoket.accept();
				ServerApp.threadPool.execute(()->{
					
					DataInputStream dis = null;
					DataOutputStream dos = null;
					OutputStream os = null;
					BufferedOutputStream bos = null;
					try {
						dis = new DataInputStream(socket.getInputStream());
						//json(파일명, 파일사이즈) 수신
						JasonCatcher jasonCatcher = new JasonCatcher();
						JSONObject root = jasonCatcher.jasonCatch(socket, dis);
					
						//파일 데이터수신
						os = new FileOutputStream(ServerApp.serverDir+File.separator+root.getString("fileName"));
						bos = new BufferedOutputStream(os);
						FileDataCatcher fileDataCatcher = new FileDataCatcher();
						fileDataCatcher.fileDataCatcher(socket, root, bos, dis);
						
						//파일 정합성 체크
						dos = new DataOutputStream(socket.getOutputStream());
						String clientFileName = root.getString("fileName");
						long clientFileSize =root.getLong("fileSize");
						File serverFile = new File(ServerApp.serverDir + File.separator + root.getString("fileName"));
						ConsistencyChecker consistencyChecker = new ConsistencyChecker();
						consistencyChecker.consistencyCheck(socket, dos, serverFile,clientFileName, clientFileSize);
						
					} catch (IOException e) {
						serverLogger.log("파일을 수신받을 수 없습니다.");
					} finally {
						if(dis!=null) {
							try {
								dis.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						if(bos!=null) {
							try {
								bos.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						
						if(dos!=null) {
							try {
								dos.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						
						if(socket!=null) {
							try {
								socket.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				});
			}
		} catch (IOException e) {
			serverLogger.log("이미 바인딩된 포트번호를 사용하려고 합니다.");
		}
	}
	
	//설정정보 로딩 함수
	public static void loadProperties() {
        Properties serverProps = new Properties();
        try {
        	serverProps.load(ServerApp.class.getResourceAsStream("server.properties"));
        	maxFile = Integer.parseInt(serverProps.getProperty("maxFile"));
            serverDir = serverProps.getProperty("serverDir");
            serverPort = Integer.parseInt(serverProps.getProperty("serverPort")); 
            ServerLogger.consoleLevel = Level.parse(serverProps.getProperty("consoleLevel"));
            ServerLogger.fileLogMode = Boolean.parseBoolean(serverProps.getProperty("fileLogMode"));
            ServerLogger.consoleLogMode = Boolean.parseBoolean(serverProps.getProperty("consoleLogMode"));
            ServerLogger.logFilePath =  serverProps.getProperty("logFilePath");
        } catch (IOException e) {
        	serverLogger.log("설정파이을 불러올 수 없습니다.");
        }
    }
}
