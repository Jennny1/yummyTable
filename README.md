# 맛집 공유 및 예약 커뮤니티(yummyTable)
위치와 키워드를 활용하여 맛집공유 글쓰기와 댓글 쓰기, 예약하기 기능이 가능한 커뮤니티를 제작하려고 합니다.


## 프로젝트 기능
### 1.회원관리 기능

* 회원가입
  	* 회원 아이디 / 비밀번호 / 위치(지하철역)
* 회원 정보 보기(내정보)
* 회원수정
* 회원탈퇴


### 2.글쓰기 기능
  	* 로그인 상태에서 가능
  	* 비로그인 상태에서는 로그인 exception 발생
* 맛집 추천 글 쓰기
  	* 식당 이름 / 글 비밀번호 / 키워드 / 위치(지하철역) / 수용인원 / 메뉴판(이미지path)
* 게시글 읽기
  	* 식당 이름 / 키워드 / 위치 / 수용인원 / 메뉴판(이미지path) / 좋아요 수 / 예약하기 수 / 글 업로드 시간
* 글 수정하기
  	* 식당 이름 / 글 비밀번호 / 키워드 / 위치 / 수용인원 / 메뉴판(이미지path)
  	* 게시글 수정은 게시글 등록한 회원만 가능
* 글 삭제하기
  	* 게시글 삭제는 게시글 등록한 회원만 가능
* 전체 글 보기
  	* 로그인 상태에서 선호 지역을 가져와서 가까운 순서 / 좋아요 수를 기준으로 정렬
  	* 비로그인 상태에서는 좋아요 수를 기준으로 정렬

### 3.글 검색 기능
  	* 비로그인 상태에서도 가능
* 위치(지하철역)
* 키워드
* 상점이름
  	  
### 4.좋아요(찜하기)기능
  	* 로그인 상태에서 가능
  	* 비로그인 상태에서는 로그인 exception 발생
* 좋아요(찜하기)
  	* 게시글을 정렬할 때 좋아요 숫자가 높은 순서대로 정렬
  	* 좋아요는 게시글 1개당 회원 1명이 1번 누를 수 있다.
* 좋아요(찜하기) 취소
  
### 5.예약하기 기능
  	* 로그인 상태에서 가능
  	* 비로그인 상태에서는 로그인 exception 발생
* 예약하기
  	* 게시글의 수용인원내에서 예약 가능
  	* 예약시 날짜 선택
  	* 예약하기 선택시 예약인원 만큼 플러스 / 수용인원 마이너스
  	* 수용인원이 꽉 찼을 경우 예약할 수 없음
* 예약수정
  	* 예약 날짜 / 인원
  	* 예약 수정은 등록 당사자만 가능하다.
* 예약 전체보기
  	* 상점 기준으로 예약 날짜를 입력하면 리스트 형태로 리턴
* 예약삭제
  	* 예약 삭제는 예약한 당사자만 가능하다
  	* 예약 삭제는 예약한 날짜만 가능하다
  	* 예약 삭제가 발생하면 예약인원 마이너스 / 수용인원 플러스
  	  
### 6.댓글쓰기 기능
* 댓글 쓰기
  	* 로그인 상태에서 가능
  	* 비로그인 상태에서는 로그인 exception 발생
  	* 작성자 아이디 / 댓글 비밀번호 / 작성 시간 / 내용
* 댓글 수정
  	* 댓글 수정은 작성한 당사자만 가능하다.
  	* 댓글 비밀번호를 확인하여 일치하는 경우에만 수정 가능하다.
  	* 댓글 비밀번호 / 업데이트 시간 / 내용
* 댓글 삭제
  	* 댓글 삭제는 작성한 당사자만 가능하다.
  	* 댓글 비밀번호를 확인하여 일치하는 경우에만 삭제 가능하다.
* 댓글 전체보기
  




## ERD
![erd](https://github.com/Jennny1/yummy/assets/59690831/3eb7b3d1-ea9c-49e8-a057-71af8afb4b7e)

## 사용 기술 (Tech Stack)
<div align=center> 
	<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
	<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
	<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 
  	<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
  	<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
</div>
