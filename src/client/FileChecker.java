package client;

import java.io.BufferedInputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.json.JSONObject;

public class FileChecker {
	
	public void fileCheck() {
		File clientDir = new File(ClientApp.clientDir);
		File[] fileList = clientDir.listFiles();
		
		if(fileList==null||fileList.length==0) {
			ClientApp.clientLogger.info("설정한 디렉토리가 없거나 디렉토리에 전송할 파일이 없습니다.");
		} else {
			for(File file : fileList) {
				try {
					Socket socket = new Socket(ClientApp.serverIP, ClientApp.serverPort);
					ClientApp.threadPool.submit(()->{
					// 파일명, 파일사이즈 json화
					JSONObject root = new JSONObject();
					root.put("fileName", file.getName());
					root.put("fileSize", file.length());
					String json = root.toString();
					
					DataOutputStream dos = null;
					InputStream is = null;
					BufferedInputStream bis = null;
					DataInputStream dis = null;
					
					try {
						dos = new DataOutputStream(socket.getOutputStream());
						is = new FileInputStream(ClientApp.clientDir+File.separator+file.getName());
						bis = new BufferedInputStream(is);
						dis = new DataInputStream(socket.getInputStream());
						
						//json(파일명, 파일사이즈) 전송
						JasonSender jasonSender = new JasonSender();
						jasonSender.jsonSend(socket, json, file, dos);

  					    //파일 전송
						FileDataSender fileDataSender = new FileDataSender();
						fileDataSender.fileDataSender(file, bis, dos);
						
						//서버로부터 파일 전송 완료 받은 후 파일 삭제
						bis.close(); // 파일 삭제를 위해 Stream close
						FileDeleter fileDeleter = new FileDeleter();
						fileDeleter.fileDelete(dis, file);
						
					} catch (IOException e) {
						Thread thread = new Thread();
						ClientApp.clientLogger.log(thread.toString() + ":" + "(" + file.getName() + ") 전송이 실패했습니다.");
					} finally {
						if(dos!=null) {
							try {
								dos.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						
						if(bis!=null) {
							try {
								bis.close();
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
				}  catch (IOException e) {
					ClientApp.clientLogger.log("서버에 연결할 수 없습니다.");
					break;
				}
			}
		}
	}
}
