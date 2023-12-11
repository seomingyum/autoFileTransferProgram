## java기반 자동파일전송프로그램 입니다.

### 1.흐름
- scr폴더 아래 client폴더는 파일을 전송하는 클라이언트, server폴더는 파일을 전송받는 서버입니다.
- clinet, server에서 propertis파일을 로딩합니다.
- client에서 server로 파일명, 파일사이즈를 전송합니다.(json)
- server에서 파일명, 파일사이즈를 수신받습니다.
- client에서 설정한 디렉토리안에 있는 파일데이터(byte)를 server로 전송합니다.
- server는 파일데이터(byte)를 설정한 디렉토리에 저장합니다.
- server는 client로부터 전송받은 파일명, 파일사이즈와 설정한 디렉토리에 저장한 파일데이터(byte)의 파일명, 파일사이즈와 비교해서 정합성을 확인합니다.
- server에서 정합성이 보장된다면 정합성이 보장된 파일명을 client로 전송합니다.
- server에서 정합성이 보장되지 않는다면 정합성이 보장되지 않는다는 것("inconsistent")을 client로 전송합니다.
- client에서 server로부터 정합성이 보장된 파일의 파일명을 수신받는다면 설정한 디렉토리에서 해당 파일을 삭제합니다.
- client에서 server로부터 정합성이 보장되지 않는다는 것("inconsistent")을 수신받는다면 해당 파일을 삭제하지 않습니다.(client 프로그램에서 log로 서버에 저장된 파일이 정합성이 보장되지 않는 것을 출력합니다.)


### 2. properties파일 
- properties파일을 활용해서 프로그램을 설정할 수 있습니다.
  - 1)Ip 및 Port(client, server)
  - 2)최대 파일 병렬전송 갯수(client)
  - 3)최대 파일 병렬수신 갯수(server)
  - 4)전송할 파일을 담은 디렉토리(client)
  - 5)수신받을 디렉토리(server)
  - 6)출력할 로그 레벨 기준(client, server)
  - 7)로그 콘솔 출력 모드(client, server)
  - 8)로그 파일 저장 모드(client, server)
  - 9)파일 자동 탐색 시간(client)

### 3. 멀티스레드를 통한 파일 병렬전송 및 수신
- client측은 멀티스레드(스레드풀)를 활용하여 파일을 병렬전송 할 수 있습니다.
- server측도 멀티스레드(스레드풀)를 활용하여 파일을 병렬로 전송 받을 수 있습니다.
