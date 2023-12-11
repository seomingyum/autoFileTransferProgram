## java기반 자동파일전송프로그램 입니다.
### scr폴더 아래 client폴더는 파일을 전송하는 클라이언트, server폴더는 파일을 전송받는 서버입니다.
### properties파일을 활용해서 Ip 및 Port(client, server), 최대 파일 전송 갯수(client), 최대 파일 수신 갯수(server)
### 1. client
- client폴더 아래 ClientApp.java를 실행하면 client.properties을 읽어서 연결할 서버IP 및 Port, 전송할 파일을 담은 디렉토리 위치,
  자동으로 디렉토리를 탐색할 시간, 클라이언트 1명당 최대 전송 파일 갯수를 로딩합니다.
- 
