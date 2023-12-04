package client;

import java.io.IOException;

import java.util.Properties;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class ClientApp {
	// 연결할 서버 Ip
	public static String serverIP;
	// 연결할 서버 Port
	public static int serverPort;
	// 서버로 전송할 파일을 담은 디렉토리
    public static String clientDir;
    // 반복 작업시 얼마만큼 시간을 두고 반복할지 시간 설정
    public static int dirCheckSecond;
    // 한명의 클라이언트가 파일 병렬전송 시 몇개의 파일을 보낼지 설정
    public static int paralleNum;
   	public static ExecutorService threadPool;
   	public static ClientLogger clientLogger;
	

	public static void main(String[] args) {
		
		//프로퍼티스 파일 로딩
		loadProperties();
		clientLogger = new ClientLogger();
		clientLogger.info("클라이언트 설정정보 로딩 완료");
		threadPool = Executors.newFixedThreadPool(ClientApp.paralleNum);
		
		
		//병렬처리 함수 사용하기 위해 객체 생성
		FileChecker fileChecker = new FileChecker();
		
		// dirCheckSecond만큼 시간을 두고 반복
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				fileChecker.fileCheck();
			}
		};
		timer.schedule(timerTask, 0, dirCheckSecond);
	}
	
	public static void loadProperties() {
        Properties clientProps = new Properties();
        try {
            clientProps.load(ClientApp.class.getResourceAsStream("client.properties"));
            serverIP = clientProps.getProperty("serverIP");
            serverPort = Integer.parseInt(clientProps.getProperty("serverPort"));
            clientDir = clientProps.getProperty("clientDir");
            dirCheckSecond = Integer.parseInt(clientProps.getProperty("DirCheckSecond"));
            paralleNum = Integer.parseInt(clientProps.getProperty("paralleNum"));
            ClientLogger.consoleLevel = Level.parse(clientProps.getProperty("consoleLevel"));
            ClientLogger.fileLogMode = Boolean.parseBoolean(clientProps.getProperty("fileLogMode"));
            ClientLogger.consoleLogMode = Boolean.parseBoolean(clientProps.getProperty("consoleLogMode"));
            ClientLogger.logFilePath =  clientProps.getProperty("logFilePath");
        } catch (IOException e) {
        	clientLogger.log("설정정보 파일을 불러오지 못했습니다.");
        }
    }
	
	 
}
