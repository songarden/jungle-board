import React, { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom';
import MyItemList from './MyItemList';
import getAllBoard from '../../apis/getAllBoardApi';
import getUserInfo from '../../apis/getUserInfoApi';
import doLogout from '../../apis/logoutApi';
import {
  Button,
  Card,
  CardHeader,
  CardBody,
  CardFooter,
  Heading,
  Stack,
  StackDivider,
  Box,
  Text,
} from '@chakra-ui/react'

/*
1. localStorage에서 내 memberId 빼오기 
  - /api/users/{memberId} GET 요청 
  - response로 오는 것들: memberId, userId, userName, userEmail, userRoles

2. [MyPage] 유저 정보 표시
  - userName, userEmail

3. [컴포넌트 1: ItemList] 내가 쓴 글 표시
  - 모든 게시물 열람 (getAllBoard) API 쓰기  
  - state 업데이트
  - 목록 걸러내기 const myItems = data.filter((it) => it.boardUser == memberId);
  - [컴포넌트 2-1: Item] home과 동일하게 myItem 화면에 뿌리기
  - [컴포넌트 2-2: ItemUpdate] 이때, 하위 컴포넌트에 다음 기능이 추가되어야 함
    - 게시물 수정 (api 구현 필요)
    - 게시물 삭제 (api 구현 필요)

4. [컴포넌트 2: Comment] 내가 쓴 댓글 표시
  - '특정 유저가 남긴 모든 댓글 열람 API' 활용 (api 구현 필요)
    - 엔드포인트: /api/cmts/member-id?v={memberId}
  - response.data 상태 저장하고 화면에 뿌려주기
  - 이때, 컴포넌트에 다음 기능이 추가되어야 함
    - 댓글 수정 (api 구현 필요)
    - 댓글 삭제 (api 구현 필요)

*/

const MyPage = ({ isLoggedIn, onLoggedOut, onEdit, onDelete, onEditComplete, setBoardData, boardData }) => {
  const navigate = useNavigate();
  const memberId = localStorage.getItem("memberId");
  const [onMyPage, setOnMyPage] = useState(false);

  // 유저 정보 가져오기
  const [userData, setUserData] = useState([]);
  const getThisUserInfo = async () => {
    const response = await getUserInfo(memberId);
    setUserData(response);
  }

  // 해당 유저의 게시글 목록 가져오기
  const [userBoardData, setUserBoardData] = useState([]);
  const getUserBoardData = async () => {
    const response = await getAllBoard();
    if (response) {
      const newBoardData = response.filter((it) => it.boardUser == memberId);
      newBoardData.sort((a, b) => b.boardId - a.boardId);
      setUserBoardData(newBoardData);      
    }
  }

  /* 
  컴포넌트가 마운트 될 때 다음 작업을 수행한다. 
    1. 유저 정보 가져오고 상태에 저장
    2. 해당 유저의 게시글 정보 가져오고 상태에 저장
    3. onMyPage 상태를 true로 바꾸기

  컴포넌트가 언마운트 될 때 다음 작업을 수행한다. 
    1. onMyPage 상태를 false로 바꾸기
    (2. isEdit 상태였다면 편집을 취소한다)
  */
  useEffect(() => {
    getThisUserInfo();
    getUserBoardData();
    setOnMyPage(true);

    return (() => {
      setOnMyPage(false);
    })
  }, []);

  // 로그아웃 버튼 핸들링
  const handleLogout = async () => {
    if (!isLoggedIn) {
      console.log('로그인된 사용자가 아닙니다.');
      return;
    }
    try {
      const response = await doLogout();
      navigate('/', { replace: true });
      onLoggedOut();
      console.log(response);
    } catch (error) {
      console.error("로그아웃 실패 (<Header />): ", error);
    }
  }

  let userName = '비유저';
  let userEmail = '비유저';

  if (userData) {
    userName = userData.userName;
    userEmail = userData.userEmail;
  }

  return (
    <div className='MyPage'>

      <Button onClick={handleLogout}>
        로그아웃
      </Button>

      <div className='user-info'>
        <h2>
          {userName}님, 안녕하세요 🙇🏻
        </h2>
      </div>

      <MyItemList
        itemList={userBoardData}
        onMyPage={onMyPage}
        onEdit={onEdit}
        onDelete={onDelete}
        onEditComplete={onEditComplete}
        setBoardData={setBoardData}
        boardData={boardData}
      />
    </div>
  )
}

export default MyPage;